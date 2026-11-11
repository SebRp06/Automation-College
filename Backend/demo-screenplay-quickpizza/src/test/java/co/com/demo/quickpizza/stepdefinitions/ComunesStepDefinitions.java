package co.com.demo.quickpizza.stepdefinitions;

import co.com.demo.quickpizza.questions.CodigoDeRespuesta;
import io.cucumber.java.es.Dado;
import io.cucumber.java.es.Entonces;
import net.serenitybdd.screenplay.rest.abilities.CallAnApi;

import static co.com.demo.quickpizza.utils.Constantes.URL_BASE;
import static net.serenitybdd.screenplay.GivenWhenThen.seeThat;
import static net.serenitybdd.screenplay.actors.OnStage.theActorCalled;
import static net.serenitybdd.screenplay.actors.OnStage.theActorInTheSpotlight;
import static org.hamcrest.Matchers.equalTo;

/**
 * =========================================================================
 * STEP DEFINITIONS → ComunesStepDefinitions
 * =========================================================================
 * Los "Step Definitions" son el PUENTE entre el archivo .feature (español)
 * y el código Java. Cada frase del feature se conecta con un método aquí.
 *
 *   Feature:  Entonces el código de respuesta debe ser 200
 *   Java:     @Entonces("el código de respuesta debe ser {int}")
 *
 * {int} y {string} capturan valores del feature y los pasan como parámetros.
 *
 * IMPORTANTE: los Step Definitions deben ser DELGADOS. Solo dicen
 * "actor, haz esta tarea" o "actor, verifica esto". La lógica real vive
 * en Tasks, Interactions y Questions.
 *
 * En esta clase están los pasos que se repiten en varios features.
 */
public class ComunesStepDefinitions {

    @Dado("que {string} quiere usar la API de QuickPizza")
    public void queElActorQuiereUsarLaApi(String nombreDelActor) {
        // 1. Llamamos al actor por su nombre (Serenity lo crea si no existe).
        // 2. whoCan(...) le da una HABILIDAD (Ability).
        //    CallAnApi = "puede llamar a una API" en la URL indicada.
        //    Por dentro, esta habilidad usa Rest Assured.
        theActorCalled(nombreDelActor).whoCan(CallAnApi.at(URL_BASE));
    }

    @Entonces("el código de respuesta debe ser {int}")
    public void elCodigoDeRespuestaDebeSer(int codigoEsperado) {
        // theActorInTheSpotlight() = el actor que está actuando en este momento.
        // should(seeThat(PREGUNTA, VALIDACIÓN)) se lee:
        //   "el actor debería ver que el código de respuesta es igual a X"
        theActorInTheSpotlight().should(
                seeThat(CodigoDeRespuesta.obtenido(), equalTo(codigoEsperado))
        );
    }
}
