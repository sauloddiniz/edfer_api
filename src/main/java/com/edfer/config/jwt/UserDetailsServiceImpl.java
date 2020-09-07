//package com.edfer.config.jwt;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.stereotype.Service;
//
//import com.edfer.model.Usuario;
//
//@Service
//public class UserDetailsServiceImpl implements UserDetailsService {
//
//	@Autowired
//	private UserSSRepository repository;
//
//	@Override
//	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
//		Usuario usuario = repository.findByEmail(email)
//				.orElseThrow(() -> new UsernameNotFoundException("E-mail Não Encontrado"));
//		return new UserSS(usuario.getIdUsuario(), usuario.getEmail(), usuario.getSenha(), usuario.getPerfis(),
//				usuario.isAtivo());
//	}
//
//}