package br.com.ortizluiz.guidecanis.controller;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.com.ortizluiz.guidecanis.model.TipoCanil;
import br.com.ortizluiz.guidecanis.repository.TipoCanilRepository;

@Controller
public class TipoCanilController {

	@Autowired
	private TipoCanilRepository rep;

	@RequestMapping("CadCanis")
	public String canis() {
		return "usuario/CadCanis";
	}

	@RequestMapping(value = "salvarCanil", method = RequestMethod.POST)
	public String salvarCanil(@Valid TipoCanil canil, BindingResult result, RedirectAttributes attr) {
		if (result.hasErrors()) {
			attr.addFlashAttribute("mensagemErro", "Verifique os campos...");
			return "redirect:CadCanis";
		}

		try {
			// Salva no banco de dados da entidade
			rep.save(canil);
			// Adiciona uma mensagem de Sucesso
			attr.addFlashAttribute("mensagemSucesso", "Canil cadastrado com sucesso id:" + canil.getId());
		} catch (Exception e) {
			// Adiciona uma mensagem de erro
			attr.addFlashAttribute("mensagemErro", "Houve um erro ao cadastrar:" + e.getMessage());
		}

		return "redirect:CadCanis";

	}

	@RequestMapping("listaCanis/{page}")
	public String listaCanis(Model model, @PathVariable("page") int page) {
		PageRequest pageable = PageRequest.of(page - 1, 6, Sort.by(Sort.Direction.ASC, "nome"));
		Page<TipoCanil> pagina = rep.findAll(pageable);
		model.addAttribute("canis", pagina.getContent());
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
		return "usuario/listaCanis";

	}

	@RequestMapping("alterarCanil")
	public String alterarCanil(Long id, Model model) {
		TipoCanil canil = rep.findById(id).get();
		model.addAttribute("canil", canil);
		return "forward:CadCanis";
	}

	@RequestMapping("excluirCanil")
	public String excluirCanil(Long id) {
		rep.deleteById(id);
		return "redirect:listaCanis/1";
	}

	@RequestMapping("buscarRaca")
	public String buscaRaca(Model model, String busca) {
		model.addAttribute("canis", rep.Busque(busca));
		return "usuario/listaCanis";
	}
	
	@RequestMapping("limparBusca")
	public String limparBusca(){
		return "redirect:listaCanis/1";
	}

}
