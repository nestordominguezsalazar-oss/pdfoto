# Progreso del proyecto

Última actualización: **2026-09-24**

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
- Versión de la app: **1.5.0** (`versionCode 6`) — `app/build.gradle.kts`
- **Icono de app** real a partir de `docs/art/1.png` (adaptativo + mipmaps + `docs/store/icon-512.png`)
- Gradle 8.13 + **AGP 8.11.1** + Kotlin 2.0.20, `compileSdk`/`targetSdk` **36**, `minSdk` 24
- Catálogo de versiones en `gradle/libs.versions.toml`; wrapper oficial incluido (`./gradlew`)
- KSP + **Hilt** (DI) y **Room** (persistencia)
- CI en `.github/workflows/android-ci.yml` (setup Android SDK + caché + build + tests)
- Repositorio **git** inicializado (`main`), con historial de commits

### UI
- `PdfotoRoot` (tema + `NavHost`) y grafo en `ui/navigation` con rutas `home`, `editor`,
  `config`, `generating`, `result`, `history`, `camera`
- `HomeScreen`: distintivos (**Sin publicidad · Sin marca de agua · 100 % offline · Sin
  recopilar datos · Gratis, sin compras**) y accesos "Crear PDF", "Escanear con cámara",
  "Historial" y "Acerca de"
- `AboutScreen`: versión, lista de **ventajas**, **correo de soporte** visible, **licencias de
  código abierto**, privacidad y enviar comentarios
- `EditorScreen`: Photo Picker (máx. 50), lista con miniaturas (Coil), **rotar**,
  **eliminar** y **reordenar con drag & drop** (`sh.calvin.reorderable`); **tocar una foto
  abre una vista previa a pantalla completa**; estado vacío con "Elegir de la galería" /
  "Hacer una foto"; FAB "Añadir más fotos" con menú **galería / cámara** y botón "Continuar"
- `ConfigScreen`: nombre de archivo, chips de tamaño de página, orientación, márgenes y
  calidad; botón "Generar PDF"
- `GeneratingScreen`: `CircularProgressIndicator`, "Procesando imagen X de Y", cancelar y
  reintentar
- `ResultScreen`: icono de éxito, "N páginas · tamaño", **Abrir** y **Compartir**
- `HistoryScreen`: nombre, fecha, páginas y tamaño; swipe para eliminar; abrir y compartir
- `ConfigChipRow`: componente reutilizable de chips con título
- Tema Material 3 (colores, formas, modo claro/oscuro + color dinámico)
- i18n: español (base) e inglés

### Generación, sesión y almacenamiento
- `data/session/CreationSession`: **singleton de Hilt** (Kotlin puro) con las fotos y la
  configuración, compartido por Editor, Config y Generating. `HomeViewModel.startNewCreation()`
  lo reinicia al empezar un PDF nuevo (desde "Crear PDF" **o** "Escanear con cámara").
- `data/pdf`: `PdfGeneratorService` (interfaz) + `PdfGenerator`, `decodeSampledBitmap`,
  `drawBitmapFitted`, `scaledDownTo` y `PdfOutputFileProvider` (escribe en `cacheDir`).
- `GenerationViewModel` orquesta la generación con progreso y cancelación.
- `data/storage`: `PdfStorage` + `AndroidPdfStorage`. En **Android 10+ (API 29)** guarda en
  la carpeta pública **Downloads** vía MediaStore; en versiones anteriores usa el directorio
  externo de la app expuesto por **FileProvider** (sin permiso de almacenamiento).
- `ui/util/PdfIntents`: `openPdf`, `sharePdf`, `openUrl`.

### Dominio y datos
- `domain/model`: `Photo`, `PdfConfig`, `PdfJob` y enums `PageSize`, `Orientation`,
  `MarginSize`, `Quality` (esta última expresada en **dpi**)
- `domain/pdf`: `PdfConfigResolver` (tamaños y resolución objetivo), `FileNameSanitizer`,
  `calculateInSampleSize`, `defaultPdfFileName`, `formatFileSize`, `formatTimestamp`,
  `PageDimensions`
- `domain/photo`: `buildPhotos`, `rotatePhoto`, `removePhoto`, `movePhoto`,
  `exifOrientationToTransform` (orientación EXIF)
- `domain/repository/PdfHistoryRepository`
- `data/local`: `PdfHistoryEntity`, `PdfHistoryDao`, `PdfotoDatabase`, mapper
- `data/repository/PdfHistoryRepositoryImpl`
- `di/`: `DatabaseModule`, `RepositoryModule`, `PdfModule`, `PdfGeneratorModule`,
  `StorageModule`, `LoggingModule`

### Cámara (CameraX)
- `CameraScreen` con vista previa y captura a `cacheDir`; el permiso `CAMERA` se pide **solo
  al abrir la cámara**. La captura se añade a la sesión y se vuelve al editor.
- `AndroidManifest`: `CAMERA` + `uses-feature camera.any (required=false)`.

