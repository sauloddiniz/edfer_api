package com.edfer.model.enuns;

import com.fasterxml.jackson.annotation.JsonValue;

public enum TipoViagem {
	LOCAL("Local"), VIAGEM("Viagem");

	private String descricao;

	private TipoViagem(String descricao) {
		this.descricao = descricao;
	}

	@JsonValue
	public String getDescricao() {
		return descricao;
	}
}
