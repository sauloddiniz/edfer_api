//package com.edfer.config.jwt;
//
//import java.io.IOException;
//import java.util.ArrayList;
//import java.util.Date;
//
//import javax.servlet.FilterChain;
//import javax.servlet.ServletException;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.AuthenticationException;
//import org.springframework.security.web.authentication.AuthenticationFailureHandler;
//import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//
//public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
//
//	private JWTUtil jwtUtil;
//	
//	private AuthenticationManager authenticationManager;
//	
//	public JWTAuthenticationFilter(AuthenticationManager authenticationManager, JWTUtil jwtUtil) {
//		setAuthenticationFailureHandler(new JWTAuthenticationFailureHandler());
//		this.authenticationManager = authenticationManager;
//		this.jwtUtil = jwtUtil;
//	}
//
//	@Override
//	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
//			throws AuthenticationException {
//		try {
//			UserSecurityDTO credencias = new ObjectMapper().readValue(request.getInputStream(), UserSecurityDTO.class);
//			UsernamePasswordAuthenticationToken authToken = 
//					new UsernamePasswordAuthenticationToken(credencias.getEmail(), credencias.getSenha(), new ArrayList<>());
//			Authentication auth = authenticationManager.authenticate(authToken);
//			return auth;
//		} catch (IOException e) {
//			throw new RuntimeException(e);
//		}
//	}
//
//	@Override
//	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
//			Authentication authResult) throws IOException, ServletException {
//		String username = ((UserSS) authResult.getPrincipal()).getUsername();
//		String token = jwtUtil.generateToken(username);
//		response.addHeader("Authorization", "Bearer " + token);
//	}
//
//	public class JWTAuthenticationFailureHandler implements AuthenticationFailureHandler {
//
//		@Override
//		public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
//				AuthenticationException exception) throws IOException, ServletException {
//			response.setStatus(401);
//			response.setContentType("application/json");
//			response.getWriter().append(json());
//		}
//
//		private String json() {
//			long date = new Date().getTime();
//			return "{\"timestamp\": " + date + ", " + "\"status\": 401, " + "\"error\": \"Não autorizado\", "
//					+ "\"message\": \"Email ou senha inválidos\", " + "\"path\": \"/login\"}";
//		}
//	}
//
//}
//
