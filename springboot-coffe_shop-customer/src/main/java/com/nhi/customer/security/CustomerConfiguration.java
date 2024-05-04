package com.nhi.customer.security;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;



@EnableWebSecurity
@Configuration
public class CustomerConfiguration {
	@Autowired
	private CustomerServiceSecurity customerServiceSecurity;
	
	@Autowired
	private DataSource dataSource;
	
	@Bean
	SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
		http
		.authorizeHttpRequests(request -> request	// login , register ,forgot-password khỏi cần ghi vì tự động vô được
			.requestMatchers("/resources/**", "/css/**", "/img/**", "/vendor/**", "/js/**", "/scss/**", "/CSS_COFFEE/**", "/Hinh_Coffee/**").permitAll()
			.requestMatchers("/login").permitAll()
			.requestMatchers( "/menu", "/detailCoffee", "/index").permitAll()
			.requestMatchers("/signup", "/signup/sendOtpToEmail", "/signup/confirmRegister").permitAll()
			.requestMatchers("/change-pass", "/change-pass/conductChange").permitAll()
			.requestMatchers("/forgot-password", "/forgot-password/sendOtpToEmail", "/forgot-password/confirmForgetPassword").permitAll()
			.anyRequest().authenticated()
		)
		.formLogin(form -> form
			.loginPage("/login") 
			.loginProcessingUrl("/do-login")
			.defaultSuccessUrl("/index", true)
			.permitAll()
		)
		.rememberMe(remember -> remember.tokenRepository(persistenTokenRepository()))
		.logout(logout -> logout
			.invalidateHttpSession(true)
			.clearAuthentication(true)
			.logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
			.logoutSuccessUrl("/login?logout")
			.permitAll()
		)
		
		;
		
		return http.build();
	}
	
	@Bean
	public PersistentTokenRepository persistenTokenRepository() {
		JdbcTokenRepositoryImpl impl =new JdbcTokenRepositoryImpl();
		impl.setDataSource(this.dataSource);
		return impl;
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return NoOpPasswordEncoder.getInstance();
	}
	
	@Bean
	public BCryptPasswordEncoder getPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	public DaoAuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
		authenticationProvider.setUserDetailsService(this.customerServiceSecurity);
		authenticationProvider.setPasswordEncoder(getPasswordEncoder());
		return authenticationProvider;
	}
}
