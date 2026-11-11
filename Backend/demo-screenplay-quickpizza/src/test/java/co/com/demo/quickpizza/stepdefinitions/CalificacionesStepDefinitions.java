package co.com.demo.quickpizza.stepdefinitions;

import co.com.demo.quickpizza.models.Calificacion;
import co.com.demo.quickpizza.models.Usuario;
import co.com.demo.quickpizza.questions.CantidadDeCalificaciones;
import co.com.demo.quickpizza.questions.CodigoDeRespuesta;
import co.com.demo.quickpizza.questions.ValorDelCampo;
import co.com.demo.quickpizza.tasks.ActualizarCalificacion;
import co.com.demo.quickpizza.tasks.CalificarPizza;
import co.com.demo.quickpizza.tasks.ConsultarCalificacion;
import co.com.demo.quickpizza.tasks.ConsultarMisCalificaciones;
import co.com.demo.quickpizza.tasks.IniciarSesion;
import co.com.demo.quickpizza.tasks.RegistrarUsuario;
import co.com.demo.quickpizza.utils.GeneradorDeDatos;
import io.cucumber.java.es.Cuando;
import io.cucumber.java.es.Dado;
import io.cucumber.java.es.Entonces;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.rest.abilities.CallAnApi;

import static co.com.demo.quickpizza.utils.Constantes.URL_BASE;
import static net.serenitybdd.screenplay.GivenWhenThen.seeThat;
import static net.serenitybdd.screenplay.actors.OnStage.theActorCalled;
import static net.serenitybdd.screenplay.actors.OnStage.theActorInTheSpotlight;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.greaterThan;

/**
 * =========================================================================
 * STEP DEFINITIONS → feature: calificar_pizzas.feature
 *                    (POST + GET + PUT con autenticación)
 * =========================================================================
 */
public class CalificacionesStepDefinitions {

    @Dado("que {string} es una usuaria registrada que inició sesión")
    public void esUnaUsuariaRegistradaQueInicioSesion(String nombreDelActor) {
        Actor actor = theActorCalled(nombreDelActor).whoCan(CallAnApi.at(URL_BASE));

        Usuario usuario = new Usuario(GeneradorDeDatos.usuarioAleatorio(), GeneradorDeDatos.CLAVE_POR_DEFECTO);

        // Una sola línea ejecuta VARIAS tareas en orden: registrarse y luego iniciar sesión.
        // Se lee casi como una historia: "Laura intenta registrarse e iniciar sesión".
        actor.attemptsTo(
                RegistrarUsuario.con(usuario),
                IniciarSesion.como(usuario)
        );

        // Verificamos que el login funcionó antes de seguir (si falla aquí, el error es claro)
        actor.should(seeThat(CodigoDeRespuesta.obtenido(), equalTo(200)));
    }

    @Cuando("califica la pizza {int} con {int} estrellas")
    public void calificaLaPizza(int idPizza, int estrellas) {
        theActorInTheSpotlight().attemptsTo(
                CalificarPizza.con(Calificacion.deLaPizza(idPizza, estrellas))
        );
    }

    // Precondición de los escenarios GET y PUT: reutiliza el paso anterior y lo verifica
    @Dado("que ya calificó la pizza {int} con {int} estrellas")
    public void yaCalificoLaPizza(int idPizza, int estrellas) {
        calificaLaPizza(idPizza, estrellas);
        theActorInTheSpotlight().should(seeThat(CodigoDeRespuesta.obtenido(), equalTo(201)));
    }

    @Cuando("consulta sus calificaciones")
    public void consultaSusCalificaciones() {
        theActorInTheSpotlight().attemptsTo(
                ConsultarMisCalificaciones.todas()
        );
    }

    @Cuando("actualiza su calificación a {int} estrellas")
    public void actualizaSuCalificacion(int estrellas) {
        theActorInTheSpotlight().attemptsTo(
                ActualizarCalificacion.con(Calificacion.conEstrellas(estrellas))
        );
    }

    @Entonces("la calificación en la respuesta debe tener {int} estrellas")
    public void laCalificacionDebeTener(int estrellasEsperadas) {
        theActorInTheSpotlight().should(
                // jsonPath devuelve texto, por eso comparamos contra el número como String
                seeThat(ValorDelCampo.llamado("stars"), equalTo(String.valueOf(estrellasEsperadas)))
        );
    }

    @Entonces("debe ver al menos una calificación")
    public void debeVerAlMenosUnaCalificacion() {
        theActorInTheSpotlight().should(
                seeThat(CantidadDeCalificaciones.enLaRespuesta(), greaterThan(0))
        );
    }

    @Entonces("al consultarla nuevamente debe tener {int} estrellas")
    public void alConsultarlaNuevamenteDebeTener(int estrellasEsperadas) {
        // Buena práctica: después de un PUT, hacemos un GET para confirmar
        // que el cambio realmente quedó guardado en el servidor.
        Actor actor = theActorInTheSpotlight();
        actor.attemptsTo(ConsultarCalificacion.recienCreada());
        actor.should(
                seeThat(CodigoDeRespuesta.obtenido(), equalTo(200)),
                seeThat(ValorDelCampo.llamado("stars"), equalTo(String.valueOf(estrellasEsperadas)))
        );
    }
}
