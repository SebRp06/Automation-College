import type { Actor } from '../screenplay/Actor';
import type { Tarea } from '../screenplay/Actividad';
import { Seleccionar } from '../interactions/Seleccionar';
import { InventarioUI } from '../ui/InventarioUI';

/**
 * ===========================================================================
 * TASKS → OrdenarProductos
 * ===========================================================================
 * Ordena el catálogo usando la lista desplegable de la página de productos.
 *
 * Opciones disponibles en saucedemo:
 *   'Name (A to Z)' | 'Name (Z to A)' | 'Price (low to high)' | 'Price (high to low)'
 */
export class OrdenarProductos implements Tarea {
  private constructor(private readonly criterio: string) {}

  static por(criterio: string): OrdenarProductos {
    return new OrdenarProductos(criterio);
  }

  async realizadaPor(actor: Actor): Promise<void> {
    await actor.intentaHacer(Seleccionar.laOpcion(this.criterio).en(InventarioUI.SELECTOR_DE_ORDEN));
  }

  toString(): string {
    return `ordena los productos por "${this.criterio}"`;
  }
}
