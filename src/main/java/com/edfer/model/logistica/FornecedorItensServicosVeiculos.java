package com.edfer.model.logistica;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.edfer.model.Fornecedor;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Getter
@Setter
@Entity
@Table(name="FORNECEDOR_ITENS_SERVICOS_VEICULOS")
public class FornecedorItensServicosVeiculos extends Fornecedor{
	private static final long serialVersionUID = 1L;
}
