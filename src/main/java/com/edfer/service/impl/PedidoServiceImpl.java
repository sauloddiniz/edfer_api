package com.edfer.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.edfer.model.EntradaPedido;
import com.edfer.model.Pedido;
import com.edfer.model.PedidoSeparacao;
import com.edfer.model.enuns.EnumLocalizacaoPedido;
import com.edfer.model.enuns.EnumStatusPedido;
import com.edfer.repository.EntradaPedidoRepository;
import com.edfer.repository.PedidoRepository;
import com.edfer.service.PedidoService;

@Service
@Transactional
public class PedidoServiceImpl implements PedidoService{

	@Autowired
	private PedidoRepository repository;
	
	@Autowired
	private EntradaPedidoRepository entrada;
	
	@Override
	public void salvar(Pedido pedido) {
		System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>" + pedido.getEntradaPedido().toString());
		repository.save(pedido);
		try {
			if (pedido.getEntradaPedido() != null ) {
				EntradaPedido entradaPedido = pedido.getEntradaPedido();
				entradaPedido.setPedido(pedido);
				entrada.save(entradaPedido);
			}
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	@Override
	@Transactional(readOnly = true)
	public List<Pedido> findAll() {
		List<Pedido> lista = repository.findAll();
		for (Pedido pedido : lista) {
			if (pedido.getPedidoSeparacao() == null) {
				pedido.setPedidoSeparacao(new PedidoSeparacao());
			}
		}
		return lista;
	}

	@Override
	@Transactional(readOnly = true)
	public List<String> getAllRotas(String rota) {
		return repository.findAllRotas(rota);
	}

	@Override
	@Transactional(readOnly = true)
	public List<String> getAllLocalizacao(String localizacao) {
		return repository.findAllLocalizacao(localizacao);
	}

	@Override
	public void update(Long idPedido, Pedido pedido) {
		pedido.setIdPedido(idPedido);
		if (pedido.getEntradaPedido() != null) {
			pedido.setEntradaPedido(null);
		}
		repository.save(pedido);
	}

	@Override
	public long countByPatios(EnumLocalizacaoPedido localizacao, EnumStatusPedido status) {
		System.out.println(repository.countByLocalizacaoPedidoAndStatus(localizacao, status));
		return 0;
	}

}
