package com.edfer.model.enuns;

import com.fasterxml.jackson.annotation.JsonValue;

public enum EnumTipoGasto {

	ALIMENTACAO("Alimentação"),
	HOSPEDAGEM("Hospedagem"),
	PEDAGIO("Pedágio"),
	OUTROS("Outros");

	private String descricao;

	private EnumTipoGasto(String descricao) {
		this.descricao = descricao;
	}

	@JsonValue
	public String getDescricao() {
		return descricao;
	}
}
