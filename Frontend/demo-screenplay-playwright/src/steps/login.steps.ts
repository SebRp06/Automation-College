import { expect } from '@playwright/test';
import { Cuando, Entonces } from './fixtures';
import { IniciarSesion } from '../tasks/IniciarSesion';
import { Texto } from '../questions/Texto';
import { InventarioUI } from '../ui/InventarioUI';
import { TITULO_PAGINA_PRODUCTOS } from '../utils/Constantes';

/**
 * ===========================================================================
 * STEPS → features/login.feature
 * ===========================================================================
 * Fíjate en el patrón:
 *   Cuando   → actor.intentaHacer( TAREA )            (hace algo)
 *   Entonces → expect.poll( actor.pregunta(PREGUNTA) ) (verifica algo)
 */

Cuando(
  'inicia sesión con usuario {string} y contraseña {string}',
  async ({ elenco }, usuario: string, clave: string) => {
    await elenco.actorEnEscena().intentaHacer(
      IniciarSesion.como({ nombreDeUsuario: usuario, clave }),
    );
  },
);

Entonces('debe ver la página de productos', async ({ elenco }) => {
  const actor = elenco.actorEnEscena();
  await expect.poll(() => actor.pregunta(Texto.de(InventarioUI.TITULO))).toBe(TITULO_PAGINA_PRODUCTOS);
});
