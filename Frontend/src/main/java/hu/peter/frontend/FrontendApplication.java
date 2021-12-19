package hu.peter.frontend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@SpringBootApplication
@Controller
public class FrontendApplication {

    public static void main(String[] args) {
        SpringApplication.run(FrontendApplication.class, args);
    }

    /* MVC controller for login-, logout-, createAccount-, and error pages ( Not ideal with mvvm architecture ) */
    @GetMapping(value = "/login")
    public String getLogin() { return "login"; }

    @GetMapping(value = "/logout-success")
    public String getLogout() { return "logout"; }

    @GetMapping(value = "/error")
    public String getError() { return "error"; }

    @GetMapping(value = "/createAccount")
    public String createAccount() { return "createAccount"; }

    @GetMapping(value = "/userReservations")
    public String userReservation() { return "userReservations"; }
}
