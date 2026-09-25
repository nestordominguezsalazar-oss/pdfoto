# 02 - Arquitectura

## Patrón
MVVM + Clean Architecture (simplificado) en un **único módulo** Gradle (`app`).

## Capas
- **UI** (`ui/`): Composables sin lógica de negocio y ViewModels con `StateFlow<UiState>`.
- **Domain** (`domain/`): modelos y casos de uso puros, sin dependencias de Android (testables
  en la JVM).
- **Data** (`data/`): repositorios, `PdfDocument`, Room (historial), MediaStore/FileProvider
  (almacenamiento), sesión de creación y log local.

La dependencia apunta hacia el dominio: `ui → domain ← data` (el dominio no conoce Android).

El detalle de capas, librerías con versiones reales y convenciones de código está en
[`02-arquitectura-convenciones.md`](./02-arquitectura-convenciones.md).
