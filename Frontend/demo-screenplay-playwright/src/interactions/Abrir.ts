import type { Actor } from '../screenplay/Actor';
import type { Interaccion } from '../screenplay/Actividad';
import { NavegarLaWeb } from '../abilities/NavegarLaWeb';

/**
 * ===========================================================================
 * INTERACTIONS → Abrir
 * ===========================================================================
 * Una INTERACCIÓN es la acción más pequeña que hace el actor.
 * Esta abre una página en el navegador.
 *
 * Uso:
 *   Abrir.laPagina('/')   → abre https://www.saucedemo.com/ (usa el baseURL)
 */
export class Abrir implements Interaccion {
  private constructor(private readonly ruta: string) {}

  static laPagina(ruta = '/'): Abrir {
    return new Abrir(ruta);
  }

  async realizadaPor(actor: Actor): Promise<void> {
    // 1. Obtenemos la habilidad del actor  2. Usamos Playwright (page.goto)
    await NavegarLaWeb.de(actor).pagina.goto(this.ruta);
  }

  toString(): string {
    return `abre la página "${this.ruta}"`;
  }
}
