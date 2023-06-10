CREATE TABLE pessoa (
    id_pessoa serial,
    cpf BIGINT(11) UNSIGNED NOT NULL,
    nome VARCHAR(75),
    endereco VARCHAR(100),
    instituicao VARCHAR(60),
    data_nascimento DATE,
    login_pessoa VARCHAR(30) NOT NULL,
    senha VARCHAR(30) NOT NULL,
    PRIMARY KEY(id_pessoa),
    UNIQUE(cpf,login_pessoa)
);

CREATE TABLE perfil (
    id_perfil serial,
    codigo_perfil CHAR(10) NOT NULL,
    tipo_perfil VARCHAR(25) NOT NULL,
    PRIMARY KEY(id_perfil),
    UNIQUE(codigo_perfil)
);

CREATE TABLE rel_pessoa_perfil (
    id_pessoa_perfil serial,
    cpf BIGINT(11) UNSIGNED NOT NULL,
    codigo_perfil CHAR(10) NOT NULL,
    PRIMARY KEY(id_pessoa_perfil),
    FOREIGN KEY (cpf) REFERENCES pessoa(cpf),
    FOREIGN KEY (codigo_perfil) REFERENCES perfil(codigo_perfil)
);

CREATE TABLE servico (
    id_servico serial,
    codigo_servico CHAR(10) NOT NULL,
    codigo_perfil CHAR(10) NOT NULL,
    tipo_servico VARCHAR(25) NOT NULL,
    PRIMARY KEY(id_servico),
    FOREIGN KEY (codigo_perfil) REFERENCES perfil(codigo_perfil),
    UNIQUE(codigo_servico)
);

CREATE TABLE rel_pessoa_servico (
    id_pessoa_servico serial,
    cpf BIGINT(11) UNSIGNED NOT NULL,
    codigo_servico CHAR(10) NOT NULL,
    data_uso_servico DATE,
    PRIMARY KEY(id_pessoa_servico),
    FOREIGN KEY (codigo_servico) REFERENCES servico(codigo_servico),
    FOREIGN KEY (cpf) REFERENCES pessoa(cpf)
);

CREATE TABLE aluno (
    id_aluno serial,
    cpf BIGINT(11) UNSIGNED NOT NULL,
    nota_ingresso NUMERIC(4,2) CHECK (nota_ingresso >= 0),
    curso VARCHAR(40),
    PRIMARY KEY(id_aluno),
    FOREIGN KEY (cpf) REFERENCES pessoa(cpf)
);

CREATE TABLE docente (
    id_docente serial,
    cpf BIGINT(11) UNSIGNED NOT NULL,
    especialidades VARCHAR(50),
    funcoes_tecnicas VARCHAR(50),
    PRIMARY KEY(id_docente),
    FOREIGN KEY (cpf) REFERENCES pessoa(cpf)
);

CREATE TABLE funcionario (
    id_funcionario serial,
    cpf BIGINT(11) UNSIGNED NOT NULL,
    especialidades VARCHAR(50),
    funcoes_tecnicas VARCHAR(50),
    PRIMARY KEY(id_funcionario),
    FOREIGN KEY (cpf) REFERENCES pessoa(cpf)
);

CREATE TABLE disciplina (
    id_disciplina serial,
    codigo_disciplina CHAR(10) NOT NULL,
    nome VARCHAR(40) NOT NULL,
    ementa VARCHAR(3000),
    data_criacao DATE,
    PRIMARY KEY(id_disciplina),
    UNIQUE(codigo_disciplina)
);

CREATE TABLE oferecimento_disciplina_docente (
    id_oferecimento_dd serial,
    codigo_oferecimento CHAR(10) NOT NULL,
    codigo_disciplina CHAR(10) NOT NULL,
    cpf_docente BIGINT(11) UNSIGNED NOT NULL,
    data_inicio DATE,
    data_fim DATE CHECK (data_fim >= data_inicio),
    PRIMARY KEY(id_disciplina),
    FOREIGN KEY (codigo_disciplina) REFERENCES disciplina(codigo_disciplina),
    FOREIGN KEY (cpf_docente) REFERENCES docente(cpf),
    UNIQUE(codigo_oferecimento)
);

CREATE TABLE rel_oferecimento_aluno (
    id_oferecimento_aluno serial,
    codigo_oferecimento CHAR(10) NOT NULL,
    cpf_aluno BIGINT(11) UNSIGNED NOT NULL,
    nota NUMERIC(4,2) CHECK (nota >= 0),
    PRIMARY KEY(id_oferecimento_aluno),
    FOREIGN KEY (codigo_oferecimento) REFERENCES oferecimento_disciplina_docente(codigo_oferecimento),
    FOREIGN KEY (cpf_aluno) REFERENCES aluno(cpf_aluno)
);

/* 3.1) */
SELECT nome, tipo_servico
FROM pessoa AS pes, rel_pessoa_perfil AS r, perfil AS per, servico AS s
WHERE pes.cpf=r.cpf AND r.codigo_perfil=per.codigo_perfil AND per.codigo_perfil=s.codigo_perfil

/* 3.2) */
SELECT tipo_perfil, COUNT (*)
FROM perfil AS per, servico AS s, rel_pessoa_servico AS ps
WHERE per.codigo_perfil=s.codigo_perfil AND s.codigo_servico=ps.codigo_servico
GROUP BY tipo_perfil
ORDER BY COUNT (*)

/* 3.3) */
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
)

/* 3.4) */

SELECT o.cpf, p1.nome, p2.nome
FROM pessoa AS p1, docente AS doc, oferecimento_disciplina_docente AS o
WHERE p1.cpf=doc.cpf AND doc.cpf=o.cpf_docente
GROUP BY o.codigo_disciplina
HAVING ( (o.data_inicio > '2020-05-01' AND o.data_fim <= '2023-05-31') OR (o.data_inicio >= '2020-05-01' AND o.data_fim >= '2023-05-01') OR (o.data_inicio > '2023-05-01' AND o.data_inicio <= '2023-05-31')) AND COUNT(*) >= (
    SELECT COUNT(*) 
    FROM oferecimento_disciplina_docente 
    GROUP BY codigo_disciplina
    ORDER BY COUNT(*) DESC
    LIMIT 1 OFFSET 4
)