package com.api.teaeduc.interceptors;

import static org.springframework.web.context.request.RequestAttributes.SCOPE_REQUEST;

import java.util.Arrays;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.api.teaeduc.exception.UnauthozedBusinessException;
import com.api.teaeduc.interceptors.bean.UsuarioLogadoBean;
import com.api.teaeduc.utils.JWTUtil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class AuthorizationInterceptor extends HandlerInterceptorAdapter  {

	@Value("${publicApi}")
	private String publicApiPadrao;

	public static final String BEARER = "Bearer";

	Logger log = LoggerFactory.getLogger(getClass().getName());

	@Resource(name = "requestScopeUsuarioLogado")
	private UsuarioLogadoBean requestScopeUsuarioLogado;

	public List<String> rotasPermitidas() {
		return Arrays.asList(
			"/api/imagens"
		);
	}

	public boolean rotaPermitida(String rotaParaAnalisar) {
		boolean permitido = false;

		for (String rotaPermitida: rotasPermitidas()) {
			if (rotaPermitida.contains("**")) {
				String[] split = rotaPermitida.split("\\*\\*");
				String inicioRotaPermitida = split[0];
				if (rotaParaAnalisar.startsWith(inicioRotaPermitida)) {
					return true;
				}
			} else {
				if (rotaParaAnalisar.contains(rotaPermitida)) {
					return true;
				}
			}
		}

		return permitido;
	}

	@Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		
		if (request.getServletPath() != null && request.getServletPath().startsWith("/api")) {
			if (
				request.getServletPath().startsWith(publicApiPadrao) 
				|| request.getRequestURI().startsWith(publicApiPadrao) 
				|| rotaPermitida(request.getServletPath())
				|| rotaPermitida(request.getRequestURI())
			) {

				log.debug("Acesso em requisição publica interceptada: {}", request.getServletPath());
			} else {
				log.debug("Acesso em requisição privada interceptada: {}", request.getServletPath());

				String authorizationHeader = request.getHeader("authorization");

				if(
						authorizationHeader == null || 
						authorizationHeader.isEmpty() || 
						!authorizationHeader.startsWith(BEARER)) {
					throw new UnauthozedBusinessException();
					
				}

				String token = authorizationHeader.substring(BEARER.length()).trim();
				Boolean tokenValido = JWTUtil.tokenValido(token);

				if (Boolean.FALSE.equals(tokenValido)){
					throw new UnauthozedBusinessException();
				}

				String username = JWTUtil.getUsername(token);

				requestScopeUsuarioLogado.setUsername(username);

				RequestContextHolder
                .currentRequestAttributes()
                .setAttribute("Username", username, SCOPE_REQUEST);
			}
		} else {
			log.debug("Acesso em requisição fora das regras interceptada: Request URI {}", request.getRequestURI());
			log.debug("Acesso em requisição fora das regras interceptada: Request URL {}", request.getRequestURL());
			log.debug("Acesso em requisição fora das regras interceptada: Context Path {}", request.getContextPath());
			log.debug("Acesso em requisição fora das regras interceptada: Servlet Path {}", request.getServletPath());
			log.debug("Acesso em requisição fora das regras interceptada: Path Info {}", request.getPathInfo());
		}
		
        return super.preHandle(request, response, handler);
    }

	

}