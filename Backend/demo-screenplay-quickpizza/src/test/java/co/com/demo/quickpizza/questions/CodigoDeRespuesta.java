package co.com.demo.quickpizza.questions;

import net.serenitybdd.rest.SerenityRest;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Question;
import net.serenitybdd.screenplay.annotations.Subject;

/**
 * =========================================================================
 * QUESTIONS → CodigoDeRespuesta
 * =========================================================================
 * ¿Qué es una PREGUNTA (Question) en Screenplay?
 *   Es la forma en que el actor OBSERVA el resultado de lo que hizo.
 *   Las preguntas NO hacen acciones, solo consultan información.
 *   Luego comparamos esa respuesta con lo esperado (la "validación").
 *
 * Esta pregunta responde: "¿Qué código HTTP devolvió la API?"
 *   200 = OK            201 = Creado
 *   400 = Datos malos   401 = Sin autorización   404 = No encontrado
 *
 * Uso:
 *   actor.should( seeThat(CodigoDeRespuesta.obtenido(), equalTo(200)) );
 */
@Subject("el código de respuesta HTTP") // texto que aparece en el reporte
public class CodigoDeRespuesta implements Question<Integer> {

    public static CodigoDeRespuesta obtenido() {
        return new CodigoDeRespuesta();
    }

    @Override
    public Integer answeredBy(Actor actor) {
        return SerenityRest.lastResponse().statusCode();
    }
}
