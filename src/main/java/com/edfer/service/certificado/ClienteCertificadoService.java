package com.edfer.service.certificado;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.edfer.model.certificado.ClienteCertificado;

@Transactional
public interface ClienteCertificadoService {

	void salvar(ClienteCertificado clienteCertificado, Long idCertificado);
	
	@Transactional(readOnly = true)
	List<ClienteCertificado> findAllByNumPedido(String numPedido);

	@Transactional(readOnly = true)
	List<ClienteCertificado> findAllByIdCertificado(Long idCertificado);

	@Transactional(readOnly = true)
	List<String> findClienteNome(String nome);

	@Transactional(readOnly = true)
	List<ClienteCertificado> findAllByNomeAndDate(String nome, String dateMin, String dateMax);
}
