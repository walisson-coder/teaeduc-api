package com.api.teaeduc.models.enumeration;

import lombok.Getter;

@Getter
public enum TipoAtividadeEnum {
    
    LETRAS("Atividade de Letras"),
    SILABAS("Atividade de Sílabas"),
    IMAGENS("Usando Imagens");

    private String descricao;

    TipoAtividadeEnum(String descicao){
        this.descricao = descicao;
    }

    public static TipoAtividadeEnum fromString(String text) {
		for (TipoAtividadeEnum b : TipoAtividadeEnum.values()) {
			if (b.getDescricao().equalsIgnoreCase(text)) {
				return b;
			}
		}
		return null;
	}
}
