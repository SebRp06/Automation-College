package co.com.demo.quickpizza.tasks;

import co.com.demo.quickpizza.utils.CookiesDelActor;
import net.serenitybdd.annotations.Step;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.Tasks;
import net.serenitybdd.screenplay.rest.interactions.Get;

import static co.com.demo.quickpizza.utils.Constantes.CALIFICACION_POR_ID;
import static co.com.demo.quickpizza.utils.Constantes.ID_CALIFICACION;
import static co.com.demo.quickpizza.utils.Constantes.TOKEN;

/**
 * =========================================================================
 * TASKS → ConsultarCalificacion            (protocolo: GET con parámetro)
 * =========================================================================
 * Consulta UNA calificación usando un "path param" (parámetro en la ruta):
 *
 *   GET https://quickpizza.grafana.com/api/ratings/{id}
 *   con id = 7  →  GET https://quickpizza.grafana.com/api/ratings/7
 *
 * El id NO lo escribimos a mano: lo tomamos de la memoria del actor
 * (lo guardó la tarea CalificarPizza). Así las pruebas no dependen de
 * datos fijos que podrían no existir.
 */
public class ConsultarCalificacion implements Task {

    public static ConsultarCalificacion recienCreada() {
        return Tasks.instrumented(ConsultarCalificacion.class);
    }

    @Step("{0} consulta la calificación que acaba de crear")
    @Override
    public <T extends Actor> void performAs(T actor) {
        String token = actor.recall(TOKEN);
        String id = actor.recall(ID_CALIFICACION);

        actor.attemptsTo(
                Get.resource(CALIFICACION_POR_ID)
                        // .with(...) permite configurar la petición de Rest Assured.
                        .with(peticion -> peticion
                                // Devuelve las cookies del actor (mismo servidor siempre)
                                .filter(CookiesDelActor.de(actor))
                                .header("Authorization", "Bearer " + token)
                                // pathParam reemplaza {id} en la URL por el valor real.
                                .pathParam("id", id))
        );
    }
}
