package nkm.study.user_crud.domain.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import nkm.study.user_crud.domain.dto.UserDTO;
import org.springframework.security.crypto.password.PasswordEncoder;

@Entity
@Table(name="user_table")
@Getter
@Setter
@Builder
@AllArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String username;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

    public User() {
    }

    public void update(String username, String email, String password) {
        if(username != null && !username.isBlank()){
            this.username = username;
        }
        if(email != null && !email.isBlank()){
            this.email = email;
        }
        if(password != null && !password.isBlank()){
            this.password = password;
        }
    }

    public static User from(UserDTO userDTO, PasswordEncoder passwordEncoder) {
        return User.builder()
                .username(userDTO.getUsername())
                .email(userDTO.getEmail())
                .password(passwordEncoder.encode(userDTO.getPassword()))
                .role(Role.ROLE_USER)
                .build();
    }
}