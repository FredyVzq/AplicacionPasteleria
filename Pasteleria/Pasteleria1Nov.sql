--Productos
drop table if exists productos;
--Creando la tabla
create table productos(
	id serial primary key,
	nombre varchar(50) not null,
	codigo varchar(25) not null,
	tipo varchar(25) not null,
	precio double precision not null,
	estatus boolean not null,
	cantidad int not null,
	idmarca int,
	idcategoria int
);

--Agregando las llaves foraneas para categoria y marca
alter table productos add constraint prodmarca foreign key (idmarca) references marca(id);
alter table productos add constraint prodcat foreign key (idcategoria) references categoria(id);

drop  table if exists marca;
--Creando la tabla marca
create table marca (
	id serial primary key,
	nombremarca varchar (25) not null,
	proveedormarca varchar(25) not null,
	estatus boolean not null
);

drop table if exists categoria;
--creando la tabla categoria
create table categoria(
	id serial primary key,
	nombre varchar(25) not null,
	estatus boolean not null
);
--------------------------------------------------------------------------------------------------------------
Drop table if exists usuarios;
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
drop table if exists cliente;
create table cliente
(
idcliente serial not null primary key,	
nombre varchar(20) not null,
apePat varchar(20) not null,
apeMat varchar(20) not null,
ciudad varchar(20) not null,
colonia varchar(20) not null,
calle varchar(20) not null,
numeroCel varchar(15) not null,
numeroCasa varchar(15) not null,
estatus boolean not null
);
------------------------------------------------------------------------------------------------
drop table if exists bases;
create table bases
(
idBases serial not null primary key,
nombre varchar(50) not null,
precio int not null,
existencia int not null,
estatus boolean not null
);
drop table if exists pedido;
create table pedido
(
  id serial primary key,
  comentario character varying(250),
  idcliente integer,
  idproducto integer,
  idbase integer,
  fecharealizado date not null,
  fechaEntrega date not null,
  estatus boolean not null
 );
alter table pedido add constraint pedidocliente foreign key (idcliente) references cliente(idcliente);
alter table pedido add constraint pedidoproducto foreign key (idproducto) references productos(id);
alter table pedido add constraint pedidobase foreign key (idbase) references bases(idBases);

drop table if exists venta;
create table venta(
	idventa serial primary key not null,
	fecha date not null,
	montototal double precision not null,
	clienteid integer not null
);
alter table venta add constraint vtacliente foreign key (clienteid) references cliente(idcliente);

drop table if exists detallevta;
create table detallevta(
	id serial primary key not null,
	venta_id integer not null,
	producto_id integer not null,
	cantidad integer not null,
	precio double precision not null
);
alter table detallevta add constraint detvta foreign key (venta_id) references venta(idventa);
alter table detallevta add constraint detprod foreign key (producto_id) references productos(id);

