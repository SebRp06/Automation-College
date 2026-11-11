# language: es
# ---------------------------------------------------------------------------
# ¿Qué es un archivo .feature?
#   Es la descripción de la prueba en lenguaje natural (Gherkin).
#   Lo puede leer cualquier persona del equipo: negocio, QA o desarrollo.
#   La primera línea "# language: es" le dice a Cucumber que usamos español.
#
#   Dado     -> el contexto o estado inicial
#   Cuando   -> la acción que se ejecuta
#   Entonces -> el resultado que esperamos
#   Y        -> continúa el paso anterior
#
# API bajo prueba: QuickPizza (https://quickpizza.grafana.com)
# PROTOCOLO DEMOSTRADO AQUÍ: POST (enviar información para CREAR algo)
# ---------------------------------------------------------------------------

@post @usuarios
Característica: Registro e inicio de sesión
  Como cliente de QuickPizza
  Quiero registrarme e iniciar sesión
  Para poder calificar las pizzas que me recomiendan

  Escenario: Registro exitoso de un usuario nuevo
    Dado que "Carlos" quiere usar la API de QuickPizza
    Cuando se registra con un usuario nuevo
    Entonces el código de respuesta debe ser 201

  Escenario: Inicio de sesión exitoso
    Dado que "Carlos" quiere usar la API de QuickPizza
    Y ya se registró con un usuario nuevo
    Cuando inicia sesión con su usuario y contraseña
    Entonces el código de respuesta debe ser 200
    Y la respuesta debe contener un token de acceso

  # Prueba negativa: también validamos cómo responde la API cuando algo sale mal
  Escenario: Inicio de sesión con contraseña incorrecta
    Dado que "Carlos" quiere usar la API de QuickPizza
    Y ya se registró con un usuario nuevo
    Cuando inicia sesión con la contraseña "clave-equivocada"
    Entonces el código de respuesta debe ser 401
