package hu.peter.frontend.viewmodel;

import hu.peter.frontend.auth.UserPrincipal;
import hu.peter.frontend.dto.UserReservationDto;
import hu.peter.frontend.service.ReservationService;
import hu.peter.frontend.util.PdfGenerator;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.io.output.ByteArrayOutputStream;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.springframework.security.core.context.SecurityContextHolder;
import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.Init;
import org.zkoss.zk.ui.select.annotation.VariableResolver;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zul.Filedownload;
import org.zkoss.zul.Messagebox;

import java.io.ByteArrayInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@VariableResolver(org.zkoss.zkplus.spring.DelegatingVariableResolver.class)
public class ReservationViewModel {

    @WireVariable
    private ReservationService reservationService;

    private List<UserReservationDto> reservations;
    private String currentUsername;

    @Init
    public void init() {
        getData();
    }

    @Command
    public void printTicket(@BindingParam("param") UserReservationDto reservation ) {
        generateBoardingPass(reservation);
    }

    private boolean notPast(UserReservationDto reservation) {
        LocalDate currentDate = LocalDate.now();
        LocalDate flightDate = reservation.getFlightDto().getDate();
        return !currentDate.isAfter(flightDate);
    }

    private void getData() {
        currentUsername = getCurrentUser();
        reservations = reservationService.getReservationByUsername(currentUsername);
        /*filter out past reservations*/
        reservations = reservations.stream().filter( r -> notPast(r) ).collect(Collectors.toList());
    }

    private void generateBoardingPass(UserReservationDto reservation) {
        getData();
        PdfGenerator generator = new PdfGenerator();
        try {
            PDDocument pdf = generator.generateBoardingPass(reservation);
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            pdf.save(byteArrayOutputStream);
            pdf.close();
            InputStream inputStream = new ByteArrayInputStream(byteArrayOutputStream.toByteArray());

            String filename = "boarding_pass_".concat(currentUsername).concat(".pdf");
            Filedownload.save(inputStream, "application/pdf",filename);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String getCurrentUser() {
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
