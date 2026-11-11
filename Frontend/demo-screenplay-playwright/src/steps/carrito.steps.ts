import { expect } from '@playwright/test';
import type { DataTable } from 'playwright-bdd';
import { Cuando, Dado, Entonces } from './fixtures';
import { AgregarAlCarrito } from '../tasks/AgregarAlCarrito';
import { QuitarDelCarrito } from '../tasks/QuitarDelCarrito';
import { IrAlCarrito } from '../tasks/IrAlCarrito';
import { CantidadEnElCarrito } from '../questions/CantidadEnElCarrito';
import { ListaDeTextos } from '../questions/ListaDeTextos';
import { CarritoUI } from '../ui/CarritoUI';

/**
 * ===========================================================================
 * STEPS → features/carrito.feature (y parte de compra.feature)
 * ===========================================================================
 */

Cuando('agrega el producto {string} al carrito', async ({ elenco }, producto: string) => {
  await elenco.actorEnEscena().intentaHacer(AgregarAlCarrito.losProductos(producto));
});

// Misma acción, pero escrita como precondición ("Dado que ya agregó...")
Dado('que ya agregó el producto {string} al carrito', async ({ elenco }, producto: string) => {
  const actor = elenco.actorEnEscena();
  await actor.intentaHacer(AgregarAlCarrito.losProductos(producto));
  await expect.poll(() => actor.pregunta(CantidadEnElCarrito.mostrada())).toBeGreaterThan(0);
});

/**
 * Paso con TABLA DE DATOS. En el feature:
 *   | producto            |
 *   | Sauce Labs Backpack |
 * tabla.hashes() la convierte en: [ { producto: 'Sauce Labs Backpack' }, ... ]
 */
Cuando('agrega los siguientes productos al carrito:', async ({ elenco }, tabla: DataTable) => {
  const productos = tabla.hashes().map((fila) => fila.producto);
  await elenco.actorEnEscena().intentaHacer(AgregarAlCarrito.losProductos(...productos));
});

Cuando('quita el producto {string} del carrito', async ({ elenco }, producto: string) => {
  await elenco.actorEnEscena().intentaHacer(QuitarDelCarrito.elProducto(producto));
});

// "producto(s)" significa que la "s" es opcional: sirve para "1 producto" y "3 productos"
Entonces('el carrito debe mostrar {int} producto(s)', async ({ elenco }, cantidad: number) => {
  const actor = elenco.actorEnEscena();
  await expect.poll(() => actor.pregunta(CantidadEnElCarrito.mostrada())).toBe(cantidad);
});

Entonces('al abrir el carrito debe ver el producto {string}', async ({ elenco }, producto: string) => {
  const actor = elenco.actorEnEscena();
  await actor.intentaHacer(IrAlCarrito.ahora());
  // toContain = "la lista CONTIENE este producto"
  await expect.poll(() => actor.pregunta(ListaDeTextos.de(CarritoUI.NOMBRES_EN_CARRITO))).toContain(producto);
});
