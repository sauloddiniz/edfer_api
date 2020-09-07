package com.edfer.model;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import com.edfer.model.logistica.Veiculo;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name="viagem")
public class Viagem implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long idViagem;

	private String descricao;

	@ManyToMany
	@JoinTable(name="viagem_veiculo", joinColumns=@JoinColumn(name="idViagem"),
										inverseJoinColumns=@JoinColumn(name="idVeiculo"))
	private List<Veiculo> veiculos;

	private LocalDate data;

	private Double pesoTransportado;

}
