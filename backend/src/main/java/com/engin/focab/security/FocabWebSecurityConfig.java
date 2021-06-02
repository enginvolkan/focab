package com.engin.focab.security;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import com.engin.focab.repository.AuthorityRepository;
import com.engin.focab.repository.CustomerRepository;

@Configuration
@EnableWebSecurity
public class FocabWebSecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private DataSource dataSource;

	@Autowired
	CustomerRepository customerRepository;
	@Autowired
	AuthorityRepository authorityRepository;

	@Value("${spring.jpa.hibernate.ddl-auto}")
	private String initializationParameter;

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		CookieCsrfTokenRepository repository = new CookieCsrfTokenRepository();
		repository.setHeaderName("X-XSRF-TOKEN");
		repository.setCookieHttpOnly(false);
		repository.setCookiePath("/");

		http.cors().and().httpBasic().and().authorizeRequests()
				.antMatchers("/login", "/home")
				.permitAll()
				.anyRequest().authenticated()
				.and().logout()
				.and().csrf().csrfTokenRepository(repository);
	}

	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {

		auth.jdbcAuthentication().dataSource(dataSource)
				.usersByUsernameQuery("select username,password,enabled from customer where username=?")
				.authoritiesByUsernameQuery(
						"select customer_username, authority_authority from customer_authority where customer_username=?")
				.passwordEncoder(passwordEncoder())
		;
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public CorsFilter corsFilter() {
		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		CorsConfiguration config = new CorsConfiguration();
		config.setAllowCredentials(true);
		config.addAllowedOrigin("https://localhost:4200");
		config.addAllowedOrigin("https://vps-zap749787-1.zap-srv.com");
		config.addAllowedHeader("*");
		config.addAllowedMethod("*");
		source.registerCorsConfiguration("/**", config);
		return new CorsFilter(source);
	}
}
