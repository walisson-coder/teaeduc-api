package com.api.teaeduc.configuration;

import com.api.teaeduc.interceptors.AuthorizationInterceptor;
import com.api.teaeduc.interceptors.bean.UsuarioLogadoBean;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;


import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Component
public class ConfigInterceptor extends WebMvcConfigurerAdapter {

    private final AuthorizationInterceptor inperceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(inperceptor);
    }

	@Bean
	@RequestScope
	public UsuarioLogadoBean requestScopeUsuarioLogado() {
		return new UsuarioLogadoBean();
	}
}
