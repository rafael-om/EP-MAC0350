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
    tipo_servico VARCHAR(50) NOT NULL,
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
    nome VARCHAR(60) NOT NULL,
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
CREATE OR REPLACE FUNCTION servicos_por_perfil()
RETURNS TABLE (nome VARCHAR, tipo_perfil VARCHAR, tipo_servico VARCHAR)
LANGUAGE plpgsql
AS $$
BEGIN
    RETURN QUERY 
        SELECT pes.nome, per.tipo_perfil, s.tipo_servico
        FROM pessoa AS pes, rel_pessoa_perfil AS r, perfil AS per, servico AS s
        WHERE pes.id_pessoa=r.id_pessoa AND r.id_perfil=per.id_perfil AND per.id_perfil=s.id_perfil
        GROUP BY pes.nome, per.tipo_perfil, s.tipo_servico;
END;
$$;

/* 3.2) */
CREATE OR REPLACE FUNCTION total_de_servicos_por_perfil()
RETURNS TABLE (tipo_perfil VARCHAR, total BIGINT)
LANGUAGE plpgsql
AS $$
BEGIN
    RETURN QUERY 
        SELECT per.tipo_perfil, COUNT (*)
        FROM perfil AS per, servico AS s, rel_pessoa_servico AS ps
        WHERE per.id_perfil=s.id_perfil AND s.id_servico=ps.id_servico
        GROUP BY per.tipo_perfil
        ORDER BY COUNT (*);
END;
$$;

/* 3.3) */
CREATE OR REPLACE FUNCTION disciplinas_mais_oferecidas()
RETURNS TABLE (id_oferecimento BIGINT, codigo_disciplina CHAR, nome_docente VARCHAR, nome_aluno VARCHAR)
LANGUAGE plpgsql
AS $$
BEGIN
    RETURN QUERY 
        SELECT oa.id_oferecimento_dd::bigint, disc.codigo_disciplina, p1.nome, p2.nome
        FROM pessoa AS p1, pessoa AS p2, aluno AS a, docente AS doc, oferecimento_disciplina_docente AS o, disciplina AS disc, rel_oferecimento_aluno AS oa
        WHERE p1.id_pessoa=doc.id_pessoa AND doc.id_docente=o.id_docente AND disc.id_disciplina=o.id_disciplina AND oa.id_oferecimento_dd=o.id_oferecimento_dd AND oa.id_aluno=a.id_aluno AND a.id_pessoa=p2.id_pessoa AND o.id_disciplina IN (
            SELECT odd.id_disciplina
            FROM oferecimento_disciplina_docente AS odd
            GROUP BY odd.id_disciplina
            ORDER BY COUNT(*) DESC
            LIMIT 5
        ) 
        GROUP BY disc.codigo_disciplina, oa.id_oferecimento_dd, p1.nome, p2.nome
        ORDER BY COUNT(DISTINCT oa.id_oferecimento_dd) DESC;
END;
$$;

/* 3.4) */
CREATE OR REPLACE FUNCTION docentes_mais_frequentes()
RETURNS TABLE (id_docente BIGINT, nome_docente VARCHAR)
LANGUAGE plpgsql
AS $$
BEGIN
    RETURN QUERY 
        SELECT o.id_docente::bigint, p.nome
        FROM pessoa AS p, docente AS doc, oferecimento_disciplina_docente AS o
        WHERE p.id_pessoa=doc.id_pessoa AND doc.id_docente=o.id_docente AND ((o.data_inicio >= '2020-05-01' AND o.data_inicio <= '2023-05-31') OR (o.data_inicio <= '2020-05-01' AND o.data_fim >= '2020-05-01')) AND o.id_docente IN (
            SELECT odd.id_docente
            FROM oferecimento_disciplina_docente AS odd
            GROUP BY odd.id_docente
            ORDER BY COUNT(*) DESC
            LIMIT 5
        )
        GROUP BY o.id_docente, p.nome
        ORDER BY COUNT(*) DESC;        
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
       (23452739012, 'Arnaldo', 'Avenida Prof. Luciano Gualberto', 'FEA - USP', '1983-07-01', 'arnaldo@example.com', 'senha789'),
       (21432739332, 'Adalberto', 'Avenida Prof. Luciano Gualberto', 'FEA - USP', '1983-07-01', 'adalberto@example.com', 'senha789'),
       (21432000032, 'Machado', 'Avenida Prof. Luciano Gualberto', 'FEA - USP', '1983-07-01', 'machado@example.com', 'senha789');

