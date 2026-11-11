# 🎭 Demo de automatización web — Screenplay + Playwright + TypeScript

Proyecto de ejemplo para aprender a automatizar pruebas **web** usando el
**patrón Screenplay** con **Playwright**, **TypeScript** y escenarios en
español con **Gherkin** (playwright-bdd).

Sitio bajo prueba: <https://www.saucedemo.com> (tienda de práctica "Swag Labs").

---

## 1. Requisitos

| Herramienta | Versión | ¿Para qué? |
|---|---|---|
| Node.js | 18 o superior (recomendado 20+) | Ejecuta JavaScript/TypeScript fuera del navegador |
| npm | Viene con Node.js | Descarga librerías y ejecuta comandos |
| VS Code (recomendado) | Cualquiera | Editor. Instala las extensiones **Playwright Test for VSCode** y **Cucumber (Gherkin)** |

Verifica en una terminal:
```bash
node -v
npm -v
```

## 2. Instalación (solo la primera vez)

```bash
npm install                    # descarga las librerías del package.json
npx playwright install         # descarga los navegadores (Chromium, Firefox, WebKit)
```

## 3. ¿Cómo ejecutar?

```bash
npm test                 # ejecuta TODOS los escenarios (sin ver el navegador)
npm run test:headed      # igual, pero VIENDO el navegador
npm run test:ui          # abre el modo interactivo de Playwright (¡ideal para aprender!)

npm run test:login       # solo los escenarios con el tag @login
npm run test:carrito     # solo @carrito
npm run test:compra      # solo @compra
npm run test:ordenar     # solo @ordenar

npm run reporte          # abre el reporte HTML de la última ejecución
```

> 💡 `npm test` hace dos cosas: `bddgen` convierte los `.feature` en pruebas
> de Playwright (carpeta `.features-gen`) y luego `playwright test` las ejecuta.

---

## 4. ¿Qué es el patrón Screenplay?

Screenplay usa la metáfora de una **obra de teatro**: en vez de pensar en
"código que hace clic en botones", pensamos en **actores que realizan tareas**.

| Concepto | Pregunta que responde | Ejemplo en este proyecto |
|---|---|---|
| **Actor** | ¿Quién? | Ana, Carlos, Sofía, Laura |
| **Habilidad** (Ability) | ¿Qué puede hacer? | `NavegarLaWeb` → puede usar un navegador |
| **Tarea** (Task) | ¿Qué hace? (negocio) | `IniciarSesion`, `AgregarAlCarrito`, `RealizarPago` |
| **Interacción** (Interaction) | ¿Cómo lo hace? (técnico) | `Abrir`, `Escribir`, `HacerClic`, `Seleccionar` |
| **Pregunta** (Question) | ¿Qué observa? | `Texto`, `ListaDeTextos`, `CantidadEnElCarrito` |
| **Objetivo** (Target) | ¿Sobre qué elemento? | `LoginUI.BOTON_INGRESAR` |

El código se lee casi como una frase:

```ts
await sofia.intentaHacer(
  IniciarSesion.como(USUARIOS.ESTANDAR),
  AgregarAlCarrito.losProductos('Sauce Labs Fleece Jacket'),
  RealizarPago.con({ nombre: 'Sofía', apellido: 'Ramírez', codigoPostal: '110111' }),
);
```

> En Java, Serenity BDD trae Screenplay listo. Aquí, en la carpeta
> `src/screenplay`, construimos una versión pequeña (unas 150 líneas) para
> entender cómo funciona por dentro. Si más adelante quieres una versión
> completa para TypeScript, existe la librería **Serenity/JS**.

---

## 5. Estructura de carpetas

```
demo-screenplay-playwright
├── package.json              ← librerías y comandos (npm test, npm run ...)
├── tsconfig.json             ← configuración de TypeScript
├── playwright.config.ts      ← configuración de Playwright (URL, navegador, reportes)
├── features/                 ← escenarios en lenguaje natural (Gherkin)
│   ├── login.feature
│   ├── carrito.feature
│   ├── compra.feature
│   └── ordenar_productos.feature
└── src
    ├── screenplay/           ← el "motor" del patrón (Actor, Actividad, Pregunta, Objetivo...)
    ├── abilities/            ← habilidades del actor
    │   └── NavegarLaWeb.ts
    ├── ui/                   ← elementos de cada página y cómo encontrarlos (selectores)
    │   ├── LoginUI.ts  InventarioUI.ts  EncabezadoUI.ts  CarritoUI.ts  PagoUI.ts
    ├── interactions/         ← acciones técnicas pequeñas
    │   ├── Abrir.ts  Escribir.ts  HacerClic.ts  Seleccionar.ts
    ├── tasks/                ← lo que el actor HACE (negocio)
    │   ├── IniciarSesion.ts  AgregarAlCarrito.ts  QuitarDelCarrito.ts
    │   ├── IrAlCarrito.ts  RealizarPago.ts  OrdenarProductos.ts
    ├── questions/            ← lo que el actor OBSERVA para validar
    │   ├── Texto.ts  ListaDeTextos.ts  CantidadEnElCarrito.ts
    ├── models/               ← forma de los datos (Usuario, DatosDeEnvio)
    ├── utils/                ← constantes (usuarios, textos esperados)
    └── steps/                ← conecta las frases del .feature con el código
        ├── fixtures.ts  comunes.steps.ts  login.steps.ts
        ├── carrito.steps.ts  compra.steps.ts  ordenar.steps.ts
```

---

## 6. Flujo de una prueba

```
.feature → step → Actor → Tarea → Interacción → Playwright → Navegador
                                                                │
.feature ← step ← expect ← Pregunta ←──── lee la página ────────┘
```

1. `bddgen` lee el `.feature` y genera las pruebas.
2. Cada frase busca su **step** en `src/steps`.
3. El step le pide al **actor** que realice una **tarea**.
4. La tarea usa **interacciones** (`Escribir`, `HacerClic`) que, con la habilidad
   `NavegarLaWeb`, le dan órdenes a **Playwright**.
5. En el *Entonces*, una **pregunta** lee la página y `expect` compara con lo esperado.

---

## 7. Escenarios incluidos (11 en total)

| Feature | Escenarios |
|---|---|
| `login.feature` | Login exitoso · Usuario bloqueado · Contraseña incorrecta · Usuario vacío |
| `carrito.feature` | Agregar 1 producto · Agregar varios (tabla de datos) · Quitar producto |
| `compra.feature` | Compra exitosa de punta a punta · Pago sin nombre (error) |
| `ordenar_productos.feature` | Ordenar A-Z, Z-A, precio menor y mayor (esquema) |

---

## 8. Ejercicios propuestos 🏋️

1. Crea la tarea `CerrarSesion` (menú hamburguesa → "Logout") y un escenario que la use.
2. Crea la pregunta `PrecioDelProducto` y valida que "Sauce Labs Backpack" cueste `$29.99`.
3. Agrega al esquema de login el usuario `problem_user` y observa qué pasa con las imágenes.
4. Crea un escenario de compra con **dos** productos y valida el total del resumen.
5. Agrega el escenario "Pago sin código postal" (`Error: Postal Code is required`).

> ⚠️ saucedemo es un sitio público de práctica. Si una prueba falla sin razón
> aparente, revisa primero si el sitio está disponible.
