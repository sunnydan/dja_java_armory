package com.hilla.daniel.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.hilla.daniel.repositories.UserRepo;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	private UserRepo userrepo;

	public WebSecurityConfig(UserRepo userrepo) {
		this.userrepo = userrepo;
	}

	@Bean
	public BCryptPasswordEncoder bCryptPasswordEncoder(UserRepo userrepo) {
		this.userrepo = userrepo;
		return new BCryptPasswordEncoder();
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {

		http.authorizeRequests().antMatchers("/static/**", "/registration").permitAll().antMatchers("/admin/**")
				.access("hasRole('ADMIN')").antMatchers("/superadmin/**").access("hasRole('SUPERADMIN')").anyRequest()
				.authenticated().and().formLogin().loginPage("/login").permitAll().and().logout().permitAll();
	}

	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userrepo).passwordEncoder(bCryptPasswordEncoder(userrepo));
	}
}
