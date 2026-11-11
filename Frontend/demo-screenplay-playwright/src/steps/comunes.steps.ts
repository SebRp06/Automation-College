import { expect } from '@playwright/test';
import { Dado, Entonces } from './fixtures';
import { Abrir } from '../interactions/Abrir';
import { IniciarSesion } from '../tasks/IniciarSesion';
import { Texto } from '../questions/Texto';
import { InventarioUI } from '../ui/InventarioUI';
import { EncabezadoUI } from '../ui/EncabezadoUI';
import { TITULO_PAGINA_PRODUCTOS, USUARIOS } from '../utils/Constantes';

/**
 * ===========================================================================
 * STEPS → pasos comunes (se usan en varios .feature)
 * ===========================================================================
 * Los "steps" son el PUENTE entre el .feature (español) y el código:
 *
 *   Feature:  Dado que "Ana" está en la página de inicio de sesión
 *   Código:   Dado('que {string} está en la página de inicio de sesión', ...)
 *
 * {string} captura el texto entre comillas ("Ana") y {int} captura números.
 *
 * Cada paso recibe { elenco } (nuestro fixture) y los valores capturados.
 *
 * REGLA DE ORO: los steps deben ser DELGADOS. Solo dicen
 *   - "actor, haz esta tarea"      → actor.intentaHacer(...)
 *   - "actor, verifica esto"       → expect.poll(() => actor.pregunta(...))
 */

Dado('que {string} está en la página de inicio de sesión', async ({ elenco }, nombre: string) => {
  const actor = elenco.actorLlamado(nombre);
  await actor.intentaHacer(Abrir.laPagina('/'));
});

Dado('que {string} inició sesión como usuario estándar', async ({ elenco }, nombre: string) => {
  const actor = elenco.actorLlamado(nombre);
  await actor.intentaHacer(
    Abrir.laPagina('/'),
    IniciarSesion.como(USUARIOS.ESTANDAR),
  );
  // Confirmamos que entró antes de seguir: si falla aquí, el error es claro
  await expect.poll(() => actor.pregunta(Texto.de(InventarioUI.TITULO))).toBe(TITULO_PAGINA_PRODUCTOS);
});

/**
 * expect.poll(...) pregunta varias veces (durante unos segundos) hasta que
 * el valor sea el esperado. Es útil porque las páginas web tardan un poco
 * en actualizarse después de un clic.
 */
Entonces('debe ver el mensaje de error {string}', async ({ elenco }, mensaje: string) => {
  const actor = elenco.actorEnEscena();
  await expect.poll(() => actor.pregunta(Texto.de(EncabezadoUI.MENSAJE_DE_ERROR))).toBe(mensaje);
});
