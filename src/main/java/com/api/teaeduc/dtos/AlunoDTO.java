package com.api.teaeduc.dtos;


import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AlunoDTO extends BaseDTO {
    private Long id;
    private String nome;
    private Long cpf;
    private String email;
    private Long idInstituicao;
    private Long idProfessor;
    private String senha;
    private String tipo;
    private List<AtividadeAlunoDTO> atividadesAluno;
    private ImagensExerciciosDTO imagensExerciciosDTO;
    private ProfessorDTO professorDTO;
}
