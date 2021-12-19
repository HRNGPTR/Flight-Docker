package hu.peter.frontend.viewmodel;

import hu.peter.frontend.auth.UserPrincipal;
import hu.peter.frontend.dto.*;
import hu.peter.frontend.service.AirportService;
import hu.peter.frontend.service.FlightService;
import hu.peter.frontend.service.ReservationService;
import hu.peter.frontend.util.PdfGenerator;
import hu.peter.frontend.util.SeatRepresentation;
import hu.peter.frontend.util.SeatRow;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.context.SecurityContextHolder;
import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.Init;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.VariableResolver;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zul.Filedownload;
import org.zkoss.zul.Messagebox;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.LocalDate;
import java.util.*;

@Getter
@Setter
@VariableResolver(org.zkoss.zkplus.spring.DelegatingVariableResolver.class)
public class ViewModel extends SelectorComposer<Component> {

    @WireVariable
    private AirportService airportService;

    @WireVariable
    private FlightService flightService;

    @WireVariable
    private ReservationService reservationService;

    private String view; //current *.zul view
    private Map<String,String> views; //all *.zul views

    /*-----security-----*/
    private String currentUsername;

    /*-----search for flights-----*/
    private List<AirportDto> airports;
    private String from;
    private String to;
    private String way; //not necessary
    private Date leaveDate;
    private Date returnDate;  //not necessary
    private int passengerNum;

    /*-----lists the available flights-----*/
    private List<FlightDto> flights; //available flights
    private FlightDto flight; //selected flight
    private List<SeatRepresentation> seatRepresentations; //seats of the selected flight

    /*-----select seat-----*/
    private List<SeatRow> seatRows; //to represent a row of seats in the zkoss list
    private int seatNum; //index of the selected seat
    private boolean seatSelected;
    private String firstName;
    private String secondName;
    private String passport;
    private PassengerDto.Sex sex;
    private List<PassengerDto> passengers;

    /*user's reservations*/
    private List<UserReservationDto> userReservations;

    @Init
    public void init() {
        views = new HashMap<>();
        views.put("open","~./zul/openingPage.zul");
        views.put("list","~./zul/listFlight.zul");
        views.put("seat","~./zul/planeShema.zul");
        views.put("pay","~./zul/pay.zul");
        views.put("reservations","~./zul/userReservations.zul");
        view = views.get("open");



        //TMP
        way = "ONE_WAY";
        seatSelected = false;
        passengers = new ArrayList<>();
        currentUsername = getCurrentUser();
        userReservations = reservationService.getReservationByUsername("fpmoles");
    }



    /*-----OPENING-PAGE-----*/

    /**
     *<p>A prefix-el kezdodo varosokat adja vissza. A aratok keresesenel van hasznalva</p>
     * @param prefix
     * @return Nothing
     */
    @Command
    @NotifyChange({"airports"})
    public void searchAirport(@BindingParam("prefix") String prefix) {
        airports = airportService.getAirportByPrefix(prefix);
    }

    /**
     * <p>A megadott parametereknek megfelelo jaratokat adja meg. Ha van talalat, akkor tovabblep a kovetkezo oldalra</p>
     * @return Nothing
     */
    @Command
    @NotifyChange("view")
    public void search() {
        if( from==null||to==null||leaveDate==null||passengerNum==0 ) {
            System.err.println("EMPTY");
            Messagebox.show("Kevés adatot adott meg!","Magenta Airline",Messagebox.OK,Messagebox.EXCLAMATION);
            return;
        }

        loadFlights();

        if(flights.isEmpty()) {
            Messagebox.show("A megadott időpontban a megadott városok között nincsen járat!","Magenta Airline",Messagebox.OK,Messagebox.EXCLAMATION);
        } else {
            view = views.get("list");
        }
    }

    /**
     * <p>Segédfüggvény a search-hoz. Visszaadja a járatokat.</p>
     * @return Nothing
     */
    private void loadFlights() {
        LocalDate lDate = convertToLocalDateViaSqlDate(leaveDate);
        flights = flightService.getFlightOnDate(from,to,lDate);
    }

    /*----LIST-FLIGHT-----*/

    /**
     * <p>A kiválasztott járatot az id-je alapján lekéri, majd továbblép a helyfoglalásoz</p>
     * @param id
     * @return Nothing
     */
    @Command
    @NotifyChange({"view","seatRepresentation"})
    public void reservation(@BindingParam("id") Long id) {
        flight = flightService.getFlightById(id);
        seatsToRepresentation(flight);
        view = views.get("seat");
    }

    /**
     * <p>A járatoz tartozó ülésekhez reprezentációt készit.</p>
     * @param flight
     * @return Nothing
     */
    private void seatsToRepresentation(FlightDto flight) {
        seatRows = new ArrayList<>();
        List<SeatDto> seatDtos = flight.getAirplane().getSeats();
        List<SeatRepresentation> seats = new ArrayList<>();
        int counter = 1;
        for(int i = 0; i < seatDtos.size(); i++) {
            SeatRepresentation.Status stat = (seatDtos.get(i).getPassenger()==null)?SeatRepresentation.Status.FREE: SeatRepresentation.Status.RESERVED;
            seats.add( new SeatRepresentation(i,stat) );
            if( (i+1) % 6 == 0 ) {
                SeatRow sr = new SeatRow(counter,seats);
                counter+=1;
                seatRows.add(sr);
                seats = new ArrayList<>();
            }
        }
    }

