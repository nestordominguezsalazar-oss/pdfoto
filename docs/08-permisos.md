# 08 - Permisos

## Photo Picker
No se necesitan permisos de almacenamiento. ✅

## Cámara (CameraX)
El permiso `CAMERA` se pide **solo al abrir la pantalla de cámara**, nunca al iniciar la app.

```xml
<uses-permission android:name="android.permission.CAMERA" />
<uses-feature android:name="android.hardware.camera.any" android:required="false" />
```

## Almacenamiento
No se solicita ningún permiso de almacenamiento:
- selección de fotos → Photo Picker del sistema;
- guardado → MediaStore (API 29+) o FileProvider en el directorio de la app (API < 29).

## Internet
La app **no** solicita el permiso `INTERNET` y no realiza ninguna conexión de red.
