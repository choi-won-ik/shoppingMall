package com.example.demo.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.csrf.CsrfTokenRequestAttributeHandler;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;

import com.example.demo.handler.LoginFailHandler;
import com.example.demo.handler.LoginSuccessHandler;
import com.example.demo.service.member.MyUserDetailsService;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
	@Autowired
	private MyUserDetailsService myUserDetailsService;
	
	@Bean
	public RequestCache requestCache() {
		return new HttpSessionRequestCache();
	}

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		CsrfTokenRequestAttributeHandler requestHandler = new CsrfTokenRequestAttributeHandler();
		requestHandler.setCsrfRequestAttributeName("_csrf");

		return http.csrf(csrf -> csrf.csrfTokenRequestHandler(requestHandler)
				.csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse()).ignoringRequestMatchers("/header"))
				.authorizeHttpRequests(request -> request
						.requestMatchers("/follow","product/productADD","/kakaoPay","/likey").authenticated()
						.requestMatchers(PathRequest.toStaticResources().atCommonLocations()).permitAll()
						.requestMatchers("join/**").permitAll()
						.anyRequest().permitAll())
				.formLogin(login -> login
						.loginPage("/login/login")
						.loginProcessingUrl("/login/login")
						.usernameParameter("userid")
						.passwordParameter("pw")
						.successHandler(new LoginSuccessHandler())
						.failureHandler(new LoginFailHandler())
						.permitAll()
						)
				.rememberMe(customizer -> customizer
						.rememberMeParameter("remember")
						.tokenValiditySeconds(2678400)
						.userDetailsService(myUserDetailsService)
						.authenticationSuccessHandler(new LoginSuccessHandler()))
				.logout(logout -> logout
						.logoutUrl("/logout")
						.logoutSuccessUrl("/index")
						.deleteCookies("remember")
						.permitAll())
				.addFilterAfter(new CsrfCookieFilter(), BasicAuthenticationFilter.class)
				.httpBasic(Customizer.withDefaults())
				.build();
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
}