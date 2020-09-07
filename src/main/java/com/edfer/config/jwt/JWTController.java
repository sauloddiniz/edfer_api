//package com.edfer.config.jwt;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.AuthenticationException;
//import org.springframework.web.bind.annotation.CrossOrigin;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//@RestController
//@RequestMapping(value = "/oauth")
//@CrossOrigin(origins="*")
//public class JWTController {
//
//	@Autowired
//	private AuthenticationManager authenticationManager;
//
//	@Autowired
//	private JWTUtil util;
//
//	@PostMapping()
//	public ResponseEntity<?> createAuthenticationToken(@RequestBody UserSecurityDTO userSecurityDTO)
//			throws AuthenticationException {
//		
//		try {
//			UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
//					userSecurityDTO.getEmail(), userSecurityDTO.getSenha());
//
//			Authentication authentication = authenticationManager.authenticate(authenticationToken);
//
//			UserSecurityDTO user = new UserSecurityDTO(authentication.getName(), authentication.getAuthorities());
//
//			String token = "Bearer " + util.generateToken(user.getEmail());
//
//			UserDTO dto = new UserDTO(user, token);
//
//			return ResponseEntity.ok(dto);
//
//		} catch (Exception e) {
//			return new ResponseEntity<String>("Unauthorized", HttpStatus.UNAUTHORIZED);
//		}
//
//	}
//
//}
