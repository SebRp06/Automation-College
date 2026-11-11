# language: es
# ---------------------------------------------------------------------------
# ¿Qué es un archivo .feature?
#   Es la descripción de la prueba en lenguaje natural (Gherkin).
#   Cualquier persona del equipo lo puede leer: negocio, QA o desarrollo.
#
#   Dado     -> el contexto o estado inicial
#   Cuando   -> la acción que realiza el usuario
#   Entonces -> el resultado que esperamos ver
#   Y        -> continúa el paso anterior
#
# Sitio bajo prueba: https://www.saucedemo.com (tienda de práctica)
# ---------------------------------------------------------------------------

@login
Característica: Inicio de sesión en Swag Labs
  Como cliente de la tienda
  Quiero iniciar sesión con mi usuario
  Para poder ver y comprar productos

  Escenario: Inicio de sesión exitoso
    Dado que "Ana" está en la página de inicio de sesión
    Cuando inicia sesión con usuario "standard_user" y contraseña "secret_sauce"
    Entonces debe ver la página de productos

  # Un "Esquema del escenario" ejecuta el MISMO escenario una vez por cada
  # fila de la tabla "Ejemplos". Así probamos varios datos sin repetir pasos.
  Esquema del escenario: Inicio de sesión fallido
    Dado que "Ana" está en la página de inicio de sesión
    Cuando inicia sesión con usuario "<usuario>" y contraseña "<clave>"
    Entonces debe ver el mensaje de error "<mensaje>"

    Ejemplos:
      | usuario         | clave          | mensaje                                                                   |
      | locked_out_user | secret_sauce   | Epic sadface: Sorry, this user has been locked out.                       |
      | standard_user   | clave_invalida | Epic sadface: Username and password do not match any user in this service |
      |                 | secret_sauce   | Epic sadface: Username is required                                        |
