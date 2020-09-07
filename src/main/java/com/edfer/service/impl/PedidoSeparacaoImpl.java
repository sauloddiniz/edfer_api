package com.edfer.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.edfer.model.PedidoSeparacao;
import com.edfer.repository.PedidoSeparacaoRepository;
import com.edfer.service.PedidoSeparacaoService;

@Service
@Transactional
public class PedidoSeparacaoImpl implements PedidoSeparacaoService {

	@Autowired
	private PedidoSeparacaoRepository repository;
	
	@Override
	public void salvar(PedidoSeparacao pedidoSeparacao) {
		repository.save(pedidoSeparacao);
	}

	@Override
	public void update(Long idPedidoSeparacao, PedidoSeparacao pedidoSeparacao) {
		pedidoSeparacao.setIdPedidoSeparacao(idPedidoSeparacao);
		repository.save(pedidoSeparacao);
	}

}
