package br.com.ortizluiz.guidecanis.rest;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.com.ortizluiz.guidecanis.model.Canil;
import br.com.ortizluiz.guidecanis.model.TipoCanil;
import br.com.ortizluiz.guidecanis.repository.CanilRepository;
import br.com.ortizluiz.guidecanis.repository.TipoCanilRepository;

@RestController
@RequestMapping("/api/canil")
public class CanilRestController {

	@Autowired
	private CanilRepository repository;
	

	@RequestMapping(value = "", method = RequestMethod.GET)
	public Iterable<Canil> getCanis() {
		return repository.findAll();
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ResponseEntity<Canil> getCanil(@PathVariable("id") Long idCanil) {
		// Tenta buscar o canil no repository
		Optional<Canil> optional = repository.findById(idCanil);
		// Se o canil existir
		if (optional.isPresent()) {
			return ResponseEntity.ok(optional.get());
		} else {
			return ResponseEntity.notFound().build();
		}
	}

	@RequestMapping(value = "/tipo/{idTipo}", method = RequestMethod.GET)
	public List<Canil> getTipoCanil(@PathVariable("idTipo") Long idTipo) {
		return repository.findByTipoId(idTipo);

	}

}
