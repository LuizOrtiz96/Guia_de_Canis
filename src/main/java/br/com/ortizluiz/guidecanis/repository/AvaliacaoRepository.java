package br.com.ortizluiz.guidecanis.repository;

import java.util.List;

import org.springframework.data.repository.PagingAndSortingRepository;

import br.com.ortizluiz.guidecanis.model.Avaliacao;

public interface AvaliacaoRepository extends PagingAndSortingRepository<Avaliacao, Long> {

	public List<Avaliacao> findByCanilId(Long idCanil);

}
