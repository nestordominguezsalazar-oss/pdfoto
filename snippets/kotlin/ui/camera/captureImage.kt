suspend fun capture(imageCapture: ImageCapture, context: Context): Uri {
    val file = File(
        context.cacheDir,
        "capture_${System.currentTimeMillis()}.jpg"
    )
    val output = ImageCapture.OutputFileOptions.Builder(file).build()

    return suspendCancellableCoroutine { cont ->
        imageCapture.takePicture(
            output,
            ContextCompat.getMainExecutor(context),
            object : ImageCapture.OnImageSavedCallback {
                override fun onImageSaved(results: ImageCapture.OutputFileResults) {
                    cont.resume(Uri.fromFile(file))
                }
                override fun onError(exc: ImageCaptureException) {
                    cont.resumeWithException(exc)
                }
            }
        )
    }
}