import type { Actor } from '../screenplay/Actor';
import type { Tarea } from '../screenplay/Actividad';
import type { DatosDeEnvio } from '../models/DatosDeEnvio';
import { Escribir } from '../interactions/Escribir';
import { HacerClic } from '../interactions/HacerClic';
import { CarritoUI } from '../ui/CarritoUI';
import { PagoUI } from '../ui/PagoUI';
import { IrAlCarrito } from './IrAlCarrito';

/**
 * ===========================================================================
 * TASKS → RealizarPago
 * ===========================================================================
 * Ejemplo de una TAREA que usa OTRA TAREA (IrAlCarrito) además de
 * interacciones. Las tareas se pueden combinar como piezas de LEGO.
 *
 *   Realizar pago =  Ir al carrito                    (tarea)
 *                  + Clic en "Checkout"               (interacción)
 *                  + Diligenciar nombre, apellido, CP (interacciones)
 *                  + Clic en "Continue"               (interacción)
 *                  + Clic en "Finish"                 (interacción)
 *
 * Si falta un dato obligatorio, saucedemo NO deja pasar de "Continue",
 * por eso solo hacemos clic en "Finish" cuando todos los datos están.
 */
export class RealizarPago implements Tarea {
  private constructor(private readonly datos: DatosDeEnvio) {}

  static con(datos: DatosDeEnvio): RealizarPago {
    return new RealizarPago(datos);
  }

  async realizadaPor(actor: Actor): Promise<void> {
    await actor.intentaHacer(
      IrAlCarrito.ahora(),
      HacerClic.en(CarritoUI.BOTON_CHECKOUT),
      Escribir.elTexto(this.datos.nombre).en(PagoUI.CAMPO_NOMBRE),
      Escribir.elTexto(this.datos.apellido).en(PagoUI.CAMPO_APELLIDO),
      Escribir.elTexto(this.datos.codigoPostal).en(PagoUI.CAMPO_CODIGO_POSTAL),
      HacerClic.en(PagoUI.BOTON_CONTINUAR),
    );

    const datosCompletos = this.datos.nombre && this.datos.apellido && this.datos.codigoPostal;
    if (datosCompletos) {
      await actor.intentaHacer(HacerClic.en(PagoUI.BOTON_FINALIZAR));
    }
  }

  toString(): string {
    return `realiza el pago a nombre de "${this.datos.nombre} ${this.datos.apellido}"`;
  }
}
