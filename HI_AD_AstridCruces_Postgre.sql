create database hitoindividualad;

drop table libro cascade;
drop table cliente cascade;

create table public.libro(
	id_libro serial primary key,
    titulo varchar(100),
    autor varchar(100),
    editorial varchar(50),
    stock int,
    precio float
);

create table public.cliente(
	id_usuario serial primary key,
    nombre varchar(90),
    apellidos varchar(90),
    tlf varchar(9),
    id_libro int,
    foreign key (id_libro) references libro(id_libro)
);

-- Líneas para empezar a trabajar:
insert into libro(titulo,autor,editorial,stock,precio) values ('Marina','Carlos Ruiz Zafon','Planeta',50,17.00);
insert into libro(titulo,autor,editorial,stock,precio) values('El principito','Antoine de Saint-ExupEry','Salamandra',20,8.99);
insert into libro(titulo,autor,editorial,stock,precio) values('Los juegos del hambre','Suzanne Collins','Molino',15,25.99);
insert into libro(titulo,autor,editorial,stock,precio) values('Rebeldes','Susan Eloise Hinton','Alfaguara',33,12.99);
insert into libro(titulo,autor,editorial,stock,precio) values('Wonder','Raquel Palacio','Nube de tinta',15,24.99);

insert into cliente(nombre,apellidos,tlf,id_libro) values('Ana','Lopez Cuevas','111111111',1);
insert into cliente(nombre,apellidos,tlf,id_libro) values('Marta','Pastor Garcia','222222222',2);
insert into cliente(nombre,apellidos,tlf,id_libro) values('Sofia','Herrero Martinez','333333333',3);
insert into cliente(nombre,apellidos,tlf,id_libro) values('Javier','Sanchez Carrizo','444444444',4);
insert into cliente(nombre,apellidos,tlf,id_libro) values('Astrid Carolina','Cruces Huamani','555555555',5);