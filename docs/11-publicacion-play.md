# 11 - Publicación en Google Play

Checklist para publicar **PDFoto** (`com.pdfoto`). Los requisitos técnicos ya están
cumplidos; lo que falta son trámites y recursos de ficha. Todos los datos listos para pegar
en Play Console están en [`14-datos-play.md`](./14-datos-play.md).

## 0. Requisitos técnicos (ya cumplidos)

- [x] `targetSdk 36` (Android 16) — obligatorio para apps nuevas desde 2026-08-31.
- [x] `minSdk 24`, sin permisos de almacenamiento, permiso `CAMERA` solo al usarlo.
- [x] Sin publicidad, analytics ni tracking.
- [x] **Soporte de páginas de 16 KB** (obligatorio para targetSdk 15+ desde 2025-11-01):
      CameraX 1.4.2 trae las libs nativas alineadas (`p_align = 0x4000`).
- [x] Release firmable con `./gradlew bundleRelease` (R8 + reducción de recursos).

## 1. Cuenta y verificación

- [ ] Crear cuenta de **Google Play Console** (25 USD, pago único).
- [ ] Completar la **verificación de identidad** del desarrollador.
- [ ] **Registrar el package name** `com.pdfoto` (verificación de desarrollador).
      Plazo de referencia: **2026-09-30**. Las apps nuevas se auto-registran al crearlas
      en Play Console.

## 2. Política de privacidad (obligatoria)

Play exige un enlace a la política **en la ficha** y **dentro de la app**.

- [x] `PRIVACY.md` con responsable y correo rellenos.
- [x] HTML listo para publicar: [`../privacy.html`](../privacy.html) (bilingüe, autocontenido).
- [x] **Publicada en GitHub Pages** (rama `main`, carpeta `/root`):
      `https://nestordominguezsalazar-oss.github.io/pdfoto/privacy.html` — verificada (HTTP 200).
- [ ] Pegar esa URL en Play Console (*Política de privacidad*).
- [x] Enlace **dentro de la app**: botón "Privacidad" en la pantalla de inicio.
      `privacy_policy_url` (`app/src/main/res/values/strings.xml`) apunta a la URL anterior.

## 3. Data safety (formulario obligatorio)

- [ ] **¿Recopila datos?** → **No**.
- [ ] **¿Comparte datos?** → **No**.
- [ ] **¿Los datos se cifran en tránsito?** → No aplica (no hay transmisión).
- [ ] **Eliminación de datos**: indicar que el usuario puede desinstalar la app (y borrar
      los PDF de *Downloads*).
- [ ] Marcar que la app **no** usa permisos de datos sensibles más allá de la cámara
      (procesada en el dispositivo).

## 4. Firma de release

- [ ] Crear la clave de subida:
  ```bash
  keytool -genkeypair -v -keystore release.jks -alias pdfoto \
    -keyalg RSA -keysize 2048 -validity 10000
  ```
- [ ] Copiar `keystore.properties.example` a `keystore.properties` y rellenarlo
      (no se versiona).
- [ ] Generar el bundle: `./gradlew bundleRelease` → `app/build/outputs/bundle/release/app-release.aab`.
- [ ] Activar **Play App Signing** al subir el primer AAB.

## 5. Recursos de la ficha

- [x] **Icono** 512×512 PNG generado en [`store/icon-512.png`](./store/icon-512.png)
      (fuente en `docs/art/`).
- [x] **Gráfico destacado** 1024×500 en [`store/feature-graphic-1024x500.png`](./store/feature-graphic-1024x500.png).
- [x] **Capturas de teléfono** (6, en [`store/capturas/`](./store/capturas/)): 1080×1920 (9:16),
      PNG 24-bit sin alfa (cumple 320–3840 px y lado mayor ≤ 2× el menor). Capturadas en un
      dispositivo real (SM-S938B) con imágenes de ejemplo neutras.
- [ ] **Nombre** ≤ 30 caracteres: `PDFoto`.
- [ ] **Descripción corta** ≤ 80 caracteres.
- [ ] **Descripción completa** ≤ 4000 caracteres (mencionar: sin publicidad, offline,
      sin permisos de almacenamiento).
- [ ] Categoría (Herramientas/Productividad), etiquetas, correo de contacto.

## 6. Clasificación de contenido y público

- [ ] Completar el cuestionario de **clasificación de contenido**.
- [ ] Declarar el **público objetivo** (no dirigida a menores).

## 7. Pruebas antes de producción

- [ ] Subir el AAB a la pista de **prueba interna** e instalar desde Play. Enlace de la pista:
      `https://play.google.com/apps/testing/com.pdfoto` → hay que **aceptar la invitación** con
      la cuenta de tester. No confundirlo con el enlace de **compartir aplicaciones de forma
      interna** (`.../apps/internaltest/<id>`), que es otra función. Ver
      [`15-prueba-cerrada.md`](./15-prueba-cerrada.md).
- [ ] Probar: galería, cámara, rotar/borrar/reordenar, configuración, generar, **abrir** y
      **compartir**, historial y borrado.
- [ ] Si la cuenta es **personal y creada después del 13/11/2023**, Play exige **prueba cerrada
      con 12 testers opt-in continuos durante 14 días** antes de habilitar producción (la prueba
      interna **no** cuenta). Pasos y formulario de acceso a producción en
      [`15-prueba-cerrada.md`](./15-prueba-cerrada.md).

## 8. Publicación

- [ ] Revisar advertencias en Play Console (pre-lanzamiento).
- [ ] Enviar a revisión y publicar en producción.

## Notas

- El AAB actual pesa ≈ 4.7 MB (criterio de éxito del proyecto: < 15 MB). ✅
- La app funciona 100 % offline; si en el futuro se añade cualquier función de red, habría
  que actualizar la política de privacidad y el formulario de Data safety.
