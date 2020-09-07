//package com.edfer.config.jwt;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.stereotype.Service;
//
//import com.edfer.model.Usuario;
//
//@Service
//public class UserSSService {
//
//	@Autowired
//	private BCryptPasswordEncoder bCrypt;
//	
//	@Autowired
//	private UserSSRepository repository;
//	
//	public void salvar(Usuario usuario) {
//		usuario.setSenha(bCrypt.encode(usuario.getSenha()));
//		repository.save(usuario);
//	}
//}
