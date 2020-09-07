package com.edfer.model.certificado;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@ToString
@Getter
@Setter
@EqualsAndHashCode(of = "idProdutoCertificado")
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "PRODUTO_CERTIFICADO")
public class ProdutoCertificado implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long idProdutoCertificado;

	@NotBlank(message="Codigo deve conter valor")
	@JsonInclude(Include.NON_NULL)
	@Column(unique=true)
	private String codigo;

	@NotBlank(message="Descrição deve conter valor")
	@JsonInclude(Include.NON_NULL)
	private String descricao;

	@NotBlank(message="Classe deve conter valor")
	@JsonInclude(Include.NON_NULL)
	private String classe;

	@ManyToMany(mappedBy = "produtos")
	@JsonIgnore
	private List<Certificado> certificados;

	@OneToMany(mappedBy="produto")
	@JsonIgnoreProperties(value= {"produto","certificado"})
	private List<ProdutoCertificadoAux> produtoCertificadoAux;

	@PrePersist
	@PreUpdate
	private void toUpper() {
		this.descricao = this.descricao.toUpperCase();
		this.classe = this.classe.toUpperCase();
	}

	public ProdutoCertificado(Long idProdutoCertificado, String codigo, String descricao, String classe) {
		this.idProdutoCertificado = idProdutoCertificado;
		this.codigo = codigo;
		this.descricao = descricao;
		this.classe = classe;
	}

	public void addCertificado(Certificado certificado) {
		if (this.certificados == null) {
			this.certificados = new ArrayList<>();
		}
		this.certificados.add(certificado);
	}
}
