 package med.voll.api.domain.medico;

import java.time.LocalDateTime;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface MedicoRepository extends JpaRepository<Medico, Long> {

	Page< Medico> findAllByAtivosTrue(Pageable paginacao);
	
	@Query("""
			select m from Medico m
			where
			m.ativos = true
			and
			m.especialidade = :especialidade
			and
			m.id not in(
				select c.medico.id from Consulta c 
				where
				c.date= :date
			)
			order by rand()
			limit 1
			""")
	Medico escolherMedicoAleatorioLivreNaData(Especialidade especialidade, LocalDateTime date);
	
	@Query("""
			select m.ativos
			from Medico m
			where
			m.id = :id
			""")
	Boolean findAtivosById(Long id);

}
