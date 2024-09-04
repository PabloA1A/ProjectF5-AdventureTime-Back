package org.forkingaround.adventuretime.config;

import java.util.Arrays;

import org.forkingaround.adventuretime.facades.encryptations.Base64Encoder;
import org.forkingaround.adventuretime.services.JpaUserDetailsService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@SuppressWarnings("unused")
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Value("${api-endpoint}")
    private String endpoint;

    private final JpaUserDetailsService jpaUserDetailsService;
    private final MyBasicAuthenticationEntryPoint myBasicAuthenticationEntryPoint;

    public SecurityConfig(JpaUserDetailsService jpaUserDetailsService, MyBasicAuthenticationEntryPoint basicEntryPoint) {
        this.jpaUserDetailsService = jpaUserDetailsService;
        this.myBasicAuthenticationEntryPoint = basicEntryPoint;
    }

        @Bean
        public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

                http
                        .cors(cors -> cors.configurationSource(corsConfiguration()))
                        .csrf(csrf -> csrf.disable())
                        .formLogin(form -> form.disable())
                        .logout(out -> out
                                .logoutUrl(endpoint + "/logout")
                                .deleteCookies("ADVENTURER"))
                        .authorizeHttpRequests(auth -> auth
                                //.requestMatchers(AntPathRequestMatcher.antMatcher("/h2-console/**")).permitAll()
                                .requestMatchers(HttpMethod.GET, endpoint + "/login").hasAnyRole("USER","ADMIN")
                                .requestMatchers(HttpMethod.POST, endpoint + "/event").hasRole("ADMIN")
                                .requestMatchers(HttpMethod.PUT, endpoint + "/event").hasRole("ADMIN")
                                .requestMatchers(HttpMethod.DELETE, endpoint + "/event").hasRole("ADMIN")
                                .requestMatchers(HttpMethod.POST, endpoint + "/participant").hasAnyRole("USER","ADMIN")
                                .requestMatchers(HttpMethod.DELETE, endpoint + "/participant/{eventId}/unregister").hasRole("USER")
                                .requestMatchers(HttpMethod.DELETE, endpoint + "/participant").hasRole("ADMIN")
                                .requestMatchers(HttpMethod.GET, endpoint + "/participant/**").hasRole("ADMIN")
                                .requestMatchers(HttpMethod.POST, endpoint + "/register").permitAll()
                                .requestMatchers(HttpMethod.GET, endpoint + "/event/**").permitAll()
        
                                .anyRequest().authenticated())
                        .userDetailsService(jpaUserDetailsService)
                        .httpBasic(basic -> basic.authenticationEntryPoint(myBasicAuthenticationEntryPoint))
                        .sessionManagement(session -> session
                                .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED));

        http.headers(headers -> headers
            .frameOptions(frame -> frame.sameOrigin()));

        return http.build();
    }

    @Bean
    public CorsConfigurationSource corsConfiguration() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowCredentials(true);
        configuration.setAllowedOrigins(Arrays.asList("http://localhost:5173"));
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        configuration.setAllowedHeaders(Arrays.asList("Authorization", "Content-Type"));
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public Base64Encoder base64Encoder() {
        return new Base64Encoder();
    }
}
