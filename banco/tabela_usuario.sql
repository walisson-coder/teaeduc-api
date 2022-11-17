drop table if exists teaeduc.usuario

CREATE TABLE teaeduc.usuario (
	ID_USUARIO INT not NULL AUTO_INCREMENT,
	CPF_CNPJ VARCHAR(20) NOT NULL,
	EMAIL VARCHAR(255) NOT NULL,
	SENHA VARCHAR(255),
	DT_ULTIMO_ACESSO DATE,
	TIPO VARCHAR(255) not null,
	ATIVO INT,
	primary key (ID_USUARIO)
);

alter table teaeduc.usuario
add unique (CPF_CNPJ, EMAIL);	