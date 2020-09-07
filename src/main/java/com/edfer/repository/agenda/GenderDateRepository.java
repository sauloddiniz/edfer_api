package com.edfer.repository.agenda;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.edfer.model.agenda.GenderDate;
import com.edfer.model.enuns.StatusGender;

@Repository
@Transactional
public interface GenderDateRepository extends JpaRepository<GenderDate, Long> {

	@Transactional(readOnly = true)
	List<GenderDate> findAllByDataRetornoLessThanEqualAndAndDataRetornoGreaterThanEqual(LocalDateTime dateMax, LocalDateTime dateMin);

	@Transactional(readOnly = true)
	Optional<GenderDate> findFirstByStatusAndAgendaIdAgenda(StatusGender status, Long idAgenda);

	@Modifying
	@Query("DELETE FROM GenderDate g WHERE g.idGenderDat = :idGenderDate")
	void deleteGenderDateById(Long idGenderDate);
}
