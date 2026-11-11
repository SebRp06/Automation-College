import type { Actor } from '../screenplay/Actor';
import type { Pregunta } from '../screenplay/Pregunta';
import { NavegarLaWeb } from '../abilities/NavegarLaWeb';
import { EncabezadoUI } from '../ui/EncabezadoUI';

/**
 * ===========================================================================
 * QUESTIONS → CantidadEnElCarrito
 * ===========================================================================
 * Pregunta de NEGOCIO: "¿Cuántos productos dice el carrito que tengo?"
 *
 * Detalle importante de saucedemo: cuando el carrito está vacío, el
 * circulito rojo con el número NO EXISTE. Esta pregunta oculta ese detalle
 * técnico y simplemente responde 0. Así los pasos quedan más simples.
 */
export class CantidadEnElCarrito implements Pregunta<number> {
  static mostrada(): CantidadEnElCarrito {
    return new CantidadEnElCarrito();
  }

  async respondidaPor(actor: Actor): Promise<number> {
    const pagina = NavegarLaWeb.de(actor).pagina;
    const contador = EncabezadoUI.CONTADOR_CARRITO.resolverEn(pagina);

    // count() cuenta cuántos elementos coinciden con el selector (0 si no existe)
    if ((await contador.count()) === 0) {
      return 0;
    }
    // El texto es "3" → lo convertimos a número con Number()
    return Number(await contador.innerText());
  }

  toString(): string {
    return 'la cantidad de productos en el carrito';
  }
}
