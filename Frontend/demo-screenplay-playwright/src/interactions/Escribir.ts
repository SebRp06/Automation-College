import type { Actor } from '../screenplay/Actor';
import type { Interaccion } from '../screenplay/Actividad';
import type { Objetivo } from '../screenplay/Objetivo';
import { NavegarLaWeb } from '../abilities/NavegarLaWeb';

/**
 * ===========================================================================
 * INTERACTIONS → Escribir
 * ===========================================================================
 * Escribe un texto en un campo de la página.
 *
 * Uso (se lee como una frase):
 *   Escribir.elTexto('standard_user').en(LoginUI.CAMPO_USUARIO)
 */
export class Escribir implements Interaccion {
  private constructor(
    private readonly texto: string,
    private readonly objetivo: Objetivo,
  ) {}

  /** Primer paso: qué texto escribir. Devuelve un objeto con el método "en". */
  static elTexto(texto: string) {
    return {
      en: (objetivo: Objetivo) => new Escribir(texto, objetivo),
    };
  }

  async realizadaPor(actor: Actor): Promise<void> {
    const pagina = NavegarLaWeb.de(actor).pagina;
    // fill() borra lo que tenga el campo y escribe el texto nuevo.
    // Playwright espera AUTOMÁTICAMENTE a que el campo esté visible y habilitado.
    await this.objetivo.resolverEn(pagina).fill(this.texto);
  }

  toString(): string {
    return `escribe "${this.texto}" en ${this.objetivo}`;
  }
}
