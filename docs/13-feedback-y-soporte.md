# 13 - Errores y comentarios de usuarios

Cómo se recogen errores y sugerencias **sin romper la privacidad**: la app es offline, no
tiene permiso de `INTERNET` ni analytics/tracking.

## 1. Errores y cierres → Google Play Console (Android vitals)
- Play recoge **crashes y ANR** a nivel de sistema. **No requiere SDK, código ni permisos**, y
  lo recoge Google (no la app), por lo que no cambia la política de privacidad.
- Revisar tras cada publicación en *Play Console → Calidad → Android vitals*.
- Usar también el **informe de pre-lanzamiento** (pre-launch report) en las pistas de prueba.

## 2. Comentarios dentro de la app (implementado)
- Botón **"Enviar comentarios"** en Inicio → abre el cliente de correo prerellenado con:
  - **diagnósticos** técnicos (versión de la app, versión de Android y dispositivo; sin datos
    personales), construidos por `domain/support/buildFeedbackDiagnostics`;
  - **adjunto**: el **log local** (`cacheDir/logs/pdffoto.log`), compartido vía FileProvider.
- **Nada se envía automáticamente**: lo manda el usuario con su app de correo. La app no abre
  ninguna conexión de red.

## 3. Reseñas y contacto de Play
- Leer y **responder reseñas** en Play Console.
- Correo de soporte en la ficha: `nestordominguezsalazar@gmail.com`.

## 5. Log local (implementado)
- `data/logging`: `AppLogger` + `FileLogger` (bucle de 500 líneas, en `cacheDir`, sin red).
- Registra: cierres no controlados (`PdfotoApp`), errores de **generación/guardado** y de
  **captura** de cámara.
- Se adjunta al correo de comentarios cuando existe.

## Qué NO se hace (y por qué)
- No hay SDK de crash reporting ni de analítica, ni permiso de `INTERNET`.
- Si algún día se quisiera **reporte automático** (Crashlytics/Sentry), habría que: añadir
  `INTERNET`, cambiar `PRIVACY.md`, actualizar el formulario **Data safety** y pedir
  consentimiento explícito. No encaja con la propuesta "privado y offline".

## Resumen
| Canal | Qué recoge | Datos que salen | Permisos |
|---|---|---|---|
| Android vitals (Play) | Crashes y ANR | A Google (no a ti) | Ninguno |
| "Enviar comentarios" (app) | Lo que reporte el usuario + log | Solo si el usuario lo envía | Ninguno |
| Reseñas / email de ficha | Opinión pública / correo | Público / email | Ninguno |
