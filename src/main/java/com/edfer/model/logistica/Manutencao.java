package com.edfer.model.logistica;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Set;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.Table;

import com.edfer.model.enuns.TipoManutencao;
import com.edfer.model.enuns.TipoNota;
import com.edfer.model.enuns.TipoServico;
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
@NoArgsConstructor
@EqualsAndHashCode
@Entity
@Table(name = "MANUTENCAO")
@ToString
public class Manutencao extends Auditable implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long idManutencao;

	@JsonInclude(Include.NON_NULL)
	@Enumerated(EnumType.STRING)
	private TipoManutencao tipoManutencao;

	@ElementCollection(fetch = FetchType.EAGER, targetClass = TipoServico.class)
	@JoinTable(name = "listaServico", joinColumns = @JoinColumn(name="manutencaoId"))
	@Enumerated(EnumType.STRING)
	private Set<TipoServico> tipoServico;

	@JsonInclude(Include.NON_NULL)
	private BigDecimal valorManutencao;

	@JsonInclude(Include.NON_NULL)
	@Enumerated(EnumType.STRING)
	private TipoNota tipoNota;

	@JsonInclude(Include.NON_NULL)
	private Long hodometroEfetuado;

	private Long validadeHodometro;

	@JsonInclude(Include.NON_NULL)
	private String observacao;

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy", locale = "pt-BR", timezone = "Brazil/East")
	@JsonInclude(Include.NON_NULL)
	private LocalDate dataManutencao;

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy", locale = "pt-BR", timezone = "Brazil/East")
	@JsonInclude(Include.NON_NULL)
	private LocalDate validadeManutencao;
	
	@ManyToOne
	@JsonInclude(Include.NON_NULL)
	@JsonIgnoreProperties(value = { "manutencoes", "modelo", "categoria", "combustivel",
			"fabricante", "dataCompra", "pesoSuportado", "hodometro", "ativo", "abastecimentos", "numTotalDeEixos",
			"pneus" })
	@JoinColumn(name = "idVeiculo")
	private Veiculo veiculo;

	private String fornecedor;

	@JsonInclude(Include.NON_NULL)
	private String numNotaOuNumOrdem;

	@PrePersist
	private void sumValidadeHodomero() {
		if (this.validadeHodometro != null) {
			this.validadeHodometro += this.hodometroEfetuado;
		}
	}
}
