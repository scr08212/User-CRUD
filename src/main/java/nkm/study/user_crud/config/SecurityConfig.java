package nkm.study.user_crud.config;


import nkm.study.user_crud.security.CustomAuthFailureHandler;
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
    private CustomAuthFailureHandler customAuthFailureHandler;
    private CustomUserDetailsService userDetailsService;

    public SecurityConfig(CustomAuthSuccessHandler customAuthSuccessHandler,
                          CustomAuthFailureHandler customAuthFailureHandler,
                          CustomUserDetailsService userDetailsService) {
        this.customAuthSuccessHandler = customAuthSuccessHandler;
        this.customAuthFailureHandler = customAuthFailureHandler;
        this.userDetailsService = userDetailsService;
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http.authorizeHttpRequests((auth) -> auth
                .requestMatchers("/","/home", "/login","login/**","/signup", "/signup/**","/mypage", "/mypage/**")
                .permitAll()
                .anyRequest()
                .authenticated()
        ).formLogin((auth) -> auth
                .loginPage("/login")
                .loginProcessingUrl("/login/process") // 해당 URL로 오는 요청을 하이재킹, 로그인 처리를 진행함!
                .usernameParameter("email")
                .passwordParameter("password")
                .failureHandler(customAuthFailureHandler)
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
