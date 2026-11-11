import type { Actor } from '../screenplay/Actor';
import type { Tarea } from '../screenplay/Actividad';
import type { Usuario } from '../models/Usuario';
import { Escribir } from '../interactions/Escribir';
import { HacerClic } from '../interactions/HacerClic';
import { LoginUI } from '../ui/LoginUI';

/**
 * ===========================================================================
 * TASKS → IniciarSesion
 * ===========================================================================
 * Una TAREA describe QUÉ hace el actor en lenguaje de NEGOCIO.
 * Por dentro está formada por varias INTERACCIONES:
 *
 *   Iniciar sesión =  Escribir usuario
 *                   + Escribir contraseña
 *                   + Hacer clic en "Login"
 *
 * Uso en un paso:
 *   await actor.intentaHacer(IniciarSesion.como(USUARIOS.ESTANDAR));
 *
 * IMPORTANTE: una tarea NO valida nada (no usa expect).
 * Las validaciones son trabajo de las PREGUNTAS.
 */
export class IniciarSesion implements Tarea {
  private constructor(private readonly usuario: Usuario) {}

  /** Método "fábrica": hace que el código se lea como una frase. */
  static como(usuario: Usuario): IniciarSesion {
    return new IniciarSesion(usuario);
  }

  async realizadaPor(actor: Actor): Promise<void> {
    await actor.intentaHacer(
      Escribir.elTexto(this.usuario.nombreDeUsuario).en(LoginUI.CAMPO_USUARIO),
      Escribir.elTexto(this.usuario.clave).en(LoginUI.CAMPO_CLAVE),
      HacerClic.en(LoginUI.BOTON_INGRESAR),
    );
  }

  toString(): string {
    return `inicia sesión como "${this.usuario.nombreDeUsuario}"`;
  }
}