### Historial (Room)
- Cada PDF generado se persiste (`PdfHistoryRepository.add`); `HistoryViewModel` observa el
  repositorio.

### Soporte y feedback (ver `docs/13`)
- **Log local** (`data/logging`): `AppLogger` + `FileLogger` (bucle de 500 líneas en
  `cacheDir`, sin red). Registra cierres no controlados, errores de generación/guardado y de
  captura.
- **"Enviar comentarios"** en Inicio: abre el correo prerellenado con diagnósticos técnicos
  (versión, Android, dispositivo) y adjunta el log (FileProvider). **Nada se envía salvo que
  el usuario lo mande.**
- Errores automáticos: **Android vitals** de Play (sin SDK ni permisos).

### Release
- Firma leída de `keystore.properties` (no versionado; partir de
  `keystore.properties.example`). Sin ese archivo, el release se compila sin firmar.
- `./gradlew bundleRelease` genera `app-release.aab` (≈4.7 MB) con R8/minificado y reducción
  de recursos. Firma verificada con `jarsigner`.

## Decisiones tomadas

- **Nombre**: PDFoto · **applicationId**: `com.pdfoto` (permanente al publicar)
- **Nomenclatura**: la marca visible es **PDFoto** (una F, fusión de "PDF" + "foto"); todos
  los identificadores técnicos usan la misma grafía en minúscula, `pdfoto` (paquete
  `com.pdfoto`, carpeta/repo, URL de privacidad). Antes convivían `PDFoto` y `pdffoto` (dos F).
- **API 36**: Google Play la exige para apps nuevas desde 2026-08-31 → obligó a subir AGP a
  8.11+ (el 8.6.1 de los docs iniciales no soporta API 36) y Gradle a 8.13
- **Dominio puro**: los URI se modelan como `String`, no `android.net.Uri`, para poder testar
  el dominio en la JVM (ver `docs/03`). No afecta a Play.
