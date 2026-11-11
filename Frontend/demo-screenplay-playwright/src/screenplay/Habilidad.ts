/**
 * ===========================================================================
 * SCREENPLAY (núcleo) → Habilidad
 * ===========================================================================
 * Una HABILIDAD (Ability) es lo que le permite a un actor interactuar con
 * el sistema. Ejemplos:
 *   - NavegarLaWeb   → puede usar un navegador (esta demo)
 *   - LlamarUnaApi   → puede enviar peticiones HTTP
 *   - ConsultarBD    → puede consultar una base de datos
 *
 * Es solo una "marca": cualquier clase puede ser una habilidad.
 */
// eslint-disable-next-line @typescript-eslint/no-empty-interface
export interface Habilidad {}

/**
 * Tipo auxiliar que representa "la CLASE de una habilidad".
 * Lo usamos para pedirle al actor una habilidad concreta:
 *   actor.usarHabilidad(NavegarLaWeb)
 */
// eslint-disable-next-line @typescript-eslint/no-explicit-any
export type TipoDeHabilidad<H extends Habilidad> = new (...args: any[]) => H;
