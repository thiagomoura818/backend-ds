package com.dsbackend.dsback20233004511.security;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.dsbackend.dsback20233004511.services.UsuarioDetailsService;

@Configuration
public class SecurityConfig {

    @Autowired
    private UsuarioDetailsService usuarioDetailsService;

    @Bean
    public JwtAuthenticationFilter jwtAuthenticationFilter() {
        return new JwtAuthenticationFilter();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .cors(cors -> cors.configurationSource(corsConfigurationSource()))  // habilita CORS com configuração customizada
            .csrf(csrf -> csrf.disable())  // desabilita CSRF para API stateless
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/h2-console/**").permitAll()
                .requestMatchers("/v3/api-docs/**", "/swagger-ui/**", "/swagger-ui.html").permitAll()
                .requestMatchers(HttpMethod.POST, "/usuarios").permitAll()
                .requestMatchers(HttpMethod.POST, "/auth/login").permitAll()
                .requestMatchers(HttpMethod.GET, "/usuarios").hasAnyRole("USUARIO")
                .requestMatchers(HttpMethod.GET, "/cliente/listar").hasAnyRole("USUARIO")
                .requestMatchers(HttpMethod.GET, "/cliente/listar/{id}").hasAnyRole("USUARIO")
                .requestMatchers(HttpMethod.GET, "/cliente/listar/token").hasAnyRole("USUARIO")
                .requestMatchers(HttpMethod.GET, "/conta/listar").hasAnyRole("USUARIO")
                .requestMatchers(HttpMethod.GET, "/conta/listar/{id}").hasAnyRole("USUARIO")
                .requestMatchers(HttpMethod.POST, "/conta").hasAnyRole("USUARIO")
                .requestMatchers(HttpMethod.POST, "/conta/listar/cliente").hasAnyRole("USUARIO")
                .requestMatchers(HttpMethod.POST, "/conta/atualizarcredito/{id}").hasAnyRole("USUARIO")
                .requestMatchers(HttpMethod.POST, "/conta/cpix/{id}").hasAnyRole("USUARIO")
                .requestMatchers(HttpMethod.POST, "/conta/saque").hasAnyRole("USUARIO")
                .requestMatchers(HttpMethod.POST, "/conta/deposito").hasAnyRole("USUARIO")
                .requestMatchers(HttpMethod.POST, "/conta/transferencia").hasAnyRole("USUARIO")
                .requestMatchers(HttpMethod.POST, "/conta/pix").hasAnyRole("USUARIO")
                .requestMatchers(HttpMethod.GET, "/lancamento/{id}").hasAnyRole("USUARIO")
                .requestMatchers(HttpMethod.GET, "/lancamento/cliente/{id}").hasAnyRole("USUARIO")
                .requestMatchers(HttpMethod.GET, "/historico/{id}").hasAnyRole("USUARIO")
                .anyRequest().authenticated()
            )
            .headers(headers -> headers.frameOptions().disable()) // para o console H2
            .sessionManagement(sess -> sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList(
            "http://localhost:4200",                            // para testes locais
            "https://frontend-20233004511.netlify.app"          // seu frontend em produção
        ));
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        configuration.setAllowedHeaders(Arrays.asList("*"));
        configuration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }


    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(usuarioDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }
}
