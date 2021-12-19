package hu.peter.frontend.viewmodel;

import hu.peter.frontend.auth.AuthGroup;
import hu.peter.frontend.auth.AuthGroupRepository;
import hu.peter.frontend.auth.User;
import hu.peter.frontend.auth.UserRepository;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.Init;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.select.annotation.VariableResolver;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zul.Messagebox;

@Getter
@Setter
@VariableResolver(org.zkoss.zkplus.spring.DelegatingVariableResolver.class)
public class SecurityViewModel {

    private PasswordEncoder encoder = new BCryptPasswordEncoder(11);

    @WireVariable
    private AuthGroupRepository authGroupRepository;

    @WireVariable
    private UserRepository userRepository;

    private String username;
    private String password;

    @Init
    public void init() {
        username = "";
        password = "";
    }

    @Command
    @NotifyChange({"username","password"})
    public void addUser() {
        if(username.isEmpty() || password.isEmpty() ) {
            Messagebox.show("A Felhasználónév vagy jelszó hiányzik!","Magenta Airline",Messagebox.OK,Messagebox.EXCLAMATION);
            return;
        }

        User existingUser = this.userRepository.findByUsername(username);
        if(null == existingUser ) {
            User user = new User();
            user.setUsername(username);
            user.setPassword(encoder.encode(password) );
            this.userRepository.save(user);

            AuthGroup auth = new AuthGroup();
            auth.setUsername(username);
            auth.setAuthGroup("USER");
            this.authGroupRepository.save(auth);

            System.out.println("USER CREATED: username:" + username + ", password: " + password);
            Messagebox.show("Fiók létrehozva","Magenta Airline",Messagebox.OK,Messagebox.INFORMATION);
        } else {
            Messagebox.show("A felhasználónév már foglalt!","Magenta Airline",Messagebox.OK,Messagebox.EXCLAMATION);
        }

        username = "";
        password = "";
    }

}
