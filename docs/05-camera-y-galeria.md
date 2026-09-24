# 05 - Cámara y galería

## Galería - Photo Picker (recomendado)
```kotlin
val launcher = rememberLauncherForActivityResult(
    contract = ActivityResultContracts.PickMultipleVisualMedia(maxItems = 50)
) { uris: List<Uri> ->
    viewModel.onPhotosPicked(uris)
}

// Lanzar:
launcher.launch(
    PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
)