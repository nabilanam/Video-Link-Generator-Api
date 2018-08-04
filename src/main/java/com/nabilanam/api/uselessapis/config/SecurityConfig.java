package com.nabilanam.api.uselessapis.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	private final RestAuthenticationEntryPoint restAuthenticationEntryPoint;

	@Autowired
	public SecurityConfig(RestAuthenticationEntryPoint entryPoint) {
		this.restAuthenticationEntryPoint = entryPoint;
	}

	@Bean
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
				.sessionManagement()
				.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
				.and()
				.csrf()
				.disable()
				.exceptionHandling()
				.authenticationEntryPoint(restAuthenticationEntryPoint)
				.and()
				.authorizeRequests()
				.antMatchers("/**").hasRole("ADMIN")
				.and()
				.httpBasic();
	}
}
