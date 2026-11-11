package co.com.demo.quickpizza.models;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * =========================================================================
 * MODELS → Usuario
 * =========================================================================
 * Un "modelo" es una clase Java que representa la ESTRUCTURA de los datos
 * que enviamos o recibimos de la API.
 *
 * Para registrarse y para iniciar sesión, QuickPizza espera este JSON:
 *   { "username": "qa123@test.com", "password": "pizzasecreta123" }
 *
 * Rest Assured (con ayuda de Jackson) convierte AUTOMÁTICAMENTE este objeto
 * Java en ese JSON. ¡No tenemos que escribir el JSON a mano!
 */
public class Usuario {

    // @JsonProperty indica el nombre EXACTO del campo en el JSON.
    // En Java usamos "nombreDeUsuario", pero la API lo llama "username".
    @JsonProperty("username")
    private final String nombreDeUsuario;

    @JsonProperty("password")
    private final String clave;

    public Usuario(String nombreDeUsuario, String clave) {
        this.nombreDeUsuario = nombreDeUsuario;
        this.clave = clave;
    }

    // Getters: métodos para LEER cada dato.
    public String getNombreDeUsuario() {
        return nombreDeUsuario;
    }

    public String getClave() {
        return clave;
    }

    /** Devuelve una copia del mismo usuario pero con otra contraseña (para pruebas negativas). */
    public Usuario conClave(String otraClave) {
        return new Usuario(nombreDeUsuario, otraClave);
    }

    /** Así se verá el usuario en el reporte de Serenity (sin mostrar la contraseña). */
    @Override
    public String toString() {
        return nombreDeUsuario;
    }
}
