# Progreso del proyecto

Última actualización: **2026-09-23**

Este documento resume qué está hecho, qué decisiones se tomaron y qué falta.
El plan paso a paso vive en [`AGENTS.md`](../AGENTS.md).

## Estado por pasos

| # | Paso | Estado |
|---|------|--------|
| 1 | Setup proyecto (Gradle, versiones, módulos) | ✅ hecho |
| 2 | Tema y navegación base | ✅ hecho |
| 3 | Modelo de dominio + Room | ✅ hecho |
| 4 | Photo Picker → EditorScreen | ✅ hecho |
| 5 | Rotar / eliminar fotos | ✅ hecho |
| 6 | PdfGenerator + tests unitarios | ✅ hecho |
| 7 | ConfigScreen | ✅ hecho |
| 8 | GeneratingScreen con progreso | ✅ hecho |
| 9 | Guardado en MediaStore + ResultScreen | ✅ hecho |
| 10 | Compartir | ✅ hecho |
| 11 | Historial (Room) | ✅ hecho |
| 12 | CameraX | ✅ hecho |
| 13 | Drag & drop | ✅ hecho |
| 14 | Pulido UI, i18n, accesibilidad | ✅ hecho |
| 15 | Release build | ✅ hecho |

## Qué hay implementado

### Build / proyecto
- Gradle 8.13 + **AGP 8.11.1** + Kotlin 2.0.20, `compileSdk`/`targetSdk` **36**, `minSdk` 24
- Catálogo de versiones en `gradle/libs.versions.toml`; wrapper oficial incluido (`./gradlew`)
- KSP + **Hilt** (DI) y **Room** (persistencia)
- CI en `.github/workflows/android-ci.yml` (setup Android SDK + caché + build + tests)

### UI
- `PdfotoRoot` (tema + `NavHost`) y grafo en `ui/navigation` con rutas `home`, `editor`,
  `config`, `generating`, `result`, `history`, `camera`
- `HomeScreen` funcional; el resto de pantallas son placeholders hasta su paso
- `EditorScreen`: Photo Picker (máx. 50), lista con miniaturas (Coil), **rotar**,
  **eliminar** y **reordenar con drag & drop** (`sh.calvin.reorderable`), estado vacío,
  FAB "Añadir más fotos" y botón "Continuar"
- `ConfigScreen`: nombre de archivo, chips de tamaño de página, orientación, márgenes y
  calidad; botón "Generar PDF" (paso 7)
- `GeneratingScreen`: `CircularProgressIndicator`, "Procesando imagen X de Y", cancelar y
  reintentar (paso 8)
- `ConfigChipRow`: componente reutilizable de chips con título
- Tema Material 3 (colores, formas, modo claro/oscuro + color dinámico)

### Generación y estado compartido (paso 8)
- `data/session/CreationSession`: **singleton de Hilt** (Kotlin puro) con las fotos y la
  configuración, compartido por Editor, Config y Generating sin pasar listas por rutas.
- `data/pdf/PdfGeneratorService` (interfaz) + `PdfGenerator` (implementación) y
  `PdfOutputFileProvider` (escribe en `cacheDir` mientras dure el flujo).
- `GenerationViewModel` orquesta la generación con progreso y cancelación.
- `HomeViewModel.startNewCreation()` limpia la sesión al empezar un PDF nuevo.

### Almacenamiento y resultado (paso 9)
- `data/storage`: `PdfStorage` (interfaz) + `AndroidPdfStorage`. En **Android 10+ (API 29)**
  guarda en la carpeta pública **Downloads** vía MediaStore; en versiones anteriores usa el
  directorio externo de la app expuesto por **FileProvider** (sin permiso de almacenamiento).
- `ResultScreen`: icono de éxito, nombre, "N páginas · tamaño" y botones **Abrir**
  (`ACTION_VIEW`) y **Compartir** (`ACTION_SEND` + chooser). `ui/util/PdfIntents`
  (`openPdf`, `sharePdf`).
- Al terminar la generación, el `PdfJob` se guarda en la sesión (`lastResult`).