    /*-----PLANE-SHEMA-----*/

    /**
     * <p>A helyfoglalásnál, ha egy ülésre kattintunk és még nem választottunk ülést és az ülés szabad, akkor mejegyzi</p>
     * @param id
     * @return Nothing
     */
    @Command
    @NotifyChange("seatRows")
    public void clickOnSeat(@BindingParam("id") int id) {
        int row = id / 6;
        int col = id % 6;
        SeatRepresentation seat = seatRows.get(row).getRow().get(col);
        if( !seatSelected &&  seat.getStatus() == SeatRepresentation.Status.FREE ) {
            seatSelected = true;
            seatNum = id;
            seat.setStatus(SeatRepresentation.Status.SELECTED);
        }
    }

    /**
     * <p>Ha választottunk helyet és megadtuk az utas adatait, akkor le tudjuk foglalni a helyet.</p>
     * @return Nothing
     */
    @Command
    @NotifyChange({"view","passengers","firstName","secondName","sex","passport","seatRows"})
    public void createSeatReservation() {

        if(firstName==null||secondName==null||sex==null||passport==null) {
            Messagebox.show("Az utas adatai hiányosak!","Magenta Airline",Messagebox.OK,Messagebox.EXCLAMATION);
            return;
        }

        if(seatSelected) {
            PassengerDto passengerDto = new PassengerDto(-1l, firstName, secondName, sex, passport);
            long id = flight.getAirplane().getSeats().get(seatNum).getId();
            SeatDto seat = new SeatDto(id, -1, passengerDto);
            ReservationDto reservation = new ReservationDto(currentUsername,flight.getId(), seat);

//            boolean success = flightService.reserveSeat(reservation); //TODO
            boolean success = true;
            resetFields();

            /*if seat is already reserved*/
            if(!success) {
                resetSeats();
                Messagebox.show("A kiválasztott helyet már lefoglalták","Magenta Airline",Messagebox.OK,Messagebox.EXCLAMATION);
            } else {
                passengers.add(passengerDto);
                passengerNum -= 1;
            }

            /*if all seats successfully reserved*/
            if(passengerNum==0) {
                Messagebox.show("Hely(ek) lefoglalva!","Magenta Airline",Messagebox.OK,Messagebox.INFORMATION);
                userReservations = reservationService.getReservationByUsername(currentUsername); //TODO
//                view = views.get("reservations");
                passengers.clear();
            }
        } else {
            Messagebox.show("Még nem választott ülőhelyet!","Magenta Airline",Messagebox.OK,Messagebox.EXCLAMATION);
        }
    }

    private void resetFields() {
        firstName = "";
        secondName = "";
        sex = null;
        passport = "";
        seatSelected = false;
    }

    private void resetSeats() {
        seatNum = -1;
        for(SeatRow srw : seatRows) {
            for(SeatRepresentation sr : srw.getRow()) {
                if(sr.getStatus() == SeatRepresentation.Status.SELECTED)
                    sr.setStatus(SeatRepresentation.Status.FREE);
            }
        }
    }

    /*-----USER-RESERVATION-----*/

    /**
     * <p>Jegy generálása és letöltése</p>
     * @return Nothing
     */
    @Command
    public void printTicket() {
        generateBoardingPass(Arrays.asList(userReservations.get(0)));
        downloadBoardingPass(currentUsername);  //not working
    }

    /**
     * <p>Legenerálja a beszállókértyát pdf-formátumban, a kapott ReservationDto lista alapján.</p>
     * @param reservations
     * @return Nothing
     */
    private void generateBoardingPass(List<UserReservationDto> reservations) {
        PdfGenerator generator = new PdfGenerator();
        for (UserReservationDto r : reservations) {
            try {
                generator.generateBoardingPass(r);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * <p>Letölti a legenerált jegyet. EGYENLŐRE NEM MÜKÖDIK!</p>
     * @param username
     * @return Nothing
     */
    //TODO: not working
    private void downloadBoardingPass(String username) {
        try {
            Filedownload.save("~./pdf/"+username+".pdf", null);
//            Filedownload.save("~./pdf/fpmoles.pdf",null);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    /*-----UTILS-----*/

    /**
     * <p>A paraméterül kapott Date tipusú időpontot átkonvertálja LocalDate tipusúra.</p>
     * @param dateToConvert
     * @return date converted to LocalDate
     */
    public LocalDate convertToLocalDateViaSqlDate(Date dateToConvert) {
        return new java.sql.Date(dateToConvert.getTime()).toLocalDate();
    }

    /**
     * <p>Elkéri a SecurityContextHolder-t és megmondja, hogy aktuálisan ki van bejelentkezve.</p>
     * @return current user
     */
    public String getCurrentUser() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username;
        if (principal instanceof UserPrincipal) {
            username = ((UserPrincipal)principal).getUsername();
        } else {
            username = principal.toString();
        }
        return username;
    }
}
