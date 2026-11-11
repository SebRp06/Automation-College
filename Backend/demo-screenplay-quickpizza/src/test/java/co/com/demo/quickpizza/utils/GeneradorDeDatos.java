package co.com.demo.quickpizza.utils;

import java.util.UUID;

/**
 * =========================================================================
 * UTILS → GeneradorDeDatos
 * =========================================================================
 * La API no permite registrar dos veces el mismo usuario. Si usáramos
 * siempre "carlos@correo.com", la prueba funcionaría UNA vez y luego
 * fallaría para siempre.
 *
 * Solución: generar un usuario distinto en cada ejecución usando UUID
 * (un identificador aleatorio prácticamente imposible de repetir).
 */
public final class GeneradorDeDatos {

    /** Contraseña de prueba para los usuarios que creamos. */
    public static final String CLAVE_POR_DEFECTO = "pizzasecreta123";

    private GeneradorDeDatos() {
    }

    /** Crea un usuario único, por ejemplo: qa3f9a1c2b@test.com */
    public static String usuarioAleatorio() {
        String parteAleatoria = UUID.randomUUID().toString().substring(0, 8);
        return "qa" + parteAleatoria + "@test.com";
    }
}
