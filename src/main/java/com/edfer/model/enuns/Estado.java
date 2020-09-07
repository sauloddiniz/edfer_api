package com.edfer.model.enuns;

import com.fasterxml.jackson.annotation.JsonValue;

public enum Estado {

	NOVO(0, "Novo"),
	USADO(1, "Usado"),
	REFORMADO(2, "Reformado");

	private int codigo;

	private String descricao;

	private Estado(int codigo, String descricao) {
		this.codigo = codigo;
		this.descricao = descricao;
	}

	public int getCodigo() {
		return codigo;
	}

	@JsonValue
	public String getDescricao() {
		return descricao;
	}
}
