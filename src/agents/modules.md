# Orders

Responsable de administrar los pedidos.

## Funciones

- Crear pedido
- Editar pedido
- Cancelar pedido
- Consultar pedido
- Cambiar estado
- Agregar productos
- Eliminar productos

## Entidades

Order

OrderDetail

## Estados

CREATED

SENT_TO_KITCHEN

PREPARING

READY

DELIVERED

PAID

CANCELLED

## Relaciones

Order

- Table
- Customer
- Employee
- Payment
- OrderDetails