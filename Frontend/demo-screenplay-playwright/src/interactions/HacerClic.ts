import type { Actor } from '../screenplay/Actor';
import type { Interaccion } from '../screenplay/Actividad';
import type { Objetivo } from '../screenplay/Objetivo';
import { NavegarLaWeb } from '../abilities/NavegarLaWeb';

/**
 * ===========================================================================
 * INTERACTIONS → HacerClic
 * ===========================================================================
 * Hace clic sobre un elemento de la página.
 *
 * Uso:
 *   HacerClic.en(LoginUI.BOTON_INGRESAR)
 */
export class HacerClic implements Interaccion {
  private constructor(private readonly objetivo: Objetivo) {}

  static en(objetivo: Objetivo): HacerClic {
    return new HacerClic(objetivo);
  }

  async realizadaPor(actor: Actor): Promise<void> {
    const pagina = NavegarLaWeb.de(actor).pagina;
    await this.objetivo.resolverEn(pagina).click();
  }

  toString(): string {
    return `hace clic en ${this.objetivo}`;
  }
}
