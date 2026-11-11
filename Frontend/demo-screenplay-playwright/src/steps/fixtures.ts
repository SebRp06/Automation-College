import { test as base, createBdd } from 'playwright-bdd';
import { Elenco } from '../screenplay/Elenco';

/**
 * ===========================================================================
 * STEPS → fixtures
 * ===========================================================================
 * Un "fixture" es algo que Playwright PREPARA antes de cada escenario y
 * LIMPIA al terminar. Playwright ya trae algunos, como "page" (una pestaña
 * nueva del navegador para cada escenario).
 *
 * Aquí creamos nuestro propio fixture: el "elenco" de actores.
 * Cada escenario recibe un elenco NUEVO, así las pruebas no se mezclan.
 */
export const test = base.extend<{ elenco: Elenco }>({
  elenco: async ({ page }, use) => {
    await use(new Elenco(page));
  },
});

/**
 * createBdd(test) nos da las funciones para conectar las frases del .feature
 * con código. Les ponemos alias en español para que se lean igual que el feature.
 */
export const { Given: Dado, When: Cuando, Then: Entonces } = createBdd(test);
