# 05 - Cámara y galería

## Galería — Photo Picker (sin permisos)
```kotlin
val launcher = rememberLauncherForActivityResult(
    contract = ActivityResultContracts.PickMultipleVisualMedia(maxItems = 50)
) { uris: List<Uri> ->
    viewModel.onPhotosPicked(uris.map(Uri::toString))
}

launcher.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
```
Se usa en `EditorScreen` para elegir fotos (al entrar vacío y desde "Añadir más fotos").

## Cámara — CameraX
`CameraScreen` con `PreviewView` + `Preview` + `ImageCapture`:

- El permiso `CAMERA` se pide **solo al abrir la pantalla** (nunca al iniciar la app).
- `capturePhoto(context, imageCapture)` guarda en `cacheDir` y devuelve el `Uri`.
- La captura se añade a la `CreationSession`, con dos comportamientos:
  - desde **Inicio** ("Escanear con cámara") → empieza un **documento nuevo**;
  - desde el **editor** ("Añadir más fotos" → "Hacer una foto") → se **añade** al documento
    actual.
- `CameraPreview` enlaza al ciclo de vida y **desvincula la cámara al salir** con
  `DisposableEffect`.

Manifest:
```xml
<uses-permission android:name="android.permission.CAMERA" />
<uses-feature android:name="android.hardware.camera.any" android:required="false" />
```
