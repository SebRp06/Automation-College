import { test } from '@playwright/test';
import type { Actividad } from './Actividad';
import type { Habilidad, TipoDeHabilidad } from './Habilidad';
import type { Pregunta } from './Pregunta';

/**
 * ===========================================================================
 * SCREENPLAY (núcleo) → Actor
 * ===========================================================================
 * El ACTOR es el protagonista de la prueba: representa a una persona que usa
 * el sistema (Ana, Carlos, Sofía...).
 *
 * Un actor:
 *   1. TIENE habilidades           → actor.puede(NavegarLaWeb.con(page))
 *   2. HACE actividades            → actor.intentaHacer(IniciarSesion.como(usuario))
 *   3. HACE preguntas              → actor.pregunta(CantidadEnElCarrito.mostrada())
 *   4. RECUERDA datos entre pasos  → actor.recuerda('clave', valor)
 *
 * Gracias a esto, el código de las pruebas se lee casi como una frase:
 *   await ana.intentaHacer(IniciarSesion.como(usuarioEstandar));
 *
 * NOTA: En Java, la librería Serenity BDD ya trae esta clase lista.
 * En TypeScript la construimos nosotros (es corta) para entender cómo
 * funciona por dentro. Existe también la librería Serenity/JS si más
 * adelante quieres una versión completa.
 */
export class Actor {
  private readonly habilidades = new Map<TipoDeHabilidad<Habilidad>, Habilidad>();
  private readonly memoria = new Map<string, unknown>();

  private constructor(public readonly nombre: string) {}

  /** Crea un actor con un nombre. Ej: Actor.llamado('Ana') */
  static llamado(nombre: string): Actor {
    return new Actor(nombre);
  }

  /** Le entrega habilidades al actor. Devuelve el mismo actor para encadenar. */
  puede(...habilidades: Habilidad[]): this {
    for (const habilidad of habilidades) {
      this.habilidades.set(habilidad.constructor as TipoDeHabilidad<Habilidad>, habilidad);
    }
    return this;
  }

  /** Obtiene una habilidad del actor. Si no la tiene, explica el error claramente. */
  usarHabilidad<H extends Habilidad>(tipo: TipoDeHabilidad<H>): H {
    const habilidad = this.habilidades.get(tipo);
    if (!habilidad) {
      throw new Error(`${this.nombre} no tiene la habilidad ${tipo.name}. ¿Olvidaste darle "puede(...)"?`);
    }
    return habilidad as H;
  }

  /**
   * Ejecuta una o varias actividades EN ORDEN.
   * test.step(...) hace que cada actividad aparezca como un paso en el reporte.
   */
  async intentaHacer(...actividades: Actividad[]): Promise<void> {
    for (const actividad of actividades) {
      await test.step(`${this.nombre} ${actividad.toString()}`, async () => {
        await actividad.realizadaPor(this);
      });
    }
  }

  /**
   * Responde una pregunta y devuelve el valor observado.
   * (No la registramos como paso del reporte porque en las validaciones
   * usamos expect.poll, que puede preguntar varias veces hasta que se cumpla).
   */
  async pregunta<T>(pregunta: Pregunta<T>): Promise<T> {
    return pregunta.respondidaPor(this);
  }

  /** Guarda un dato en la memoria del actor. */
  recuerda(clave: string, valor: unknown): void {
    this.memoria.set(clave, valor);
  }

  /** Recupera un dato guardado antes con recuerda(). */
  recupera<T>(clave: string): T {
    if (!this.memoria.has(clave)) {
      throw new Error(`${this.nombre} no recuerda nada con la clave "${clave}"`);
    }
    return this.memoria.get(clave) as T;
  }
}
