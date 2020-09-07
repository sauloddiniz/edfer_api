package com.edfer.model;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@EqualsAndHashCode(of = "idOrcamento")
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "ORCAMENTO")
public class Orcamento implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long idOrcamento;

	private LocalDate dataOrcamento;

	private LocalDate dataVencimentoOrcamento;

	@OneToMany(mappedBy = "orcamento", cascade = CascadeType.ALL)
	@JsonIgnoreProperties(value = { "orcamento" })
	private List<OrcamentoItem> listaItemOrc;

	public void addOrcamentoItem(OrcamentoItem item) {
		if (this.listaItemOrc != null) {
			this.listaItemOrc = new ArrayList<>();
		}
		item.setOrcamento(this);
		this.listaItemOrc.add(item);
	}
}
