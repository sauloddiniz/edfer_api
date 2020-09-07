package com.edfer.model.logistica;

import java.io.Serializable;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import com.edfer.service.auditable.Auditable;
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
@AllArgsConstructor
@ToString
@NoArgsConstructor
@EqualsAndHashCode(of = "idParteDiaria")
@Table(name = "PARTE_DIARIA")
@Entity
public class ParteDiaria extends Auditable implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long idParteDiaria;

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy", locale = "pt-BR", timezone = "Brazil/East")
	@NotNull(message = "Data da parte diaria não pode ser nulo")
	private LocalDate dataParteDiaria;

	@ManyToOne
	@JsonIgnoreProperties(value = { "categoria", "combustivel", "fabricante", "dataCompra", "pesoSuportado", "ativo",
			"manutencoes", "abastecimentos", "numTotalDeEixos", "pneus", "parteDiarias" })
	@JsonInclude(Include.NON_NULL)
	@JoinColumn(name = "idVeiculo", nullable = false)
	private Veiculo veiculo;

	private String observacao;

	@Min(value=1)
	private Long kmRodado;

	@Valid
	@OneToMany(mappedBy = "parteDiaria", cascade = CascadeType.ALL, orphanRemoval = true)
	@JsonIgnoreProperties(value = "parteDiaria")
	private List<RotaDiaria> rotaDiaria = new ArrayList<>();

	@ManyToMany
	@JoinTable(name = "PARTE_DIARIA_AJUDANTES",
		joinColumns = @JoinColumn(name = "idParteDiaria"),
		inverseJoinColumns = @JoinColumn(name = "idAjudante"))
	private List<Ajudante> ajudantes;

	@OneToMany(mappedBy = "parteDiaria", cascade = CascadeType.ALL, orphanRemoval = true)
	@JsonIgnoreProperties(value = "parteDiaria")
	private List<GastosDiaria> gastos;
	
	@OneToOne
	@JoinColumn(name = "idMotorista")
	private Motorista motorista;

	private Duration hsTotalParteDiaria;

	private Duration hsTotalValida;
	
	private LocalTime hsInicioAlmoco;
	
	private LocalTime hsFimAlmoco;

	public void addRotaDiaria(RotaDiaria rota) {
		if (this.rotaDiaria != null) {
			this.rotaDiaria = new ArrayList<>();
		}

		rota.setParteDiaria(this);
		this.rotaDiaria.add(rota);
	}

	public void removeRotaDiaria(RotaDiaria rotaDiaria) {
		if (rotaDiaria != null) {
			this.rotaDiaria.remove(rotaDiaria);
		}
	}
	
	public void addGastoDiaria(GastosDiaria gasto) {
		if (this.gastos != null) {
			this.gastos = new ArrayList<>();
		}
		gasto.setParteDiaria(this);
		this.gastos.add(gasto);
	}
}
