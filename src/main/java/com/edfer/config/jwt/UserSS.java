//package com.edfer.config.jwt;
//
//import java.util.Collection;
//import java.util.Set;
//import java.util.stream.Collectors;
//
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.core.authority.SimpleGrantedAuthority;
//import org.springframework.security.core.userdetails.UserDetails;
//
//import com.edfer.model.enuns.EnumPerfil;
//
//import lombok.Setter;
//import lombok.ToString;
//
//@ToString
//@Setter
//public class UserSS implements UserDetails{
//
//	private static final long serialVersionUID = 1L;
//	private Long id;
//	private String email;
//	private String senha;
//	private Collection<? extends GrantedAuthority> authorities;
//	private boolean ativo;
//	
//	public UserSS(Long id, String email, String senha, Set<EnumPerfil> perfis, boolean ativo) {
//		this.id = id;
//		this.email = email;
//		this.senha = senha;
//		this.authorities = perfis.stream().map(role -> new SimpleGrantedAuthority(role.getDescrição())).collect(Collectors.toList());
//		this.ativo = ativo;
//	}
//
//	@Override
//	public String getPassword() {
//		return this.senha;
//	}
//
//	@Override
//	public String getUsername() {
//		return this.email;
//	}
//
//	@Override
//	public boolean isAccountNonExpired() {
//		return true;
//	}
//
//	@Override
//	public boolean isAccountNonLocked() {
//		return true;
//	}
//
//	@Override
//	public boolean isCredentialsNonExpired() {
//		return true;
//	}
//
//	@Override
//	public boolean isEnabled() {
//		return this.ativo;
//	}
//
//	@Override
//	public Collection<? extends GrantedAuthority> getAuthorities() {
//		return this.authorities;
//	}
//}
