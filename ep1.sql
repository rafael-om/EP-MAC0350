CREATE TABLE pessoa (
    id_pessoa serial PRIMARY KEY,
    cpf NUMERIC(12) NOT NULL CHECK (cpf >= 0 AND cpf::text LIKE '___________'),
    nome VARCHAR(75),
    endereco VARCHAR(100),
    instituicao VARCHAR(60),
    data_nascimento DATE,
    login_pessoa VARCHAR(30) NOT NULL,
    senha VARCHAR(30) NOT NULL,
    CONSTRAINT uk_pessoa_cpf_login UNIQUE(cpf, login_pessoa)
);

CREATE TABLE perfil (
    id_perfil serial PRIMARY KEY,
    codigo_perfil CHAR(10) NOT NULL,
    tipo_perfil VARCHAR(25) NOT NULL,
    CONSTRAINT uk_perfil_codigo UNIQUE(codigo_perfil)
);

CREATE TABLE rel_pessoa_perfil (
    id_pessoa_perfil serial PRIMARY KEY,
    id_pessoa INTEGER NOT NULL,
    id_perfil INTEGER NOT NULL,
    FOREIGN KEY (id_pessoa) REFERENCES pessoa(id_pessoa),
    FOREIGN KEY (id_perfil) REFERENCES perfil(id_perfil)
);

CREATE TABLE servico (
    id_servico serial PRIMARY KEY,
    codigo_servico CHAR(10) NOT NULL,
    id_perfil INTEGER NOT NULL,
    tipo_servico VARCHAR(25) NOT NULL,
    FOREIGN KEY (id_perfil) REFERENCES perfil(id_perfil),
    CONSTRAINT uk_servico_codigo UNIQUE(codigo_servico)
);

CREATE TABLE rel_pessoa_servico (
    id_pessoa_servico serial PRIMARY KEY,
    id_pessoa INTEGER NOT NULL,
    id_servico INTEGER NOT NULL,
    data_uso_servico DATE,
    FOREIGN KEY (id_servico) REFERENCES servico(id_servico),
    FOREIGN KEY (id_pessoa) REFERENCES pessoa(id_pessoa)
);

CREATE TABLE aluno (
    id_aluno serial PRIMARY KEY,
    id_pessoa INTEGER NOT NULL,
    nota_ingresso NUMERIC(4,2) CHECK (nota_ingresso >= 0),
    curso VARCHAR(40),
    FOREIGN KEY (id_pessoa) REFERENCES pessoa(id_pessoa)
);

CREATE TABLE docente (
    id_docente serial PRIMARY KEY,
    id_pessoa INTEGER NOT NULL,
    especialidades VARCHAR(50),
    funcoes_tecnicas VARCHAR(50),
    FOREIGN KEY (id_pessoa) REFERENCES pessoa(id_pessoa)
);

CREATE TABLE funcionario (
    id_funcionario serial PRIMARY KEY,
    id_pessoa INTEGER NOT NULL,
    especialidades VARCHAR(50),
    funcoes_tecnicas VARCHAR(50),
    FOREIGN KEY (id_pessoa) REFERENCES pessoa(id_pessoa)
);

CREATE TABLE disciplina (
    id_disciplina serial PRIMARY KEY,
    codigo_disciplina CHAR(7) NOT NULL,
    nome VARCHAR(40) NOT NULL,
    ementa VARCHAR(3000),
    data_criacao DATE,
    CONSTRAINT uk_disciplina_codigo UNIQUE(codigo_disciplina)
);

CREATE TABLE oferecimento_disciplina_docente (
    id_oferecimento_dd serial PRIMARY KEY,
    codigo_oferecimento CHAR(10) NOT NULL,
    id_disciplina INTEGER NOT NULL,
    id_docente INTEGER NOT NULL,
    data_inicio DATE,
    data_fim DATE CHECK (data_fim >= data_inicio),
    FOREIGN KEY (id_disciplina) REFERENCES disciplina(id_disciplina),
    FOREIGN KEY (id_docente) REFERENCES docente(id_docente),
    CONSTRAINT uk_oferecimento_codigo UNIQUE(codigo_oferecimento)
);

