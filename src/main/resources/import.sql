insert into regiones (id, nombre) values(1,'Sudamerica');
insert into regiones (id, nombre) values(2,'Norteamerica');
insert into regiones (id, nombre) values(3,'centro america');
insert into regiones (id, nombre) values(4,'Europa');
insert into regiones (id, nombre) values(5,'Africa');
insert into regiones (id, nombre) values(6,'Asia');
insert into regiones (id, nombre) values(7,'oceania');


insert into clientes (region_id,nombre, apellido, email, create_at) values(1,'Jose', 'Garcia','jorogae@gmail.com','2018-01-01');
insert into clientes (region_id,nombre, apellido, email, create_at) values(2,'Miguel', 'Sanchez','miguel@gmail.com','2018-01-01');
insert into clientes (region_id,nombre, apellido, email, create_at) values(3,'Toño', 'Nunez','toninu1@gmail.com','2018-01-01');
insert into clientes (region_id,nombre, apellido, email, create_at) values(4,'Macizo', 'Nunez','toninu2@gmail.com','2018-01-01');
insert into clientes (region_id,nombre, apellido, email, create_at) values(2,'Chango', 'Nunz','toninu3@gmail.com','2018-01-01');
insert into clientes (region_id,nombre, apellido, email, create_at) values(5,'Mengano', 'Nunz','toninu4@gmail.com','2018-01-01');
insert into clientes (region_id,nombre, apellido, email, create_at) values(6,'Sutano', 'Nunz','toninu5@gmail.com','2018-01-01');
insert into clientes (region_id,nombre, apellido, email, create_at) values(7,'Fulano', 'Nunz','toninu6@gmail.com','2018-01-01');
insert into clientes (region_id,nombre, apellido, email, create_at) values(5,'Muniain', 'Nunez','toninu7@gmail.com','2018-01-01');
insert into clientes (region_id,nombre, apellido, email, create_at) values(4,'Ander', 'Nunz','toninu8@gmail.com','2018-01-01');



insert into `usuarios` (username, password, enabled, nombre, apellido ,email) values ('roberto','$2a$10$SQmOwvGNFyLANDwTgsTD9eHsMqD6p52v5fQqf0HF.FXaXmcglmoVa',1, 'Andres', 'Guillermo', 'andres@gmail.com')
insert into `usuarios` (username, password, enabled, nombre, apellido, email) values ('admin','$2a$10$SQmOwvGNFyLANDwTgsTD9eHsMqD6p52v5fQqf0HF.FXaXmcglmoVa',1, 'Jose', 'Garcia', 'joserge@gmail.com')

insert into `roles` (nombre) values('ROLE_USER')
insert into `roles` (nombre) values('ROLE_ADMIN')

insert into `usuarios_roles` (usuario_id, role_id) values (1,1)
insert into `usuarios_roles` (usuario_id, role_id) values (2,2)
insert into `usuarios_roles` (usuario_id, role_id) values (2,1)


insert into `productos` (nombre, precio, create_at) values('Bateria panasonic', 25412, NOW());
insert into `productos` (nombre, precio, create_at) values('Clavos grandes', 0.125, NOW());
insert into `productos` (nombre, precio, create_at) values('Cemento CESA', 10.15, NOW());
insert into `productos` (nombre, precio, create_at) values('Inodoro marca Surron', 150.00, NOW());
insert into `productos` (nombre, precio, create_at) values('Lavaplatos', 70.63, NOW());
insert into `productos` (nombre, precio, create_at) values('Alarma carro', 201400, NOW());


insert into facturas(descripcion, observacion, cliente_id, create_at) values ('Factura equipos de oficina', null,1, NOW());
insert into facturas_items (cantidad, factura_id, producto_id) values(1,1,1);
insert into facturas_items (cantidad, factura_id, producto_id) values(2,1,2);
insert into facturas_items (cantidad, factura_id, producto_id) values(1,1,3);
insert into facturas_items (cantidad, factura_id, producto_id) values(1,1,4);

