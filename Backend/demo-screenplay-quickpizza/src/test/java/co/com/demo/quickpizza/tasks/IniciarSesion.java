package co.com.demo.quickpizza.tasks;

import co.com.demo.quickpizza.interactions.RecordarDato;
import co.com.demo.quickpizza.models.Usuario;
import io.restassured.http.ContentType;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.mapper.ObjectMapperType;
import co.com.demo.quickpizza.utils.CookiesDelActor;
import net.serenitybdd.annotations.Step;
import net.serenitybdd.rest.SerenityRest;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.Tasks;
import net.serenitybdd.screenplay.rest.interactions.Post;

import static co.com.demo.quickpizza.utils.Constantes.INICIAR_SESION;
import static co.com.demo.quickpizza.utils.Constantes.TOKEN;

/**
 * =========================================================================
 * TASKS → IniciarSesion                    (protocolo: POST + memoria)
 * =========================================================================
 * Ejemplo de una tarea compuesta por VARIAS interacciones:
 *
 *   1. POST https://quickpizza.grafana.com/api/users/token/login
 *      Body: { "username": "...", "password": "..." }
 *      Respuesta: { "token": "..." }
 *   2. Tomar el campo "token" de la respuesta y recordarlo.
 *
 * Ese token es como una "manilla de acceso" a un evento: sin ella no puedes
 * entrar a los servicios privados (/api/ratings).
 */
public class IniciarSesion implements Task {

    private final Usuario usuario;

    public IniciarSesion(Usuario usuario) {
        this.usuario = usuario;
    }

    public static IniciarSesion como(Usuario usuario) {
        return Tasks.instrumented(IniciarSesion.class, usuario);
    }

    @Step("{0} inicia sesión como #usuario")
    @Override
    public <T extends Actor> void performAs(T actor) {
        actor.attemptsTo(
                // Interacción 1: enviar las credenciales
                Post.to(INICIAR_SESION)
                        .with(peticion -> peticion
                                // Devuelve las cookies del actor (mismo servidor siempre)
                                .filter(CookiesDelActor.de(actor))
                                .contentType(ContentType.JSON)
                                .body(usuario, ObjectMapperType.JACKSON_2)
                                // Depuración: imprime en consola la petición y la respuesta.
                                // Muy útil cuando una prueba falla y no sabes por qué.
                                .log().all()
                                .filter(new ResponseLoggingFilter()))
        );

        // Interacción 2 (propia): guardar el token, SOLO si el login funcionó.
        // En la prueba negativa (contraseña incorrecta) no habrá token que guardar.
        if (SerenityRest.lastResponse().statusCode() == 200) {
            actor.attemptsTo(RecordarDato.delCampo("token").como(TOKEN));
        }
    }
}
