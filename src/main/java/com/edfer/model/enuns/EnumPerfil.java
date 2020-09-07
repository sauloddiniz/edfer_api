package com.edfer.model.enuns;

import com.fasterxml.jackson.annotation.JsonValue;

public enum EnumPerfil {

	ADMINISTRADOR(0,"ROLE_ADMIN"),
	VENDA(1,"ROLE_VENDA"),
	LOGISTICA(2, "ROLE_LOGISTICA"),
	COMPRA(3, "ROLE_COMPRA"),
	GESTOR(4, "ROLE_GESTOR");

	private int codigo;
	private String descrição;

	private EnumPerfil(int codigo,String descricao) {
		this.codigo = codigo;
		this.descrição = descricao;
	}

	public int getCodigo() {
		return codigo;
	}

	@JsonValue
	public String getDescrição() {
		return descrição;
	}

	public static EnumPerfil toEnumCod(Integer codigo) {
		if (codigo == null) {
			return null;
		}
		for (EnumPerfil perfil : EnumPerfil.values()) {
			if (codigo.equals(perfil.getCodigo())) {
				return perfil;
			}
		}
		throw new IllegalArgumentException("Id Invalido: " + codigo);
	}
	
	
}
