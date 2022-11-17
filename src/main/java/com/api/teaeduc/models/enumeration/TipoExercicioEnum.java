package com.api.teaeduc.models.enumeration;

import lombok.Getter;

@Getter
public enum TipoExercicioEnum {
    PALAVRA_ORGANIZADAS("Arrastar palavra organizadas até sua imagem"),
	PALAVRA_DESORGANIZADAS("Arrastar palavra desorganizadas até sua imagem"),
	PALAVRA_DESORGANIZADAS_2("Arrastar palavra desorganizadas até sua imagem (2 imagens)"),
	PALAVRA_ORGANIZADAS_2("Arrastar palavra organizadas até sua imagem (2 imagens)"),
    LETRAS_ORGANIZADAS_ESCRITA("Arrastar letras organizadasaté sua sombra(Escrita)"),
	LETRAS_DESORGANIZADAS_ESCRITA("Arrastar letras desorganizadas até sua sombra(Escrita)"),
	LETRAS_ORGANIZADAS_SOM("Arrastar letras organizadasaté sua sombra(Som)"),
	LETRAS_DESORGANIZADAS_SOM("Arrastar letras desorganizadasaté sua sombra(Som)"),
	LETRAS_EMBARALHADAS("letras ficam embaralhadas(Embaralhado)"),
    SILABAS_ORGANIZADAS_ESCRITA("Arrastar sílabas organizadasaté sua sombra(Escrita)"),
	SILABAS_DESORGANIZADAS_ESCRITA("Arrastar sílabas desorganizadas até sua sombra(Escrita)"),
	SILABAS_ORGANIZADAS_SOM("Arrastar sílabas organizadasaté sua sombra(Som)"),
	SILABAS_DESORGANIZADAS_SOM("Arrastar sílabas desorganizadasaté sua sombra(Som)"),
	SILABAS_EMBARALHADAS("Silabas ficam embaralhadas(Embaralhado)");

    private String descricao;

    TipoExercicioEnum(String descicao){
        this.descricao = descicao;
    }

	public static TipoExercicioEnum fromString(String text) {
		for (TipoExercicioEnum b : TipoExercicioEnum.values()) {
			if (b.getDescricao().equalsIgnoreCase(text)) {
				return b;
			}
		}
		return null;
	}
}
