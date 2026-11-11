import type { Actor } from '../screenplay/Actor';
import type { Pregunta } from '../screenplay/Pregunta';
import type { Objetivo } from '../screenplay/Objetivo';
import { NavegarLaWeb } from '../abilities/NavegarLaWeb';

/**
 * ===========================================================================
 * QUESTIONS → Texto
 * ===========================================================================
 * Pregunta GENÉRICA: "¿Qué texto muestra este elemento?"
 * Sirve para títulos, mensajes de error, mensajes de confirmación, etc.
 *
 * Uso:
 *   const titulo = await actor.pregunta(Texto.de(InventarioUI.TITULO));
 *   // titulo === 'Products'
 */
export class Texto implements Pregunta<string> {
  private constructor(private readonly objetivo: Objetivo) {}

  static de(objetivo: Objetivo): Texto {
    return new Texto(objetivo);
  }

  async respondidaPor(actor: Actor): Promise<string> {
    const pagina = NavegarLaWeb.de(actor).pagina;
    // innerText() = el texto que ve el usuario. trim() quita espacios sobrantes.
    return (await this.objetivo.resolverEn(pagina).innerText()).trim();
  }

  toString(): string {
    return `el texto de ${this.objetivo}`;
  }
}
