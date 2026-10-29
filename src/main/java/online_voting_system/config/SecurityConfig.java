package online_voting_system.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableMethodSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http)
            throws Exception {

        http
            .csrf(csrf -> csrf.disable())

            .authorizeHttpRequests(auth -> auth

                .requestMatchers(
                    "/",
                    "/index.html",
                    "/register.html",
                    "/login.html",
                    "/style.css",
                    "/script.js",
                    "/css/**",
                    "/js/**",
                    "/images/**",
                    "/api/auth/**",
                    "/api/users/register"
                ).permitAll()

                .requestMatchers(
                    "/admin.html",
                    "/manage-elections.html",
                    "/manage-candidates.html",
                    "/manage-users.html"
                ).hasRole("ADMIN")

                .requestMatchers(
                    "/elections.html",
                    "/candidates.html",
                    "/vote.html"
                ).authenticated()

                .requestMatchers(
                    "/api/users/**"
                ).hasRole("ADMIN")

                .requestMatchers(
                    "/api/elections/**",
                    "/api/candidates/**",
                    "/api/results/**"
                ).authenticated()

                .requestMatchers(
                    "/api/votes/**"
                ).hasRole("VOTER")

                .anyRequest().authenticated()
            )

            .exceptionHandling(exception -> exception
                .accessDeniedHandler((request, response, accessDeniedException) -> {

                    response.setStatus(403);
                    response.setContentType("application/json");

                    response.getWriter().write(
                        "{\"message\":\"Access Denied\"}"
                    );
                })
            );

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(
            AuthenticationConfiguration configuration)
            throws Exception {

        return configuration.getAuthenticationManager();
    }
}