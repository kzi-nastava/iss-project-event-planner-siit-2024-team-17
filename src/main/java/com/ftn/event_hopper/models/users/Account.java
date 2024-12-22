package com.ftn.event_hopper.models.users;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ftn.event_hopper.models.registration.RegistrationRequest;
import jakarta.persistence.*;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.Email;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode

@Entity
@Table(name = "accounts")
public class Account implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false, unique = true)
    @Email(message = "Email must be in a valid format")
    private String email;


    @Column(nullable = false)
    @Size(min = 8, message = "Password must be at least 8 characters long")
    @Pattern(regexp = "^(?=.*[A-Z])(?=.*\\d).*$", message = "Password must include at least one uppercase letter and one number")
    private String password;

    @Column(nullable = false)
    private boolean isVerified;

    @Column(nullable = false)
    private boolean isActive;

    @Column(nullable = true)
    private LocalDateTime suspensionTimestamp;

    @Column(nullable = false)
    private PersonType type;

    @OneToOne(optional = false, fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "person_id", nullable = true)
    private Person person;

    @OneToOne(optional = true, fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "registration_request_id", nullable = true)
    private RegistrationRequest registrationRequest;



    public boolean isValid(){
        return this.isActive && this.isVerified;
    }

    public String getUsername(){return this.email;}


}
