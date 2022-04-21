package br.com.ortizluiz.guidecanis.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import br.com.ortizluiz.guidecanis.annotation.Privado;
import br.com.ortizluiz.guidecanis.model.Canil;

import br.com.ortizluiz.guidecanis.repository.CanilRepository;
import br.com.ortizluiz.guidecanis.repository.TipoCanilRepository;
import br.com.ortizluiz.guidecanis.util.FirebaseUtil;

@Controller
public class CanilController {

	@Autowired
	private TipoCanilRepository repTipo;
	@Autowired
	private CanilRepository repository;
	@Autowired
	private FirebaseUtil fireUtil;
	
	@Privado
	@RequestMapping("formCanil")
	public String form(Model model) {
		model.addAttribute("tipos", repTipo.findAllByOrderByNomeAsc());
		return "canil/form";
	}
	@Privado
	@RequestMapping("salvarCanis")
	public String salvar(Canil canis, @RequestParam("fileFotos") MultipartFile[] fileFotos) {
		// String para armazenar as URLs
		String fotos = canis.getFotos();
		for (MultipartFile arquivo : fileFotos) {
			// Verifica se o arquivo existe
			if (arquivo.getOriginalFilename().isEmpty()) {
				// Vai para o próximo arquivo
				continue;
			}
			try {
				// Faz o uploud e guarda a URL na String Fotos
				fotos += fireUtil.upload(arquivo) + ";";
			} catch (IOException e) {
				e.printStackTrace();
				throw new RuntimeException(e);
			}

		}
		// Guarda na variavel canil as fotos
		canis.setFotos(fotos);
		// Salva no Banco de dados
		repository.save(canis);
		return "redirect:formCanil";
	}
	@Privado
	@RequestMapping("listaCanil/{page}")
	public String listaCanil(Model model, @PathVariable("page") int page) {
		PageRequest pageable = PageRequest.of(page - 1, 6, Sort.by(Sort.Direction.ASC, "nome"));
		Page<Canil> pagina = repository.findAll(pageable);
		model.addAttribute("canil", pagina.getContent());
		int totalPages = pagina.getTotalPages();
		List<Integer> numPaginas = new ArrayList<Integer>();
		for (int i = 1; i <= totalPages; i++) {
			numPaginas.add(i);
		}
		// Adiciona os valores à model
		model.addAttribute("numPaginas", numPaginas);
		model.addAttribute("totalPages", totalPages);
		model.addAttribute("pagAtual", page);
		// Retorna para o html a lista
		return "canil/listaCanil";

	}
	@Privado
	@RequestMapping("alterarCanis")
	public String alterarCanis(Long id, Model model) {
		Canil canis = repository.findById(id).get();
		model.addAttribute("canis", canis);
		return "forward:formCanil";
	}
	@Privado
	@RequestMapping("excluirCanis")
	public String excluirCanil(Long idCanil) {
		Canil canis = repository.findById(idCanil).get();
		if (canis.getFotos().length() > 0) {
			for (String foto : canis.verFotos()) {
				fireUtil.deletar(foto);
			}
		}
		repository.delete(canis);
		return "redirect:listaCanil/1";
	}
	@Privado
	@RequestMapping("buscarCanil")
	public String buscaRaca(Model model, String busca) {
		model.addAttribute("canil", repository.Buscar(busca));
		return "usuario/listaCanis";
	}
	@Privado
	@RequestMapping("limparBuscaCanil")
	public String limparBusca() {
		return "redirect:listaCanil/1";
	}
	@Privado
	@RequestMapping("excluirFotos")
	public String excluirFotos(Long idCanil, int numFoto, Model model) {
		// Buscar o canil no banco
		Canil cani = repository.findById(idCanil).get();
		// Busca a URL da foto
		String urlFoto = cani.verFotos()[numFoto];
		// Deleta a Foto
		fireUtil.deletar(urlFoto);
		// remove a url do atributo fotos
		cani.setFotos(cani.getFotos().replace(urlFoto + ";", ""));
		// Salva no bd
		repository.save(cani);
		// Coloca o cani na Model
		model.addAttribute("canis", cani);
		return "forward:formCanil";

	}

}
