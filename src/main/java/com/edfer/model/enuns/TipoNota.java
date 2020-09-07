package com.edfer.model.enuns;

import com.fasterxml.jackson.annotation.JsonValue;

public enum TipoNota {
	NOTA_FISCAL(0, "Nota fiscal"),
	NOTA_DE_ORDEM(1, "Nota de ordem");

	private int codigo;
	private String descricao;

	private TipoNota(int codigo, String descricao) {
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
