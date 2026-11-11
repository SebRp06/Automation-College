# language: es
# ---------------------------------------------------------------------------
# PROTOCOLO DEMOSTRADO AQUÍ: POST con un cuerpo (body) más elaborado
#
# Le enviamos a la API nuestras "restricciones" en formato JSON y ella nos
# responde con una pizza recomendada:
#   { "maxCaloriesPerSlice": 500, "mustBeVegetarian": false, ... }
#
# Este servicio usa un token "público" fijo (no hay que iniciar sesión).
#
# Un "Esquema del escenario" ejecuta el MISMO escenario una vez por cada
# fila de la tabla "Ejemplos". Ideal para probar varios datos sin repetir.
# ---------------------------------------------------------------------------

@post @pizzas
Característica: Recomendar una pizza
  Como cliente con hambre
  Quiero pedir una recomendación de pizza con mis preferencias
  Para descubrir una pizza nueva

  Esquema del escenario: Pedir una pizza con máximo <calorias> calorías por porción
    Dado que "Ana" quiere usar la API de QuickPizza
    Cuando pide una pizza de máximo <calorias> calorías por porción con entre <min> y <max> ingredientes
    Entonces el código de respuesta debe ser 200
    Y la pizza recomendada debe tener un nombre

    Ejemplos:
      | calorias | min | max |
      | 500      | 2   | 5   |
      | 800      | 3   | 6   |

  Escenario: Pedir una pizza vegetariana
    Dado que "Ana" quiere usar la API de QuickPizza
    Cuando pide una pizza vegetariana
    Entonces el código de respuesta debe ser 200
    Y la pizza recomendada debe ser vegetariana
