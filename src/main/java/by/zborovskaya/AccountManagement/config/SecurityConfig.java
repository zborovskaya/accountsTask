package by.zborovskaya.AccountManagement.config;

import by.zborovskaya.AccountManagement.services.PersonDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final PersonDetailsService personDetailsService;

    @Autowired
    public SecurityConfig(PersonDetailsService personDetailsService) {
        this.personDetailsService = personDetailsService;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable()) // Настройка CSRF
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/accounts/admin/**").hasRole("ADMIN")
                        .requestMatchers("/accounts/user/**").hasRole("USER")
                        .requestMatchers("/login", "/main").permitAll() // Доступ разрешен всем
                        .anyRequest().hasAnyRole("ADMIN", "USER")// Все остальные запросы требуют аутентификации
                )
                .formLogin(formLogin -> formLogin
                        .loginPage("/login") // Указываем свою страницу логина
                        .loginProcessingUrl("/process_login")
                        .defaultSuccessUrl("/main")
                        .failureUrl("/login?error")
                )
                .logout(logoutConfiguration -> logoutConfiguration
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/login"));
        return http.build();
    }


    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
        AuthenticationManagerBuilder authBuilder = http.getSharedObject(AuthenticationManagerBuilder.class);
        authBuilder.userDetailsService(personDetailsService);
        return authBuilder.build();
    }

    @Bean
    public PasswordEncoder getPasswordEncoder() {
        return NoOpPasswordEncoder.getInstance();
    }
}
