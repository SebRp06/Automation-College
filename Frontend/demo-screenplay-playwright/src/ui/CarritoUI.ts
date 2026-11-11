import { Objetivo } from '../screenplay/Objetivo';

/**
 * ===========================================================================
 * UI → CarritoUI   (página del carrito: /cart.html)
 * ===========================================================================
 */
export const CarritoUI = {
  NOMBRES_EN_CARRITO: Objetivo.elemento('los productos del carrito', (page) =>
    page.locator('.cart_item .inventory_item_name'),
  ),
  BOTON_CHECKOUT: Objetivo.elemento('el botón Checkout', (page) => page.getByTestId('checkout')),
};
