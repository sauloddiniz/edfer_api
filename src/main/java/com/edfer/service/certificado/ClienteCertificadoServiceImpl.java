package com.edfer.service.certificado;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.edfer.model.certificado.ClienteCertificado;
import com.edfer.repository.certificado.ClienteCertificadoRepository;

@Service
public class ClienteCertificadoServiceImpl implements ClienteCertificadoService {

	@Autowired
	private ClienteCertificadoRepository  repository;
	
	@Override
	public void salvar(ClienteCertificado clienteCertificado, Long idCertificado) {
		
		repository.save(clienteCertificado);
	}

	@Override
	public List<ClienteCertificado> findAllByNumPedido(String numPedido) {
		return repository.findAllByNumPedidoOrderByDataDesc(numPedido);
	}

	@Override
	public List<ClienteCertificado> findAllByIdCertificado(Long idCertificado) {
		return repository.findAllByProdutoCertificadoAux_Certificado_IdCertificado(idCertificado);
	}

	@Override
	public List<String> findClienteNome(String nome) {
		return repository.findNome(nome);
	}

	@Override
	public List<ClienteCertificado> findAllByNomeAndDate(String nome, String dateMin, String dateMax) {

		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		LocalDate dateMinAux = LocalDate.parse(dateMin, formatter);
		LocalDate dateMaxAux = LocalDate.parse(dateMax, formatter);
		
		System.out.println(nome);
	//	return repository.findAll();
		return repository.findAllByNomeEqualsOrDataGreaterThanEqual(nome, dateMinAux);
	}
}
