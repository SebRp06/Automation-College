package co.com.demo.quickpizza.stepdefinitions;

import io.cucumber.java.Before;
import net.serenitybdd.screenplay.actors.OnStage;
import net.serenitybdd.screenplay.actors.OnlineCast;

/**
 * =========================================================================
 * STEP DEFINITIONS → Hooks
 * =========================================================================
 * Un "hook" (gancho) es código que se ejecuta automáticamente ANTES o
 * DESPUÉS de cada escenario.
 *
 * En Screenplay usamos la metáfora del TEATRO:
 *   - Stage (escenario)  → donde actúan los actores.
 *   - Cast  (elenco)     → el grupo de actores disponibles.
 *   - Actor              → quien ejecuta las tareas (Ana, Carlos, Laura...).
 *
 * Aquí "montamos el escenario" antes de cada prueba, con un elenco nuevo,
 * para que cada escenario empiece limpio (sin memoria de pruebas anteriores).
 */
public class Hooks {

    @Before
    public void prepararEscenario() {
        OnStage.setTheStage(new OnlineCast());
    }
}
