package com.seethec.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.seethec.api.filter.JwtRequestFilter;
import com.seethec.api.services.MyUserDetailService;


@EnableWebSecurity
public class SecurityConfigurer extends WebSecurityConfigurerAdapter{
	
	@Autowired
	private MyUserDetailService myUserDetailService;
 
	@Autowired
	private JwtRequestFilter mJwtReqFilter;
	
	 @Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(myUserDetailService);
	}

	 @Bean
	public PasswordEncoder passwordEncoder() {
		 /* DO NOT do nay hashing */
		 return NoOpPasswordEncoder.getInstance();
	}
	 
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable()
		    .authorizeRequests().antMatchers("/auth").permitAll()
		    .anyRequest().authenticated()
		    .and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
		
		http.addFilterBefore(mJwtReqFilter, UsernamePasswordAuthenticationFilter.class);
	}
	
	@Override
	protected AuthenticationManager authenticationManager() throws Exception {
		// TODO Auto-generated method stub
		return super.authenticationManager();
	}
	
	@Override
	@Bean
	public AuthenticationManager authenticationManagerBean() throws Exception {
		// TODO Auto-generated method stub
		return super.authenticationManagerBean();
	}
}
