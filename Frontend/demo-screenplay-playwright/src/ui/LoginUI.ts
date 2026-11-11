import { Objetivo } from '../screenplay/Objetivo';

/**
 * ===========================================================================
 * UI → LoginUI   (página: https://www.saucedemo.com/)
 * ===========================================================================
 * En la carpeta "ui" guardamos los ELEMENTOS de cada página: campos,
 * botones, mensajes... y cómo encontrarlos (selectores).
 *
 * Aquí NO hay acciones (clic, escribir). Solo "dónde están las cosas".
 *
 * TIP: para descubrir los selectores, abre la página, clic derecho →
 * "Inspeccionar". saucedemo usa el atributo data-test, por ejemplo:
 *   <input data-test="username">   →   page.getByTestId('username')
 */
export const LoginUI = {
  CAMPO_USUARIO: Objetivo.elemento('el campo usuario', (page) => page.getByTestId('username')),
  CAMPO_CLAVE: Objetivo.elemento('el campo contraseña', (page) => page.getByTestId('password')),
  BOTON_INGRESAR: Objetivo.elemento('el botón Login', (page) => page.getByTestId('login-button')),
};
