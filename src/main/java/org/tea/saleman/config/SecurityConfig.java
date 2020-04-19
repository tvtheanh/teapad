package org.tea.saleman.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.MessageDigestPasswordEncoder;

@SuppressWarnings("deprecation")   // allow MD5
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Autowired
	DataSource dataSource;
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		String QUERY_USER = "SELECT username, password, enabled FROM accounts WHERE username=?";;
		String QUERY_ROLE = "SELECT username, rolename FROM accounts WHERE username=?";
		auth.jdbcAuthentication()
			.dataSource(dataSource)
			.passwordEncoder(new MessageDigestPasswordEncoder("MD5"))
			.usersByUsernameQuery(QUERY_USER)
			.authoritiesByUsernameQuery(QUERY_ROLE);
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		
		http.authorizeRequests()
			.antMatchers("/css/**").permitAll()
			.antMatchers("/js/**").permitAll()
			.antMatchers("/rest/**").hasAnyRole("USER", "ADMIN")
			.anyRequest().authenticated()
			.and().formLogin()
			.loginPage("/login").permitAll()
			.and().logout().permitAll();
		
		
		http.csrf().disable();
		
		http
		   .headers()
		      .frameOptions()
		         .sameOrigin();
	}

}
