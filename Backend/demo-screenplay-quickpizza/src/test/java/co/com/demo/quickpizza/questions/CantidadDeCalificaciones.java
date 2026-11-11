package co.com.demo.quickpizza.questions;

import net.serenitybdd.rest.SerenityRest;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Question;
import net.serenitybdd.screenplay.annotations.Subject;

/**
 * =========================================================================
 * QUESTIONS → CantidadDeCalificaciones
 * =========================================================================
 * Responde: "¿Cuántas calificaciones vinieron en la respuesta?"
 *
 * La API devuelve:  { "ratings": [ {...}, {...} ] }
 * Con jsonPath "ratings" obtenemos la lista y contamos sus elementos.
 */
@Subject("la cantidad de calificaciones")
public class CantidadDeCalificaciones implements Question<Integer> {

    public static CantidadDeCalificaciones enLaRespuesta() {
        return new CantidadDeCalificaciones();
    }

    @Override
    public Integer answeredBy(Actor actor) {
        return SerenityRest.lastResponse().jsonPath().getList("ratings").size();
    }
}
