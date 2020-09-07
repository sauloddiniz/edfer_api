package com.edfer.model.enuns;

import com.fasterxml.jackson.annotation.JsonValue;

public enum EnumLocalizacaoPedido {

	LOJA("Loja"),
	VIGAS_6M("Vigas 6M"),
	VIGAS_12M("Vigas 12M"),
	CHAPA("Chapa"),
	TELHAS("Telha"),
	OXICORTE("Oxicorte"),
	MIUDEZAS("Miudezas");
	
	private String descricao;

	private EnumLocalizacaoPedido(String descricao) {
		this.descricao = descricao;
	}
	
	@JsonValue
	public String getDescricao() {
		return descricao;
	}
}
