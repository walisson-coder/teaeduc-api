package com.api.teaeduc.exception;

import com.api.teaeduc.utils.BusinessException;

import lombok.Getter;

/**
 * UnauthozedBusinessException
 */
public class UnauthozedBusinessException extends BusinessException {

    /**
     *
     */
    private static final long serialVersionUID = 1L;


	@Getter
	private final String errorDescription;

    public 	UnauthozedBusinessException() {
        super("Não autorizado");
		this.errorDescription = "Sua sessão expirou";
    }
    
}