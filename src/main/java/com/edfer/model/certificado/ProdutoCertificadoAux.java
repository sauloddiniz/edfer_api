package com.edfer.model.certificado;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@EqualsAndHashCode(of = "idProdutoCertificadoAux")
@ToString
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "PRODUTO_CERTIFICADO_AUX")
public class ProdutoCertificadoAux implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long idProdutoCertificadoAux;

	private String corrida;

	private Double peso;

	private String volume;

	@ManyToOne
	@JoinColumn(name = "idProduto")
	@JsonIgnoreProperties(value= {"certificados","produtoCertificadoAux", "produtoCertificadoAux"})
	private ProdutoCertificado produto;

	@ManyToOne
	@JoinColumn(name = "idCertificado")
	@JsonIgnoreProperties(value= {"produtoCertificadoAux","produtos", "norma"})
	private Certificado certificado;

	public void addCertificado(Certificado certificado) {
		this.certificado = certificado;
	}
}
