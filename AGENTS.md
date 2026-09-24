# AGENTS.md — Instrucciones para el agente

## Contexto

Estás construyendo **PDFoto**, una app Android nativa en **Kotlin + Jetpack Compose**
que convierte fotos (de galería o cámara) en un PDF configurable.

La app es **100 % libre de publicidad**: nunca se añaden anuncios, analytics ni SDKs
de tracking (ver [Restricciones](#restricciones)).

Antes de escribir código, lee los documentos de [`docs/`](./docs) en orden numérico:

| Doc | Contenido |
|-----|-----------|
| [00-overview](./docs/00-overview.md) | Objetivo, alcance MVP, casos de uso |
| [01-requisitos](./docs/01-requisitos.md) | Requisitos funcionales y no funcionales |
| [02-arquitectura](./docs/02-arquitectura.md) | MVVM + Clean Architecture |
| [03-modelo-de-datos](./docs/03-modelo-de-datos.md) | Entidades de dominio |
| [04-ui-screens](./docs/04-ui-screens.md) | Pantallas y navegación |
| [05-camera-y-galeria](./docs/05-camera-y-galeria.md) | Photo Picker y CameraX |
| [06-generacion-pdf](./docs/06-generacion-pdf.md) | `PdfDocument`, tamaños y pipeline |
| [07-almacenamiento](./docs/07-almacenamiento.md) | MediaStore y compartir |
| [08-permisos](./docs/08-permisos.md) | Permisos |
| [09-testing](./docs/09-testing.md) | Estrategia de tests |
| [10-build-y-release](./docs/10-build-y-release.md) | Gradle, versiones y release |
| [11-publicacion-play](./docs/11-publicacion-play.md) | Checklist de publicación en Google Play |
| [12-textos-ficha-play](./docs/12-textos-ficha-play.md) | Textos de la ficha (es/en) |
| [13-feedback-y-soporte](./docs/13-feedback-y-soporte.md) | Errores y comentarios de usuarios |

Estado actual del desarrollo: [`docs/progreso.md`](./docs/progreso.md).

## Orden de implementación

1. Setup proyecto (Gradle, versiones, módulos)
2. Tema y navegación base
3. Modelo de dominio + Room
4. Photo Picker → EditorScreen (sin drag aún)
5. Rotar/eliminar fotos
6. PdfGenerator + tests unitarios
7. ConfigScreen
8. GeneratingScreen con progreso
9. Guardado en MediaStore + ResultScreen
10. Compartir
11. Historial (Room)
12. CameraX
13. Drag & drop
14. Pulido UI, i18n, accesibilidad
15. Release build

## Reglas de trabajo

### Al escribir código

- Kotlin idiomático, sin `!!` salvo justificación
- Composables pequeños y sin lógica de negocio
- ViewModels exponen `StateFlow<UiState>` inmutable
- Casos de uso `suspend` que devuelven `Result<T>`
- Nada de lógica en `Application` o `Activity`
- KDoc en clases públicas
- No agregar dependencias sin justificar en un commit/PR aparte

### Al crear archivos

- Un archivo = una clase pública principal
- Tests junto al código fuente que prueban (mismo package)
- Recursos en `res/values/strings.xml` (nunca strings hardcodeados)

### Al refactorizar

- Si un Composable supera 150 líneas, dividir
- Si una función supera 40 líneas, extraer
- Eliminar código muerto en el mismo PR

### Convenciones de commits

- **Conventional Commits** con descripción en español: `feat:`, `fix:`, `refactor:`, `test:`, `docs:`, `build:`, `chore:`
- Alcance por módulo o capa: `feat(editor): ...`, `fix(pdf): ...`
- Asunto en imperativo, en minúscula, sin punto final, ≤ 72 caracteres
- Un commit = un cambio lógico; no mezclar refactor con feature
- Cuerpo opcional explicando el *qué* y el *por qué*; referenciar issues con `Refs #123`
- Cambios incompatibles: `BREAKING CHANGE:` en el pie

Ejemplos:

```
feat(pdf): generar PDF en tamaño A4 con margen configurable
fix(permisos): solicitar CAMERA solo al abrir la cámara
test(pdf): cubrir CalculateInSampleSize con bitmaps grandes
build(release): configurar firma y minificado del APK
```

## Restricciones

### Prohibido

- **Añadir publicidad, analytics o SDKs de tracking** (política de producto: la app es 100 % libre de anuncios; no negociable)
- **Añadir marca de agua a los PDF** (el documento generado es 100 % del usuario; no negociable)
- Usar librerías de PDF propietarias (iText comercial)
- Romper `minSdk 24`
- Bloquear el hilo principal con I/O o bitmaps
- Pedir permisos al iniciar la app

### Cuando dudes

- Pregunta antes de añadir una dependencia nueva
- Propón alternativa antes de un refactor grande
- Muestra el plan antes de tocar más de 3 archivos

## Comandos útiles

```bash
./gradlew assembleDebug              # compilar
./gradlew installDebug               # instalar en dispositivo
./gradlew testDebugUnitTest          # tests unitarios
./gradlew connectedDebugAndroidTest  # tests instrumentados
./gradlew lint                       # lint
./gradlew ktlintFormat               # formato (si está configurado)
./gradlew bundleRelease              # bundle de release (ver scripts/build-release.sh)
```

## Estructura del repositorio

```
.
├── AGENTS.md
├── README.md
├── settings.gradle.kts
├── build.gradle.kts
├── gradle.properties
├── keystore.properties.example   # plantilla de firma (copiar a keystore.properties)
├── gradle/
│   ├── libs.versions.toml        # catálogo de versiones
│   └── snippets/                 # fragmentos de configuración Gradle (referencia)
├── snippets/kotlin/              # fragmentos de código Kotlin (referencia; pasos 3-12)
├── docs/                         # especificación — leer en orden numérico
├── scripts/build-release.sh      # build del bundle de release
├── .github/workflows/android-ci.yml
└── app/
    ├── build.gradle.kts
    ├── proguard-rules.pro
    └── src/main/
        ├── AndroidManifest.xml
        ├── java/com/pdffoto/
        │   ├── MainActivity.kt
        │   ├── PdfotoApp.kt      # Application (@HiltAndroidApp)
        │   ├── data/
        │   │   ├── local/        # Room: entity, DAO, base de datos, mapper
        │   │   ├── pdf/          # PdfGenerator y utilidades de bitmap
        │   │   ├── repository/   # implementaciones de repositorios
        │   │   ├── session/      # CreationSession (estado del flujo)
        │   │   └── storage/      # guardado en Downloads (MediaStore/FileProvider)
        │   ├── di/               # módulos de Hilt
        │   ├── domain/
        │   │   ├── model/        # entidades puras (sin Android)
        │   │   ├── pdf/          # páginas, nombres de archivo, sampling
        │   │   ├── photo/        # operaciones puras sobre fotos
        │   │   └── repository/   # interfaces de repositorio
        │   └── ui/
        │       ├── PdfotoRoot.kt # raíz: tema + NavHost
        │       ├── navigation/
        │       ├── screens/
        │       ├── components/
        │       ├── util/         # intents (abrir/compartir PDF)
        │       └── theme/
        └── res/                  # strings, temas, iconos
```

> **Nota:** el proyecto es un esqueleto Android compilable verificado
> (`./gradlew assembleDebug` OK con Gradle 8.13 + AGP 8.11.1, `targetSdk 36`).
> Los fragmentos de código sin `package`/`import` viven en `snippets/kotlin/`
> (fuera del source set) y se integrarán en sus pasos correspondientes.
