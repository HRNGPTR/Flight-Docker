package hu.peter.frontend.auth;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "AUTH_USER_GROUP")
@NoArgsConstructor
@Getter
@Setter
public class AuthGroup {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "AUTH_USER_GROUP_ID")
    private Long id;
    @Column(name = "USERNAME")
    private String username;
    @Column(name = "AUTH_GROUP")
    private String authGroup;
}

