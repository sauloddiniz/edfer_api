//package com.edfer.config.jwt;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.http.HttpMethod;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
//import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
//import org.springframework.security.config.http.SessionCreationPolicy;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.web.cors.CorsConfiguration;
//import org.springframework.web.cors.CorsConfigurationSource;
//import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
//
//@Configuration
//@EnableWebSecurity
//@EnableGlobalMethodSecurity(prePostEnabled=true)
//public class SecurityConfig extends WebSecurityConfigurerAdapter{
//
//	@Autowired
//	private UserDetailsService userDetailsService;
//
//	@Autowired
//	private JWTUtil jwtUtil;
//	
//	private static final String[] PUBLIC_MATCHERS_AUTH = { "/oauth"};
//
//	@Bean
//	BCryptPasswordEncoder encoder() {
//		return new BCryptPasswordEncoder();
//	}
//
//	@Bean
//	CorsConfigurationSource corsConfigurationSource() {
//		final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
//		final CorsConfiguration corsConfiguration = new CorsConfiguration();
//		corsConfiguration.applyPermitDefaultValues().addAllowedMethod(HttpMethod.PUT);
//		corsConfiguration.applyPermitDefaultValues().addAllowedMethod(HttpMethod.DELETE);
//		corsConfiguration.applyPermitDefaultValues().addAllowedMethod(HttpMethod.PATCH);
//		source.registerCorsConfiguration("/**", corsConfiguration);
//		return source;
//	}
//
//	@Override
//	protected void configure(HttpSecurity http) throws Exception {
//		http.cors().and().csrf().disable();
//		http.authorizeRequests().antMatchers(HttpMethod.POST, PUBLIC_MATCHERS_AUTH).permitAll();
//		http.addFilter(new JWTAuthenticationFilter(authenticationManager(), jwtUtil));
//		http.addFilter(new JWTAuthorizationFilter(authenticationManager(), jwtUtil, userDetailsService));
//		http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
//	}
//
//	@Override
//	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//		auth.userDetailsService(userDetailsService).passwordEncoder(encoder());
//	}
//
//	@Bean
//	@Override
//	public AuthenticationManager authenticationManagerBean() throws Exception {
//		return super.authenticationManagerBean();
//	}
//
//}
