package com.edfer.model.enuns;

import com.fasterxml.jackson.annotation.JsonValue;

public enum EnumAtivo {

	ATIVO("Ativo"),
	INATIVO("Inativo");
	
	private String descricao;
	
	private EnumAtivo (String descricao) {
		this.descricao = descricao;
	}

	@JsonValue
	public String getDescricao() {
		return descricao;
	}	
}
