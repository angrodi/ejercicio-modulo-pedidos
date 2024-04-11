insert into sucursal(descripcion) values('Sucursal 01');
insert into sucursal(descripcion) values('Sucursal 02');

insert into vendedor(nombre_completo) values('Diego Rodriguez');
insert into vendedor(nombre_completo) values('Karen Diaz');

insert into forma_pago(descripcion) values('FORMA PAGO 01');
insert into forma_pago(descripcion) values('FORMA PAGO 02');

insert into cliente(razon_social) values('RAZON SOCIAL 01');
insert into cliente(razon_social) values('RAZON SOCIAL 02');

insert into producto(descripcion) values('PRODUCTO 01');
insert into producto(descripcion) values('PRODUCTO 02');

insert into stock_resumen(cantidad_disponible, cantidad_en_pedido, id_producto, id_sucursal) values(500, 0, 1, 1);
insert into stock_resumen(cantidad_disponible, cantidad_en_pedido, id_producto, id_sucursal) values(600, 0, 1, 2);
insert into stock_resumen(cantidad_disponible, cantidad_en_pedido, id_producto, id_sucursal) values(700, 0, 2, 1);
insert into stock_resumen(cantidad_disponible, cantidad_en_pedido, id_producto, id_sucursal) values(200, 0, 2, 2);

insert into estado_pedido(descripcion) values('BORRADOR');
insert into estado_pedido(descripcion) values('PENDIENTE_EVALUACION');
insert into estado_pedido(descripcion) values('APROBADO');
insert into estado_pedido(descripcion) values('DESAPROBADO');

insert into usuario(nombre_completo) values('Marco Silverio');
insert into usuario(nombre_completo) values('Luis Almeyda');
insert into usuario(nombre_completo) values('Ivan Moran');
insert into usuario(nombre_completo) values('Ibarra');
insert into usuario(nombre_completo) values('Perlacios');
insert into usuario(nombre_completo) values('Ortiz');
insert into usuario(nombre_completo) values('Eche');