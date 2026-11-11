/**
 * ===========================================================================
 * playwright.config.ts → Configuración general de Playwright
 * ===========================================================================
 * Aquí le decimos a Playwright:
 *   - Dónde están las pruebas (los .feature y los steps).
 *   - En qué página trabajar (baseURL).
 *   - En qué navegador ejecutar.
 *   - Qué guardar cuando algo falla (capturas, videos, trazas).
 */
import { defineConfig, devices } from '@playwright/test';
import { defineBddConfig } from 'playwright-bdd';

// playwright-bdd convierte los archivos .feature en pruebas de Playwright.
// El comando "bddgen" las genera en la carpeta .features-gen
const testDir = defineBddConfig({
  features: 'features/**/*.feature', // escenarios en lenguaje natural
  steps: 'src/steps/**/*.ts',        // código que conecta cada frase con TypeScript
});

export default defineConfig({
  testDir,

  // Tiempo máximo por escenario (30 segundos)
  timeout: 30_000,

  // Ejecuta los escenarios en paralelo para terminar más rápido
  fullyParallel: true,

  // Reportes: "list" en consola y "html" para abrir en el navegador
  reporter: [['list'], ['html', { open: 'never' }]],

  use: {
    // Todas las rutas relativas parten de esta URL. Ej: '/' → https://www.saucedemo.com/
    baseURL: 'https://www.saucedemo.com',

    // saucedemo marca sus elementos con el atributo data-test="...".
    // Con esto podemos usar page.getByTestId('username').
    testIdAttribute: 'data-test',

    // Tiempo máximo que Playwright espera a que un elemento aparezca
    // antes de hacer clic, escribir o leer su texto (10 segundos)
    actionTimeout: 10_000,

    // Evidencias solo cuando una prueba falla
    //screenshot: 'only-on-failure',
    //video: 'retain-on-failure',
    //trace: 'retain-on-failure',

    //Evidencias absolutas sin importar si falla o pasa
  screenshot: { mode: 'on', fullPage: true },  // captura final de cada escenario
   video: 'on', // video completo
   trace: 'on', // grabación paso a paso
  },

  // Navegadores donde se ejecutan las pruebas.
  // Puedes descomentar firefox o webkit para probar en más navegadores.
  projects: [
    { name: 'chromium', use: { ...devices['Desktop Chrome'] } },
    // { name: 'firefox', use: { ...devices['Desktop Firefox'] } },
    // { name: 'webkit',  use: { ...devices['Desktop Safari'] } },
  ],
});
