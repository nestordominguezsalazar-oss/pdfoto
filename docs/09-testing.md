# 09 - Testing

## Estado actual
**59 tests unitarios (JVM)** en `app/src/test`. Se ejecutan con `./gradlew testDebugUnitTest`.

### Dominio y utilidades puras
- `PdfConfigResolverTest`: tamaños A4/Carta/AUTO, orientación y `targetDecodeDimension` (DPI).
- `FileNameSanitizerTest`: caracteres inválidos y extensión `.pdf`.
- `ImageSamplingTest`: `calculateInSampleSize`.
- `DefaultFileNameTest`, `FileSizeFormatterTest`, `DateFormatterTest`.
- `PhotoSelectionTest` (`buildPhotos`), `PhotoEditsTest` (rotar / eliminar / mover).
- `ExifOrientationTest` (mapeo de la orientación EXIF).
- `FeedbackDiagnosticsTest` (diagnósticos de soporte).

### Datos y ViewModels
- `PdfHistoryMapperTest` (entity ⇄ dominio).
- `FileLoggerTest` (bucle del log local en `cacheDir`).
- `EditorViewModelTest`, `HomeViewModelTest`, `ConfigViewModelTest`, `CameraViewModelTest`.
- `GenerationViewModelTest`: éxito, error de generación, error de guardado y cancelación, con
  dobles en memoria (`FakePdfGeneratorService`, `FakePdfStorage`, `FakePdfHistoryRepository`).
- `HistoryViewModelTest`: `Empty` / `Content` / eliminar.

## Instrumentados (Android) — `app/src/androidTest`
**Ejecutados en un dispositivo real** (Samsung SM-S938B, Android 16 / API 36): **8/8 en verde**
con `./gradlew connectedDebugAndroidTest`. También compilan solos con `assembleDebugAndroidTest`.

- `PdfGeneratorInstrumentedTest`: genera un PDF real desde imágenes sintéticas y lo reabre con
  `PdfRenderer` (nº de páginas, tamaño A4 en vertical, progreso 1-based y fotos ilegibles o con
  URI inaccesible omitidas).
- `AndroidPdfStorageTest`: ejercita la ruta real de guardado (MediaStore en API 29+ o
  FileProvider por debajo) y vuelve a leer el PDF.
- `EditorScreenSmokeTest` y `ConfigScreenSmokeTest`: la pantalla se renderiza y su acción
  principal está presente (`createComposeRule`).

## Pendiente
- **CI**: ejecutar los instrumentados en un emulador (el workflow actual solo corre unit tests).
- **UI**: ampliar los smoke tests a interacciones (no solo render).

## Herramientas
- JUnit 4 + Truth (unitarios e instrumentados)
- `kotlinx-coroutines-test` (ViewModels)
- `androidx.test` + Compose UI Test (`createComposeRule`) en instrumentados
- Turbine y MockK están declarados en el catálogo, aún sin uso.

## Cobertura objetivo
- Domain: 80 % · Data: 60 % · UI: smoke tests principales.
