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
				.antMatchers("/login", "/home", "/logout"
				// , "/search", "/analyzeMovie", "/detectIdiom", "/delete"
				)
				.permitAll()
				.anyRequest().authenticated()
				.and().logout()
				// .permitAll()
				// .invalidateHttpSession(true).deleteCookies("JSESSIONID")
				// .logoutSuccessHandler((new
				// HttpStatusReturningLogoutSuccessHandler(HttpStatus.OK)))
				.and().csrf().csrfTokenRepository(repository)

				// /logout is handled by Spring security and OPTIONS request fails to pass CORS
				// check (401). Code below solves this problem.
				// https://stackoverflow.com/a/36512011/8893760
//				.and().exceptionHandling().authenticationEntryPoint(new BasicAuthenticationEntryPoint() {
//					@Override
//					public void commence(final HttpServletRequest request, final HttpServletResponse response,
//							final org.springframework.security.core.AuthenticationException authException)
//							throws IOException, ServletException {
//						if (HttpMethod.OPTIONS.matches(request.getMethod())) {
//							response.setStatus(HttpServletResponse.SC_OK);
//							response.setHeader(HttpHeaders.ACCESS_CONTROL_ALLOW_ORIGIN,
//									request.getHeader(HttpHeaders.ORIGIN));
//							response.setHeader(HttpHeaders.ACCESS_CONTROL_ALLOW_HEADERS,
//									request.getHeader(HttpHeaders.ACCESS_CONTROL_REQUEST_HEADERS));
//							response.setHeader(HttpHeaders.ACCESS_CONTROL_ALLOW_METHODS,
//									request.getHeader(HttpHeaders.ACCESS_CONTROL_REQUEST_METHOD));
//							response.setHeader(HttpHeaders.ACCESS_CONTROL_ALLOW_CREDENTIALS, "true");
//						} else {
//							response.sendError(HttpServletResponse.SC_UNAUTHORIZED, authException.getMessage());
//						}
//					}
//				})
//	         .formLogin()
////	            .loginPage("/login")
//	            .permitAll()
//	            .and()
//	            .logout()
//	            .permitAll()
		;
//		http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.ALWAYS);
	}

	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
//		if (initializationParameter == "create" || initializationParameter == "create-drop") {
//			Customer customer = new Customer("engin");
//			customer.setEmail("envolkan@gmail.com");
//			customer.setEnabled(true);
//			customer.setPassword(passwordEncoder().encode("123qwe"));
//
//			Role admin = new Role("ADMIN");
//			customer.setRoles(new HashSet<Role>());
//			customer.getRoles().add(admin);
//
//			roleRepository.save(admin);
//			customerRepository.save(customer);
//		}

		auth.jdbcAuthentication().dataSource(dataSource)
//		      .withDefaultSchema()
				.usersByUsernameQuery("select username,password,enabled from customer where username=?")
				.authoritiesByUsernameQuery(
						"select customer_username, authority_authority from customer_authority where customer_username=?")
				.passwordEncoder(passwordEncoder())

		// .withUser(User.withUsername("user")
//		        .roles("ADMIN"))
		;
		// auth
//	         .inMemoryAuthentication()
//	         .withUser("user").password("password").roles("USER");
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
		config.addAllowedHeader("*");
		config.addAllowedMethod("*");
		source.registerCorsConfiguration("/**", config);
		return new CorsFilter(source);
	}
}
