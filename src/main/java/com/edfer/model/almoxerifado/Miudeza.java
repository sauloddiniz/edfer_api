package com.edfer.model.almoxerifado;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.edfer.model.almoxerifado.enuns.Unidade;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

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
@ToString
@Entity
@Table(name = "MIUDEZA")
public class Miudeza implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long idMiudeza;

	@Column(unique = true)
	@NotEmpty(message = "Codigo e obrigatório")
	private String codigo;

	@NotNull(message = "Estoque deve conter pelo menos o valor 0, para cadastro")
	@Min(value = 0, message = "Estoque deve conter pelo menos o valor 0, para cadastro")
	private Double estoque;

	private Double estoqueMinimo;

	@Enumerated(EnumType.STRING)
	private Unidade tipoUnidade;

	@NotBlank(message = "Descrição é obrigatório")
	private String descricao;

	@JsonIgnore
	@OneToMany(mappedBy = "miudeza")
	@JsonIgnoreProperties(value = { "miudeza" })
	private List<EntradaSaidaMiudeza> entradaSaida;

	public void sumEstoque(Long estoque) {
		this.estoque += estoque;
	}

	public void subEstoque(Long estoque) {
		this.estoque -= estoque;
	}

	public void addEntradaSaida(EntradaSaidaMiudeza entradaSaidaMiudeza) {
		if (entradaSaidaMiudeza != null) {
			this.entradaSaida = new ArrayList<EntradaSaidaMiudeza>();
		}
		entradaSaidaMiudeza.addMiudeza(this);
		this.entradaSaida.add(entradaSaidaMiudeza);
	}
}
