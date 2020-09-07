CREATE TABLE agenda (
	idAgenda BIGINT(20) PRIMARY KEY AUTO_INCREMENT,
	createdBy VARCHAR(255),
    createdDate DATETIME,
    modifieldBy VARCHAR(255),
    modifieldDate DATETIME,
	cliente VARCHAR(255) NOT NULL,
	contato VARCHAR(255) NOT NULL,
    dataRetorno DATETIME,
    dataVisita DATE,
    observacao VARCHAR(255),
    orcamentoNumber VARCHAR(11),
    status INT(11),
	telefone VARCHAR(11),
    tipoCliente INT(11) NOT NULL,
    valorOrcamento DECIMAL(19,2),
    idUsuario BIGINT(20)
) ENGINE=InnoDB DEFAUlT CHARSET=utf8;

CREATE TABLE genderDate (
	idGenderDat BIGINT(20) PRIMARY KEY AUTO_INCREMENT,
    dataVisita DATE,
    dataRetorno DATETIME,
    status INT(11),
    observacao VARCHAR(255)
) ENGINE=InnoDB DEFAUlT CHARSET=utf8;

ALTER TABLE genderDate ADD COLUMN agendaId BIGINT(20), 
						ADD CONSTRAINT fk_agendaId
                        FOREIGN KEY (agendaId) 
                        REFERENCES agenda(idAgenda);

CREATE TABLE usuario (
	idUsuario BIGINT(20) PRIMARY KEY AUTO_INCREMENT,
    usuario VARCHAR(255) NOT NULL,
    nome VARCHAR(255) NOT NULL,
    senha VARCHAR(255) NOT NULL,
    ativo BIT(1) NOT NULL,
    email VARCHAR(255) NOT NULL
) ENGINE=InnoDB DEFAUlT CHARSET=utf8;

CREATE TABLE perfis (
	perfil INT(11)
) ENGINE=InnoDB DEFAUlT CHARSET=utf8;

ALTER TABLE perfis ADD COLUMN usuarioId BIGINT(20), 
						ADD CONSTRAINT fk_usuarioId
                        FOREIGN KEY (perfis)
                        REFERENCES usuario(idUsuario);