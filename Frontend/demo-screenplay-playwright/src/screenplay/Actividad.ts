import type { Actor } from './Actor';

/**
 * ===========================================================================
 * SCREENPLAY (núcleo) → Actividad, Tarea e Interacción
 * ===========================================================================
 * Una ACTIVIDAD es cualquier cosa que el actor puede HACER.
 * Existen dos tipos:
 *
 *   INTERACCIÓN → la acción más pequeña y técnica.
 *                 Ej: hacer clic, escribir un texto, abrir una página.
 *
 *   TAREA       → una acción con sentido de NEGOCIO, compuesta por varias
 *                 interacciones (o incluso otras tareas).
 *                 Ej: "Iniciar sesión" = escribir usuario + escribir clave + clic.
 *
 * Ambas cumplen el mismo "contrato" (interface): tienen un método
 * realizadaPor(actor) que ejecuta la acción.
 */
export interface Actividad {
  /** Ejecuta la actividad usando las habilidades del actor. */
  realizadaPor(actor: Actor): Promise<void>;

  /** Descripción legible. Aparece en el reporte de Playwright. */
  toString(): string;
}

/** Una Tarea es una Actividad de negocio. (Mismo contrato, distinto propósito) */
export type Tarea = Actividad;

/** Una Interacción es una Actividad técnica de bajo nivel. */
export type Interaccion = Actividad;
