package co.com.demo.quickpizza.stepdefinitions;

import co.com.demo.quickpizza.models.Usuario;
import co.com.demo.quickpizza.questions.CodigoDeRespuesta;
import co.com.demo.quickpizza.questions.ValorDelCampo;
import co.com.demo.quickpizza.tasks.IniciarSesion;
import co.com.demo.quickpizza.tasks.RegistrarUsuario;
import co.com.demo.quickpizza.utils.GeneradorDeDatos;
import io.cucumber.java.es.Cuando;
import io.cucumber.java.es.Dado;
import io.cucumber.java.es.Entonces;
import net.serenitybdd.screenplay.Actor;

import static co.com.demo.quickpizza.utils.Constantes.USUARIO_REGISTRADO;
import static net.serenitybdd.screenplay.GivenWhenThen.seeThat;
import static net.serenitybdd.screenplay.actors.OnStage.theActorInTheSpotlight;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.emptyOrNullString;

/**
 * =========================================================================
 * STEP DEFINITIONS → feature: registro_e_inicio_de_sesion.feature (POST)
 * =========================================================================
 * Fíjate en el patrón de cada método:
 *   - "Cuando"   → actor.attemptsTo( TAREA )         (hace algo)
 *   - "Entonces" → actor.should( seeThat(PREGUNTA) ) (verifica algo)
 */
public class UsuariosStepDefinitions {

    /** Arma un usuario con un nombre ÚNICO para que la prueba sea repetible. */
    private Usuario usuarioNuevo() {
        return new Usuario(GeneradorDeDatos.usuarioAleatorio(), GeneradorDeDatos.CLAVE_POR_DEFECTO);
    }

    @Cuando("se registra con un usuario nuevo")
    public void seRegistraConUnUsuarioNuevo() {
        theActorInTheSpotlight().attemptsTo(
                RegistrarUsuario.con(usuarioNuevo())
        );
    }

    // Misma acción, pero escrita como precondición ("Y ya se registró...").
    // Aquí sí verificamos el resultado: si el registro falla, el error queda claro.
    @Dado("ya se registró con un usuario nuevo")
    public void yaSeRegistroConUnUsuarioNuevo() {
        seRegistraConUnUsuarioNuevo();
        theActorInTheSpotlight().should(seeThat(CodigoDeRespuesta.obtenido(), equalTo(201)));
    }

    @Cuando("inicia sesión con su usuario y contraseña")
    public void iniciaSesionConSuUsuarioYContrasena() {
        Actor actor = theActorInTheSpotlight();
        // Recuperamos el usuario que la tarea RegistrarUsuario guardó en la memoria del actor
        Usuario usuario = actor.recall(USUARIO_REGISTRADO);

        actor.attemptsTo(IniciarSesion.como(usuario));
    }

    @Cuando("inicia sesión con la contraseña {string}")
    public void iniciaSesionConLaContrasena(String claveIncorrecta) {
        Actor actor = theActorInTheSpotlight();
        Usuario usuario = actor.recall(USUARIO_REGISTRADO);

        actor.attemptsTo(IniciarSesion.como(usuario.conClave(claveIncorrecta)));
    }

    @Entonces("la respuesta debe contener un token de acceso")
    public void laRespuestaDebeContenerUnToken() {
        theActorInTheSpotlight().should(
                // not(emptyOrNullString()) = "no está vacío ni es nulo"
                seeThat(ValorDelCampo.llamado("token"), not(emptyOrNullString()))
        );
    }
}
