package br.com.ortizluiz.guidecanis.interceptor;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;

import br.com.ortizluiz.guidecanis.annotation.Privado;
import br.com.ortizluiz.guidecanis.annotation.Publico;
import br.com.ortizluiz.guidecanis.rest.UsuarioRestController;

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
				// Variável para o Token
				String token = null;
				// Verificar se o método é privado
				if (metodo.getMethodAnnotation(Privado.class) != null) {
					try {
						// se o método for privado recupera o token
						token = request.getHeader("Authorization");
						// Cria o algoritmo para assinar
						Algorithm algoritmo = Algorithm.HMAC256(UsuarioRestController.SECRET);
						// Objeto para verificar o token
						JWTVerifier verifier = JWT.require(algoritmo).withIssuer(UsuarioRestController.EMISSOR).build();
						// Decodifica o token
						DecodedJWT jwt = verifier.verify(token);
						// Recurepa os dados do payload
						Map<String, Claim> claims = jwt.getClaims();
						System.out.println(claims.get("name"));
						return true;
					} catch (Exception e) {
						e.printStackTrace();
						if (token == null) {
							response.sendError(HttpStatus.UNAUTHORIZED.value(), e.getMessage());
						} else {
							response.sendError(HttpStatus.FORBIDDEN.value(), e.getMessage());
						}
						return false;
					}

				}
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
