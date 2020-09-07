package com.edfer.model.agenda;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.springframework.lang.NonNull;

import com.edfer.model.enuns.StatusGender;
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
@ToString
@EqualsAndHashCode(of="idGenderDat")
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "GENDER_DATE")
public class GenderDate implements Serializable{

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long idGenderDat;

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy", locale = "pt-BR", timezone = "Brazil/East")
	@NotNull
	@JsonInclude(Include.NON_NULL)
	private LocalDate dataVisita;

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy HH:mm", locale = "pt-BR", timezone = "Brazil/East")
	@JsonInclude(Include.NON_NULL)
	private LocalDateTime dataRetorno;

	@Enumerated(EnumType.ORDINAL)
	@NonNull
	@JsonInclude(Include.NON_NULL)
	private StatusGender status;

	@NotBlank(message="Observação não pode estar em branco")
	@NonNull
	@JsonInclude(Include.NON_NULL)
	private String observacao;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name="idAgenda")
	@JsonIgnoreProperties(value= {"genderDate"})
	private Agenda agenda;
}