INSERT INTO aluno (id_pessoa, nota_ingresso, curso)
VALUES (1, 9.40, 'BCC'),
       (2, 7.65, 'Engenharia de Produção'),
       (3, 8.47, 'Administração'),
       (4, 6.54, 'BCC');

INSERT INTO funcionario (id_pessoa, especialidades, funcoes_tecnicas)
VALUES (5, 'administração', 'diretor'),
       (16, 'administração TI', 'suporte'),
       (17, 'secretaria', 'secretaria');

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
VALUES ('MAC0110', 'Introdução à Computação','Recursão','2022-06-01'),
       ('MAC0239', 'Introdução à Lógica','lógica','2022-06-01'),
       ('MAC0460', 'Aprendizado de Máquina','machine learning','2022-06-01'),
       ('MAC0425', 'Inteligência Artificial','IA','2022-06-01'),
       ('MAC0420', 'Computação Gráfica','computação gráfica','2022-06-01'),
       ('MAC0417', 'Processamento de Imagens','visão computacional','2022-06-01'),
       ('MAC0350', 'Desenvolvimento de Software','banco de dados','2022-06-01'),
       ('MAC0209', 'Modelagem','física','2022-06-01'),
       ('MAC0210', 'Métodos Númericos','métodos numéricos','2022-06-01');

INSERT INTO perfil (codigo_perfil, tipo_perfil)
VALUES ('aBcDeFgHiJ', 'Aluno'),
       ('KlMnOpQrSt', 'Docente'),
       ('uVwXyZ1234', 'Diretor'),
       ('FgHiJkLmNo', 'Secretário'),
       ('pQrStUvWxY', 'Suporte');

INSERT INTO rel_pessoa_perfil (id_pessoa, id_perfil)
VALUES (1, 1),
       (2, 1),
       (3, 1),
       (4, 1),
       (5, 3),
       (6, 2),
       (7, 2),
       (8, 2),
       (9, 2),
       (10, 2),
       (11, 3),
       (12, 2),
       (13, 2),
       (14, 2),
       (15, 2),
       (16, 5),
       (17, 4);

INSERT INTO servico (codigo_servico, id_perfil, tipo_servico)
VALUES ('ABCDEFGHIJ', 1, 'alteração de dados'),
       ('AFCDFFGFIJ', 1, 'matricula em disciplinas'),
       ('AFCDFFAAIA', 1, 'recuperação do histórico'),
       ('EECDEFGFIJ', 2, 'criação de disciplinas'),
       ('AFCDFFAAIO', 2, 'inserção das notas'),
       ('EECSESGFIJ', 3, 'acesso total'),
       ('AFCDSFSAIO', 4, 'acesso a finanças'),
       ('AFCTFFATIO', 5, 'controle dos sistemas');

INSERT INTO rel_pessoa_servico (id_pessoa, id_servico, data_uso_servico)
VALUES (1, 1, '2022-06-01'),
       (1, 1, '2022-06-02'),
       (1, 2, '2022-06-02'),
       (1, 3, '2022-06-03'),
       (2, 1, '2022-06-02'),
       (2, 2, '2022-06-03'),
       (3, 3, '2022-06-04'),
       (4, 2, '2022-06-05'),
       (6, 4, '2022-06-03'),
       (7, 4, '2022-06-02'),
       (8, 5, '2022-06-03'),
       (9, 5, '2022-06-04'),
       (10, 4, '2022-06-05'),
       (5, 6, '2022-06-03'),
       (16, 8, '2022-06-02'),
       (17, 7, '2022-06-03');

