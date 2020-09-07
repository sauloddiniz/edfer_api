package com.edfer.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.edfer.model.Usuario;
import com.edfer.repository.UsuarioRepository;
import com.edfer.service.UsuarioService;

@Service
@Transactional
public class UsuarioServiceImpl implements UsuarioService{

	@Autowired
	private UsuarioRepository repository;

	@Override
	public void salvar(Usuario usuario) {
		BCryptPasswordEncoder bCrypt = new BCryptPasswordEncoder();
		usuario.setSenha(bCrypt.encode(usuario.getSenha()));
		repository.save(usuario);
	}

	@Override
	public void update(Long idUsuario, Usuario usuario) {
		BCryptPasswordEncoder bCrypt = new BCryptPasswordEncoder();
		usuario.setSenha(bCrypt.encode(usuario.getSenha()));
		usuario.setIdUsuario(idUsuario);
		repository.save(usuario);
	}

	@Override
	public void delete(Long idUsuario) {
		repository.deleteById(idUsuario);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Usuario> findAll() {
		return repository.findAll();
	}

	@Override
	@Transactional(readOnly = true)
	public Optional<Usuario> findByEmail(String email) {
		return repository.findByEmail(email);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Usuario> findAllByPerfilVendas() {
		return repository.findByPerfilAndAtivo(1, true);
	}

	@Override
	@Transactional(readOnly = true)
	public Optional<Usuario> findById(Long idUsuario) {
		return repository.findById(idUsuario);
	}

}
