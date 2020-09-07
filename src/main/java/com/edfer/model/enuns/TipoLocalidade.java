package com.edfer.model.enuns;

import com.fasterxml.jackson.annotation.JsonValue;

public enum TipoLocalidade {
	CLIENTE("Cliente"),
	FORNECEDOR("Fornecedor"),
	PONTO_DE_APOIO("Ponto de Apoio"),
	CARREGAMENTO("Carregamento");

	private String descricao;

	private TipoLocalidade(String descricao) {
		this.descricao = descricao;
	}

	@JsonValue
	public String getDescricao() {
		return descricao;
	}
}
