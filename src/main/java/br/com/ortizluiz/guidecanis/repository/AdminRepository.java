package br.com.ortizluiz.guidecanis.repository;

import org.springframework.data.repository.PagingAndSortingRepository;

import br.com.ortizluiz.guidecanis.model.Administrador;
import br.com.ortizluiz.guidecanis.model.Canil;

public interface AdminRepository extends PagingAndSortingRepository<Administrador, Long> {

	public Administrador findByEmailAndSenha(String email, String senha);
}
