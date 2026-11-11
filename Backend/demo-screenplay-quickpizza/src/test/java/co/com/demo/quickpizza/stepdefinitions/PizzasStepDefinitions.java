package co.com.demo.quickpizza.stepdefinitions;

import co.com.demo.quickpizza.models.PreferenciasDePizza;
import co.com.demo.quickpizza.questions.ValorDelCampo;
import co.com.demo.quickpizza.tasks.PedirRecomendacionDePizza;
import io.cucumber.java.es.Cuando;
import io.cucumber.java.es.Entonces;

import static net.serenitybdd.screenplay.GivenWhenThen.seeThat;
import static net.serenitybdd.screenplay.actors.OnStage.theActorInTheSpotlight;
import static org.hamcrest.Matchers.emptyOrNullString;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.not;

/**
 * =========================================================================
 * STEP DEFINITIONS → feature: recomendar_pizza.feature (POST)
 * =========================================================================
 */
public class PizzasStepDefinitions {

    @Cuando("pide una pizza de máximo {int} calorías por porción con entre {int} y {int} ingredientes")
    public void pideUnaPizza(int calorias, int minIngredientes, int maxIngredientes) {
        // Armamos el modelo con los datos que vienen del feature
        PreferenciasDePizza preferencias =
                new PreferenciasDePizza(calorias, false, minIngredientes, maxIngredientes);

        theActorInTheSpotlight().attemptsTo(
                PedirRecomendacionDePizza.con(preferencias)
        );
    }

    @Cuando("pide una pizza vegetariana")
    public void pideUnaPizzaVegetariana() {
        theActorInTheSpotlight().attemptsTo(
                PedirRecomendacionDePizza.con(new PreferenciasDePizza(1000, true, 2, 5))
        );
    }

    @Entonces("la pizza recomendada debe tener un nombre")
    public void laPizzaDebeTenerUnNombre() {
        theActorInTheSpotlight().should(
                // "pizza.name" = el campo "name" que está DENTRO del objeto "pizza"
                seeThat(ValorDelCampo.llamado("pizza.name"), not(emptyOrNullString()))
        );
    }

    @Entonces("la pizza recomendada debe ser vegetariana")
    public void laPizzaDebeSerVegetariana() {
        theActorInTheSpotlight().should(
                seeThat(ValorDelCampo.llamado("vegetarian"), equalTo("true"))
        );
    }
}
