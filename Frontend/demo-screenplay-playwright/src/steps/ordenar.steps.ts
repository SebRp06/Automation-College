import { expect } from '@playwright/test';
import { Cuando, Entonces } from './fixtures';
import { OrdenarProductos } from '../tasks/OrdenarProductos';
import { ListaDeTextos } from '../questions/ListaDeTextos';
import { InventarioUI } from '../ui/InventarioUI';

/**
 * ===========================================================================
 * STEPS → features/ordenar_productos.feature
 * ===========================================================================
 */

Cuando('ordena los productos por {string}', async ({ elenco }, criterio: string) => {
  await elenco.actorEnEscena().intentaHacer(OrdenarProductos.por(criterio));
});

Entonces('el primer producto de la lista debe ser {string}', async ({ elenco }, producto: string) => {
  const actor = elenco.actorEnEscena();
  // [0] = la primera posición de la lista (en programación se cuenta desde 0)
  await expect
    .poll(async () => (await actor.pregunta(ListaDeTextos.de(InventarioUI.NOMBRES_DE_PRODUCTOS)))[0])
    .toBe(producto);
});
