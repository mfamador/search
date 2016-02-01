prompt 
prompt ===========================================
prompt Installing Search Database Model...
prompt ===========================================
prompt 

prompt Creating table REPORT...
prompt

create table REPORT
(
  id            NUMBER(19) not null,
  changedate    TIMESTAMP(6),
  creationdate  TIMESTAMP(6),
  creatorid     VARCHAR2(255 CHAR),
  reportname    VARCHAR2(255 CHAR),
  searchname    VARCHAR2(255 CHAR),
  state         NUMBER(10) not null,
  reporttype    VARCHAR2(255 CHAR),
  changedby     VARCHAR2(255 CHAR),
  reportCount   NUMBER(10),
  slidingWindow NUMBER(10)
);

prompt Creating table ORDERPARAM...
prompt

create table ORDERPARAM
(
  name         VARCHAR2(255 CHAR) not null,
  displayorder NUMBER(10),
  ordertype    VARCHAR2(255 CHAR),
  report_id    NUMBER(19) not null
);

prompt Creating table REPORTENTITY...
prompt

create table REPORTENTITY
(
  entityid     VARCHAR2(255 CHAR) not null,
  entitytypeid VARCHAR2(255 CHAR) not null,
  permissionid VARCHAR2(255 CHAR),
  report_id    NUMBER(19) not null
);

prompt Creating table RESULTPARAM...
prompt

create table RESULTPARAM
(
  name         VARCHAR2(255 CHAR) not null,
  displayorder NUMBER(10),
  report_id    NUMBER(19) not null
);

prompt Creating table SEARCHPARAM...
prompt

create table SEARCHPARAM
(
  name      VARCHAR2(255 CHAR) not null,
  value     VARCHAR2(255 CHAR),
  report_id NUMBER(19) not null
);


prompt Creating primary and foreign keys...
prompt

alter table REPORT 
add primary key (ID);

alter table ORDERPARAM
add primary key (NAME, REPORT_ID);

alter table ORDERPARAM
add constraint ORDERPARAM_X_REPORT_FK foreign key (REPORT_ID)
references REPORT (ID);

alter table REPORTENTITY
add primary key (ENTITYID, ENTITYTYPEID, REPORT_ID);

alter table REPORTENTITY
add constraint REPORTENTITY_X_REPORT_FK foreign key (REPORT_ID)
references REPORT (ID);

alter table RESULTPARAM
add primary key (NAME, REPORT_ID);

alter table RESULTPARAM
add constraint RESULTPARAM_X_REPORT_FK foreign key (REPORT_ID)
references REPORT (ID);

alter table SEARCHPARAM
add primary key (NAME, REPORT_ID);

alter table SEARCHPARAM
add constraint SEARCHPARAM_X_REPORT_FK foreign key (REPORT_ID)
references REPORT (ID);

prompt Creating sequence HIBERNATE_SEQUENCE...
prompt

create sequence HIBERNATE_SEQUENCE
minvalue 1
maxvalue 999999999999999999999999999
start with 1
increment by 1
cache 20;
