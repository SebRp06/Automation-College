package co.com.demo.quickpizza.tasks;

import co.com.demo.quickpizza.models.Usuario;
import io.restassured.http.ContentType;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.mapper.ObjectMapperType;
import co.com.demo.quickpizza.utils.CookiesDelActor;
import net.serenitybdd.annotations.Step;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.Tasks;
import net.serenitybdd.screenplay.rest.interactions.Post;

import static co.com.demo.quickpizza.utils.Constantes.REGISTRAR_USUARIO;
import static co.com.demo.quickpizza.utils.Constantes.USUARIO_REGISTRADO;

/**
 * =========================================================================
 * TASKS → RegistrarUsuario                 (protocolo: POST)
 * =========================================================================
 * ¿Qué es una TAREA en Screenplay?
 *   Describe QUÉ hace el actor en términos de negocio ("registrarse").
 *   Por dentro usa INTERACCIONES técnicas (aquí: Post de Serenity, que a su
 *   vez usa Rest Assured para enviar la petición HTTP).
 *
 *   POST https://quickpizza.grafana.com/api/users
 *   Content-Type: application/json
 *   Body: { "username": "...", "password": "..." }
 *
 * Diferencia clave con GET: POST lleva un CUERPO (body) con la información.
 *
 * Uso en un Step Definition:
 *   actor.attemptsTo( RegistrarUsuario.con(usuario) );
 */
public class RegistrarUsuario implements Task {

    private final Usuario usuario;

    public RegistrarUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    /** Método "fábrica": hace que el código se lea como una frase. */
    public static RegistrarUsuario con(Usuario usuario) {
        // Tasks.instrumented(...) crea la tarea de forma que Serenity
        // pueda registrarla en el reporte (siempre úsalo para crear Tasks).
        return Tasks.instrumented(RegistrarUsuario.class, usuario);
    }

    /**
     * @Step hace que esta acción aparezca en el reporte de Serenity.
     * {0} se reemplaza por el nombre del actor; #usuario por el valor del atributo.
     */
    @Step("{0} se registra en QuickPizza como #usuario")
    @Override
    public <T extends Actor> void performAs(T actor) {
        // attemptsTo = "intenta hacer". El actor ejecuta la interacción POST.
        actor.attemptsTo(
                Post.to(REGISTRAR_USUARIO)
                        .with(peticion -> peticion
                                // Devuelve las cookies del actor (mismo servidor siempre)
                                .filter(CookiesDelActor.de(actor))
                                // Le decimos a la API que le enviamos JSON
                                .contentType(ContentType.JSON)
                                // El objeto Java se convierte en JSON automáticamente.
                                // JACKSON_2 indica QUÉ librería hace la conversión: es la que
                                // entiende las anotaciones @JsonProperty de los modelos.
                                // (Serenity también trae Gson, que las ignora y enviaría
                                //  "nombreDeUsuario" en vez de "username").
                                .body(usuario, ObjectMapperType.JACKSON_2)
                                // Depuración: imprime en consola la petición que se envía
                                .log().all()
                                // ...y la respuesta que llega
                                .filter(new ResponseLoggingFilter()))
        );

        // Guardamos el usuario en la memoria del actor para iniciar sesión después
        actor.remember(USUARIO_REGISTRADO, usuario);
        // No validamos nada aquí: las validaciones son trabajo de las QUESTIONS.
    }
}
