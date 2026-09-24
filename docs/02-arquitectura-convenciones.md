
## Capas
- **UI**: Composables, ViewModels con StateFlow
- **Domain**: casos de uso puros, sin dependencias Android
- **Data**: repositorios, PdfDocument, Room, MediaStore

## Librerías
| Uso          | Librería                          |
|--------------|-----------------------------------|
| DI           | Hilt 2.51                         |
| Async        | Coroutines 1.8 + Flow             |
| Navegación   | Compose Navigation 2.7            |
| Imágenes     | Coil 2.6                          |
| Persistencia | Room 2.6                          |
| Cámara       | CameraX 1.3                       |
| Preferencias | DataStore 1.1                     |

## Convenciones
- Nombres de archivos: PascalCase para clases, camelCase para funciones
- Composable público: `NombreScreen` + `NombreViewModel`
- Estados UI: sealed interface `UiState` (Loading, Success, Error)
- Sin lógica en Composables; todo en ViewModel