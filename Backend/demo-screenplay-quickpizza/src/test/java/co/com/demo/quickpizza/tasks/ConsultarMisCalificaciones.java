package co.com.demo.quickpizza.tasks;

import co.com.demo.quickpizza.utils.CookiesDelActor;
import net.serenitybdd.annotations.Step;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.Tasks;
import net.serenitybdd.screenplay.rest.interactions.Get;

import static co.com.demo.quickpizza.utils.Constantes.CALIFICACIONES;
import static co.com.demo.quickpizza.utils.Constantes.TOKEN;

/**
 * =========================================================================
 * TASKS → ConsultarMisCalificaciones       (protocolo: GET autenticado)
 * =========================================================================
 *   GET https://quickpizza.grafana.com/api/ratings
 *   Authorization: Bearer <token>
 *
 * Respuesta esperada (200):
 *   { "ratings": [ { "id": 7, "stars": 4, "pizza_id": 1 }, ... ] }
 *
 * GET solo CONSULTA información: no envía cuerpo y no modifica nada.
 */
public class ConsultarMisCalificaciones implements Task {

    public static ConsultarMisCalificaciones todas() {
        return Tasks.instrumented(ConsultarMisCalificaciones.class);
    }

    @Step("{0} consulta todas sus calificaciones")
    @Override
    public <T extends Actor> void performAs(T actor) {
        String token = actor.recall(TOKEN);

        actor.attemptsTo(
                Get.resource(CALIFICACIONES)
                        .with(peticion -> peticion
                                // Devuelve las cookies del actor (mismo servidor siempre)
                                .filter(CookiesDelActor.de(actor))
                                .header("Authorization", "Bearer " + token))
        );
    }
}
