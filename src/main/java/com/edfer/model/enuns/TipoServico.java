package com.edfer.model.enuns;

import com.fasterxml.jackson.annotation.JsonValue;

public enum TipoServico {

	ALINHAMENTO("Alinhamento"),
	BALANCEAMENTO("Balanceamento"),
	BATERIA("Bateria"),
	CORREIA_DENTADA("Correia Dentada"),
	EMBREAGEM("Embreagem"),
	ESCAPAMENTO("Escapamento"),
	EXTINTOR("Extintor"),
	FAROL_LANTERNA("Farol / Lanterna"),
	FILTRO_DE_AR("Filtro de Ar"),
	FILTRO_DE_COMBUSTIVEL("Filtro de Combustivel"),
	FILTRO_DE_OLEO("Filtro de Óleo"),
	FREIOS("Freios"),
	INJECAO("Injeção"),
	RADIADOR("Radiador"),
	TRANSMISSAO("Transmissão"),
	TROCA_DE_PECA("Troca de Peça"),
	TROCA_DE_OLEO("Troca de Óleo"),
	VELAS("Velas"),
	MANUTENCAO_DE_LANTERNAGEM("Manutenção de Lanternagem"),
	MANUTENCAO_ELETRICA("Manutenção de Eletrica"),
	MANUTENCAO_MECANICA("Manutenção Mecânica"),
	MANUTENCAO_HIDRAULICA("Manutenção Hidraulica"),
	MANUTENCAO_CARROCERIA("Manutenção da Carroceria"),
	MANUTENCAO_RODOAR("Manutenção Rodoar"),
	MANUTENCAO_DE_COMPONENTES_DA_CABINE("Manutenção de Componentes da Cabine"),
	LAVAGEM("Lavagem"),
	LUBRIFICACAO("Lubrificacao"),
	MATERIAL("Manutenção de Lanternagem"),
	TROCA_DE_PNEU("Troca de Pneu"),
	REFORMA_DE_PNEU("Reforma de Pneu"),
	REFORMA_DE_CONSUMO("Material de Consumo");
	
	private String descricao;

	private TipoServico(String descricao) {
		this.descricao = descricao;
	}

	@JsonValue
	public String getDescricao() {
		return descricao;
	}
}
