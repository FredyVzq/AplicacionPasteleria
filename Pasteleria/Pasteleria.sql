--Productos
drop table productos
--Creando la tabla
create table productos(
	id serial primary key,
	nombre varchar(50) not null,
	codigo varchar(25) not null,
	tipo varchar(25) not null,
	precio varchar(7) not null,
	estatus boolean not null,
	idmarca int,
	idcategoria int
);

--Agregando las llaves foraneas para categoria y marca
alter table productos add constraint prodmarca foreign key (idmarca) references marca(id)
alter table productos add constraint prodcat foreign key (idcategoria) references categoria(id)

drop  table marca
--Creando la tabla marca
create table marca (
	id serial primary key,
	nombremarca varchar (25) not null,
	proveedormarca varchar(25) not null,
	estatus boolean not null
);

drop table categoria
--creando la tabla categoria
create table categoria(
	id serial primary key,
	nombre varchar(25) not null,
	estatus boolean not null
);
--------------------------------------------------------------------------------------------------------------
Drop table usuarios
CREATE TABLE usuarios(
	idUsuario serial NOT NULL,
	NomUsuario varchar(10) NOT NULL,
	Contrasenia varchar(15) NOT NULL,
	Nivel varchar(15) NOT NULL,
	estatus boolean NOT NULL,
	UsuarioManejador varchar(15) NOT NULL, 
	primary key(IdUsuario)
);
INSERT INTO usuarios(IdUsuario,nomUsuario,contrasenia, nivel, estatus,UsuarioManejador) VALUES (default,'admin','12345', 'administrador', true,'UsuarioEjemplo');
INSERT INTO usuarios(Idusuario,nomUsuario,contrasenia, nivel, estatus,UsuarioManejador) VALUES (default,'user', '67890', 'usuario', true,'UsuarioEjemplo');
---------------------------------------------------------------------------------------------------------------
CREATE TABLE FORMULARIO(
	IdFormulario serial NOT NULL,
	NombreFormulario varchar(15) NOT NULL,
	DescripcionFormulario varchar(30) NOT NULL,
	FechaCreacion date NOT NULL,
	FechaEdicion date,
	UsuarioManejador varchar(15) NOT NULL, 
	estatus boolean,
	primary key(IdFormulario)
);
-----------------------------------------------------------------------------------------------
drop table cliente
create table cliente
(
idcliente serial not null primary key,	
nombre varchar(20) not null,
apePat varchar(20) not null,
apeMat varchar(20) not null,
ciudad varchar(50) not null,
colonia varchar(50),
calle varchar(50) not null,
numeroCel varchar(15) not null,
numeroCasa varchar(15) not null,
estatus boolean not null
);
------------------------------------------------------------------------------------------------
create table bases
(
idBases serial not null primary key,
nombre varchar(50) not null,
precio int not null,
existencia int not null,
estatus boolean not null
);
drop table pedido
create table pedido
(
  id serial primary key,
  comentario character varying(250),
  idcliente integer,
  idproducto integer,
  idbase integer
 );
alter table pedido add constraint pedidocliente foreign key (idcliente) references cliente(idcliente);
alter table pedido add constraint pedidoproducto foreign key (idproducto) references productos(id);
alter table pedido add constraint pedidobase foreign key (idbase) references bases(idBases);


