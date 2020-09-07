package com.edfer.model.logistica;

import java.io.Serializable;
import java.time.Duration;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;

import com.edfer.model.enuns.TipoLocalidade;
import com.edfer.model.enuns.TipoViagem;
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
@EqualsAndHashCode(of = "idRotaDiaria")
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "ROTA_DIARIA")
@Entity
public class RotaDiaria implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long idRotaDiaria;

	@NotEmpty(message = "Nome Cliente não pode ser vazio")
	private String nomeClienteOuFornecedor;

	@Enumerated(EnumType.STRING)
	private TipoLocalidade tipoClienteFornecedor;

	@ElementCollection
	@CollectionTable(name = "NOTAS_ROTA", joinColumns = @JoinColumn(name = "ROTA_ID"))
	@Column(name = "NOTA")
	private List<String> notas = new ArrayList<String>();

	@Enumerated(EnumType.STRING)
	private TipoViagem tipoViagem;

	@JsonInclude(Include.NON_NULL)
	private LocalTime hsInicioCargaDescargaEdfer;

	@JsonInclude(Include.NON_NULL)
	private LocalTime hsFinalCargaDescargaEdfer;

	@JsonInclude(Include.NON_NULL)
	private LocalTime hsSaidaParaClienteOuFornecedor;

	@JsonInclude(Include.NON_NULL)
	private LocalTime hsChegadaEmClienteOuFornecedor;

	@JsonInclude(Include.NON_NULL)
	private LocalTime hsInicioCargaDescargaClienteOuFornecedor;

	@JsonInclude(Include.NON_NULL)
	private LocalTime hsFinalCargaDescargaClienteOuFornecedor;

	@JsonInclude(Include.NON_NULL)
	private LocalTime hsSaidaDoClienteOuFornecedor;

	@JsonInclude(Include.NON_NULL)
	private LocalTime hsChegada;

	@JsonInclude(Include.NON_NULL)
	private Long kmInicial;

	@JsonInclude(Include.NON_NULL)
	private Long kmFinal;

	@JsonInclude(Include.NON_NULL)
	@ManyToOne
	@JoinColumn(name = "idParteDiaria")
	@JsonIgnoreProperties(value = { "rotaDiaria" })
	private ParteDiaria parteDiaria;

	@JsonInclude(Include.NON_NULL)
	private Duration hsTotalCargaDescgaEdfer;

	private Duration hsTotalItinerarioClienteFornecedor;

	private Duration hsTotalCargaDesClienteFornecedor;

	private Duration hsTotalItinerarioEdfer;

	private Duration hsTotalDisposicaoCliente;

	@PrePersist
	@PreUpdate
	private void uperCaseName() {
		this.nomeClienteOuFornecedor = this.nomeClienteOuFornecedor.toUpperCase();
	}

	public Duration cargaDescargaEdfer() {
		Duration duration = Duration.ZERO;
		if (this.hsInicioCargaDescargaEdfer != null && this.hsFinalCargaDescargaEdfer != null) {
			if (this.hsInicioCargaDescargaEdfer.isBefore(this.hsFinalCargaDescargaEdfer)) {
				duration = Duration.between(this.hsInicioCargaDescargaEdfer, this.hsFinalCargaDescargaEdfer);
				this.hsTotalCargaDescgaEdfer = duration;
			}
		}
		return duration;
	}

	public Duration itinerarioClienteOuFornecedor() {
		Duration duration = Duration.ZERO;
		if (this.hsSaidaParaClienteOuFornecedor != null && this.hsChegadaEmClienteOuFornecedor != null) {
			if (this.hsSaidaParaClienteOuFornecedor.isBefore(this.hsChegadaEmClienteOuFornecedor)) {
				duration = Duration.between(this.hsSaidaParaClienteOuFornecedor, this.hsChegadaEmClienteOuFornecedor);
				this.hsTotalItinerarioClienteFornecedor = duration;
			}
		}
		return duration;
	}

	public Duration cargaDescargaClienteFornecedor() {
		Duration duration = Duration.ZERO;
		if (this.hsInicioCargaDescargaClienteOuFornecedor != null
				&& this.hsFinalCargaDescargaClienteOuFornecedor != null) {
			if (this.hsInicioCargaDescargaClienteOuFornecedor.isBefore(this.hsFinalCargaDescargaClienteOuFornecedor)) {
				duration = Duration.between(this.hsInicioCargaDescargaClienteOuFornecedor,
						this.hsFinalCargaDescargaClienteOuFornecedor);
				this.hsTotalCargaDesClienteFornecedor = duration;
			}
		}
		return duration;
	}

	public Duration itinerarioEdfer() {
		Duration duration = Duration.ZERO;
		if (this.hsSaidaDoClienteOuFornecedor != null && this.hsChegada != null) {
			if (this.hsSaidaDoClienteOuFornecedor.isBefore(this.hsChegada)) {
				duration = Duration.between(this.hsSaidaDoClienteOuFornecedor, this.hsChegada);
				this.hsTotalItinerarioEdfer = duration;
			}
		}
		return duration;
	}

	public void disposicaoCliente() {
		Duration duration = Duration.ZERO;
		if (this.hsChegadaEmClienteOuFornecedor != null && this.hsSaidaDoClienteOuFornecedor != null) {
			if (this.hsChegadaEmClienteOuFornecedor.isBefore(this.hsSaidaDoClienteOuFornecedor)) {
				duration = Duration.between(this.hsChegadaEmClienteOuFornecedor, this.hsSaidaDoClienteOuFornecedor);
				this.hsTotalDisposicaoCliente = duration;
			}
		}
	}

	public Duration totalTime() {
		List<LocalTime> times = new ArrayList<>();

		if (hsInicioCargaDescargaEdfer != null) {
			times.add(hsInicioCargaDescargaEdfer);
		}

		if (hsFinalCargaDescargaEdfer != null) {
			times.add(hsFinalCargaDescargaEdfer);
		}

		if (hsSaidaParaClienteOuFornecedor != null) {
			times.add(hsSaidaParaClienteOuFornecedor);
		}

		if (hsChegadaEmClienteOuFornecedor != null) {
			times.add(hsChegadaEmClienteOuFornecedor);
		}

		if (hsInicioCargaDescargaClienteOuFornecedor != null) {
			times.add(hsInicioCargaDescargaClienteOuFornecedor);
		}

		if (hsFinalCargaDescargaClienteOuFornecedor != null) {
			times.add(hsFinalCargaDescargaClienteOuFornecedor);
		}
		
		if (hsSaidaDoClienteOuFornecedor != null) {
			times.add(hsSaidaDoClienteOuFornecedor);
		}

		if (hsChegada != null) {
			times.add(hsChegada);
		}

		Optional<LocalTime> firstWork = times.stream().min((time1, time2) -> time1.compareTo(time2));
		Optional<LocalTime> lastWork = times.stream().max((time1, time2) -> time1.compareTo(time2));

		return Duration.between(firstWork.get(), lastWork.get());
	}
}
