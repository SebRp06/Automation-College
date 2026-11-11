import type { Page } from '@playwright/test';
import type { Habilidad } from '../screenplay/Habilidad';
import type { Actor } from '../screenplay/Actor';

/**
 * ===========================================================================
 * ABILITIES → NavegarLaWeb (BrowseTheWeb)
 * ===========================================================================
 * Esta HABILIDAD le permite al actor usar un navegador.
 * Por dentro guarda el objeto "page" de Playwright, que representa una
 * pestaña del navegador.
 *
 * Uso:
 *   actor.puede(NavegarLaWeb.con(page))      → entregarle la habilidad
 *   NavegarLaWeb.de(actor).pagina            → usarla dentro de una interacción
 */
export class NavegarLaWeb implements Habilidad {
  constructor(public readonly pagina: Page) {}

  static con(pagina: Page): NavegarLaWeb {
    return new NavegarLaWeb(pagina);
  }

  /** Atajo para obtener la habilidad de un actor. */
  static de(actor: Actor): NavegarLaWeb {
    return actor.usarHabilidad(NavegarLaWeb);
  }
}
