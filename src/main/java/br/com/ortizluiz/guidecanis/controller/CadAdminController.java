package br.com.ortizluiz.guidecanis.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;
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

import br.com.ortizluiz.guidecanis.annotation.Privado;
import br.com.ortizluiz.guidecanis.annotation.Publico;
import br.com.ortizluiz.guidecanis.model.Administrador;
import br.com.ortizluiz.guidecanis.model.Canil;
import br.com.ortizluiz.guidecanis.repository.AdminRepository;
import br.com.ortizluiz.guidecanis.util.HashUtil;

@Controller
public class CadAdminController {

	// variável para persistencia dos dados

	@Autowired
	private AdminRepository repository;
	@Privado
	@RequestMapping("CadUsuarioAdmin")
	public String cadUserAdmin() {
		return "administrador/CadAdmin";

	}

	// requesting mapping para salvar administrador, do tipo Post
	@Privado
	@RequestMapping(value = "salvarAdmin", method = RequestMethod.POST)
	public String salvarAdmin(@Valid Administrador admin, BindingResult result, RedirectAttributes attr) {
		// verifica se houveram erros na validação
		if (result.hasErrors()) {
			// adiciona uma mensagem de erro
			attr.addFlashAttribute("mensagemErro", "Verifique os campos...");
			// redireciona para o formulario
			return "redirect:CadUsuarioAdmin";
		}

		// Variável para descobrir alteração ou inserção
		boolean alteracao = admin.getId() != null ? true : false;
		// Verificar se a senha está vazia
		if (admin.getSenha().equals(HashUtil.hash(""))) {
			if (!alteracao) {
				// Retira a parte antes do @ no e-mail
				String parte = admin.getEmail().substring(0, admin.getEmail().indexOf("@"));
				// "Setar" a parte na senha dele
				admin.setSenha(parte);
			} else {
				// buscar a senha atual no banco de dados
				String hash = repository.findById(admin.getId()).get().getSenha();
				// "Setar" o hash na senha
				admin.setSenhaComHash(hash);
			}

		}

		try {
			// Salva no banco de dados da entidade
			repository.save(admin);
			// Adiciona uma mensagem de Sucesso
			attr.addFlashAttribute("mensagemSucesso", "Administrador cadastrado com sucesso id:" + admin.getId());
		} catch (Exception e) {
			// Adiciona uma mensagem de erro
			attr.addFlashAttribute("mensagemErro", "Houve um erro ao cadastrar:" + e.getMessage());
		}

		return "redirect:CadUsuarioAdmin";

	}

	// request mapping para listar os administradores
	@Privado
	@RequestMapping("listaAdmin/{page}")
	public String listaAdmin(Model model, @PathVariable("page") int page) {
		// Cria um pageble informando os parâmetros da página
		PageRequest pageable = PageRequest.of(page - 1, 6, Sort.by(Sort.Direction.ASC, "nome"));
		// Cria um Page de administrador através dos parâmetros passados pelo repository
		Page<Administrador> pagina = repository.findAll(pageable);
		// Adiciona a Model a lista com os admin
		model.addAttribute("admins", pagina.getContent());
		// variável para o total de páginas
		int totalPages = pagina.getTotalPages();
		// Cria um list de inteiros para armazenar os números das páginas
		List<Integer> numPaginas = new ArrayList<Integer>();
		// Preencher o list com as páginas
		for (int i = 1; i <= totalPages; i++) {
			// Adiciona a página ao list
			numPaginas.add(i);
		}
		// Adiciona os valores à model
		model.addAttribute("numPaginas", numPaginas);
		model.addAttribute("totalPages", totalPages);
		model.addAttribute("pagAtual", page);
		// Retorna para o html a lista
		return "administrador/listaAdm";
	}
	@Privado
	@RequestMapping("alterar")
	public String alterar(Long id, Model model) {
		Administrador administrador = repository.findById(id).get();
		model.addAttribute("administrador", administrador);
		return "forward:CadUsuarioAdmin";
	}
	@Privado
	@RequestMapping("excluir")
	public String excluir(Long id) {
		repository.deleteById(id);
		return "redirect:listaAdmin/1";
	}

	@Publico
	@RequestMapping("login")
	public String login(Administrador admLogin, RedirectAttributes attr, HttpSession session) {
		// Busca o adm no banco
		Administrador admin = repository.findByEmailAndSenha(admLogin.getEmail(), admLogin.getSenha());
		// Verifica se existe
		if (admin == null) {
			attr.addFlashAttribute("mensagemErro", "Login e/ou senha inválido(s)");
			return "redirect:/";
		} else {
			// Salva o administrador na sessão
			session.setAttribute("usuarioLogado", admin);
			return "redirect:/listaAdmin/1";
		}

	}
	@Privado
	@RequestMapping("logout")
	public String logout(HttpSession session) {
		// invalida a sessão
		session.invalidate();
		// Voltar para a página inicial
		return "redirect:/";
	}

}
