package co.com.demo.quickpizza.tasks;

import co.com.demo.quickpizza.models.Calificacion;
import io.restassured.http.ContentType;
import io.restassured.mapper.ObjectMapperType;
import co.com.demo.quickpizza.utils.CookiesDelActor;
import net.serenitybdd.annotations.Step;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.Tasks;
import net.serenitybdd.screenplay.rest.interactions.Put;

import static co.com.demo.quickpizza.utils.Constantes.CALIFICACION_POR_ID;
import static co.com.demo.quickpizza.utils.Constantes.ID_CALIFICACION;
import static co.com.demo.quickpizza.utils.Constantes.TOKEN;

/**
 * =========================================================================
 * TASKS → ActualizarCalificacion           (protocolo: PUT)
 * =========================================================================
 *   PUT https://quickpizza.grafana.com/api/ratings/{id}
 *   Authorization: Bearer <token>
 *   Body: { "stars": 5 }
 *
 * ¿PUT o PATCH?
 *   - PUT   en teoría reemplaza el recurso COMPLETO.
 *   - PATCH modifica solo los campos que envías.
 *   En la práctica, cada API decide qué campos exige: QuickPizza acepta
 *   un PUT con solo las estrellas, que es lo único que se puede cambiar.
 *
 * Respuesta esperada: 200 OK con la calificación ya actualizada.
 */
public class ActualizarCalificacion implements Task {

    private final Calificacion datosNuevos;

    public ActualizarCalificacion(Calificacion datosNuevos) {
        this.datosNuevos = datosNuevos;
    }

    public static ActualizarCalificacion con(Calificacion datosNuevos) {
        return Tasks.instrumented(ActualizarCalificacion.class, datosNuevos);
    }

    @Step("{0} actualiza su calificación a #datosNuevos")
    @Override
    public <T extends Actor> void performAs(T actor) {
        String token = actor.recall(TOKEN);
        String id = actor.recall(ID_CALIFICACION);

        actor.attemptsTo(
                Put.to(CALIFICACION_POR_ID)
                        .with(peticion -> peticion
                                // Devuelve las cookies del actor (mismo servidor siempre)
                                .filter(CookiesDelActor.de(actor))
                                .header("Authorization", "Bearer " + token)
                                .pathParam("id", id)
                                .contentType(ContentType.JSON)
                                .body(datosNuevos, ObjectMapperType.JACKSON_2))
        );
    }
}
