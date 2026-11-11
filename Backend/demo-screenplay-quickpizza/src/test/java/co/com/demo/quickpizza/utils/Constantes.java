package co.com.demo.quickpizza.utils;

/**
 * =========================================================================
 * UTILS → Constantes
 * =========================================================================
 * En esta clase guardamos los valores FIJOS del proyecto: la URL base y las
 * rutas (endpoints) de la API.
 *
 * ¿Por qué no escribirlos directamente en cada clase?
 *   Porque si mañana la URL cambia, solo la modificamos AQUÍ y no en 20
 *   archivos distintos. A esto se le llama "una única fuente de verdad".
 *
 *   (¡Esto pasó de verdad! La API de cocodrilos de k6 fue retirada y
 *    reemplazada por QuickPizza. Con las URLs centralizadas, el cambio
 *    empieza en este archivo.)
 */
public final class Constantes {

    /** Dirección principal de QuickPizza, la API de práctica de Grafana k6. */
    public static final String URL_BASE = "https://quickpizza.grafana.com";

    // ------------------- Usuarios -------------------
    /** POST: registra un usuario nuevo. */
    public static final String REGISTRAR_USUARIO = "/api/users";
    /** POST: inicia sesión y devuelve un token de acceso. */
    public static final String INICIAR_SESION = "/api/users/token/login";

    // ------------------- Pizzas -------------------
    /** POST: pide una recomendación de pizza según tus restricciones. */
    public static final String RECOMENDAR_PIZZA = "/api/pizza";
    /**
     * Token "público" que la propia documentación de k6 usa para /api/pizza.
     * No pertenece a ningún usuario: sirve para pedir pizzas sin iniciar sesión.
     */
    public static final String TOKEN_PUBLICO = "abcdef0123456789";

    // ------------------- Calificaciones (requieren token de usuario) -------------------
    /** POST: crea una calificación. GET: lista mis calificaciones. */
    public static final String CALIFICACIONES = "/api/ratings";
    /** GET / PUT: consulta o actualiza una calificación. {id} se reemplaza por el número real. */
    public static final String CALIFICACION_POR_ID = "/api/ratings/{id}";

    // ------------------- Claves para la "memoria" del actor -------------------
    // El actor puede recordar datos entre pasos con actor.remember(clave, valor)
    // y recuperarlos con actor.recall(clave).
    public static final String TOKEN = "TOKEN_DE_ACCESO";
    public static final String USUARIO_REGISTRADO = "USUARIO_REGISTRADO";
    public static final String ID_CALIFICACION = "ID_CALIFICACION_CREADA";
    public static final String COOKIES = "COOKIES_DEL_ACTOR";

    /** Constructor privado: esta clase no se instancia, solo se usan sus constantes. */
    private Constantes() {
    }
}