CREATE TABLE rel_oferecimento_aluno (
    id_oferecimento_aluno serial PRIMARY KEY,
    id_oferecimento_dd INTEGER NOT NULL,
    id_aluno INTEGER NOT NULL,
    nota NUMERIC(4,2) CHECK (nota >= 0),
    FOREIGN KEY (id_oferecimento_dd) REFERENCES oferecimento_disciplina_docente(id_oferecimento_dd),
    FOREIGN KEY (id_aluno) REFERENCES aluno(id_aluno)
);

/* 3.1) */
CREATE OR REPLACE PROCEDURE servicos_por_perfil()
LANGUAGE plpgsql
AS $$
BEGIN
    SELECT nome, tipo_servico
    FROM pessoa AS pes, rel_pessoa_perfil AS r, perfil AS per, servico AS s
    WHERE pes.id_pessoa=r.id_pessoa AND r.id_perfil=per.id_perfil AND per.id_perfil=s.id_perfil;
END;
$$;

/* 3.2) */
CREATE OR REPLACE PROCEDURE total_de_servicos_por_perfil()
LANGUAGE plpgsql
AS $$
BEGIN
    SELECT tipo_perfil, COUNT (*)
    FROM perfil AS per, servico AS s, rel_pessoa_servico AS ps
    WHERE per.codigo_perfil=s.codigo_perfil AND s.codigo_servico=ps.codigo_servico
    GROUP BY tipo_perfil
    ORDER BY COUNT (*);
END;
$$;

/* 3.3) */
CREATE OR REPLACE PROCEDURE disciplinas_mais_oferecidas()
LANGUAGE plpgsql
AS $$
BEGIN
    SELECT o.codigo_disciplina, p1.nome, p2.nome
    FROM pessoa AS p1, pessoa AS p2, aluno AS a, docente AS doc, oferecimento_disciplina_docente AS o, disciplina AS disc, rel_oferecimento_aluno AS oa
    WHERE p1.cpf=doc.cpf AND doc.cpf=o.cpf_docente AND disc.codigo_disciplina=o.codigo_disciplina AND oa.codigo_oferecimento=o.codigo_oferecimento AND oa.cpf_aluno=a.cpf AND a.cpf=p2.cpf
    GROUP BY o.codigo_disciplina
    HAVING COUNT(*) >= (
        SELECT COUNT(*) 
        FROM oferecimento_disciplina_docente 
        GROUP BY codigo_disciplina
        ORDER BY COUNT(*) DESC
        LIMIT 1 OFFSET 4
    );
END;
$$;

/* 3.4) */
CREATE OR REPLACE PROCEDURE docentes_mais_frequentes()
LANGUAGE plpgsql
AS $$
BEGIN
    SELECT o.cpf, p1.nome, p2.nome
    FROM pessoa AS p1, docente AS doc, oferecimento_disciplina_docente AS o
    WHERE p1.cpf=doc.cpf AND doc.cpf=o.cpf_docente
    GROUP BY o.codigo_disciplina
    HAVING ((o.data_inicio >= '2020-05-01' AND o.data_inicio <= '2023-05-31') OR (o.data_inicio <= '2020-05-01' AND o.data_fim => '2020-05-01')) AND COUNT(*) >= (
        SELECT COUNT(*) 
        FROM oferecimento_disciplina_docente 
        GROUP BY codigo_disciplina
        ORDER BY COUNT(*) DESC
        LIMIT 1 OFFSET 4
    );
END;
$$;

/* Dados para testes */

