package br.com.ortizluiz.guidecanis.repository;

import java.util.List;

import org.springframework.data.repository.PagingAndSortingRepository;

import br.com.ortizluiz.guidecanis.model.Canil;
import br.com.ortizluiz.guidecanis.model.TipoCanil;

public interface CanilRepository extends PagingAndSortingRepository<Canil, Long> {

	

}
