
---

## 📄 `docs/09-testing.md`

```markdown
# 09 - Testing

## Unitarios (JVM)

### PdfConfigResolverTest
- Dado `PageSize.A4` + `Orientation.PORTRAIT` → dimensiones (595, 842)
- Dado `AUTO` + bitmap 2000x1500 → dimensiones = bitmap / 72 * 72 = mismo

### FileNameSanitizerTest
- Caracteres inválidos se reemplazan
- Se añade extensión `.pdf` si falta

### CalculateInSampleSizeTest
- Bitmap 4000x3000, maxDim 2048 → inSampleSize = 2

## Instrumentados (Android)

### PdfGeneratorInstrumentedTest
- Genera PDF de 3 imágenes de test
- Verifica que existe el archivo y pesa > 0
- Verifica que PdfRenderer puede leer 3 páginas

### MediaStoreSaverInstrumentedTest
- Guarda y consulta el archivo
- Limpia después

## UI (Compose)
- `EditorScreenTest`: añadir foto → aparece tarjeta
- `ConfigScreenTest`: cambiar chips actualiza estado
- Usar `createAndroidComposeRule<MainActivity>()`

## Cobertura objetivo
- Domain: 80%
- Data: 60%
- UI: smoke tests principales

## Herramientas
- JUnit 4
- Truth
- Turbine (Flow)
- Mockk
- Compose UI Test