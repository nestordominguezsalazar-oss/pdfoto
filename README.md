# PDFoto

App Android nativa para convertir fotos en archivos PDF.

**PDFoto es 100 % libre de publicidad**: no incluye anuncios, analytics ni SDKs de tracking, y
**no añade marca de agua** a los PDF.

## Stack
- Kotlin 2.x
- Jetpack Compose (Material 3)
- CameraX
- PdfDocument (nativo Android)
- Room (historial)
- Coil (previews)
- Hilt (DI)

## Requisitos previos
- Android Studio Ladybug o superior
- JDK 17
- Android SDK 36 (target) / minSdk 24
- Gradle 8.13+

## Build rápido
```bash
./gradlew assembleDebug
./gradlew installDebug
```

## Estado

**Los 15 pasos del plan están completados**: app funcional de punta a punta (galería y
cámara, edición con reordenar/rotar/borrar, configuración, generación con progreso,
guardado en Downloads, abrir/compartir e historial). Detalle y verificación en
[`docs/progreso.md`](./docs/progreso.md).

## Documentación

- Especificación completa en [`docs/`](./docs) (leer en orden numérico).
- Estado del desarrollo en [`docs/progreso.md`](./docs/progreso.md).
- Publicación en Play en [`docs/11-publicacion-play.md`](./docs/11-publicacion-play.md).
- Textos de la ficha en [`docs/12-textos-ficha-play.md`](./docs/12-textos-ficha-play.md).
- Errores y comentarios en [`docs/13-feedback-y-soporte.md`](./docs/13-feedback-y-soporte.md).
- Política de privacidad en [`PRIVACY.md`](./PRIVACY.md).
- Instrucciones de trabajo para agentes en [`AGENTS.md`](./AGENTS.md).
