package br.com.ortizluiz.guidecanis.repository;

import org.springframework.data.repository.PagingAndSortingRepository;

import br.com.ortizluiz.guidecanis.model.Administrador;

public interface AdminRepository extends PagingAndSortingRepository<Administrador, Long> {

}