### Historial (paso 11)
- Cada PDF generado se persiste en Room (`PdfHistoryRepository.add`).
- `HistoryScreen`: lista con nombre, **fecha, páginas y tamaño**; **swipe para eliminar**;
  toque para abrir y botón para compartir. `HistoryViewModel` observa el repositorio.

### Release (paso 15)
- Firma leída de `keystore.properties` (no versionado; partir de
  `keystore.properties.example`). Sin ese archivo, el release se compila sin firmar.
- `./gradlew bundleRelease` genera `app-release.aab` (≈4.7 MB) con R8/minificado y
  reducción de recursos. Firma verificada con `jarsigner`.

### Pulido, i18n y accesibilidad (paso 14)
- Traducción completa al **inglés** (`values-en/strings.xml`); el español es el idioma base.
- Accesibilidad: `contentDescription` en acciones e iconos, objetivos táctiles de 48dp
  (componentes Material 3) y tema claro/oscuro. `lint` no reporta avisos de accesibilidad
  ni de traducciones faltantes.

### Cámara (paso 12)
- `CameraScreen` con **CameraX**: vista previa y captura a `cacheDir`.
- El permiso `CAMERA` se pide **solo al abrir la cámara** (nunca al iniciar la app).
- La foto capturada se añade a la sesión y se navega al editor.
- `AndroidManifest`: `CAMERA` + `uses-feature camera.any (required=false)`.

> **Ruling:** en Android < 10 el PDF no va a la carpeta pública Downloads sino al
> directorio externo de la app (vía FileProvider), para no pedir `WRITE_EXTERNAL_STORAGE`.
> Coste si es mala decisión: en API 24-28 el archivo no aparece en Downloads público.

### Dominio y datos
- `domain/model`: `Photo`, `PdfConfig`, `PdfJob` y enums `PageSize`, `Orientation`,
  `MarginSize`, `Quality`
- `domain/repository/PdfHistoryRepository`
- `data/local`: `PdfHistoryEntity`, `PdfHistoryDao`, `PdfotoDatabase`, mapper
- `data/repository/PdfHistoryRepositoryImpl`
- `di/`: `DatabaseModule`, `RepositoryModule`

### Generación de PDF (paso 6)
- `domain/pdf`: `PdfConfigResolver` (tamaños A4/Carta/AUTO y orientación),
  `FileNameSanitizer`, `calculateInSampleSize`, `PageDimensions`
- `data/pdf`: `PdfGenerator` (con `PdfDocument` nativo), `decodeSampledBitmap` y
  `drawBitmapFitted`. Aún **no cableado** a la UI: se usa en los pasos 8-9.

## Decisiones tomadas

- **Nombre**: PDFoto · **applicationId**: `com.pdffoto` (permanente al publicar)
- **API 36**: Google Play la exige para apps nuevas desde 2026-08-31 → obligó a subir
  AGP a 8.11+ (el 8.6.1 de los docs iniciales no soporta API 36) y Gradle a 8.13
- **Dominio puro**: los URI se modelan como `String`, no `android.net.Uri`, para poder
  testar el dominio en la JVM (ver `docs/03`). No afecta a Play.
