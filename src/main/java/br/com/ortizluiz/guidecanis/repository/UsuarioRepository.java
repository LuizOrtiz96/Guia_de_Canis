package br.com.ortizluiz.guidecanis.repository;

import org.springframework.data.repository.PagingAndSortingRepository;

import br.com.ortizluiz.guidecanis.model.Usuario;

public interface UsuarioRepository extends PagingAndSortingRepository<Usuario, Long> {

}
