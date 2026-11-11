package co.com.demo.quickpizza.questions;

import net.serenitybdd.rest.SerenityRest;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Question;

/**
 * =========================================================================
 * QUESTIONS → ValorDelCampo
 * =========================================================================
 * Pregunta GENÉRICA y reutilizable: lee cualquier campo del JSON de respuesta.
 *
 * Si la API responde:
 *   { "id": 7, "stars": 5, "pizza_id": 1 }
 *
 * Entonces:
 *   ValorDelCampo.llamado("stars")  → "5"
 *   ValorDelCampo.llamado("id")     → "7"
 *
 * Para campos "dentro de otros" usamos punto:
 *   { "pizza": { "name": "Margarita" } }  →  ValorDelCampo.llamado("pizza.name")
 *
 * Una sola clase nos sirve para validar muchos campos distintos.
 */
public class ValorDelCampo implements Question<String> {

    private final String campo;

    public ValorDelCampo(String campo) {
        this.campo = campo;
    }

    public static ValorDelCampo llamado(String campo) {
        return new ValorDelCampo(campo);
    }

    @Override
    public String answeredBy(Actor actor) {
        return SerenityRest.lastResponse().jsonPath().getString(campo);
    }

    /** Texto que se mostrará en el reporte de Serenity. */
    @Override
    public String toString() {
        return "el valor del campo '" + campo + "' en la respuesta";
    }
}
