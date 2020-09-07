package com.edfer.model.enuns;

import com.fasterxml.jackson.annotation.JsonValue;

public enum TipoManutencao {

	PREVENTIVA(0, "Preventiva"),
	CORRETIVA(1, "Corretiva"),
	COMPRA_DE_PECA(2,"Compra de peça");

	private int codigo;
	private String descricao;

	private TipoManutencao(int codigo, String descricao) {
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
