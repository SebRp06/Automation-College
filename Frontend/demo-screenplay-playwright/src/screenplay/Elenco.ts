import type { Page } from '@playwright/test';
import { Actor } from './Actor';
import { NavegarLaWeb } from '../abilities/NavegarLaWeb';

/**
 * ===========================================================================
 * SCREENPLAY (núcleo) → Elenco (Cast)
 * ===========================================================================
 * Siguiendo la metáfora del teatro, el ELENCO es el grupo de actores de la
 * obra. Se encarga de:
 *   - Crear un actor la primera vez que se le llama por su nombre.
 *   - Darle sus habilidades (aquí: navegar la web con el navegador de la prueba).
 *   - Recordar cuál es el actor "en escena" para los pasos siguientes.
 *
 * Así, en un paso decimos  elenco.actorLlamado('Ana')  y en los siguientes
 * simplemente  elenco.actorEnEscena()  sin repetir el nombre.
 */
export class Elenco {
  private readonly actores = new Map<string, Actor>();
  private actorActual?: Actor;

  constructor(private readonly page: Page) {}

  /** Devuelve el actor con ese nombre (lo crea si no existe) y lo pone en escena. */
  actorLlamado(nombre: string): Actor {
    let actor = this.actores.get(nombre);
    if (!actor) {
      actor = Actor.llamado(nombre).puede(NavegarLaWeb.con(this.page));
      this.actores.set(nombre, actor);
    }
    this.actorActual = actor;
    return actor;
  }

  /** Devuelve el actor que está actuando en este momento. */
  actorEnEscena(): Actor {
    if (!this.actorActual) {
      throw new Error('No hay ningún actor en escena. Usa primero actorLlamado("Nombre").');
    }
    return this.actorActual;
  }
}
