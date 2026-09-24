# 10 - Build y release

## Configuración Gradle

### `gradle/libs.versions.toml`
```toml
[versions]
kotlin = "2.0.20"
# AGP 8.11+ y Gradle 8.13+ son necesarios para compileSdk/targetSdk 36.
agp = "8.11.1"
gradle = "8.13"
composeBom = "2024.09.02"
hilt = "2.52"
room = "2.6.1"
coil = "2.7.0"
camerax = "1.3.4"
navigation = "2.8.0"

[libraries]
compose-bom = { module = "androidx.compose:compose-bom", version.ref = "composeBom" }
hilt-android = { module = "com.google.dagger:hilt-android", version.ref = "hilt" }
hilt-android-compiler = { module = "com.google.dagger:hilt-android-compiler", version.ref = "hilt" }
room-runtime = { module = "androidx.room:room-runtime", version.ref = "room" }
# ... etc
```

## Requisitos de Google Play (2026)

- **targetSdk 36** obligatorio para apps nuevas y actualizaciones desde el **31-08-2026** (Android 16).
- `compileSdk 36` requiere **AGP 8.11+** y **Gradle 8.13+**; `minSdk 24` se mantiene.
- Paquete: `com.pdffoto` (permanente una vez publicado). No hace falta poseer un dominio.
- Verificación de desarrollador: registrar el package name antes del **30-09-2026**.
- Firma con **Play App Signing**; la clave de subida se configura desde `keystore.properties` (ver `keystore.properties.example`).
- Antes de publicar: política de privacidad (enlace en Play Console y dentro de la app), formulario de **Data safety** ("no recopila datos"), icono 512×512, gráfico 1024×500 y capturas.