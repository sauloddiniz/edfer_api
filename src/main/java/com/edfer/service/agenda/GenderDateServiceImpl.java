package com.edfer.service.agenda;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.edfer.model.agenda.GenderDate;
import com.edfer.model.enuns.StatusGender;
import com.edfer.repository.agenda.GenderDateRepository;

@Service
public class GenderDateServiceImpl implements GenderDateService {

	@Autowired
	private GenderDateRepository repository;

	@Override
	public void deleteById(Long idGenderDate) {
		repository.deleteGenderDateById(idGenderDate);
		
	}

	@Override
	public Optional<GenderDate> findByStatusAndIdAgenda(StatusGender status, Long idAgenda) {
		return repository.findFirstByStatusAndAgendaIdAgenda(status, idAgenda);
	}
}
