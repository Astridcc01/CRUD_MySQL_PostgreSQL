-- create database hitoindividualAD;
-- use hitoindividualAD;

-- drop table cliente;
-- drop table libro;

create table libro(
	id_libro int auto_increment primary key,
    titulo varchar(100),
    autor varchar(100),
    editorial varchar(50),
    stock int,
    precio float
);

create table cliente(
	id_usuario int auto_increment primary key,
    nombre varchar(90),
    apellidos varchar(90),
    tlf varchar(9),
    id_libro int,
    foreign key (id_libro) references libro(id_libro)
);

-- Líneas para empezar a trabajar:
insert into libro values(null,"Marina","Carlos Ruiz Zafon","Planeta",50,17.00);
insert into libro values(null,"El principito","Antoine de Saint-ExupEry","Salamandra",20,8.99);
insert into libro values(null,"Los juegos del hambre","Suzanne Collins","Molino",15,25.99);
insert into libro values(null,"Rebeldes","Susan Eloise Hinton","Alfaguara",33,12.99);
insert into libro values(null,"Wonder","Raquel Palacio","Nube de tinta",15,24.99);

insert into cliente values(null,"Ana","Lopez Cuevas","111111111",1);
insert into cliente values(null,"Marta","Pastor Garcia","222222222",2);
insert into cliente values(null,"Sofia","Herrero Martinez","333333333",3);
insert into cliente values(null,"Javier","Sanchez Carrizo","444444444",4);
insert into cliente values(null,"Astrid Carolina","Cruces Huamani","555555555",5);
