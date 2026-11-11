import type { Actor } from '../screenplay/Actor';
import type { Tarea } from '../screenplay/Actividad';
import { HacerClic } from '../interactions/HacerClic';
import { InventarioUI } from '../ui/InventarioUI';

/**
 * ===========================================================================
 * TASKS → AgregarAlCarrito
 * ===========================================================================
 * Agrega uno o varios productos al carrito desde la página de productos.
 *
 * Uso:
 *   AgregarAlCarrito.losProductos('Sauce Labs Backpack')
 *   AgregarAlCarrito.losProductos('Sauce Labs Backpack', 'Sauce Labs Onesie')
 *
 * "...productos" (tres puntos) significa que el método acepta CUANTOS
 * productos quieras, separados por coma.
 */
export class AgregarAlCarrito implements Tarea {
  private constructor(private readonly productos: string[]) {}

  static losProductos(...productos: string[]): AgregarAlCarrito {
    return new AgregarAlCarrito(productos);
  }

  async realizadaPor(actor: Actor): Promise<void> {
    // Recorremos la lista y hacemos clic en "Add to cart" de cada producto
    for (const producto of this.productos) {
      await actor.intentaHacer(HacerClic.en(InventarioUI.BOTON_AGREGAR(producto)));
    }
  }

  toString(): string {
    return `agrega al carrito: ${this.productos.join(', ')}`;
  }
}
