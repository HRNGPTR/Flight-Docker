package hu.peter.frontend.viewmodel;

import hu.peter.frontend.auth.UserPrincipal;
import hu.peter.frontend.dto.UserReservationDto;
import hu.peter.frontend.service.ReservationService;
import hu.peter.frontend.util.PdfGenerator;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.context.SecurityContextHolder;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.Init;
import org.zkoss.zk.ui.select.annotation.VariableResolver;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zul.Filedownload;
import org.zkoss.zul.Messagebox;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

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
        currentUsername = getCurrentUser();
        reservations = reservationService.getReservationByUsername(currentUsername);
    }

    @Command
    public void printTicket() {
//       generateBoardingPass();
       downloadBoardingPass(currentUsername); //TODO: not working
    }

    private void generateBoardingPass() {
        PdfGenerator generator = new PdfGenerator();
        for (UserReservationDto r : reservations) {
            try {
                generator.generateBoardingPass(r);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        try {
            generator.generatePdf();
        } catch (IOException e) { e.printStackTrace(); }
    }
    //TODO: not working
    private void downloadBoardingPass(String username) {
        try {
//            Filedownload.save("~./pdf/"+username+".pdf", null);
            Filedownload.save("~./zul/fpmoles.pdf",null);
        } catch (FileNotFoundException e) {
            Messagebox.show("Hiba a fájl letöltése során!","Magenta Airline",Messagebox.OK,Messagebox.ERROR);
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
