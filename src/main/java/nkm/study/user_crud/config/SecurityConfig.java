package nkm.study.user_crud.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        // 임시. 자세한건 조정해야함.
        http.authorizeHttpRequests((auth) -> auth
                .requestMatchers("/login").permitAll()
                .requestMatchers("/").permitAll()
                .requestMatchers("/admin").hasRole("ADMIN")
                .requestMatchers("/mypage/**").hasAnyRole("ADMIN", "USER")
                .anyRequest().authenticated()
        );


        return http.build();
    }
}
