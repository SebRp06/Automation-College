import { Objetivo } from '../screenplay/Objetivo';

/**
 * ===========================================================================
 * UI → EncabezadoUI   (barra superior, visible en todas las páginas internas)
 * ===========================================================================
 */
export const EncabezadoUI = {
  ICONO_CARRITO: Objetivo.elemento('el ícono del carrito', (page) => page.locator('.shopping_cart_link')),

  /** El circulito rojo con el número de productos. Solo aparece si hay productos. */
  CONTADOR_CARRITO: Objetivo.elemento('el contador del carrito', (page) => page.locator('.shopping_cart_badge')),

  /** Mensaje de error rojo (se usa en el login y en el formulario de pago). */
  MENSAJE_DE_ERROR: Objetivo.elemento('el mensaje de error', (page) => page.getByTestId('error')),
};
