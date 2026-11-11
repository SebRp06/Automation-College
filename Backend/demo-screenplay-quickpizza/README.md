# 🍕 Demo de automatización de APIs — Screenplay + Maven + Java + Rest Assured

Proyecto de ejemplo para aprender a automatizar pruebas de APIs REST usando el
**patrón Screenplay** con **Serenity BDD**, **Cucumber** (escenarios en español)
y **Rest Assured**.

La API que probamos es **QuickPizza** (<https://quickpizza.grafana.com>), la API
pública de práctica de Grafana k6.

> ℹ️ **¿Y la API de cocodrilos?** Este proyecto empezó con la API de cocodrilos
> de k6 (`test-api.k6.io`), pero Grafana la retiró: hoy esa dirección redirige a
> QuickPizza. Es una buena lección: **las APIs cambian**, y por eso centralizamos
> las URLs en `utils/Constantes.java`.

---

## 1. Requisitos

| Herramienta | Versión | ¿Para qué? |
|---|---|---|
| Java JDK | 17 o superior | Lenguaje del proyecto |
| Maven | 3.8 o superior | Descarga librerías y ejecuta las pruebas |
| IntelliJ IDEA (recomendado) | Cualquiera | Editor. Instala los plugins **Cucumber for Java** y **Gherkin** |

```bash
java -version
mvn -version
```

## 2. ¿Cómo ejecutar?

```bash
# Ejecuta TODOS los escenarios y genera el reporte
mvn clean verify

# Ejecuta solo los escenarios con un tag
mvn clean verify -Dcucumber.filter.tags="@get"
mvn clean verify -Dcucumber.filter.tags="@post"
mvn clean verify -Dcucumber.filter.tags="@put"
```

También puedes hacer clic derecho sobre `runners/CucumberTestSuite` → **Run** en IntelliJ.

Al terminar, abre el reporte: `target/site/serenity/index.html`
(se genera con `mvn clean verify`; al ejecutar desde IntelliJ solo verás el resultado en consola).

---

## 3. ¿Qué es el patrón Screenplay?

Screenplay usa la metáfora de una **obra de teatro**: en vez de pensar en
"código que llama endpoints", pensamos en **actores que realizan tareas**.

| Concepto | Pregunta que responde | Ejemplo en este proyecto |
|---|---|---|
| **Actor** | ¿Quién? | Ana, Carlos, Laura |
| **Habilidad** (Ability) | ¿Qué puede hacer? | `CallAnApi` → puede llamar a una API |
| **Tarea** (Task) | ¿Qué hace? (negocio) | `CalificarPizza`, `IniciarSesion` |
| **Interacción** (Interaction) | ¿Cómo lo hace? (técnico) | `Get`, `Post`, `Put`, `RecordarDato` |
| **Pregunta** (Question) | ¿Qué observa? | `CodigoDeRespuesta`, `ValorDelCampo` |

```java
laura.attemptsTo( CalificarPizza.con(Calificacion.deLaPizza(1, 5)) );
laura.should( seeThat(CodigoDeRespuesta.obtenido(), equalTo(201)) );
```
> "Laura intenta calificar la pizza 1 con 5 estrellas. Laura debería ver que el código de respuesta es 201."

---

## 4. Estructura de carpetas

```
demo-screenplay-quickpizza
├── pom.xml                         ← librerías y configuración de Maven
├── serenity.properties             ← configuración del reporte
└── src/test
    ├── java/co/com/demo/quickpizza
    │   ├── runners/                ← el "botón de play" (lanza Cucumber)
    │   │   └── CucumberTestSuite.java
    │   ├── stepdefinitions/        ← conecta las frases del .feature con Java
    │   │   ├── Hooks.java                        (prepara el escenario antes de cada prueba)
    │   │   ├── ComunesStepDefinitions.java       (pasos compartidos)
    │   │   ├── UsuariosStepDefinitions.java
    │   │   ├── PizzasStepDefinitions.java
    │   │   └── CalificacionesStepDefinitions.java
    │   ├── tasks/                  ← lo que el actor HACE (negocio)
    │   │   ├── RegistrarUsuario.java              POST
    │   │   ├── IniciarSesion.java                 POST + guarda token
    │   │   ├── PedirRecomendacionDePizza.java     POST con token público
    │   │   ├── CalificarPizza.java                POST autenticado
    │   │   ├── ConsultarMisCalificaciones.java    GET autenticado
    │   │   ├── ConsultarCalificacion.java         GET con parámetro {id}
    │   │   └── ActualizarCalificacion.java        PUT
    │   ├── interactions/           ← acciones técnicas pequeñas y reutilizables
    │   │   └── RecordarDato.java
    │   ├── questions/              ← lo que el actor OBSERVA para validar
    │   │   ├── CodigoDeRespuesta.java
    │   │   ├── CantidadDeCalificaciones.java
    │   │   └── ValorDelCampo.java
    │   ├── models/                 ← estructura de los JSON que se envían
    │   │   ├── Usuario.java
    │   │   ├── Calificacion.java
    │   │   └── PreferenciasDePizza.java
    │   └── utils/                  ← constantes y utilidades
    │       ├── Constantes.java
    │       └── GeneradorDeDatos.java
    └── resources
        ├── junit-platform.properties
        └── features/               ← escenarios en lenguaje natural (Gherkin)
            ├── registro_e_inicio_de_sesion.feature   (POST)
            ├── recomendar_pizza.feature              (POST)
            └── calificar_pizzas.feature              (POST + GET + PUT)
```

> 💡 Las **habilidades** (abilities) suelen tener su propia carpeta. Aquí no la
> necesitamos porque usamos `CallAnApi`, que ya viene incluida en Serenity.

---

## 5. Flujo de una prueba

```
.feature  →  StepDefinition  →  Task  →  Interaction (Rest Assured)  →  API
                                                                         │
.feature  ←  StepDefinition  ←  Question  ←───────── respuesta ──────────┘
```

---

## 6. Protocolos HTTP que se demuestran

| Método | ¿Qué hace? | Código de éxito | Escenario |
|---|---|---|---|
| **GET** | Consulta información | 200 | Consultar mis calificaciones / una calificación |
| **POST** | Crea algo nuevo | 201 / 200 | Registrar usuario, iniciar sesión, pedir pizza, calificar |
| **PUT** | Actualiza algo existente | 200 | Cambiar las estrellas de una calificación |

También hay una **prueba negativa**: iniciar sesión con contraseña incorrecta (401).

---

## 7. Endpoints usados

| Método | Endpoint | Autorización |
|---|---|---|
| POST | `/api/users` | Ninguna |
| POST | `/api/users/token/login` | Ninguna (devuelve el token) |
| POST | `/api/pizza` | `Authorization: Token abcdef0123456789` (token público) |
| POST | `/api/ratings` | `Authorization: Bearer <token del usuario>` |
| GET | `/api/ratings` | `Authorization: Bearer <token del usuario>` |
| GET | `/api/ratings/{id}` | `Authorization: Bearer <token del usuario>` |
| PUT | `/api/ratings/{id}` | `Authorization: Bearer <token del usuario>` |

---

### 🍪 Lección aprendida: las cookies del balanceador

QuickPizza tiene varios servidores detrás de un balanceador de carga de AWS.
En la primera respuesta llega la cookie `AWSALB`, que indica a qué servidor
te enviaron. Si no la devuelves, el registro puede quedar en un servidor y el
login llegar a otro que no conoce al usuario → **401 "authentication failed"**.

Los navegadores y k6 devuelven las cookies solos; **Rest Assured no**. Por eso
cada tarea agrega `.filter(CookiesDelActor.de(actor))` (ver `utils/CookiesDelActor.java`).
Para descubrirlo usamos `.log().all()` y `ResponseLoggingFilter`, que imprimen
la petición y la respuesta en consola (están en `RegistrarUsuario` e `IniciarSesion`).

---

## 8. Ejercicios propuestos 🏋️

1. **DELETE**: crea la tarea `EliminarCalificacion` con `Delete.from(...)` y valida el código `204`.
2. Agrega un escenario que intente calificar una pizza **sin token** y observa qué código devuelve.
3. Crea una pregunta `IngredientesDeLaPizza` (campo `pizza.ingredients`) y valida que tenga al menos 2.
4. Convierte "Calificar una pizza" en un **Esquema del escenario** con 1, 3 y 5 estrellas.
5. Agrega un escenario que intente registrar **dos veces** el mismo usuario y observa la respuesta.

> ⚠️ QuickPizza es una API pública y compartida: si en algún momento falla,
> puede ser intermitencia del servicio y no un error de tu código.
