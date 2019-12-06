/*==============================================================*/
/* DBMS name:      Microsoft SQL Server 2016                    */
/* Created on:     12/2/2019 9:23:29 PM                         */
/*==============================================================*/


if exists (select 1
   from sys.sysreferences r join sys.sysobjects o on (o.id = r.constid and o.type = 'F')
   where r.fkeyid = object_id('SESION') and o.name = 'FK_SESION_REFERENCE_PERSONA')
alter table SESION
   drop constraint FK_SESION_REFERENCE_PERSONA
go

if exists (select 1
            from  sysobjects
           where  id = object_id('PERSONA')
            and   type = 'U')
   drop table PERSONA
go

if exists (select 1
            from  sysobjects
           where  id = object_id('SESION')
            and   type = 'U')
   drop table SESION
go

/*==============================================================*/
/* Table: PERSONA                                               */
/*==============================================================*/
create table PERSONA (
   ID_PER               integer              identity,
   CEDULA_PER           varchar(10)          null,
   NOMBRE_PER           varchar(60)          null,
   APELLIDO_PER         varchar(60)          null,
   NACIMIENTO_PER       date                 null,
   RESIDENCIA_PER       varchar(250)         null,
   constraint PK_PERSONA primary key (ID_PER)
)
go

/*==============================================================*/
/* Table: SESION                                                */
/*==============================================================*/
create table SESION (
   ID_SES               integer              identity,
   ID_PER               integer              null,
   TIPO_SES             varchar(60)          null,
   REPETICIONES_ESTABLESIDAS_SES integer              null,
   REPETICIONES_COMPLETADAS_SES integer              null,
   TIEMPO_SES           integer              null,
   FECHA_SES            date                 null,
   constraint PK_SESION primary key (ID_SES)
)
go

alter table SESION
   add constraint FK_SESION_REFERENCE_PERSONA foreign key (ID_PER)
      references PERSONA (ID_PER)
go

