package br.com.ortizluiz.guidecanis.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import br.com.ortizluiz.guidecanis.model.Canil;


public interface CanilRepository extends PagingAndSortingRepository<Canil, Long> {

	@Query("SELECT c FROM Canil c WHERE c.nome LIKE %:t% OR c.descricao LIKE %:t% OR c.cep LIKE %:t% OR c.rua LIKE %:t% OR c.bairro LIKE %:t% OR c.cidade LIKE %:t% OR c.estado LIKE %:t% OR c.site LIKE %:t% OR c.tipo LIKE %:t% ")
	public List<Canil> Buscar(@Param("t") String tudo);

	public List<Canil> findAllByOrderByNomeAsc();

}
