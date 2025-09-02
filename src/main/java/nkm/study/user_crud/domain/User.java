package nkm.study.user_crud.domain;

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

    private String username;

    @Column(unique = true)
    private String email;

    private String password;

    private String role;

    public User() {
    }

    public static User from(UserDTO userDTO, PasswordEncoder passwordEncoder) {
        return User.builder()
                .username(userDTO.getUsername())
                .email(userDTO.getEmail())
                .password(passwordEncoder.encode(userDTO.getPassword()))
                .role("ROLE_USER")
                .build();
    }
}