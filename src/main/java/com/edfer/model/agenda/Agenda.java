package com.edfer.model.agenda;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
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
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import org.springframework.lang.NonNull;

import com.edfer.model.Usuario;
import com.edfer.model.enuns.StatusGender;
import com.edfer.model.enuns.TipoCliente;
import com.edfer.service.auditable.Auditable;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "AGENDA")
public class Agenda extends Auditable implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long idAgenda;

	@Enumerated(EnumType.ORDINAL)
	@NonNull
	private TipoCliente tipoCliente;

	@NotBlank(message = "Cliente não pode estar em branco")
	@NonNull
	private String cliente;

	@NotBlank(message = "Contato não pode estar em branco")
	@NonNull
	private String contato;

	@NotBlank(message = "Telefone não pode estar em branco")
	@NonNull
	@Size(min=10, max=11, message="Telefone requer de 10 a 11 dígitos")
	private String telefone;

	@Column(unique = true)
	private String orcamentoNumber;

	private BigDecimal valorOrcamento;

	@ManyToOne
	@JoinColumn(name = "id_usuario")
	@JsonIgnoreProperties(value = { "agenda", "idUsuario", "perfil", "ativo", "senha", "perfis" }, allowSetters = false)
	private Usuario usuario;

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy", locale = "pt-BR", timezone = "Brazil/East")
	@JsonInclude(Include.NON_NULL)
	private LocalDate dataVisita;

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy HH:mm", locale = "pt-BR", timezone = "Brazil/East")
	@JsonInclude(Include.NON_NULL)
	private LocalDateTime dataRetorno;

	@Enumerated(EnumType.ORDINAL)
	@NonNull
	@JsonInclude(Include.NON_NULL)
	private StatusGender status;

	@NotBlank(message = "Observação não pode estar em branco")
	@NonNull
	@JsonInclude(Include.NON_NULL)
	private String observacao;

	@OneToMany(mappedBy = "agenda", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JsonIgnoreProperties(value = { "agenda" })
	private List<GenderDate> genderDate;

	public void addDates(GenderDate genderDate) {
		if (this.genderDate == null) {
			this.genderDate = new ArrayList<GenderDate>();
		}
		genderDate.setAgenda(this);
		this.genderDate.add(genderDate);
	}
}