- **Hilt**: `hilt { enableAggregatingTask = false }` en `app/build.gradle.kts` como
  workaround del conflicto JavaPoet/KotlinPoet (google/dagger#4976); válido en un
  módulo único
- **Iconos**: solo el set *core* de Material Icons (no `material-icons-extended`) para
  no engordar el APK
- **Sin publicidad**: prohibición absoluta de ads/analytics/tracking (regla en `AGENTS.md`)
- **Tests**: JUnit 4 + Truth; lógica sensible extraída a funciones puras
  (`buildPhotos`, `rotatePhoto`, `removePhoto`, mappers)

## Verificación

Última corrida local (JVM unit tests, sin emulador):

| Check | Resultado |
|-------|-----------|
| `./gradlew assembleDebug` | ✅ APK debug |
| `./gradlew testDebugUnitTest` | ✅ 42/42 tests |
| `./gradlew lintDebug` | ✅ 0 errores (28 avisos, todos "hay versión más nueva") |
| `./gradlew bundleRelease` | ✅ `app-release.aab` ≈ 4.7 MB (firma verificada) |

### Cómo reproducirlo en local

Con **Android Studio** (Ladybug o superior, JDK 17) basta con abrir el proyecto.
Por CLI:

```bash
export JAVA_HOME=/ruta/a/jdk-17
export ANDROID_HOME=/ruta/a/android-sdk   # necesita platforms;android-36
./gradlew assembleDebug testDebugUnitTest lintDebug
```

## Revisión de código (2026-09-23)

Revisor independiente sobre el commit inicial. Fix pass aplicado:

- ✅ **I1 (Important)**: la cámara quedaba **enlazada al salir** de `CameraScreen`.
  `CameraPreview` ahora la desvincula con `DisposableEffect`.
- ✅ **I2 (Important)**: la opción **"Calidad" no tenía efecto**. `PdfGenerator` ahora
  recomprime el bitmap con `quality.jpeg` (`data/pdf/JpegConverter.kt`).

Rulings (comportamiento sin cambios):

- **C1 (Critical, rechazado)**: se afirmaba que `file://` no funciona con
  `ContentResolver.openInputStream`. Verificado en AOSP: `openInputStream` trata
  `SCHEME_FILE` con `new FileInputStream(uri.getPath())`. La cámara funciona; sin cambios.
- **I3 (rechazado, sigue el spec)**: en `PageSize.AUTO` la página usa las dimensiones del
  bitmap, tal como define `docs/06` y fija su test.
- **I4 (degradado a Minor)**: `MarginSize.dp` se usa como puntos del PDF; el spec lo
  define como `dp`.

Minors diferidos (no arreglados en esta pasada):

- M1 `HistoryViewModel` usa `SharingStarted.Eagerly` (mejor `WhileSubscribed`).
- M2 una foto que no decodifica se omite en silencio en `PdfGenerator`.
- M3 cancelar no borra el PDF parcial en `cacheDir`.
- M4 Room `exportSchema = false` (útil para migraciones futuras).
- M5 `allowBackup="true"` sin `dataExtractionRules`.
- M6 `suspendCancellableCoroutine` sin `invokeOnCancellation` (captura/cámara).
- M7 el flujo de cámara no limpia la sesión (solo "Crear PDF" llama a `clear()`).
- M8 `FileNameSanitizer` no cubre puntos iniciales/finales ni nombres reservados.
- M9 el progreso se emite antes de decodificar (barra ligeramente adelantada).
- M10 eliminar del historial no borra el archivo del PDF.
- M11 `formatTimestamp` en hora local vs `defaultPdfFileName` en UTC.
- M12 sin tests (requieren instrumentados) para decoder/storage/generador/dibujo.

## Pendiente para publicar en Google Play

1. Terminar la app (pasos 6-15).
2. **Política de privacidad** — obligatoria para todas las apps; enlace en Play Console
   **y** dentro de la app. Sin dominio se puede alojar gratis en GitHub Pages.
3. **Data safety** — declarar "no recopila datos" (la cámara se procesa en el dispositivo,
   Photo Picker no pide permisos).
4. **Firma de release** con Play App Signing (`keystore.properties` partir de
   `keystore.properties.example`).
5. Recursos de ficha: icono 512×512, gráfico 1024×500, capturas, descripción.
6. Clasificación de contenido y público objetivo.
7. **Registro del package name** (verificación de desarrollador; plazo 2026-09-30).
   Las apps nuevas de Play se auto-registran al crearlas en Play Console.

## Notas de entorno

- La verificación de arriba se hizo con un JDK 17 y Android SDK 36 **portables** en
  `/tmp/opencode` (efímeros). `local.properties` apunta ahí; Android Studio lo
  regenera al abrir el proyecto. Está en `.gitignore`.
- El repositorio **todavía no está inicializado con git** (`git init` pendiente).
- Los documentos `docs/` numerados son la especificación; `docs/progreso.md` (este
  archivo) es el estado de avance.
