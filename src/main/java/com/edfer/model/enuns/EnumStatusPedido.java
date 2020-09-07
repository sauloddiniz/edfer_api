package com.edfer.model.enuns;

import com.fasterxml.jackson.annotation.JsonValue;

public enum EnumStatusPedido {

	EM_ANDAMENTO("Em Andamento"),
	AGUARDANDO_MATERIAIS("Aguardando Materiais"),
	AGUARDANDO_MEDIDAS("Aguardando Medidas"),
	SAIU_PARA_ENTREGA("Saiu Para Entrega"),
	ENTREGA_CANCELADA("Entrega Cancelada");
	
	private String descricao;

	private EnumStatusPedido(String descricao) {
		this.descricao = descricao;
	}
	
	@JsonValue
	public String getDescricao() {
		return descricao;
	}
}
