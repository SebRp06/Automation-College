import type { Usuario } from '../models/Usuario';

/**
 * ===========================================================================
 * UTILS → Constantes
 * ===========================================================================
 * Valores fijos que se usan en varias partes del proyecto.
 * Si cambian, se modifican SOLO aquí ("una única fuente de verdad").
 *
 * saucedemo muestra sus usuarios de prueba en la misma página de login.
 * Todos usan la contraseña "secret_sauce".
 */
export const CLAVE_POR_DEFECTO = 'secret_sauce';

export const USUARIOS = {
  /** Usuario normal: todo funciona bien. */
  ESTANDAR: { nombreDeUsuario: 'standard_user', clave: CLAVE_POR_DEFECTO } as Usuario,
  /** Usuario bloqueado: no puede entrar. */
  BLOQUEADO: { nombreDeUsuario: 'locked_out_user', clave: CLAVE_POR_DEFECTO } as Usuario,
};

/** Texto del título que aparece al entrar a la tienda. */
export const TITULO_PAGINA_PRODUCTOS = 'Products';
