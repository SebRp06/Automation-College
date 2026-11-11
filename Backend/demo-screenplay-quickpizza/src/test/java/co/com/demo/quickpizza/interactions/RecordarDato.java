package co.com.demo.quickpizza.interactions;

import net.serenitybdd.annotations.Step;
import net.serenitybdd.rest.SerenityRest;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Interaction;
import net.serenitybdd.screenplay.Tasks;

/**
 * =========================================================================
 * INTERACTIONS → RecordarDato
 * =========================================================================
 * ¿Qué es una INTERACCIÓN en Screenplay?
 *   Es la acción MÁS PEQUEÑA y técnica que puede hacer un actor.
 *   Ejemplos: hacer clic, escribir un texto, enviar un GET, enviar un POST.
 *
 * ¿Y una TAREA (Task)?
 *   Es algo con SENTIDO DE NEGOCIO, compuesto por una o varias interacciones.
 *   Ejemplo: "Iniciar sesión" = enviar POST + recordar el token.
 *
 * Serenity ya trae interacciones listas para REST:
 *   Get.resource(...), Post.to(...), Put.to(...), Delete.from(...)
 * Por eso no las creamos nosotros.
 *
 * Esta interacción es PROPIA del proyecto: toma un valor de la última
 * respuesta de la API y lo guarda en la "memoria" del actor para usarlo en
 * pasos posteriores (por ejemplo, el token o el id de la calificación creada).
 *
 * Uso:
 *   actor.attemptsTo( RecordarDato.delCampo("id").como("ID_CALIFICACION") );
 */
public class RecordarDato implements Interaction {

    private final String campoJson;
    private final String clave;

    public RecordarDato(String campoJson, String clave) {
        this.campoJson = campoJson;
        this.clave = clave;
    }

    /** Paso 1 del "builder": indica qué campo leer del JSON de respuesta. */
    public static Builder delCampo(String campoJson) {
        return new Builder(campoJson);
    }

    /**
     * @Step hace que esta acción aparezca en el reporte de Serenity.
     * {0} se reemplaza por el nombre del actor; #campoJson por el valor del atributo.
     */
    @Step("{0} recuerda el campo '#campoJson' de la respuesta")
    @Override
    public <T extends Actor> void performAs(T actor) {
        // SerenityRest.lastResponse() = la última respuesta HTTP que recibió el actor
        String valor = SerenityRest.lastResponse().jsonPath().getString(campoJson);

        // remember(clave, valor) guarda el dato en la memoria del actor
        actor.remember(clave, valor);
    }

    /** Clase auxiliar para que el uso se lea como una frase en español. */
    public static class Builder {
        private final String campoJson;

        Builder(String campoJson) {
            this.campoJson = campoJson;
        }

        public RecordarDato como(String clave) {
            // Tasks.instrumented(...) crea la interacción de forma que Serenity
            // pueda registrarla en el reporte (siempre úsalo para crear Tasks/Interactions).
            return Tasks.instrumented(RecordarDato.class, campoJson, clave);
        }
    }
}
