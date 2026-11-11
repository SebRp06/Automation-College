package co.com.demo.quickpizza.tasks;

import co.com.demo.quickpizza.interactions.RecordarDato;
import co.com.demo.quickpizza.models.Calificacion;
import io.restassured.http.ContentType;
import io.restassured.mapper.ObjectMapperType;
import co.com.demo.quickpizza.utils.CookiesDelActor;
import net.serenitybdd.annotations.Step;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.Tasks;
import net.serenitybdd.screenplay.rest.interactions.Post;

import static co.com.demo.quickpizza.utils.Constantes.CALIFICACIONES;
import static co.com.demo.quickpizza.utils.Constantes.ID_CALIFICACION;
import static co.com.demo.quickpizza.utils.Constantes.TOKEN;

/**
 * =========================================================================
 * TASKS → CalificarPizza                   (protocolo: POST autenticado)
 * =========================================================================
 *   POST https://quickpizza.grafana.com/api/ratings
 *   Authorization: Bearer <token>
 *   Body: { "stars": 2, "pizza_id": 1 }
 *
 * Respuesta esperada: 201 Created, con el "id" de la calificación nueva.
 * Guardamos ese id para poder consultarla (GET) o actualizarla (PUT) después.
 */
public class CalificarPizza implements Task {

    private final Calificacion calificacion;

    public CalificarPizza(Calificacion calificacion) {
        this.calificacion = calificacion;
    }

    public static CalificarPizza con(Calificacion calificacion) {
        return Tasks.instrumented(CalificarPizza.class, calificacion);
    }

    @Step("{0} califica con #calificacion")
    @Override
    public <T extends Actor> void performAs(T actor) {
        // recall(clave) = recuperar un dato que el actor guardó antes
        String token = actor.recall(TOKEN);

        actor.attemptsTo(
                Post.to(CALIFICACIONES)
                        .with(peticion -> peticion
                                // Devuelve las cookies del actor (mismo servidor siempre)
                                .filter(CookiesDelActor.de(actor))
                                // Header de autorización: así la API sabe quiénes somos
                                .header("Authorization", "Bearer " + token)
                                .contentType(ContentType.JSON)
                                .body(calificacion, ObjectMapperType.JACKSON_2)),

                RecordarDato.delCampo("id").como(ID_CALIFICACION)
        );
    }
}
