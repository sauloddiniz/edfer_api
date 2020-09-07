package com.edfer.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.edfer.model.EntradaPedido;
import com.edfer.model.Pedido;
import com.edfer.model.filtersDTO.EntradaPedidoDTO;
import com.edfer.repository.EntradaPedidoRepository;
import com.edfer.service.EntradaPedidoService;

@Service
public class EntradaPedidoServiceImpl implements EntradaPedidoService {

	@Autowired
	private EntradaPedidoRepository repository;

	@Override
	public void salvar(EntradaPedido entradaPedido) {
		repository.save(entradaPedido);
	}

	@Override
	public EntradaPedidoDTO findAllByCriteria(String sortField, String sortOrder, String rows, String first,
			String numeroPedido, String cliente, String rota, String localizacao, String pesoInicio, String pesoFinal,
			String previsaoInicio, String previsaoFinal, String status, String localizacaoPedido,
			String situacaoFinal, String dataCriacaoInicio, String dataCriacaoFinal) {

		EntradaPedidoDTO lista = repository.findByCriteria(sortField, sortOrder, Integer.parseInt(rows),
				Integer.parseInt(first), numeroPedido, cliente, rota, localizacao, pesoInicio, pesoFinal,
				previsaoInicio, previsaoFinal, status, localizacaoPedido, situacaoFinal, dataCriacaoInicio, dataCriacaoFinal);
		
		for (EntradaPedido entradaPedido : lista.getObjs()) {
			if (entradaPedido.getPedido() == null) {
				entradaPedido.setPedido(new Pedido());
			}
		}

		return lista;
	}

	@Override
	public long countByPedidoIsNull() {
		return repository.countByPedidoIsNull();
	}

}
