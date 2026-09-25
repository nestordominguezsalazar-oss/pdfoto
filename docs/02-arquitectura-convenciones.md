# 02 (bis) - Arquitectura: capas, librerías y convenciones

## Capas
- **UI**: Composables, ViewModels con StateFlow
- **Domain**: casos de uso puros, sin dependencias Android
- **Data**: repositorios, PdfDocument, Room, MediaStore

## Librerías (versiones reales del catálogo)
| Uso | Librería |
|-----|----------|
| DI | Hilt 2.52 |
| Async | Coroutines + Flow |
| Navegación | Navigation Compose 2.8.0 |
| Imágenes | Coil 2.7.0 |
| Persistencia | Room 2.6.1 |
| Cámara | CameraX 1.4.2 |
| Reordenar | sh.calvin.reorderable 2.4.0 |
| EXIF | androidx.exifinterface 1.3.7 |

## Convenciones
- PascalCase para clases, camelCase para funciones.
- Composable público: `NombreScreen` + `NombreViewModel`.
- Estados UI: `sealed interface` (p. ej. `Empty`/`Content`, `Idle`/`InProgress`/`Success`/`Error`).
- Sin lógica en Composables; todo en ViewModel.
- Textos en `res/values/strings.xml` (base español) y `values-en` (inglés).
- Dominio sin Android: los URI son `String` (ver `docs/03`).
