package br.com.ortizluiz.guidecanis.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import br.com.ortizluiz.guidecanis.annotation.Publico;

@Component
public class AppInterceptor implements HandlerInterceptor {

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		// Variável para obter a URI
		String uri = request.getRequestURI();
		// Variável para a sessão
		HttpSession session = request.getSession();
		// se for página de erro, libere
		if (uri.startsWith("/error")) {
			return true;
		}
		// Verificar se Handler é im HandlerMethod
		// O que indica que ele está chamando o método em algum controller
		if (handler instanceof HandlerMethod) {
			// Casting de Object para HandlerMethod
			HandlerMethod metodo = (HandlerMethod) handler;
			if (uri.startsWith("/api")) {
				return true;
			} else {

				// Verifica se este método é público
				if (metodo.getMethodAnnotation(Publico.class) != null) {
					return true;
				}
				// Verifica se o usúario está logado
				if (session.getAttribute("usuarioLogado") != null) {
					return true;
				}
				// Redireciona para a página inicial
				response.sendRedirect("/");
				return false;
			}
		}
		return true;

	}

}