INSERT INTO pessoa (cpf, nome, endereco, instituicao, data_nascimento, login_pessoa, senha)
VALUES (12345678912, 'Maria', 'Rua do Matão', 'IME - USP', '2000-05-01', 'maria@example.com', 'senha123'),
       (21987654321, 'Pedro', 'Avenida da Universidade', 'POLI - USP', '2000-06-01', 'pedro@example.com', 'senha456'),
       (11111111111, 'Ana', 'Avenida Prof. Luciano Gualberto', 'FEA - USP', '2000-07-01', 'ana@example.com', 'senha789'),
       (12345678901, 'João', 'Rua do Matão', 'IME - USP', '2002-05-01', 'joao@example.com', 'senha123'),
       (98765432109, 'Fernando', 'Avenida da Universidade', 'POLI - USP', '1976-06-01', 'fernando@example.com', 'senha456'),
       (45678901234, 'Júlia', 'Avenida Prof. Luciano Gualberto', 'FEA - USP', '1985-07-01', 'julia@example.com', 'senha789'),
       (56789012345, 'Lucas', 'Rua do Matão', 'IME - USP', '1960-05-01', 'lucas@example.com', 'senha123'),
       (89012345678, 'Antonio', 'Avenida da Universidade', 'POLI - USP', '1970-06-01', 'antonio@example.com', 'senha456'),
       (23456789012, 'Guilherme', 'Avenida Prof. Luciano Gualberto', 'FEA - USP', '1980-07-01', 'guilherme@example.com', 'senha789'),
       (90123456789, 'Eduardo', 'Avenida Prof. Luciano Gualberto', 'FEA - USP', '1984-07-01', 'eduardo@example.com', 'senha789'),
       (34567890123, 'Nelson', 'Rua do Matão', 'IME - USP', '1962-05-01', 'nelson@example.com', 'senha123'),
       (78901234567, 'Carlos', 'Avenida da Universidade', 'POLI - USP', '1971-06-01', 'carlos@example.com', 'senha456'),
       (67890123456, 'Gabriel', 'Avenida Prof. Luciano Gualberto', 'FEA - USP', '1987-07-01', 'gabriel@example.com', 'senha789'),
       (12343674901, 'Fábio', 'Avenida da Universidade', 'POLI - USP', '1969-06-01', 'fabio@example.com', 'senha456'),
       (23452739012, 'Arnaldo', 'Avenida Prof. Luciano Gualberto', 'FEA - USP', '1983-07-01', 'arnaldo@example.com', 'senha789');

INSERT INTO aluno (id_pessoa, nota_ingresso, curso)
VALUES (1, 9.40, 'BCC'),
       (2, 7.65, 'Engenharia de Produção'),
       (3, 8.47, 'Administração'),
       (4, 6.54, 'BCC');

INSERT INTO funcionario (id_pessoa, especialidades, funcoes_tecnicas)
VALUES (5, 'administração','diretor');

INSERT INTO docente (id_pessoa, especialidades, funcoes_tecnicas)
VALUES (6, 'Redes','professor associado 1'),
       (7, 'SO','professor aposentado'),
       (8, 'Machine Learning','professor titular'),
       (9, 'Computação Gráfica','professor titular'),
       (10, 'Estatística','professor aposentado'),
       (11, 'Sistemas Distribuidos','professor associado 2'),
       (12, 'Combinatória','professor titular'),
       (13, 'Matemática Discreta','professor aposentado'),
       (14, 'Ciência de Dados','professor associado 3'),
       (15, 'Banco de Dados','professor titular');

INSERT INTO disciplina (codigo_disciplina, nome, ementa, data_criacao)
VALUES (6, 'Redes','professor associado 1'),
       (7, 'SO','professor aposentado'),
       (8, 'Machine Learning','professor titular'),
       (9, 'Computação Gráfica','professor titular'),
       (10, 'Estatística','professor aposentado'),
       (11, 'Sistemas Distribuidos','professor associado 2'),
       (12, 'Combinatória','professor titular'),
       (13, 'Matemática Discreta','professor aposentado'),
       (14, 'Ciência de Dados','professor associado 3'),
       (15, 'Banco de Dados','professor titular');
