package com.edfer.repository.agenda;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.edfer.model.agenda.Agenda;

@Repository
public interface AgendaRepository extends JpaRepository<Agenda, Long>, AgendaRepositoryExtends{

	//List<Agenda> findTop30ByUsuario_EmailOrderByIdAgendaDesc(String emal);

	@Transactional(readOnly = true)
	@Query(value="SELECT a.cliente FROM Agenda a WHERE a.cliente LIKE :cliente% GROUP BY a.cliente")
	List<String> findCliente(String cliente);

	@Transactional(readOnly = true)
	@Query(value="SELECT a.contato FROM Agenda a WHERE a.contato LIKE :contato% GROUP BY a.contato")
	List<String> findContato(String contato);

	@Transactional(readOnly = true)
	List<Agenda> findAllByDataRetornoLessThanEqualAndAndDataRetornoGreaterThanEqual(LocalDateTime dateMax, LocalDateTime dateMin);
}
