package com.edfer.model.enuns;

import com.fasterxml.jackson.annotation.JsonValue;

public enum TipoCliente {

	FISICA("Fisica"),
	JURIDICA("Juridica");
	
	private String descricao;
	
	private TipoCliente(String descricao) {
		this.descricao = descricao;
	}

	@JsonValue
	public String getDescricao() {
		return descricao;
	}
	
	
}
