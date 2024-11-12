create database SistemaPedidos;
-- usamos la base de datos
use SistemaPedidos;

CREATE TABLE IF NOT EXISTS Clientes (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    apellido VARCHAR(100) NOT NULL,
    direccion VARCHAR(255) NOT NULL,
    telefono VARCHAR(15) NOT NULL,
    empresa VARCHAR(100) NOT NULL
) ENGINE=InnoDB;



-- Crear la tabla Pedido
CREATE TABLE IF NOT EXISTS Pedido (
    id INT AUTO_INCREMENT PRIMARY KEY,
    medida VARCHAR(100) NOT NULL,
    miconaje VARCHAR(100) NOT NULL,
    material VARCHAR(100) NOT NULL,
    empresa VARCHAR(100) NOT NULL
) ENGINE=InnoDB;

-- Crear la tabla AlmacenDeposito
CREATE TABLE IF NOT EXISTS AlmacenDeposito (
    id INT AUTO_INCREMENT PRIMARY KEY,
    sector VARCHAR(100) NOT NULL,
    cliente VARCHAR(100) NOT NULL,
    cantidad INT NOT NULL
) ENGINE=InnoDB;

select * from sistemapedidos.clientes;
select * from sistemapedidos.Pedido;
select *from sistemapedidos.almacendeposito;



-- crear tabla usuarios
create table usuario(
idUsuario int (11) auto_increment primary key,
nombre varchar(30) not null,
apellido varchar(30) not null,
usuario varchar(15) not null,
password varchar(15) not null,
telefono varchar(15) not null,
estado int(1) not null
);


insert into usuario(nombre, apellido, usuario, password, telefono, estado)
values("Elvis", "Aranda", "elvis", "12345", "1158177128", 1);

select * from usuario;
ALTER TABLE usuario 
CHANGE usuario user varchar(15);
select usuario, password from tb_usuario where usuario = "edison" and password = "12345";

UPDATE clientes
SET apellido = "Inevntado" where id =8;

select * from clientes;