INSERT INTO oferecimento_disciplina_docente (codigo_oferecimento, id_disciplina, id_docente, data_inicio, data_fim)
VALUES ('AAAAAAAAAA',1,1,'2018-06-01','2018-07-01'),
       ('BBBBBBBBBB',2,1,'2021-06-01','2021-07-01'),
       ('CCCCCCCCCC',2,2,'2018-06-01','2018-07-01'),
       ('DDDDDDDDDD',2,3,'2021-06-01','2021-07-01'),
       ('EEEEEEEEEE',3,1,'2018-06-01','2018-07-01'),
       ('FFFFFFFFFF',3,3,'2021-06-01','2021-07-01'),
       ('GGGGGGGGGG',4,2,'2021-06-01','2021-07-01'),
       ('HHHHHHHHHH',4,4,'2021-06-01','2021-07-01'),
       ('IIIIIIIIII',5,4,'2018-06-01','2018-07-01'),
       ('JJJJJJJJJJ',5,5,'2021-06-01','2021-07-01'),
       ('KKKKKKKKKK',5,6,'2018-06-01','2018-07-01'),
       ('LLLLLLLLLL',5,7,'2021-06-01','2021-07-01'),
       ('MMMMMMMMMM',6,8,'2021-06-01','2021-07-01'),
       ('NNNNNNNNNN',7,8,'2018-06-01','2018-07-01'),
       ('OOOOOOOOOO',7,9,'2021-06-01','2021-07-01'),
       ('PPPPPPPPPP',8,9,'2018-06-01','2018-07-01'),
       ('QQQQQQQQQQ',8,9,'2021-06-01','2021-07-01'),
       ('RRRRRRRRRR',9,10,'2021-06-01','2021-07-01');

INSERT INTO rel_oferecimento_aluno (id_oferecimento_dd, id_aluno, nota)
VALUES (1, 1, 10.00),
       (1, 2, 10.00),
       (1, 3, 10.00),
       (1, 4, 10.00),
       (2, 1, 10.00),
       (2, 2, 10.00),
       (2, 3, 10.00),
       (2, 4, 10.00),
       (3, 1, 10.00),
       (3, 2, 10.00),
       (3, 3, 10.00),
       (3, 4, 10.00),
       (4, 1, 10.00),
       (4, 2, 10.00),
       (4, 3, 10.00),
       (4, 4, 10.00),
       (5, 1, 10.00),
       (5, 2, 10.00),
       (5, 3, 10.00),
       (5, 4, 10.00),
       (6, 1, 10.00),
       (6, 2, 10.00),
       (6, 3, 10.00),
       (6, 4, 10.00),
       (7, 1, 10.00),
       (7, 2, 10.00),
       (7, 3, 10.00),
       (7, 4, 10.00),
       (8, 1, 10.00),
       (8, 2, 10.00),
       (8, 3, 10.00),
       (8, 4, 10.00),
       (9, 1, 10.00),
       (9, 2, 10.00),
       (9, 3, 10.00),
       (9, 4, 10.00),
       (10, 1, 10.00),
       (10, 2, 10.00),
       (10, 3, 10.00),
       (10, 4, 10.00),
       (11, 1, 10.00),
       (11, 2, 10.00),
       (11, 3, 10.00),
       (11, 4, 10.00),
       (12, 1, 10.00),
       (12, 2, 10.00),
       (12, 3, 10.00),
       (12, 4, 10.00),
       (13, 1, 10.00),
       (13, 2, 10.00),
       (13, 3, 10.00),
       (13, 4, 10.00),
       (14, 1, 10.00),
       (14, 2, 10.00),
       (14, 3, 10.00),
       (14, 4, 10.00),
       (15, 1, 10.00),
       (15, 2, 10.00),
       (15, 3, 10.00),
       (15, 4, 10.00),
       (16, 1, 10.00),
       (16, 2, 10.00),
       (16, 3, 10.00),
       (16, 4, 10.00),
       (17, 1, 10.00),
       (17, 2, 10.00),
       (17, 3, 10.00),
       (17, 4, 10.00),
       (18, 1, 10.00),
       (18, 2, 10.00),
       (18, 3, 10.00),
       (18, 4, 10.00);
