import { Objetivo } from '../screenplay/Objetivo';

/**
 * ===========================================================================
 * UI → PagoUI   (páginas de checkout: datos, resumen y confirmación)
 * ===========================================================================
 */
export const PagoUI = {
  // Paso 1: "Your Information"
  CAMPO_NOMBRE: Objetivo.elemento('el campo nombre', (page) => page.getByTestId('firstName')),
  CAMPO_APELLIDO: Objetivo.elemento('el campo apellido', (page) => page.getByTestId('lastName')),
  CAMPO_CODIGO_POSTAL: Objetivo.elemento('el campo código postal', (page) => page.getByTestId('postalCode')),
  BOTON_CONTINUAR: Objetivo.elemento('el botón Continue', (page) => page.getByTestId('continue')),

  // Paso 2: "Overview" (resumen)
  BOTON_FINALIZAR: Objetivo.elemento('el botón Finish', (page) => page.getByTestId('finish')),

  // Paso 3: "Complete!"
  MENSAJE_CONFIRMACION: Objetivo.elemento('el mensaje de confirmación', (page) =>
    page.locator('.complete-header'),
  ),
};
