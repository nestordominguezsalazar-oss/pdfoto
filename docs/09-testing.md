# 09 - Testing

## Estado actual
**48 tests unitarios (JVM)** en `app/src/test`. Se ejecutan con `./gradlew testDebugUnitTest`.

### Dominio y utilidades puras
- `PdfConfigResolverTest`: tamaños A4/Carta/AUTO, orientación y `targetDecodeDimension` (DPI).
- `FileNameSanitizerTest`: caracteres inválidos y extensión `.pdf`.
- `ImageSamplingTest`: `calculateInSampleSize`.
- `DefaultFileNameTest`, `FileSizeFormatterTest`, `DateFormatterTest`.
- `PhotoSelectionTest` (`buildPhotos`), `PhotoEditsTest` (rotar / eliminar / mover).

### Datos y ViewModels
- `PdfHistoryMapperTest` (entity ⇄ dominio).
- `EditorViewModelTest`, `HomeViewModelTest`, `ConfigViewModelTest`.
- `GenerationViewModelTest`: éxito, error de generación, error de guardado y cancelación, con
  dobles en memoria (`FakePdfGeneratorService`, `FakePdfStorage`, `FakePdfHistoryRepository`).
- `HistoryViewModelTest`: `Empty` / `Content` / eliminar.

## Pendiente (requiere emulador o dispositivo)
- **Instrumentados (Android)**: `PdfGenerator` de extremo a extremo (generar y verificar con
  `PdfRenderer`) y `AndroidPdfStorage` (MediaStore / FileProvider).
- **UI (Compose)**: smoke tests de Editor y Config con `createAndroidComposeRule`.

## Herramientas
- JUnit 4 + Truth
- `kotlinx-coroutines-test` (ViewModels)
- Turbine y MockK están declarados en el catálogo, aún sin uso.

## Cobertura objetivo
- Domain: 80 % · Data: 60 % · UI: smoke tests principales.
