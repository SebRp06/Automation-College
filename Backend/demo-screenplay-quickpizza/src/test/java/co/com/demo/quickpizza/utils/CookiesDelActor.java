package co.com.demo.quickpizza.utils;

import io.restassured.filter.cookie.CookieFilter;
import net.serenitybdd.screenplay.Actor;

import static co.com.demo.quickpizza.utils.Constantes.COOKIES;

/**
 * =========================================================================
 * UTILS → CookiesDelActor
 * =========================================================================
 * ¿Qué es una cookie?
 *   Un pequeño dato que el servidor le entrega al cliente en la respuesta
 *   (header "Set-Cookie") y que el cliente debe devolver en las siguientes
 *   peticiones (header "Cookie").
 *
 * ¿Por qué la necesitamos aquí?
 *   QuickPizza tiene VARIOS servidores detrás de un balanceador de carga
 *   (AWS). El balanceador entrega la cookie "AWSALB" para recordar a qué
 *   servidor te envió. Si no la devolvemos, el registro puede quedar en el
 *   servidor A y el inicio de sesión llegar al servidor B, que no conoce al
 *   usuario → 401 "authentication failed".
 *
 *   Un navegador (o k6) devuelve las cookies automáticamente.
 *   Rest Assured NO: hay que pedírselo con un CookieFilter.
 *
 * Cada actor tiene su PROPIO CookieFilter, guardado en su memoria, igual
 * que una persona real tiene sus propias cookies en su navegador.
 *
 * Uso dentro de una tarea:
 *   .with(peticion -> peticion.filter(CookiesDelActor.de(actor)) ...)
 */
public final class CookiesDelActor {

    private CookiesDelActor() {
    }

    /** Devuelve el "frasco de cookies" del actor (lo crea la primera vez). */
    public static CookieFilter de(Actor actor) {
        CookieFilter cookies = actor.recall(COOKIES);
        if (cookies == null) {
            cookies = new CookieFilter();
            actor.remember(COOKIES, cookies);
        }
        return cookies;
    }
}
