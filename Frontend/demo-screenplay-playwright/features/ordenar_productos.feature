# language: es

@ordenar
Característica: Ordenar el catálogo de productos
  Como cliente que ya inició sesión
  Quiero ordenar los productos
  Para encontrar más rápido lo que busco

  Antecedentes:
    Dado que "Laura" inició sesión como usuario estándar

  Esquema del escenario: Ordenar productos por "<criterio>"
    Cuando ordena los productos por "<criterio>"
    Entonces el primer producto de la lista debe ser "<primer_producto>"

    Ejemplos:
      | criterio            | primer_producto                   |
      | Name (A to Z)       | Sauce Labs Backpack               |
      | Name (Z to A)       | Test.allTheThings() T-Shirt (Red) |
      | Price (low to high) | Sauce Labs Onesie                 |
      | Price (high to low) | Sauce Labs Fleece Jacket          |
