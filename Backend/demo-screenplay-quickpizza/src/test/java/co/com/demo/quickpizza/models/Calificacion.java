package co.com.demo.quickpizza.models;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * =========================================================================
 * MODELS → Calificacion
 * =========================================================================
 * Representa la calificación que un usuario le da a una pizza.
 *
 * Para CREAR (POST) enviamos:     { "stars": 2, "pizza_id": 1 }
 * Para ACTUALIZAR (PUT) enviamos: { "stars": 5 }
 *
 * @JsonInclude(NON_NULL) hace que los campos vacíos (null) NO se envíen.
 * Así usamos el mismo modelo para los dos casos.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Calificacion {

    @JsonProperty("stars")
    private final Integer estrellas; // de 1 a 5

    @JsonProperty("pizza_id")
    private final Integer idPizza;

    private Calificacion(Integer estrellas, Integer idPizza) {
        this.estrellas = estrellas;
        this.idPizza = idPizza;
    }

    /** Para crear: una pizza y sus estrellas. */
    public static Calificacion deLaPizza(int idPizza, int estrellas) {
        return new Calificacion(estrellas, idPizza);
    }

    /** Para actualizar: solo las estrellas nuevas. */
    public static Calificacion conEstrellas(int estrellas) {
        return new Calificacion(estrellas, null);
    }

    public Integer getEstrellas() {
        return estrellas;
    }

    public Integer getIdPizza() {
        return idPizza;
    }

    @Override
    public String toString() {
        return estrellas + " estrellas" + (idPizza != null ? " para la pizza " + idPizza : "");
    }
}
