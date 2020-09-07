package com.edfer.service;

import com.edfer.model.PedidoSeparacao;

public interface PedidoSeparacaoService {
	
	void salvar(PedidoSeparacao pedidoSeparacao);

	void update(Long idPedidoSeparacao, PedidoSeparacao pedidoSeparacao);

}
