package com.dsbackend.dsback20233004511.security;

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
//Desabilita verificação CSRF para permitir POST com token JWT
				.csrf(csrf -> csrf.disable())
				.authorizeHttpRequests(auth -> auth.requestMatchers("/h2-console/**").permitAll() // Acesso ao H2
																									// Console
						.requestMatchers("/v3/api-docs/**", "/swagger-ui/**", "/swagger-ui.html").permitAll() // Acesso
																												// ao
																												// Swagger
																												// UI
						.requestMatchers(HttpMethod.POST, "/usuarios").permitAll() // Permitir criação de usuário
						.requestMatchers(HttpMethod.POST, "/auth/login").permitAll() // Permitir endpoint de login
						.requestMatchers(HttpMethod.GET, "/usuarios").hasAnyRole("USUARIO")
						.requestMatchers(HttpMethod.GET, "/cliente/listar").hasAnyRole("USUARIO") // Regras de Autorização para
																							// Clientes
						.requestMatchers(HttpMethod.GET, "/cliente/listar/{id}").hasAnyRole("USUARIO")
						.requestMatchers(HttpMethod.GET, "/conta/listar").hasAnyRole("USUARIO")
						.requestMatchers(HttpMethod.GET, "/conta/listar/{id}").hasAnyRole("USUARIO")
						.requestMatchers(HttpMethod.POST, "/conta").hasAnyRole("USUARIO")
						.requestMatchers(HttpMethod.POST, "/conta/atualizarcredito/{id}").hasAnyRole("USUARIO")
						.requestMatchers(HttpMethod.POST, "/conta/cpix/{id}").hasAnyRole("USUARIO")
						.requestMatchers(HttpMethod.POST, "/conta/saque").hasAnyRole("USUARIO")
						.requestMatchers(HttpMethod.POST, "/conta/deposito").hasAnyRole("USUARIO")
						.requestMatchers(HttpMethod.POST, "/conta/transferencia").hasAnyRole("USUARIO")
						.requestMatchers(HttpMethod.POST, "/conta/pix").hasAnyRole("USUARIO")
						.requestMatchers(HttpMethod.GET, "/lancamento/{id}").hasAnyRole("USUARIO")
						.requestMatchers(HttpMethod.GET, "/lancamento/cliente/{id}").hasAnyRole("USUARIO")
						.anyRequest()
						.authenticated() // Todos os outros endpoints exigem autenticação
				).headers(headers -> headers.frameOptions().disable()) // Para H2 Console
				.sessionManagement(sess -> sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
				.addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);
		return http.build();
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