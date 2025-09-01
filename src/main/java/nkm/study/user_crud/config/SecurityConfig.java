package nkm.study.user_crud.config;


import nkm.study.user_crud.security.CustomAuthSuccessHandler;
import nkm.study.user_crud.service.CustomUserDetailsService;
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
    private CustomAuthSuccessHandler customAuthSuccessHandler;

    private CustomUserDetailsService userDetailsService;

    public SecurityConfig(CustomAuthSuccessHandler customAuthSuccessHandler,
                          CustomUserDetailsService userDetailsService) {
        this.customAuthSuccessHandler = customAuthSuccessHandler;
        this.userDetailsService = userDetailsService;
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        // 임시. 자세한건 조정해야함.
        http.authorizeHttpRequests((auth) -> auth
                .requestMatchers("/home", "/signup", "/signupProc", "/mypage/**")
                .permitAll()
                .anyRequest()
                .authenticated()
        ).formLogin((auth) -> auth
                .loginPage("/home")
                .loginProcessingUrl("/loginProc") // 해당 URL로 오는 요청을 하이재킹, 로그인 처리를 진행함!
                .usernameParameter("email")
                .passwordParameter("password")
                .successHandler(customAuthSuccessHandler)
        ).logout((auth)->auth
                .logoutUrl("/logout")
                .logoutSuccessUrl("/home")
                .invalidateHttpSession(true)
                .permitAll()
        ).sessionManagement((auth)-> auth
                .maximumSessions(1)
                .maxSessionsPreventsLogin(true)
        ).sessionManagement((session)->session
                .sessionFixation((sessionFixation) -> sessionFixation
                        .newSession())
        );

        return http.build();
    }
}
