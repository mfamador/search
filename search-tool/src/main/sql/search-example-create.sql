CREATE TABLE utilizador (
    id                 int         NOT NULL,
    nome               VARCHAR(40) NOT NULL,
    bi                 int,
    num_empregado      int,
    id_tipo_utilizador int,
    telefone           int
);

CREATE TABLE tipo_utilizador (
    id                 int         NOT NULL,
    nome               VARCHAR(40) NOT NULL
);

CREATE VIEW utilizador_view as
SELECT
    a.id, a.nome, a.bi, a.num_empregado, 
    a.telefone, a.id_tipo_utilizador, b.nome as tipo
FROM
    utilizador a,
    tipo_utilizador b
WHERE
    a.id_tipo_utilizador = b.id;


INSERT INTO tipo_utilizador VALUES (1,  'Operador');
INSERT INTO tipo_utilizador VALUES (2,  'Admin');
INSERT INTO tipo_utilizador VALUES (3,  'Consulta');

INSERT INTO utilizador VALUES (1,  'nome 1',  234234234, 1,  1, 234234345);
INSERT INTO utilizador VALUES (2,  'nome 2',  234234234, 2,  2, 234234345);
INSERT INTO utilizador VALUES (3,  'nome 3',  234234234, 3,  1, 234234345);
INSERT INTO utilizador VALUES (4,  'nome 4',  234234234, 4,  1, 234234345);
INSERT INTO utilizador VALUES (5,  'nome 5',  234234234, 5,  1, 234234345);
INSERT INTO utilizador VALUES (6,  'nome 6',  234234234, 6,  1, 234234345);
INSERT INTO utilizador VALUES (7,  'nome 7',  234234234, 7,  3, 234234345);
INSERT INTO utilizador VALUES (8,  'nome 8',  234234234, 8,  1, 234234345);
INSERT INTO utilizador VALUES (9,  'nome 9',  234234234, 9,  1, 234234345);
INSERT INTO utilizador VALUES (10, 'nome 10', 234234234, 10, 1, 234234345);
INSERT INTO utilizador VALUES (11, 'nome 11', 234234234, 11, 2, 234234345);
INSERT INTO utilizador VALUES (12, 'nome 12', 234234234, 12, 1, 234234345);
INSERT INTO utilizador VALUES (13, 'nome 13', 234234234, 13, 1, 234234345);
INSERT INTO utilizador VALUES (14, 'nome 14', 234234234, 14, 1, 234234345);
INSERT INTO utilizador VALUES (15, 'nome 15', 234234234, 15, 1, 234234345);
INSERT INTO utilizador VALUES (16, 'nome 16', 234234234, 16, 1, 234234345);
INSERT INTO utilizador VALUES (17, 'nome 17', 234234234, 17, 1, 234234345);
INSERT INTO utilizador VALUES (18, 'nome 18', 234234234, 18, 1, 234234345);
INSERT INTO utilizador VALUES (19, 'nome 19', 234234234, 19, 1, 234234345);
INSERT INTO utilizador VALUES (20, 'nome 20', 234234234, 20, 1, 234234345);
INSERT INTO utilizador VALUES (21, 'nome 21', 234234234, 21, 2, 234234345);
INSERT INTO utilizador VALUES (22, 'nome 22', 234234234, 22, 3, 234234345);
INSERT INTO utilizador VALUES (23, 'nome 23', 234234234, 23, 1, 234234345);
INSERT INTO utilizador VALUES (24, 'nome 24', 234234234, 24, 1, 234234345);
INSERT INTO utilizador VALUES (25, 'nome 25', 234234234, 25, 1, 234234345);
INSERT INTO utilizador VALUES (26, 'nome 26', 234234234, 26, 1, 234234345);
INSERT INTO utilizador VALUES (27, 'nome 27', 234234234, 27, 3, 234234345);
INSERT INTO utilizador VALUES (28, 'nome 28', 234234234, 28, 2, 234234345);
INSERT INTO utilizador VALUES (29, 'nome 29', 234234234, 29, 1, 234234345);
INSERT INTO utilizador VALUES (30, 'nome 30', 234234234, 30, 1, 234234345);
INSERT INTO utilizador VALUES (31, 'nome 31', 234234234, 31, 1, 234234345);
INSERT INTO utilizador VALUES (32, 'nome 32', 234234234, 32, 3, 234234345);
INSERT INTO utilizador VALUES (33, 'nome 33', 234234234, 33, 3, 234234345);
INSERT INTO utilizador VALUES (34, 'nome 34', 234234234, 34, 1, 234234345);
INSERT INTO utilizador VALUES (35, 'nome 35', 234234234, 35, 1, 234234345);
INSERT INTO utilizador VALUES (36, 'nome 36', 234234234, 36, 2, 234234345);
INSERT INTO utilizador VALUES (37, 'nome 37', 234234234, 37, 1, 234234345);
INSERT INTO utilizador VALUES (38, 'nome 38', 234234234, 38, 1, 234234345);
INSERT INTO utilizador VALUES (39, 'nome 39', 234234234, 39, 1, 234234345);
INSERT INTO utilizador VALUES (40, 'nome 40', 234234234, 40, 1, 234234345);
INSERT INTO utilizador VALUES (41, 'nome 41', 234234234, 41, 1, 234234345);
INSERT INTO utilizador VALUES (42, 'nome 42', 234234234, 42, 1, 234234345);
INSERT INTO utilizador VALUES (43, 'nome 43', 234234234, 43, 1, 234234345);
INSERT INTO utilizador VALUES (44, 'nome 44', 234234234, 44, 1, 234234345);
INSERT INTO utilizador VALUES (45, 'nome 45', 234234234, 45, 1, 234234345);
INSERT INTO utilizador VALUES (46, 'nome 46', 234234234, 46, 1, 234234345);
INSERT INTO utilizador VALUES (47, 'nome 47', 234234234, 47, 1, 234234345);
INSERT INTO utilizador VALUES (48, 'nome 48', 234234234, 48, 1, 234234345);
INSERT INTO utilizador VALUES (49, 'nome 49', 234234234, 49, 1, 234234345);
INSERT INTO utilizador VALUES (50, 'nome 50', 234234234, 50, 1, 234234345);
INSERT INTO utilizador VALUES (51, 'nome 51', 234234234, 51, 1, 234234345);
INSERT INTO utilizador VALUES (52, 'nome 52', 234234234, 52, 1, 234234345);
INSERT INTO utilizador VALUES (53, 'nome 53', 234234234, 53, 3, 234234345);
INSERT INTO utilizador VALUES (54, 'nome 54', 234234234, 54, 1, 234234345);
