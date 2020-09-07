package com.edfer.service;

import java.util.List;

import com.edfer.model.Pedido;
import com.edfer.model.enuns.EnumLocalizacaoPedido;
import com.edfer.model.enuns.EnumStatusPedido;

public interface PedidoService {

	void salvar(Pedido pedido);

	void update(Long idPedido, Pedido pedido);

	List<Pedido> findAll();

	List<String> getAllRotas(String nome);

	List<String> getAllLocalizacao(String nome);

	long countByPatios(EnumLocalizacaoPedido localizacao, EnumStatusPedido status);
}
