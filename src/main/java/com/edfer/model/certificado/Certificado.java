package com.edfer.model.certificado;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.edfer.service.auditable.Auditable;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@ToString
@EqualsAndHashCode(of = "idCertificado", callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "CERTIFICADO")
public class Certificado extends Auditable implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long idCertificado;

	@NotNull(message = "Data de emissão e obrigatório")
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy", locale = "pt-BR", timezone = "Brazil/East")
	private LocalDate dataEmissao;

	@NotBlank(message = "Numero certificado e obrigatório")
	@Column(unique = true)
	private String numero;

	private String numeroNotaFiscal;
	
	private String dimensao;

	private boolean isPdf;

	@NotBlank(message = "Norma / Qualidade e obrigatório")
	private String norma;

	@NotBlank(message = "Fornecedor e obrigatório")
	private String fornecedor;

	@OneToMany(mappedBy = "certificado", cascade = CascadeType.ALL, orphanRemoval = true)
	@JsonIgnoreProperties(value = "certificado")
	private List<ProdutoCertificadoAux> produtoCertificadoAux;

	@ManyToMany
	@JoinTable(name = "CERTIFICADO_PRODUTO", joinColumns = {
			@JoinColumn(name = "CERTIFICADO_ID") }, inverseJoinColumns = { @JoinColumn(name = "PRODUTO_ID") })
	@JsonIgnoreProperties(value = { "certificados", "produtoCertificadoAux" })
	private List<ProdutoCertificado> produtos;

	@PrePersist
	@PreUpdate
	private void toUpperCase() {
		this.fornecedor = this.fornecedor.toUpperCase();
		this.norma = this.norma.toUpperCase();
		if (this.dimensao != null) {
			this.dimensao = this.dimensao.toUpperCase();			
		}
	}

	public void addProduto(ProdutoCertificado produtoCertificado) {
		if (this.produtos == null) {
			this.produtos = new ArrayList<>();
		}
		this.produtos.add(produtoCertificado);
	}
	
}
