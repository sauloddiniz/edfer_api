package com.edfer.model;

import java.io.Serializable;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import com.edfer.model.agenda.Agenda;
import com.edfer.model.enuns.EnumPerfil;
import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "USUARIO")
@EqualsAndHashCode(of = "idUsuario")
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Usuario implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Getter
	@Setter
	private Long idUsuario;

	@NotBlank(message = "Usuário é obrigátorio")
	@Getter
	@Setter
	@Column(unique = true)
	private String usuario;

	@NotBlank(message = "Nome é obrigátorio")
	@Getter
	@Setter
	private String nome;

	@Column(name = "senha")
	private String senha;

	@Column(name = "ativo")
	@Getter
	@Setter
	private boolean ativo;

	@NotBlank
	@Getter
	@Setter
	@Column(unique = true)
	@Email(message = "Não e um E-mail valido")
	private String email;

	@ElementCollection(fetch = FetchType.EAGER)
	@CollectionTable(name = "perfis", joinColumns = @JoinColumn(name = "USUARIO_ID"))
	@Setter
	private Set<Integer> perfil = new HashSet<Integer>();

	@OneToMany(mappedBy = "usuario")
	@JsonIgnore
	private List<Agenda> agenda;

	public Set<EnumPerfil> getPerfis() {
		return perfil.stream().map(x -> EnumPerfil.toEnumCod(x)).collect(Collectors.toSet());
	}

	public Usuario(Long idUsuario, String usuario, String nome, boolean ativo, String email, Set<Integer> perfil) {
		this.idUsuario = idUsuario;
		this.usuario = usuario;
		this.nome = nome;
		this.ativo = ativo;
		this.email = email;
		this.perfil = perfil;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public Usuario(String usuario) {
		this.usuario = usuario;
	}

}
