import type { Actor } from '../screenplay/Actor';
import type { Interaccion } from '../screenplay/Actividad';
import type { Objetivo } from '../screenplay/Objetivo';
import { NavegarLaWeb } from '../abilities/NavegarLaWeb';

/**
 * ===========================================================================
 * INTERACTIONS → Seleccionar
 * ===========================================================================
 * Elige una opción de una lista desplegable (<select>) por su texto visible.
 *
 * Uso:
 *   Seleccionar.laOpcion('Price (low to high)').en(InventarioUI.SELECTOR_DE_ORDEN)
 */
export class Seleccionar implements Interaccion {
  private constructor(
    private readonly opcion: string,
    private readonly objetivo: Objetivo,
  ) {}

  static laOpcion(opcion: string) {
    return {
      en: (objetivo: Objetivo) => new Seleccionar(opcion, objetivo),
    };
  }

  async realizadaPor(actor: Actor): Promise<void> {
    const pagina = NavegarLaWeb.de(actor).pagina;
    await this.objetivo.resolverEn(pagina).selectOption({ label: this.opcion });
  }

  toString(): string {
    return `selecciona "${this.opcion}" en ${this.objetivo}`;
  }
}
