//package com.edfer.config.jwt;
//
//import java.io.Serializable;
//import java.util.Collection;
//
//import org.springframework.security.core.GrantedAuthority;
//
//import lombok.Getter;
//import lombok.NoArgsConstructor;
//import lombok.Setter;
//import lombok.ToString;
//
//@Getter
//@Setter
//@NoArgsConstructor
//@ToString
//public class UserSecurityDTO implements Serializable {
//	private static final long serialVersionUID = 1L;
//
//	private String email;
//	private String senha;
//	private Collection<? extends GrantedAuthority> authorities;
//
//	public UserSecurityDTO(String email, Collection<? extends GrantedAuthority> collection) {
//		this.email = email;
//		this.authorities = collection;
//	}
//
//}
