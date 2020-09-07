package com.edfer.service.agenda;

import java.util.Optional;

import com.edfer.model.agenda.GenderDate;
import com.edfer.model.enuns.StatusGender;

public interface GenderDateService {

	Optional<GenderDate> findByStatusAndIdAgenda(StatusGender status, Long idAgenda);

	void deleteById(Long id);
}
