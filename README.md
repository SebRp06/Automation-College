# 🎭 Automation College

Repositorio de aprendizaje de **automatización de pruebas con el patrón Screenplay**, pensado para personas que están empezando.

Tiene dos proyectos de demostración, uno de **Backend (APIs)** y otro de **Frontend (web)**. Los dos siguen el mismo patrón, así que puedes comparar cómo se aplica Screenplay en dos lenguajes y herramientas distintas.

![Java](https://img.shields.io/badge/Java-17-orange?logo=openjdk)
![Maven](https://img.shields.io/badge/Maven-3.8+-C71A36?logo=apachemaven)
![Serenity BDD](https://img.shields.io/badge/Serenity_BDD-4-2E7D32)
![Rest Assured](https://img.shields.io/badge/Rest_Assured-API-5A9E3C)
![TypeScript](https://img.shields.io/badge/TypeScript-5-3178C6?logo=typescript)
![Playwright](https://img.shields.io/badge/Playwright-1.60-2EAD33?logo=playwright)
![Cucumber](https://img.shields.io/badge/Gherkin-Español-23D96C?logo=cucumber)

---

## 📁 Estructura del repositorio

```
Automation-College
├── Backend/
│   └── demo-screenplay-quickpizza/     → Pruebas de API: Java + Maven + Serenity + Rest Assured
└── Frontend/
    └── demo-screenplay-playwright/     → Pruebas web: TypeScript + Playwright + playwright-bdd
```

| Proyecto | Qué prueba | Tecnologías | Escenarios |
|---|---|---|---|
| [**Backend**](Backend/demo-screenplay-quickpizza) | API REST de [QuickPizza](https://quickpizza.grafana.com) (Grafana k6) | Java 17, Maven, Serenity BDD, Rest Assured, Cucumber, JUnit 5 | 8 |
| [**Frontend**](Frontend/demo-screenplay-playwright) | Tienda web [Swag Labs](https://www.saucedemo.com) | TypeScript, Node.js, Playwright, playwright-bdd | 11 |

---

## 🎭 ¿Qué es el patrón Screenplay?

Screenplay organiza las pruebas con la metáfora de una **obra de teatro**: en lugar de escribir "código que hace clics o llama endpoints", describimos **actores que realizan tareas**.

| Elemento | Pregunta | Backend (Java) | Frontend (TypeScript) |
|---|---|---|---|
| **Actor** | ¿Quién? | Ana, Carlos, Laura | Ana, Carlos, Sofía, Laura |
| **Habilidad** | ¿Qué puede hacer? | `CallAnApi` | `NavegarLaWeb` |
| **Tarea** | ¿Qué hace? (negocio) | `CalificarPizza`, `IniciarSesion` | `IniciarSesion`, `RealizarPago` |
| **Interacción** | ¿Cómo lo hace? (técnico) | `Get`, `Post`, `Put` | `Abrir`, `Escribir`, `HacerClic` |
| **Pregunta** | ¿Qué observa? | `CodigoDeRespuesta`, `ValorDelCampo` | `Texto`, `CantidadEnElCarrito` |

El resultado es un código que se lee casi como una frase:

```java
// Backend (Java)
laura.attemptsTo(CalificarPizza.con(Calificacion.deLaPizza(1, 5)));
laura.should(seeThat(CodigoDeRespuesta.obtenido(), equalTo(201)));
```

```ts
// Frontend (TypeScript)
await sofia.intentaHacer(
  IniciarSesion.como(USUARIOS.ESTANDAR),
  AgregarAlCarrito.losProductos('Sauce Labs Fleece Jacket'),
  RealizarPago.con({ nombre: 'Sofía', apellido: 'Ramírez', codigoPostal: '110111' }),
);
```

Los escenarios están escritos en **español con Gherkin** (`Dado`, `Cuando`, `Entonces`), así que cualquier persona del equipo los puede leer.

---

## 🔙 Backend: pruebas de API

📂 [`Backend/demo-screenplay-quickpizza`](Backend/demo-screenplay-quickpizza)

**Escenarios automatizados**

| Feature | Escenarios | Métodos HTTP |
|---|---|---|
| Registro e inicio de sesión | Registro exitoso · Login con token · Contraseña incorrecta (401) | POST |
| Recomendar pizza | Esquema con distintas calorías e ingredientes · Pizza vegetariana | POST |
| Calificar pizzas | Calificar · Consultar mis calificaciones · Actualizar y verificar con un GET | POST · GET · PUT |

**Ejecución rápida**

```bash
cd Backend/demo-screenplay-quickpizza
mvn clean verify                                   # todos los escenarios
mvn clean verify -Dcucumber.filter.tags="@put"     # solo un tag
```

📊 Reporte: `target/site/serenity/index.html`

**Requisitos:** Java 17+ y Maven 3.8+.

---

## 🖥️ Frontend: pruebas web

📂 [`Frontend/demo-screenplay-playwright`](Frontend/demo-screenplay-playwright)

**Escenarios automatizados**

| Feature | Escenarios |
|---|---|
| Login | Exitoso · Usuario bloqueado · Contraseña incorrecta · Usuario vacío |
| Carrito | Agregar un producto · Agregar varios (tabla de datos) · Quitar un producto |
| Compra | Compra completa de punta a punta · Pago sin nombre (error) |
| Ordenar productos | A-Z · Z-A · Precio menor · Precio mayor |

**Ejecución rápida**

```bash
cd Frontend/demo-screenplay-playwright
npm install                 # solo la primera vez
npx playwright install      # solo la primera vez
npm test                    # todos los escenarios
npm run test:ui             # modo visual interactivo (ideal para aprender)
npm run reporte             # abre el reporte con capturas, videos y traces
```

📊 Reporte: `playwright-report/index.html`

**Requisitos:** Node.js 20+.

> En TypeScript no existe un "Serenity" listo para usar como en Java, así que en `src/screenplay` construimos un núcleo pequeño (Actor, Tarea, Pregunta, Objetivo, Elenco). Así se puede ver cómo funciona el patrón por dentro.

---

## 🧠 Lecciones aprendidas en el camino

Problemas reales que aparecieron al montar los proyectos. Son tan útiles como el código:

| Problema | Causa | Solución |
|---|---|---|
| La API de cocodrilos de k6 devolvía HTML o 403 | Grafana retiró `test-api.k6.io` y la redirige a QuickPizza | Migrar a QuickPizza. Tener las URLs centralizadas en `Constantes` facilitó el cambio |
| `NoClassDefFoundError: ParentRunner` | El reporter de Serenity todavía usa clases de JUnit 4 | Agregar `junit:junit` 4.13.2 al `pom.xml` |
| Login con **401** justo después de registrarse | QuickPizza tiene varios servidores detrás de un balanceador de AWS; sin la cookie `AWSALB`, cada petición puede llegar a un servidor distinto | Darle a cada actor un `CookieFilter` (`CookiesDelActor`) |
| JSON con nombres de campo incorrectos (posible causa) | Serenity trae Jackson y Gson; Gson ignora `@JsonProperty` | Forzar `ObjectMapperType.JACKSON_2` |
| `registerESMLoader` en Playwright | `^` en el `package.json` instaló una versión de Playwright incompatible con playwright-bdd | Fijar versiones exactas (`1.60.0` y `8.5.1`) |
| `ERR_NAME_NOT_RESOLVED` | Falla del DNS de la red local | `ipconfig /flushdns` o usar DNS 8.8.8.8 |

> 💡 Para depurar una prueba de API que falla, usa `.log().all()` y `ResponseLoggingFilter`: así ves en consola exactamente qué se envió y qué respondió el servidor.

---

## 🏋️ Para practicar

Cada proyecto tiene en su README una lista de **ejercicios propuestos**:

- **Backend:** implementar DELETE, probar sin token, validar ingredientes, crear esquemas de escenario.
- **Frontend:** cerrar sesión, validar precios, pago sin código postal, compra con varios productos.

**Orden sugerido para leer el código:**
`.feature` → `stepdefinitions/steps` → `tasks` → `interactions` → `questions`

---

## 📚 Recursos

- [Serenity BDD: Screenplay](https://serenity-bdd.github.io/docs/screenplay/screenplay_fundamentals)
- [Rest Assured](https://rest-assured.io)
- [Playwright](https://playwright.dev)
- [playwright-bdd](https://github.com/vitalets/playwright-bdd)
- [Gherkin: referencia de palabras clave](https://cucumber.io/docs/gherkin/reference)
- [QuickPizza (Grafana k6)](https://quickpizza.grafana.com)
- [Swag Labs (saucedemo)](https://www.saucedemo.com)

---

<p align="center">Hecho para aprender automatización paso a paso 🚀</p>
