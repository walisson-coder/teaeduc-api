drop table if exists teaeduc.ALUNO

CREATE TABLE teaeduc.ALUNO (
	ID_ALUNO INT NOT NULL AUTO_INCREMENT,
	ID_PROFESSOR INT NOT NULL,
	ID_INSTITUICAO INT NOT NULL,
	NOME VARCHAR(255) NOT NULL,
	EMAIL  VARCHAR(255) NOT NULL,
	primary key (ID_ALUNO),
	CONSTRAINT fkProfessorAluno FOREIGN KEY (ID_PROFESSOR) REFERENCES professor(ID_PROFESSOR),
	CONSTRAINT fkInstituicaoAluno FOREIGN KEY (ID_INSTITUICAO) REFERENCES instituicao(ID_INSTITUICAO)
);

alter table teaeduc.ALUNO
add unique (EMAIL);