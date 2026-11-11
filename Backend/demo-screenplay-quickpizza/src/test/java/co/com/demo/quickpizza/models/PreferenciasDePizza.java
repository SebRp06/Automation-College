package co.com.demo.quickpizza.models;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Collections;
import java.util.List;

/**
 * =========================================================================
 * MODELS → PreferenciasDePizza
 * =========================================================================
 * Las "restricciones" que le enviamos a QuickPizza para que nos recomiende
 * una pizza:
 *   {
 *     "maxCaloriesPerSlice": 500,
 *     "mustBeVegetarian": false,
 *     "excludedIngredients": [],
 *     "excludedTools": [],
 *     "minNumberOfToppings": 2,
 *     "maxNumberOfToppings": 5
 *   }
 *
 * Fíjate que un JSON puede tener números, verdadero/falso (boolean) y
 * listas ([]). Jackson convierte cada tipo de Java al tipo JSON correcto.
 */
public class PreferenciasDePizza {

    @JsonProperty("maxCaloriesPerSlice")
    private final int maxCaloriasPorPorcion;

    @JsonProperty("mustBeVegetarian")
    private final boolean debeSerVegetariana;

    @JsonProperty("excludedIngredients")
    private final List<String> ingredientesExcluidos = Collections.emptyList();

    @JsonProperty("excludedTools")
    private final List<String> herramientasExcluidas = Collections.emptyList();

    @JsonProperty("minNumberOfToppings")
    private final int minIngredientes;

    @JsonProperty("maxNumberOfToppings")
    private final int maxIngredientes;

    public PreferenciasDePizza(int maxCaloriasPorPorcion, boolean debeSerVegetariana,
                               int minIngredientes, int maxIngredientes) {
        this.maxCaloriasPorPorcion = maxCaloriasPorPorcion;
        this.debeSerVegetariana = debeSerVegetariana;
        this.minIngredientes = minIngredientes;
        this.maxIngredientes = maxIngredientes;
    }

    public int getMaxCaloriasPorPorcion() {
        return maxCaloriasPorPorcion;
    }

    public boolean isDebeSerVegetariana() {
        return debeSerVegetariana;
    }

    public List<String> getIngredientesExcluidos() {
        return ingredientesExcluidos;
    }

    public List<String> getHerramientasExcluidas() {
        return herramientasExcluidas;
    }

    public int getMinIngredientes() {
        return minIngredientes;
    }

    public int getMaxIngredientes() {
        return maxIngredientes;
    }

    @Override
    public String toString() {
        return "máx. " + maxCaloriasPorPorcion + " calorías, " + minIngredientes + "-" + maxIngredientes
                + " ingredientes" + (debeSerVegetariana ? ", vegetariana" : "");
    }
}
