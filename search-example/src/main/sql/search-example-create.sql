create table UTILIZADOR (
    ID                 int         not null,
    NOME               varchar(40) not null,
    BI                 int,
    NUM_EMPREGADO      varchar(5),
    ID_TIPO_UTILIZADOR int,
    TELEFONE           int,
    DATA               date
);

create table TIPO_UTILIZADOR (
    ID                 int         not null,
    NOME               varchar(40) not null
);

create view UTILIZADOR_VIEW as
select
    A.ID, A.NOME, A.BI, A.NUM_EMPREGADO, 
    A.TELEFONE, A.ID_TIPO_UTILIZADOR, B.NOME as TIPO,
    A.DATA
from
    UTILIZADOR A,
    TIPO_UTILIZADOR B
where
    A.ID_TIPO_UTILIZADOR = B.ID;


INSERT INTO tipo_utilizador VALUES (1,  'Operador');
INSERT INTO tipo_utilizador VALUES (2,  'Admin');
INSERT INTO tipo_utilizador VALUES (3,  'Consulta');

INSERT INTO utilizador VALUES (1,  'nome 1',  234234234, null,  1, 234234345, '1994-02-23');
INSERT INTO utilizador VALUES (2,  'nome 2',  234234234, 'xb 02',  2, 234234345, '1994-02-23');
INSERT INTO utilizador VALUES (3,  'nome 3',  234234234, 'a2 03',  1, 234234345, '1994-02-23');
INSERT INTO utilizador VALUES (4,  'nome 4',  234234234, 'ab 04',  1, 234234345, '1994-02-23');
INSERT INTO utilizador VALUES (5,  'nome 5',  234234234, 'ax 05',  1, 234234345, '1994-02-23');
INSERT INTO utilizador VALUES (6,  'nome 6',  234234234, 'ab 06',  1, 234234345, '1994-02-23');
INSERT INTO utilizador VALUES (7,  'nome 7',  234234234, 'ab 07',  3, 234234345, '1994-02-23');
INSERT INTO utilizador VALUES (8,  'nome 8',  234234234, 'ab 08',  1, 234234345, '1994-02-23');
INSERT INTO utilizador VALUES (9,  'nome 9',  234234234, 'as 09',  1, 234234345, '1994-02-23');
INSERT INTO utilizador VALUES (10, 'nome 10', 234234234, 'ab 10', 1, 234234345, '1994-02-23');
INSERT INTO utilizador VALUES (11, 'nome 11', 234234234, 'ab 11', 2, 234234345, '1994-02-23');
INSERT INTO utilizador VALUES (12, 'nome 12', 234234234, 'sb 12', 1, 234234345, '1994-02-23');
INSERT INTO utilizador VALUES (13, 'nome 13', 234234234, 'ab 13', 1, 234234345, '1994-02-23');
INSERT INTO utilizador VALUES (14, 'nome 14', 234234234, 'bd 14', 1, 234234345, '1994-02-23');
INSERT INTO utilizador VALUES (15, 'nome 15', 234234234, 'ab 15', 1, 234234345, '1994-02-23');
INSERT INTO utilizador VALUES (16, 'nome 16', 234234234, 'ab 16', 1, 234234345, '1994-02-23');
INSERT INTO utilizador VALUES (17, 'nome 17', 234234234, 'ab 17', 1, 234234345, '1994-02-23');
INSERT INTO utilizador VALUES (18, 'nome 18', 234234234, 'ab 18', 1, 234234345, '1994-02-23');
INSERT INTO utilizador VALUES (19, 'nome 19', 234234234, 'dr 19', 1, 234234345, '1994-02-23');
INSERT INTO utilizador VALUES (20, 'nome 20', 234234234, 'tt 20', 1, 234234345, '1994-02-23');
INSERT INTO utilizador VALUES (21, 'nome 21', 234234234, 'tf 21', 2, 234234345, '1994-02-23');
INSERT INTO utilizador VALUES (22, 'nome 22', 234234234, 'ar 22', 3, 234234345, '1994-02-23');
INSERT INTO utilizador VALUES (23, 'nome 23', 234234234, 'ab 23', 1, 234234345, '1994-02-23');
INSERT INTO utilizador VALUES (24, 'nome 24', 234234234, 'aa 24', 1, 234234345, '1994-02-23');
INSERT INTO utilizador VALUES (25, 'nome 25', 234234234, 'ap 25', 1, 234234345, '1994-02-23');
INSERT INTO utilizador VALUES (26, 'nome 26', 234234234, 'ab 26', 1, 234234345, '1994-02-23');
INSERT INTO utilizador VALUES (27, 'nome 27', 234234234, 'ff 27', 3, 234234345, '1994-02-23');
INSERT INTO utilizador VALUES (28, 'nome 28', 234234234, 'ab 28', 2, 234234345, '1994-02-23');
INSERT INTO utilizador VALUES (29, 'nome 29', 234234234, 'ob 29', 1, 234234345, '1994-02-23');
INSERT INTO utilizador VALUES (30, 'nome 30', 234234234, 'ab 30', 1, 234234345, '1994-02-23');
INSERT INTO utilizador VALUES (31, 'nome 31', 234234234, 'ay 31', 1, 234234345, '1994-02-23');
INSERT INTO utilizador VALUES (32, 'nome 32', 234234234, 'rr 32', 3, 234234345, '1994-02-23');
INSERT INTO utilizador VALUES (33, 'nome 33', 234234234, 'ax 33', 3, 234234345, '1994-02-23');
INSERT INTO utilizador VALUES (34, 'nome 34', 234234234, 'ab 34', 1, 234234345, '1994-02-23');
INSERT INTO utilizador VALUES (35, 'nome 35', 234234234, 'ax 35', 1, 234234345, '1994-02-23');
INSERT INTO utilizador VALUES (36, 'nome 36', 234234234, 'ab 36', 2, 234234345, '1994-02-23');
INSERT INTO utilizador VALUES (37, 'nome 37', 234234234, 'yy 37', 1, 234234345, '1994-02-23');
INSERT INTO utilizador VALUES (38, 'nome 38', 234234234, 'ii 38', 1, 234234345, '1994-02-23');
INSERT INTO utilizador VALUES (39, 'nome 39', 234234234, 'at 39', 1, 234234345, '1994-02-23');
INSERT INTO utilizador VALUES (40, 'nome 40', 234234234, 'ab 40', 1, 234234345, '1994-02-23');
INSERT INTO utilizador VALUES (41, 'nome 41', 234234234, 'ft 41', 1, 234234345, '1994-02-23');
INSERT INTO utilizador VALUES (42, 'nome 42', 234234234, 'ab 42', 1, 234234345, '1994-02-23');
INSERT INTO utilizador VALUES (43, 'nome 43', 234234234, 'aj 43', 1, 234234345, '1994-02-23');
INSERT INTO utilizador VALUES (44, 'nome 44', 234234234, 'an 44', 1, 234234345, '1994-02-23');
INSERT INTO utilizador VALUES (45, 'nome 45', 234234234, 'mm 45', 1, 234234345, '1994-02-23');
INSERT INTO utilizador VALUES (46, 'nome 46', 234234234, 'ab 46', 1, 234234345, '1994-02-23');
INSERT INTO utilizador VALUES (47, 'nome 47', 234234234, 'gg 47', 1, 234234345, '1994-02-23');
INSERT INTO utilizador VALUES (48, 'nome 48', 234234234, 'ga 48', 1, 234234345, '1994-02-23');
INSERT INTO utilizador VALUES (49, 'nome 49', 234234234, 'ah 49', 1, 234234345, '1994-02-23');
INSERT INTO utilizador VALUES (50, 'nome 50', 234234234, 'ab 50', 1, 234234345, '1994-02-23');
INSERT INTO utilizador VALUES (51, 'nome 51', 234234234, 'pp 51', 1, 234234345, '1994-02-23');
INSERT INTO utilizador VALUES (52, 'nome 52', 234234234, 'jb 52', 1, 234234345, '1994-02-23');
INSERT INTO utilizador VALUES (53, 'nome 53', 234234234, 'aj 53', 3, 234234345, '1994-02-23');
INSERT INTO utilizador VALUES (54, 'nome 54', 234234234, 'jj 54', 1, 234234345, '1994-02-23');
