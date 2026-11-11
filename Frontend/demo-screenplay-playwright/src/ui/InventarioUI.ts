import { Objetivo } from '../screenplay/Objetivo';

/**
 * ===========================================================================
 * UI → InventarioUI   (página de productos: /inventory.html)
 * ===========================================================================
 * Algunos elementos dependen de un dato (ej. el NOMBRE del producto).
 * Para esos usamos FUNCIONES que reciben el dato y devuelven el Objetivo.
 */
export const InventarioUI = {
  TITULO: Objetivo.elemento('el título de la página', (page) => page.locator('.title')),

  NOMBRES_DE_PRODUCTOS: Objetivo.elemento('los nombres de los productos', (page) =>
    page.locator('.inventory_item_name'),
  ),

  SELECTOR_DE_ORDEN: Objetivo.elemento('el selector de orden', (page) =>
    page.locator('.product_sort_container'),
  ),

  /**
   * Botón "Add to cart" de un producto específico.
   * 1. Busca la tarjeta (.inventory_item) que contiene el nombre del producto.
   * 2. Dentro de ella, busca el botón cuyo texto es "Add to cart".
   */
  BOTON_AGREGAR: (producto: string) =>
    Objetivo.elemento(`el botón "Add to cart" de ${producto}`, (page) =>
      page.locator('.inventory_item').filter({ hasText: producto }).getByRole('button', { name: 'Add to cart' }),
    ),

  /** Botón "Remove" de un producto específico (aparece después de agregarlo). */
  BOTON_QUITAR: (producto: string) =>
    Objetivo.elemento(`el botón "Remove" de ${producto}`, (page) =>
      page.locator('.inventory_item').filter({ hasText: producto }).getByRole('button', { name: 'Remove' }),
    ),
};
