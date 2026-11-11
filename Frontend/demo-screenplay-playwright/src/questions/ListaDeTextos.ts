import type { Actor } from '../screenplay/Actor';
import type { Pregunta } from '../screenplay/Pregunta';
import type { Objetivo } from '../screenplay/Objetivo';
import { NavegarLaWeb } from '../abilities/NavegarLaWeb';

/**
 * ===========================================================================
 * QUESTIONS → ListaDeTextos
 * ===========================================================================
 * Igual que "Texto", pero para VARIOS elementos a la vez.
 * Devuelve un arreglo (lista) de textos: string[]
 *
 * Uso:
 *   const nombres = await actor.pregunta(ListaDeTextos.de(InventarioUI.NOMBRES_DE_PRODUCTOS));
 *   // ['Sauce Labs Backpack', 'Sauce Labs Bike Light', ...]
 *   nombres[0]  → el primer producto de la lista
 */
export class ListaDeTextos implements Pregunta<string[]> {
  private constructor(private readonly objetivo: Objetivo) {}

  static de(objetivo: Objetivo): ListaDeTextos {
    return new ListaDeTextos(objetivo);
  }

  async respondidaPor(actor: Actor): Promise<string[]> {
    const pagina = NavegarLaWeb.de(actor).pagina;
    const textos = await this.objetivo.resolverEn(pagina).allInnerTexts();
    return textos.map((texto) => texto.trim());
  }

  toString(): string {
    return `los textos de ${this.objetivo}`;
  }
}
