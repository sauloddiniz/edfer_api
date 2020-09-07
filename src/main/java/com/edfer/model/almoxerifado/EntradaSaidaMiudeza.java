package com.edfer.model.almoxerifado;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.edfer.model.almoxerifado.enuns.EnumEntradaSaida;
import com.edfer.service.auditable.Auditable;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@ToString
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Entity
@Table(name="ENTRADA_SAIDA")
public class EntradaSaidaMiudeza extends Auditable implements Serializable{
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long idEntradaSaida;

	@Enumerated(EnumType.STRING)
	private EnumEntradaSaida entradaSaida;

	private String numPedido;

	@NotNull
	private Long quantidade;

	@ManyToOne
	@JoinColumn(name="idMiudeza")
	@JsonIgnoreProperties(value= {"entradaSaida"})
	private Miudeza miudeza;

	public void addMiudeza(Miudeza miudeza) {
		this.miudeza = miudeza;
	}
}
