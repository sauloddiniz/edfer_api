package com.edfer.model.enuns;

import com.fasterxml.jackson.annotation.JsonValue;

public enum TipoCombustivel {

	GASOLINA_COMUM(0, "Gasolina Comum"),
	GASOLINA_ADITIVADA(1, "Gasolina Aditivada"),
	GASOLINA_PREMIUM(2, "Gasolina Premium"),
	GASOLINA_FORMULADA(3, "Gasolina Formulada"),
	ETANOL(4, "Etanol"),
	ETANOL_ADITIVADO(5, "Etanol aditivado"),
	GNV(6, "Gás Natural Veícular"),
	DIESEL(7, "Diesel"),
	DIESEL_S10(8, "Diesel S10"),
	DIESEL_ADITIVADO(9, "Diesel Aditivado"),
	DIESEL_PREMIUM(10, "Diesel Premium");

	private int codigo;
	private String descricao;

	private TipoCombustivel(int codigo, String descricao) {
		this.codigo = codigo;
		this.descricao = descricao;
	}

	@JsonValue
	public String getDescricao() {
		return this.descricao;
	}

	public int getCodigo() {
		return this.codigo;
	}

}
