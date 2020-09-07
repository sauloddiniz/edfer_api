package com.edfer.service;

import java.util.List;
import java.util.Optional;

import com.edfer.model.Usuario;

public interface UsuarioService {

	void salvar(Usuario usuario);

	void update(Long idUsuario, Usuario usuario);

	Optional<Usuario> findById(Long idUsuario);
	
	Optional<Usuario> findByEmail(String email);

	void delete(Long idUsuario);

	List<Usuario> findAll();
	
	List<Usuario> findAllByPerfilVendas();
}
