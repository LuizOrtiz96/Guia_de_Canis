package br.com.ortizluiz.guidecanis.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import br.com.ortizluiz.guidecanis.model.Canil;
import br.com.ortizluiz.guidecanis.repository.CanilRepository;
import br.com.ortizluiz.guidecanis.repository.TipoCanilRepository;

@Controller
public class CanilController {

	@Autowired
	private TipoCanilRepository repTipo;
	@Autowired
	private CanilRepository repository;

	@RequestMapping("formCanil")
	public String form(Model model) {
		model.addAttribute("tipos", repTipo.findAllByOrderByNomeAsc());
		return "canil/form";
	}

	@RequestMapping("salvarCanis")
	public String salvar(Canil canis,@RequestParam("fileFotos") MultipartFile[] fileFotos) {
		
		repository.save(canis);
		return "redirect:formCanil";
	}

}
