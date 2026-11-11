package co.com.demo.quickpizza.runners;

import org.junit.platform.suite.api.ConfigurationParameter;
import org.junit.platform.suite.api.IncludeEngines;
import org.junit.platform.suite.api.SelectClasspathResource;
import org.junit.platform.suite.api.Suite;

import static io.cucumber.junit.platform.engine.Constants.FILTER_TAGS_PROPERTY_NAME;
import static io.cucumber.junit.platform.engine.Constants.GLUE_PROPERTY_NAME;
import static io.cucumber.junit.platform.engine.Constants.PLUGIN_PROPERTY_NAME;

/**
 * =========================================================================
 * RUNNERS → CucumberTestSuite
 * =========================================================================
 * El "Runner" es el BOTÓN DE PLAY de las pruebas. Le dice a Cucumber:
 *   - Dónde están los archivos .feature          (@SelectClasspathResource)
 *   - Dónde están los Step Definitions (glue)    (GLUE_PROPERTY_NAME)
 *   - Qué "plugins" usar para el reporte          (PLUGIN_PROPERTY_NAME)
 *
 * Formas de ejecutar:
 *   - Clic derecho sobre esta clase → Run (en IntelliJ / Eclipse)
 *   - Por consola:  mvn clean verify
 *   - Solo un tag:  mvn clean verify -Dcucumber.filter.tags="@put"
 */
@Suite
@IncludeEngines("cucumber")
@SelectClasspathResource("features")
@ConfigurationParameter(key = GLUE_PROPERTY_NAME,
        value = "co.com.demo.quickpizza.stepdefinitions")
@ConfigurationParameter(key = PLUGIN_PROPERTY_NAME,
        value = "io.cucumber.core.plugin.SerenityReporterParallel,pretty")
// Descomenta la siguiente línea para ejecutar solo los escenarios con cierto tag
// @ConfigurationParameter(key = FILTER_TAGS_PROPERTY_NAME, value = "@post")
public class CucumberTestSuite {
    // Esta clase queda vacía a propósito: toda la configuración está en las anotaciones.
}
