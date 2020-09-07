package com.edfer.repository.extend;

import com.edfer.model.filtersDTO.EntradaPedidoDTO;

public interface EntradaPedidoRepositoryExtends {

	EntradaPedidoDTO findByCriteria(String sortField, String sortOrder, int rows, int first, String numeroPedido,
			String cliente, String pesoPedido, String rota, String localizacao, String pesoInicio,
			String previsaoInicio, String previsaoFinal, String status, String localizacaoPedido, String situacaoFinal, String dataCriacaoInicio, String dataCriacaoFinal);
}
