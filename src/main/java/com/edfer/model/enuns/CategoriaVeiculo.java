package com.edfer.model.enuns;

import com.fasterxml.jackson.annotation.JsonValue;

public enum CategoriaVeiculo {

	CAMINHAO("Caminhão"),
	CARRO("Carro");
	
	private String descricao;

	private CategoriaVeiculo(String descricao) {
		this.descricao = descricao;
	}
	
	@JsonValue
	public String getDescricao() {
		return descricao;
	}
	
}
