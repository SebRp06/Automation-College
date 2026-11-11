# language: es
# ---------------------------------------------------------------------------
# PROTOCOLOS DEMOSTRADOS AQUÍ: POST + GET + PUT con autenticación
#
#   POST -> crear una calificación (de 1 a 5 estrellas) para una pizza
#   GET  -> consultar mis calificaciones
#   PUT  -> ACTUALIZAR una calificación existente
#
# Estos servicios son PRIVADOS: necesitan el token que se obtiene al
# iniciar sesión. Por eso usamos "Antecedentes": pasos que se ejecutan
# automáticamente ANTES de cada escenario de este archivo.
# ---------------------------------------------------------------------------

@privado
Característica: Calificar pizzas
  Como usuaria registrada de QuickPizza
  Quiero calificar pizzas y actualizar mis calificaciones
  Para recordar cuáles me gustaron más

  Antecedentes:
    Dado que "Laura" es una usuaria registrada que inició sesión

  @post
  Escenario: Calificar una pizza
    Cuando califica la pizza 1 con 2 estrellas
    Entonces el código de respuesta debe ser 201
    Y la calificación en la respuesta debe tener 2 estrellas

  @get
  Escenario: Consultar mis calificaciones
    Dado que ya calificó la pizza 1 con 4 estrellas
    Cuando consulta sus calificaciones
    Entonces el código de respuesta debe ser 200
    Y debe ver al menos una calificación

  @put
  Escenario: Actualizar una calificación
    Dado que ya calificó la pizza 1 con 2 estrellas
    Cuando actualiza su calificación a 5 estrellas
    Entonces el código de respuesta debe ser 200
    Y la calificación en la respuesta debe tener 5 estrellas
    Y al consultarla nuevamente debe tener 5 estrellas
