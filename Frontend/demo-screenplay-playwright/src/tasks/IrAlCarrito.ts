import type { Actor } from '../screenplay/Actor';
import type { Tarea } from '../screenplay/Actividad';
import { HacerClic } from '../interactions/HacerClic';
import { EncabezadoUI } from '../ui/EncabezadoUI';

/**
 * ===========================================================================
 * TASKS → IrAlCarrito
 * ===========================================================================
 * Una tarea puede tener UNA sola interacción. Aun así vale la pena crearla:
 * "ir al carrito" se entiende mejor que "hacer clic en .shopping_cart_link".
 */
export class IrAlCarrito implements Tarea {
  static ahora(): IrAlCarrito {
    return new IrAlCarrito();
  }

  async realizadaPor(actor: Actor): Promise<void> {
    await actor.intentaHacer(HacerClic.en(EncabezadoUI.ICONO_CARRITO));
  }

  toString(): string {
    return 'va al carrito de compras';
  }
}
