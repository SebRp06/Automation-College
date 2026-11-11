import type { Locator, Page } from '@playwright/test';

/**
 * ===========================================================================
 * SCREENPLAY (núcleo) → Objetivo (Target)
 * ===========================================================================
 * Un OBJETIVO es un elemento de la página (un botón, un campo, un texto)
 * con un NOMBRE legible para humanos.
 *
 *   Objetivo.elemento('el campo usuario', (page) => page.getByTestId('username'))
 *
 * ¿Por qué no usar directamente el selector?
 *   1. El reporte dice "hace clic en el botón Login" en vez de "#login-button".
 *   2. Si la página cambia, solo actualizamos el selector en UN lugar (carpeta ui).
 *
 * "Locator" es la forma en que Playwright encuentra elementos en la página.
 */
export class Objetivo {
  private constructor(
    private readonly descripcion: string,
    private readonly ubicador: (page: Page) => Locator,
  ) {}

  static elemento(descripcion: string, ubicador: (page: Page) => Locator): Objetivo {
    return new Objetivo(descripcion, ubicador);
  }

  /** Busca el elemento en la página indicada. */
  resolverEn(page: Page): Locator {
    return this.ubicador(page);
  }

  toString(): string {
    return this.descripcion;
  }
}