- **Hilt**: `hilt { enableAggregatingTask = false }` como workaround del conflicto
  JavaPoet/KotlinPoet (google/dagger#4976); válido en un módulo único
- **Iconos**: solo el set *core* de Material Icons (no `material-icons-extended`)
- **Sin publicidad**: prohibición absoluta de ads/analytics/tracking (regla en `AGENTS.md`)
- **Calidad = DPI**: `Quality` expresa el **dpi objetivo** de las imágenes, no una calidad
  JPEG. Es lo que controla el peso del PDF (ver más abajo).
- **Tests**: JUnit 4 + Truth + `kotlinx-coroutines-test`; lógica sensible extraída a funciones
  puras.

## Verificación

Última corrida local (JVM unit tests, sin emulador):

| Check | Resultado |
|-------|-----------|
| `./gradlew assembleDebug` | ✅ APK debug |
| `./gradlew testDebugUnitTest` | ✅ 59/59 tests |
| `./gradlew lintDebug` | ✅ 0 errores (28 avisos, todos "hay versión más nueva") |
| `./gradlew bundleRelease` | ✅ `app-release.aab` ≈ 4.7 MB (firma verificada) |

Tests instrumentados (`androidTest`) escritos y compilados (`assembleDebugAndroidTest`); para
**ejecutarlos** hace falta un dispositivo o emulador (`connectedDebugAndroidTest`) — ver `docs/09`.

### Cómo reproducirlo en local

Con **Android Studio** (Ladybug o superior, JDK 17) basta con abrir el proyecto. Por CLI:

```bash
export JAVA_HOME=/ruta/a/jdk-17
export ANDROID_HOME=/ruta/a/android-sdk   # necesita platforms;android-36
./gradlew assembleDebug testDebugUnitTest lintDebug
```

## Revisión de código (2026-09-23)

Revisor independiente. Fix pass aplicado:

- ✅ **I1 (Important)**: la cámara quedaba enlazada al salir de `CameraScreen`;
  `CameraPreview` ahora la desvincula con `DisposableEffect`.
- ✅ **I2 (Important)**: la opción "Calidad" no tenía efecto. **Superado** por el cambio a
  DPI del 2026-09-24 (la recomprresión JPEG se descartó; ver más abajo).

Rulings (comportamiento sin cambios):

- **C1 (Critical, rechazado)**: se afirmaba que `file://` no funciona con
  `ContentResolver.openInputStream`. Verificado en AOSP: `openInputStream` trata `SCHEME_FILE`
  con `new FileInputStream(uri.getPath())`. La cámara funciona.
- **I3 (rechazado, sigue el spec)**: en `PageSize.AUTO` la página usa las dimensiones del
  bitmap, tal como define `docs/06`.
- **I4 (Minor)**: `MarginSize.dp` se usa como puntos del PDF; el spec lo define como `dp`.

Minors diferidos (no arreglados):

- M1 `HistoryViewModel` usa `SharingStarted.Eagerly` (mejor `WhileSubscribed`).
- M2 una foto que no decodifica se omite en silencio en `PdfGenerator`.
- M3 cancelar no borra el PDF parcial en `cacheDir`.
- M4 Room `exportSchema = false` (útil para migraciones futuras).
- M5 `allowBackup="true"` sin `dataExtractionRules`.
- M6 `suspendCancellableCoroutine` sin `invokeOnCancellation` (captura/cámara).
- M7 el flujo de cámara no limpia la sesión (ya cubierto: las dos entradas de Inicio limpian).
- M8 `FileNameSanitizer` no cubre puntos iniciales/finales ni nombres reservados.
- M9 el progreso se emite antes de decodificar (barra ligeramente adelantada).
- M10 eliminar del historial no borra el archivo del PDF.
- M11 `formatTimestamp` en hora local vs `defaultPdfFileName` en UTC.
- M12 sin tests (requieren instrumentados) para decoder/storage/generador/dibujo.

## Correcciones tras pruebas manuales

### 2026-09-23
- **La sesión no se reiniciaba al empezar por cámara.** El botón "Escanear con cámara" ahora
  llama a `startNewCreation()`; cada PDF empieza de cero.
- **El editor abría el selector de galería aunque ya hubiera una foto.** `EditorViewModel`
  exponía `stateIn(initialValue = Empty)`; ahora expone `session.photos` directamente.

### 2026-09-24
- **No se podía añadir una foto desde la cámara dentro del editor.** El FAB "Añadir más
  fotos" ahora abre un menú con **galería** o **cámara**, y el estado vacío ofrece ambas.
- **Peso del PDF demasiado alto (≈9 MB por página).** Causa: `PdfDocument` (Skia) incrusta
  las imágenes a la resolución del bitmap y su codificación por defecto es **sin pérdida**;
  la recomprresión JPEG previa no reducía nada. Solución: **la calidad ahora es un DPI
  objetivo** (Baja 96 / Media 150 / Alta 200 dpi) que fija la resolución de decodificación
  (`PdfConfigResolver.targetDecodeDimension`) y se aplica un escalado exacto
  (`Bitmap.scaledDownTo`). Se eliminó `JpegConverter`. *(Validado por el usuario: el tamaño
  ya es correcto.)*
- **No se distinguían los documentos en el editor (miniatura pequeña).** Añadida **vista
  previa a pantalla completa** al tocar una foto (se cierra tocando la imagen o la X).
  No es el RF10 (vista previa del **PDF**), que sigue pendiente.
- **Las fotos salían giradas 90° en el PDF.** Causa: las fotos (cámara y muchas de galería)
  traen una etiqueta **EXIF de orientación** con los píxeles "sin enderezar". Coil (miniatura
  del editor) aplica EXIF, pero el decoder del PDF no. Solución: se lee la orientación EXIF
  (`androidx.exifinterface`) y se endereza el bitmap, combinándola con la rotación del
  usuario. Función pura `exifOrientationToTransform` (`domain/photo`) + tests.

### Revisión de congruencia de la documentación (2026-09-24)
- Conteo de tests actualizado (54 → **59**) y añadidos los que faltaban en `docs/09`
  (`CameraViewModelTest`, `FileLoggerTest`, `FeedbackDiagnosticsTest`).
- "Calidad JPEG" → **calidad en DPI** en `docs/01` (RF9) y en los textos de ficha (`docs/12`).
- "Recortar" quitado de los objetivos de `docs/00` y añadido a *Fuera de alcance (v2+)*.
- Lista de módulos de Hilt completada en `docs/03` (incluye `LoggingModule`).
- Estado de `privacy_policy_url` corregido en `docs/11` (ya apunta a la URL de GitHub Pages).
- `docs/02-arquitectura` desarrollado (capas + enlace al bis) y EXIF añadido a la tabla de
  librerías de `docs/02-arquitectura-convenciones`.

## Pendiente para publicar en Google Play

1. **Política de privacidad** — hecha (`PRIVACY.md` + `privacy.html`); falta **publicarla en
   una URL** (GitHub Pages) y pegar el enlace en Play Console. El enlace **dentro de la app**
   ya existe (botón "Privacidad" en Inicio) y `privacy_policy_url` ya apunta a
   `https://nestordominguezsalazar.github.io/pdfoto/privacy.html`; falta que esa URL esté
   publicada.
2. **Data safety** — declarar "no recopila datos".
3. **Firma de release** con Play App Signing (`keystore.properties`).
4. Recursos de ficha: icono 512×512, gráfico 1024×500, capturas (textos en `docs/12`).
5. Clasificación de contenido y público objetivo.
6. **Registro del package name** (verificación de desarrollador). Las apps nuevas de Play se
   auto-registran al crearlas en Play Console.

## Notas de entorno

- La verificación se hizo con un JDK 17 y Android SDK 36 **portables** en `/tmp/opencode`
  (efímeros). `local.properties` apunta ahí; Android Studio lo regenera. Está en `.gitignore`.
- Los documentos `docs/` numerados son la especificación; `docs/progreso.md` (este archivo) es
  el estado de avance.
