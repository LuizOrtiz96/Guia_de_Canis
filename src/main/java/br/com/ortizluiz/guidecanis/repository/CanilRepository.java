package br.com.ortizluiz.guidecanis.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import br.com.ortizluiz.guidecanis.model.TipoCanil;

public interface CanilRepository extends PagingAndSortingRepository<TipoCanil, Long> {

	@Query("SELECT c FROM TipoCanil c WHERE c.nome LIKE %:t% OR c.descricao LIKE %:t% OR c.palavrasChave LIKE %:t% ")
	public List<TipoCanil> Busque(@Param("t") String tudo);
}
