# 11 - Publicación en Google Play

Checklist para publicar **PDFoto** (`com.pdffoto`). Los requisitos técnicos ya están
cumplidos; lo que falta son trámites y recursos de ficha.

## 0. Requisitos técnicos (ya cumplidos)

- [x] `targetSdk 36` (Android 16) — obligatorio para apps nuevas desde 2026-08-31.
- [x] `minSdk 24`, sin permisos de almacenamiento, permiso `CAMERA` solo al usarlo.
- [x] Sin publicidad, analytics ni tracking.
- [x] Release firmable con `./gradlew bundleRelease` (R8 + reducción de recursos).

## 1. Cuenta y verificación

- [ ] Crear cuenta de **Google Play Console** (25 USD, pago único).
- [ ] Completar la **verificación de identidad** del desarrollador.
- [ ] **Registrar el package name** `com.pdffoto` (verificación de desarrollador).
      Plazo de referencia: **2026-09-30**. Las apps nuevas se auto-registran al crearlas
      en Play Console.

## 2. Política de privacidad (obligatoria)

Play exige un enlace a la política **en la ficha** y **dentro de la app**.

- [ ] Rellenar los campos `[REQUERIDO]` (responsable y correo) en [`../PRIVACY.md`](../PRIVACY.md).
- [x] HTML listo para publicar: [`../privacy.html`](../privacy.html) (bilingüe, autocontenido).
- [ ] Publicarla en una URL pública. Sin dominio, la vía gratuita es **GitHub Pages**:
  1. Subir el repo a GitHub.
  2. *Settings → Pages → Deploy from a branch* (rama `main`, carpeta `/root`).
  3. La URL queda como `https://<usuario>.github.io/pdffoto/privacy.html`.
- [ ] Pegar la URL en Play Console (*Política de privacidad*).
- [x] Enlace **dentro de la app**: botón "Privacidad" en la pantalla de inicio.
      ⚠️ Actualiza `privacy_policy_url` en `app/src/main/res/values/strings.xml` con la URL
      definitiva antes de publicar.

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
  keytool -genkeypair -v -keystore release.jks -alias pdffoto \
    -keyalg RSA -keysize 2048 -validity 10000
  ```
- [ ] Copiar `keystore.properties.example` a `keystore.properties` y rellenarlo
      (no se versiona).
- [ ] Generar el bundle: `./gradlew bundleRelease` → `app/build/outputs/bundle/release/app-release.aab`.
- [ ] Activar **Play App Signing** al subir el primer AAB.

## 5. Recursos de la ficha

- [x] **Icono** 512×512 PNG generado en [`store/icon-512.png`](./store/icon-512.png)
      (fuente en `docs/art/`).
- [ ] **Gráfico destacado** 1024×500 PNG/JPG.
- [ ] **Capturas de teléfono** (mín. 2; recomendado 4-8) — se pueden hacer con un
      emulador de Android Studio.
- [ ] **Nombre** ≤ 30 caracteres: `PDFoto`.
- [ ] **Descripción corta** ≤ 80 caracteres.
- [ ] **Descripción completa** ≤ 4000 caracteres (mencionar: sin publicidad, offline,
      sin permisos de almacenamiento).
- [ ] Categoría (Herramientas/Productividad), etiquetas, correo de contacto.

## 6. Clasificación de contenido y público

- [ ] Completar el cuestionario de **clasificación de contenido**.
- [ ] Declarar el **público objetivo** (no dirigida a menores).

## 7. Pruebas antes de producción

- [ ] Subir el AAB a la pista de **prueba interna** e instalar desde Play.
- [ ] Probar: galería, cámara, rotar/borrar/reordenar, configuración, generar, **abrir** y
      **compartir**, historial y borrado.
- [ ] Si la cuenta es personal y nueva, Play exige **prueba cerrada con varios testers
      durante 14 días** antes de habilitar producción (revisa el requisito vigente en tu
      cuenta).

## 8. Publicación

- [ ] Revisar advertencias en Play Console (pre-lanzamiento).
- [ ] Enviar a revisión y publicar en producción.

## Notas

- El AAB actual pesa ≈ 4.7 MB (criterio de éxito del proyecto: < 15 MB). ✅
- La app funciona 100 % offline; si en el futuro se añade cualquier función de red, habría
  que actualizar la política de privacidad y el formulario de Data safety.
