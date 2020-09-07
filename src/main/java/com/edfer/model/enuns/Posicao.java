package com.edfer.model.enuns;

import com.fasterxml.jackson.annotation.JsonValue;

public enum Posicao {

	ESQUERDO("Esquerdo"),
	DIREITO("Direito"),
	ESQUERDO_DENTRO("Esquerdo Dentro"),
	ESQUERDO_FORA("Esquerdo Fora"),
	DIREITO_DENTRO("Direito Dentro"),
	DIREITO_FORA("Direito Fora"),
	DIANTEIRO("Dianteiro"),
	TRAZEIRO("Trazeiro"),
	ESTEPE("Estepe");

	private String descricao;

	private Posicao(String descricao) {
		this.descricao = descricao;
	}

	@JsonValue
	public String getDescricao() {
		return descricao;
	}
}
