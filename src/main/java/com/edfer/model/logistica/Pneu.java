package com.edfer.model.logistica;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.edfer.model.enuns.EnumAtivo;
import com.edfer.model.enuns.Estado;
import com.edfer.model.enuns.Posicao;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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
@EqualsAndHashCode(of = "idPneu")
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "PNEU")
public class Pneu implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long idPneu;

	@OneToOne
	@JoinColumn(name = "idFabricantePneu")
	@JsonInclude(Include.NON_NULL)
	private FabricantePneu fabricantePneu;

	@JsonInclude(Include.NON_NULL)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy", locale = "pt-BR", timezone = "Brazil/East")
	private LocalDate dataCompra;

	@JsonInclude(Include.NON_NULL)
	private String modelo;

	@JsonInclude(Include.NON_NULL)
	private String numNotaOuNumOrdem;

	@JsonInclude(Include.NON_NULL)
	private String codControleEdfer;

	@JsonInclude(Include.NON_NULL)
	private String numSerie;

	@Enumerated(EnumType.STRING)
	private EnumAtivo ativo;

	private Long km;

	@ManyToOne
	@JoinColumn(name = "idVeiculoAtual")
	@JsonIgnoreProperties({ "categoria", "combustivel", "fabricante", "dataCompra", "pesoSuportado", "ativo",
			"abastecimentos", "numTotalDeEixos", "pneus", "manutencoes" })
	@JsonInclude(Include.NON_NULL)
	private Veiculo veiculo;
	
	@JsonInclude(Include.NON_NULL)
	private int numeroEixo;

	@JsonInclude(Include.NON_NULL)
	@Enumerated(EnumType.STRING)
	private Posicao posicao;

	@OneToMany(mappedBy = "pneu")
	@JsonIgnoreProperties(value = { "pneu", "veiculo" })
	@JsonInclude(Include.NON_NULL)
	private List<HistoricoPneu> historico;

	@OneToMany(mappedBy = "pneu", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JsonIgnoreProperties(value = { "pneu", "veiculo" })
	@JsonInclude(Include.NON_NULL)
	private List<EstadoPneu> estadosPneu;

	@Enumerated(EnumType.STRING)
	private Estado estadoAtual;

	public void addEstadoPneu(EstadoPneu estado) {
		if (this.estadosPneu != null) {
			this.estadosPneu = new ArrayList<>();
		}
		estado.setPneu(this);
		this.estadosPneu.add(estado);
	}

	public void sumKm(Long km) {
		this.km += km;
	}

}
