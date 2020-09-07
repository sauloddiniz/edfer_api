package com.edfer.service;

import com.edfer.model.EntradaPedido;
import com.edfer.model.filtersDTO.EntradaPedidoDTO;

public interface EntradaPedidoService {

	public void salvar(EntradaPedido entradaPedido);

	long countByPedidoIsNull();

	EntradaPedidoDTO findAllByCriteria(String sortField, String sortOrder, String rows, String first, String numeroPedido,
			String cliente, String rota, String localizacao, String pesoInicio, String pesoFinal, String previsaoInicio,
			String previsaoFinal, String status, String localizacaoPedido, String situacaoFinal, String dataCriacaoInicio, String dataCriacaoFinal);
}
