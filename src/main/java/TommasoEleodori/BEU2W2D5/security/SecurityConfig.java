package TommasoEleodori.BEU2W2D5.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {
    @Autowired
    private JWTAuthFilter JAFilter;

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
        http.csrf(httpSecurityCsrfConfigurer -> httpSecurityCsrfConfigurer.disable());
        http.formLogin(httpSecurityFormLoginConfigurer -> httpSecurityFormLoginConfigurer.disable());
        http.addFilterBefore(JAFilter, UsernamePasswordAuthenticationFilter.class);
        http.authorizeHttpRequests(request -> request.requestMatchers("/**").permitAll());
        return http.build();
    }
}
