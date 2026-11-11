/**
 * ===========================================================================
 * MODELS → Usuario
 * ===========================================================================
 * Un MODELO describe la forma de un dato. En TypeScript usamos "interface"
 * para decir: "un Usuario SIEMPRE tiene nombreDeUsuario y clave (ambos texto)".
 *
 * Si alguien intenta crear un Usuario sin clave, TypeScript marca el error
 * en el editor ANTES de ejecutar la prueba.
 */
export interface Usuario {
  nombreDeUsuario: string;
  clave: string;
}
