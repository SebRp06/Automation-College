package co.com.demo.quickpizza.tasks;

import co.com.demo.quickpizza.models.PreferenciasDePizza;
import io.restassured.http.ContentType;
import io.restassured.mapper.ObjectMapperType;
import co.com.demo.quickpizza.utils.CookiesDelActor;
import net.serenitybdd.annotations.Step;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.Tasks;
import net.serenitybdd.screenplay.rest.interactions.Post;

import static co.com.demo.quickpizza.utils.Constantes.RECOMENDAR_PIZZA;
import static co.com.demo.quickpizza.utils.Constantes.TOKEN_PUBLICO;

/**
 * =========================================================================
 * TASKS → PedirRecomendacionDePizza        (protocolo: POST)
 * =========================================================================
 *   POST https://quickpizza.grafana.com/api/pizza
 *   Authorization: Token abcdef0123456789
 *   Body: { "maxCaloriesPerSlice": 500, "mustBeVegetarian": false, ... }
 *
 * Respuesta esperada (200), por ejemplo:
 *   { "pizza": { "name": "...", "ingredients": [...] }, "vegetarian": false, ... }
 *
 * Ojo: este servicio usa el formato "Token xxx" (no "Bearer xxx").
 * Cada API define su propio formato de autorización: ¡siempre lee la documentación!
 */
public class PedirRecomendacionDePizza implements Task {

    private final PreferenciasDePizza preferencias;

    public PedirRecomendacionDePizza(PreferenciasDePizza preferencias) {
        this.preferencias = preferencias;
    }

    public static PedirRecomendacionDePizza con(PreferenciasDePizza preferencias) {
        return Tasks.instrumented(PedirRecomendacionDePizza.class, preferencias);
    }

    @Step("{0} pide una pizza con #preferencias")
    @Override
    public <T extends Actor> void performAs(T actor) {
        actor.attemptsTo(
                Post.to(RECOMENDAR_PIZZA)
                        .with(peticion -> peticion
                                // Devuelve las cookies del actor (mismo servidor siempre)
                                .filter(CookiesDelActor.de(actor))
                                .header("Authorization", "Token " + TOKEN_PUBLICO)
                                .contentType(ContentType.JSON)
                                .body(preferencias, ObjectMapperType.JACKSON_2))
        );
    }
}
