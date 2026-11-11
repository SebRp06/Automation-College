# language: es
# ---------------------------------------------------------------------------
# "Antecedentes" son pasos que se ejecutan automáticamente ANTES de cada
# escenario de este archivo. Evitan repetir el inicio de sesión en cada uno.
# ---------------------------------------------------------------------------

@carrito
Característica: Carrito de compras
  Como cliente que ya inició sesión
  Quiero agregar y quitar productos del carrito
  Para preparar mi compra

  Antecedentes:
    Dado que "Carlos" inició sesión como usuario estándar

  Escenario: Agregar un producto al carrito
    Cuando agrega el producto "Sauce Labs Backpack" al carrito
    Entonces el carrito debe mostrar 1 producto

  # Una "tabla de datos" permite pasar una lista de valores a un solo paso
  Escenario: Agregar varios productos al carrito
    Cuando agrega los siguientes productos al carrito:
      | producto                |
      | Sauce Labs Backpack     |
      | Sauce Labs Bike Light   |
      | Sauce Labs Bolt T-Shirt |
    Entonces el carrito debe mostrar 3 productos
    Y al abrir el carrito debe ver el producto "Sauce Labs Bike Light"

  Escenario: Quitar un producto del carrito
    Dado que ya agregó el producto "Sauce Labs Onesie" al carrito
    Cuando quita el producto "Sauce Labs Onesie" del carrito
    Entonces el carrito debe mostrar 0 productos
