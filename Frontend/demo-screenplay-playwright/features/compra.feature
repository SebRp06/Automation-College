# language: es
# ---------------------------------------------------------------------------
# Flujo completo de punta a punta (End to End): desde elegir un producto
# hasta confirmar la compra. Es el escenario más valioso para el negocio.
# ---------------------------------------------------------------------------

@compra
Característica: Realizar una compra
  Como cliente que ya inició sesión
  Quiero pagar los productos de mi carrito
  Para recibirlos en mi casa

  Antecedentes:
    Dado que "Sofía" inició sesión como usuario estándar

  Escenario: Compra exitosa de un producto
    Dado que ya agregó el producto "Sauce Labs Fleece Jacket" al carrito
    Cuando realiza el pago con nombre "Sofía", apellido "Ramírez" y código postal "110111"
    Entonces debe ver el mensaje de confirmación "Thank you for your order!"

  Escenario: No se puede continuar el pago sin nombre
    Dado que ya agregó el producto "Sauce Labs Backpack" al carrito
    Cuando realiza el pago con nombre "", apellido "Ramírez" y código postal "110111"
    Entonces debe ver el mensaje de error "Error: First Name is required"
