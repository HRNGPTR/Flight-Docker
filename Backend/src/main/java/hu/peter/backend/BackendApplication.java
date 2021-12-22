package hu.peter.backend;

import hu.peter.backend.entity.AirplaneType;
import hu.peter.backend.entity.Airport;
import hu.peter.backend.entity.Flight;
import hu.peter.backend.service.AirplaneTypeService;
import hu.peter.backend.service.AirportService;
import hu.peter.backend.service.FlightService;
import org.apache.tomcat.jni.Local;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
public class BackendApplication implements CommandLineRunner {

    @Autowired
    private AirportService airportService;
    @Autowired
    private AirplaneTypeService airplaneTypeService;
    @Autowired
    private FlightService flightService;

    public static void main(String[] args){
        SpringApplication.run(BackendApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        addAirports();
        addPlaneTypes();
        addFlights();
    }

    private void addAirports() {
        List<Airport> airports = airportService.getAllAirport();
        if( airports==null || airports.isEmpty()) {
            List<Airport> ap = new ArrayList<>();
            ap.add( new Airport("Budapest","Magyarország") );
            ap.add( new Airport("Berlin","Németország") );
            ap.add( new Airport("Bukarest", "Románia") );
            ap.add( new Airport("Bécs", "Ausztria") );
            ap.add( new Airport("Bari", "Olaszország") );
            ap.add( new Airport("Róma", "Olaszország") );
            ap.add( new Airport("Amszterdam", "Holland Királyság") );
            ap.add( new Airport("London", "Egyesült Királyság") );
            ap.add( new Airport("Washington D.C.", "Amerikai Egyesült Államok") );
            ap.add( new Airport("Ontario", "Kanada") );
            ap.add( new Airport("Párizs", "Franciaország") );
            ap.add( new Airport("Stockholm", "Svédország") );
            ap.add( new Airport("Prága", "Csehország") );
            ap.add( new Airport("Moszkva", "Orosz Federáció") );
            ap.add( new Airport("Abu-Dabi", "Egyesült Arab Emirségek") );
            ap.add( new Airport("Delhi", "India") );
            ap.add( new Airport("Peking", "Kinai Népköztársaság") );
            ap.add( new Airport("Kairó", "Egyiptom") );
            for(Airport a : ap) {
                airportService.createAirport(a.getCity(),a.getCountry());
            }
        }
    }
    private void addPlaneTypes() {
        List<AirplaneType> airplaneTypes = airplaneTypeService.getAllAirplaneType();
        List<AirplaneType> apt = new ArrayList<>();
        final int spr = 6;
        if(airplaneTypes==null || airplaneTypes.isEmpty()) {
            apt.add(new AirplaneType("Boeing 747",12,spr));
            apt.add(new AirplaneType("Airbus A320",22,spr));
            for(AirplaneType a : apt) {
                airplaneTypeService.createAirplaneType(a.getTypeName(),a.getNumOfRows(),a.getSeatsPerRows());
            }
        }
    }
    private void addFlights() {
        List<Flight> flights = flightService.getAllFlight();
        if(flights==null || flights.isEmpty()) {
            /*-----Budapest -> Berlin-----*/
            flightService.createFlight("Budapest","Berlin", LocalDate.of(2022,1,1)
                    , LocalTime.of(9,10), LocalTime.of(11,20),20_000, "Boeing 747");
            flightService.createFlight("Budapest","Berlin", LocalDate.of(2022,4,13)
                    , LocalTime.of(10,40), LocalTime.of(14,10),25_000, "Boeing 747");
            flightService.createFlight("Budapest","Berlin", LocalDate.of(2022,8,30)
                    , LocalTime.of(20,0), LocalTime.of(22,20),9_900, "Airbus A320");
            flightService.createFlight("Budapest","Berlin", LocalDate.of(2022,11,11)
                    , LocalTime.of(4,0), LocalTime.of(5,55),33_000, "Airbus A320");
            /*-----Berlin -> Budapest-----*/
            flightService.createFlight("Berlin","Budapest",LocalDate.of(2022,1,1),
                    LocalTime.of(12,0), LocalTime.of(14,0),45_000, "Boeing 747");
            flightService.createFlight("Berlin","Budapest",LocalDate.of(2022,4,13),
                    LocalTime.of(15,0), LocalTime.of(17,0),38_500, "Boeing 747");
            flightService.createFlight("Budapest","Berlin", LocalDate.of(2022,8,30)
                    , LocalTime.of(21,0), LocalTime.of(23,20),9_900, "Airbus A320");
            /*-----mixed-----*/
            flightService.createFlight("Budapest","Róma",LocalDate.of(2022,2,1)
                    ,LocalTime.of(16,0),LocalTime.of(17,0),11_000,"Airbus A320");
            flightService.createFlight("Budapest","Bari",LocalDate.of(2022,2,22)
                    ,LocalTime.of(8,30),LocalTime.of(9,45),22_000,"Boeing 747");
            flightService.createFlight("Budapest","London",LocalDate.of(2022,2,11)
                    ,LocalTime.of(14,40),LocalTime.of(16,30),42_000,"Airbus A320");
            flightService.createFlight("Buckarest","Moszkva",LocalDate.of(2022,2,8)
                    ,LocalTime.of(7,0),LocalTime.of(14,0),100_000,"Boeing 747");
            flightService.createFlight("Párizs","Ontario", LocalDate.of(2022,2,4)
                    ,LocalTime.of(7,0),LocalTime.of(16,0),240_000,"Airbus A320");
            flightService.createFlight("Amszterdam","Budapest",LocalDate.of(2022,2,28)
                    ,LocalTime.of(12,0),LocalTime.of(13,55),12_000,"Airbus A320");
            flightService.createFlight("Budapest","Róma", LocalDate.of(2022,2,23)
                    ,LocalTime.of(10,30), LocalTime.of(13,10),23_100, "Boeing 747");
            flightService.createFlight("Abu-Dabi","Delhi", LocalDate.of(2022,1,30)
                    ,LocalTime.of(16,0),LocalTime.of(21,00),400_000,"Airbus A320");
            flightService.createFlight("Abu-Dabi","Peking", LocalDate.of(2022,3,2)
                    ,LocalTime.of(6,0),LocalTime.of(23,0),640_000, "Boeing 747");
            flightService.createFlight("Prága","Kairó",LocalDate.of(2022,3,1)
                    ,LocalTime.of(10,23),LocalTime.of(21,0), 500_000,"Boeing 747");
        }
    }
}
