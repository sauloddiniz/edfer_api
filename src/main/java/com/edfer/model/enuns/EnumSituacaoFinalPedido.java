package com.edfer.model.enuns;

import com.fasterxml.jackson.annotation.JsonValue;

public enum EnumSituacaoFinalPedido {

	ENTREGA_REALIZADA("Entrega Realizada"),
	ENTREGA_REALIZADA_EM_ATRASO("Entrega Realizada em Atraso");

	private String descricao;

	private EnumSituacaoFinalPedido(String descricao) {
		this.descricao = descricao;
	}

	@JsonValue
	public String getDescricao() {
		return descricao;
	}
}
