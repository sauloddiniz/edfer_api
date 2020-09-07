package com.edfer.model.enuns;

import com.fasterxml.jackson.annotation.JsonValue;

public enum StatusGender {

	INICIANDO(0, "Iniciando"),
	EM_ANDAMENTO(1, "Em andamento"),
	VENDIDO(2, "Vendido"),
	FINALIZADO(3, "Finalizado");

	private String descricao;

	private int codigo;

	private StatusGender(int codigo, String descricao) {
		this.codigo = codigo;
		this.descricao = descricao;
	}

	@JsonValue
	public String getDescricao() {
		return descricao;
	}

	public int getCodigo() {
		return codigo;
	}
}
