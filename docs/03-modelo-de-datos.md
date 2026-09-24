# 03 - Modelo de datos

## Entidades de dominio

El dominio es puro, **sin dependencias de Android**, para poder testarlo en la JVM.
Por eso los URI se modelan como `String` en vez de `android.net.Uri`; la conversión a
`Uri` se hace en la frontera (UI/data).

```kotlin
data class Photo(
    val id: String,               // UUID
    val uri: String,              // URI del contenido original (como texto)
    val rotationDegrees: Int = 0, // 0, 90, 180, 270
    val order: Int = 0
)

enum class PageSize { A4, LETTER, AUTO }
enum class Orientation { PORTRAIT, LANDSCAPE, AUTO }
enum class MarginSize(val dp: Int) { NONE(0), SMALL(24), LARGE(48) }
enum class Quality(val dpi: Int) { LOW(96), MEDIUM(150), HIGH(200) }

data class PdfConfig(
    val pageSize: PageSize = PageSize.AUTO,
    val orientation: Orientation = Orientation.AUTO,
    val margin: MarginSize = MarginSize.NONE,
    val quality: Quality = Quality.MEDIUM
)

data class PdfJob(
    val id: String,
    val fileName: String,
    val createdAt: Long,
    val pageCount: Int,
    val uri: String,
    val sizeBytes: Long
)
```

## Persistencia (Room)

| Elemento | Fichero |
|----------|---------|
| Entidad (`pdf_history`) | `data/local/PdfHistoryEntity.kt` |
| DAO (`observeAll`, `insert`, `delete`) | `data/local/PdfHistoryDao.kt` |
| Base de datos (versión 1) | `data/local/PdfotoDatabase.kt` |
| Mapper entity ⇄ dominio | `data/local/PdfHistoryMapper.kt` |

## Repositorio

- Interfaz (dominio): `domain/repository/PdfHistoryRepository`
- Implementación (data, Hilt `@Singleton`): `data/repository/PdfHistoryRepositoryImpl`

## Inyección de dependencias

Módulos de Hilt en `com.pdffoto.di`:

- `DatabaseModule` — provee `PdfotoDatabase` y `PdfHistoryDao`
- `RepositoryModule` — enlaza `PdfHistoryRepositoryImpl` a `PdfHistoryRepository`

`PdfotoApp` está anotada con `@HiltAndroidApp` y `MainActivity` con `@AndroidEntryPoint`.
