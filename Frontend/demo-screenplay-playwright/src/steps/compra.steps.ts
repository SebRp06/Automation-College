import { expect } from '@playwright/test';
import { Cuando, Entonces } from './fixtures';
import { RealizarPago } from '../tasks/RealizarPago';
import { Texto } from '../questions/Texto';
import { PagoUI } from '../ui/PagoUI';

/**
 * ===========================================================================
 * STEPS → features/compra.feature
 * ===========================================================================
 */

Cuando(
  'realiza el pago con nombre {string}, apellido {string} y código postal {string}',
  async ({ elenco }, nombre: string, apellido: string, codigoPostal: string) => {
    // Armamos el modelo DatosDeEnvio y se lo entregamos a la tarea
    await elenco.actorEnEscena().intentaHacer(
      RealizarPago.con({ nombre, apellido, codigoPostal }),
    );
  },
);

Entonces('debe ver el mensaje de confirmación {string}', async ({ elenco }, mensaje: string) => {
  const actor = elenco.actorEnEscena();
  await expect.poll(() => actor.pregunta(Texto.de(PagoUI.MENSAJE_CONFIRMACION))).toBe(mensaje);
});
