import type { Actor } from './Actor';

/**
 * ===========================================================================
 * SCREENPLAY (núcleo) → Pregunta
 * ===========================================================================
 * Una PREGUNTA (Question) es la forma en que el actor OBSERVA el sistema.
 *   - NO hace clics ni escribe: solo consulta información.
 *   - Devuelve un valor (texto, número, verdadero/falso...).
 *   - Luego comparamos ese valor con lo esperado usando expect().
 *
 * El <T> significa "el tipo de dato que devuelve la pregunta":
 *   Pregunta<string>  → devuelve un texto
 *   Pregunta<number>  → devuelve un número
 *   Pregunta<boolean> → devuelve verdadero o falso
 */
export interface Pregunta<T> {
  respondidaPor(actor: Actor): Promise<T>;
  toString(): string;
}
