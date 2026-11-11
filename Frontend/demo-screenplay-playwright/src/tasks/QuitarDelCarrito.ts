import type { Actor } from '../screenplay/Actor';
import type { Tarea } from '../screenplay/Actividad';
import { HacerClic } from '../interactions/HacerClic';
import { InventarioUI } from '../ui/InventarioUI';

/**
 * ===========================================================================
 * TASKS → QuitarDelCarrito
 * ===========================================================================
 * Quita un producto del carrito usando el botón "Remove" de la página de productos.
 */
export class QuitarDelCarrito implements Tarea {
  private constructor(private readonly producto: string) {}

  static elProducto(producto: string): QuitarDelCarrito {
    return new QuitarDelCarrito(producto);
  }

  async realizadaPor(actor: Actor): Promise<void> {
    await actor.intentaHacer(HacerClic.en(InventarioUI.BOTON_QUITAR(this.producto)));
  }

  toString(): string {
    return `quita del carrito "${this.producto}"`;
  }
}
