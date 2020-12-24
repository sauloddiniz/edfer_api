package com.edfer.model.logistica;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.PrePersist;
import javax.persistence.Table;

import com.edfer.model.Categoria;
import com.edfer.model.enuns.EnumAtivo;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@EqualsAndHashCode(of = "idVeiculo")
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "VEICULO")
@ToString
public class Veiculo implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@JsonInclude(Include.NON_NULL)
	private Long idVeiculo;

	@JsonInclude(Include.NON_NULL)
	@Column(unique = true)
	private String placa;

	@JsonInclude(Include.NON_NULL)
	private String modelo;

	@JsonInclude(Include.NON_NULL)
	private String anoModelo;

	@JsonInclude(Include.NON_NULL)
	@Column(unique = true)
	private String chassi;

	@OneToOne
	@JoinColumn(name = "idCategoria")
	@JsonInclude(Include.NON_NULL)
	private Categoria categoria;

	@JsonInclude(Include.NON_NULL)
	private String renavam;

	@OneToOne
	@JsonInclude(Include.NON_NULL)
	@JoinColumn(name = "idFabricante")
	private FabricanteVeiculo fabricante;

	@JsonInclude(Include.NON_NULL)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy", locale = "pt-BR", timezone = "Brazil/East")
	private LocalDate dataCompra;

	@JsonInclude(Include.NON_NULL)
	private double pesoSuportado;

	@JsonInclude(Include.NON_NULL)
	private long hodometro;

	@Enumerated(EnumType.STRING)
	private EnumAtivo ativo;

	@JsonInclude(Include.NON_NULL)
	private int numTotalDeEixos;

	@OneToMany(mappedBy = "veiculo")
	@JsonInclude(Include.NON_NULL)
	@JsonIgnore
	private Set<Manutencao> manutencoes;

	@OneToMany(mappedBy = "veiculo")
	@JsonIgnore
	@JsonInclude(Include.NON_NULL)
	private Set<Abastecimento> abastecimentos;

	@OneToMany(mappedBy = "veiculo")
	@JsonInclude(Include.NON_NULL)
	@JsonIgnore
	private Set<Pneu> pneus;

	@JsonInclude(Include.NON_NULL)
	@OneToMany(mappedBy = "veiculo")
	@JsonIgnore
	private Set<ParteDiaria> parteDiarias;

	public Veiculo(Long idVeiculo, String placa, String modelo) {
		this.idVeiculo = idVeiculo;
		this.placa = placa;
		this.modelo = modelo;
	}

	public Veiculo(int numTotalDeEixos, Categoria categoria) {
		this.numTotalDeEixos = numTotalDeEixos;
		this.categoria = categoria;
	}
	
	public void sumHodometro(Long hodometro) {
		this.hodometro += hodometro;
	}
	
	@PrePersist
	public void setUpperCase() {
		this.placa = this.placa.toUpperCase();
	}
}
