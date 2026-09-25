# 14 - Datos para subir a Play Console

Paquete único, listo para copiar/pegar y subir **PDFoto** (`com.pdfoto`) a Google Play.
Los textos largos de la ficha también viven en [`12-textos-ficha-play.md`](./12-textos-ficha-play.md);
aquí están reunidos con el resto de datos del formulario para tenerlos a mano el día de la subida.

## 1. Identidad y datos técnicos

| Campo | Valor |
|-------|-------|
| Nombre de la app (ficha) | `PDFoto` |
| Package name (`applicationId`) | `com.pdfoto` |
| Versión | `1.5.0` (`versionCode 6`) |
| Correo de contacto | `nestordominguezsalazar@gmail.com` |
| Política de privacidad (URL) | `https://nestordominguezsalazar-oss.github.io/pdfoto/privacy.html` |
| Categoría | Aplicación → **Productividad** (alternativa: Herramientas) |
| Etiquetas | PDF, fotos, escáner, documentos |
| Idioma predeterminado | Español (es-ES); añadir English (en-US) |
| App gratuita | Sí |
| Contiene anuncios | **No** |
| Compras en la app | **No** |
| Repositorio | `https://github.com/nestordominguezsalazar-oss/pdfoto` |
| Peso del AAB | ≈ 4.7 MB |

## 2. Ficha principal

### Nombre de la app
```
PDFoto
```

### Descripción corta (≤ 80)
Español:
```
Convierte tus fotos en PDF sin publicidad, sin conexión y con tu privacidad.
```
English:
```
Turn your photos into a PDF — no ads, fully offline and private by design.
```

### Descripción completa (≤ 4000)

Español:
```
PDFoto convierte tus fotos en un documento PDF en segundos, sin publicidad y sin necesidad de conexión a internet.

Elige fotos de tu galería o hazlas con la cámara, ordénalas a tu gusto y genera un PDF listo para guardar o compartir. Todo el procesamiento ocurre en tu dispositivo: tus fotos no salen de él.

PRINCIPAL
• Selecciona varias fotos a la vez desde la galería
• Haz fotos con la cámara integrada
• Reordena con arrastrar y soltar
• Rota y elimina las que no quieras
• Genera el PDF con progreso visible
• Guárdalo en Downloads o compártelo donde quieras
• Historial de tus PDF generados
• Sin marca de agua en el PDF

CONFIGURABLE
• Tamaño de página: A4, Carta o ajustar a la imagen
• Orientación: vertical, horizontal o automática
• Márgenes: ninguno, pequeño o grande
• Calidad (resolución): baja, media o alta

PRIVADO POR DISEÑO
• Sin publicidad, sin analítica y sin rastreadores
• No requiere conexión a internet
• No pide permisos de almacenamiento (usa el selector de fotos del sistema)
• La cámara solo se usa cuando escaneas y las imágenes se procesan en el dispositivo
• No recopilamos ningún dato personal

IDIOMAS
• Español e inglés

Ideal para escanear apuntes, digitalizar documentos, enviar varias fotos como un solo archivo o guardar recuerdos en PDF.

PDFoto no añade marca de agua ni incluye compras dentro de la aplicación ni suscripciones.
```

English:
```
PDFoto turns your photos into a PDF in seconds — no ads and no internet connection needed.

Pick photos from your gallery or take them with the camera, arrange them the way you want, and generate a PDF ready to save or share. Everything is processed on your device: your photos never leave it.

KEY FEATURES
• Select multiple photos from the gallery
• Take photos with the built-in camera
• Reorder with drag and drop
• Rotate and delete the ones you don't want
• Generate the PDF with visible progress
• Save it to Downloads or share it anywhere
• History of your generated PDFs
• No watermark on the PDF

CONFIGURABLE
• Page size: A4, Letter or fit to image
• Orientation: portrait, landscape or auto
• Margins: none, small or large
• Quality (resolution): low, medium or high

PRIVATE BY DESIGN
• No ads, no analytics and no trackers
• No internet connection required
• No storage permissions (uses the system photo picker)
• The camera is used only when you scan, and images are processed on-device
• We collect no personal data

LANGUAGES
• Spanish and English

Great for scanning notes, digitizing documents, sending several photos as a single file, or keeping memories as a PDF.

PDFoto adds no watermark and has no in-app purchases and no subscriptions.
```

### Texto promocional (opcional, ≤ 170)
Español:
```
Convierte fotos en PDF sin publicidad y sin conexión. Sin marca de agua. Reordena, rota y ajusta tamaño, orientación, márgenes y calidad. Todo ocurre en tu dispositivo.
```
English:
```
Turn photos into PDF with no ads and no connection. No watermark. Reorder, rotate and set page size, orientation, margins and quality. Everything stays on your device.
```

### Novedades de la versión (≤ 500)
Español:
```
PDFoto 1.5.0 — Convierte fotos en PDF sin publicidad y sin marca de agua. Galería y cámara, vista previa de cada foto, reordenar/rotar/eliminar, tamaño de página, orientación, márgenes y calidad, guardado en Downloads, compartir e historial.
```
English:
```
PDFoto 1.5.0 — Turn photos into PDF with no ads and no watermark. Gallery and camera, preview each photo, reorder/rotate/delete, page size, orientation, margins and quality, save to Downloads, share and history.
```

## 3. Gráficos

| Asset | Archivo |
|-------|---------|
| Icono (512×512, 32-bit PNG con alfa) | [`store/icon-512.png`](./store/icon-512.png) |
| Gráfico destacado (1024×500) | [`store/feature-graphic-1024x500.png`](./store/feature-graphic-1024x500.png) |
| Captura 1 — Inicio | [`store/capturas/01-inicio.png`](./store/capturas/01-inicio.png) |
| Captura 2 — Editor | [`store/capturas/02-editor.png`](./store/capturas/02-editor.png) |
| Captura 3 — Configuración | [`store/capturas/03-config.png`](./store/capturas/03-config.png) |
| Captura 4 — Resultado | [`store/capturas/04-resultado.png`](./store/capturas/04-resultado.png) |
| Captura 5 — Historial | [`store/capturas/05-historial.png`](./store/capturas/05-historial.png) |
| Captura 6 — Acerca de | [`store/capturas/06-acercade.png`](./store/capturas/06-acercade.png) |

> Las capturas son 1080×1920 (9:16), PNG 24-bit sin alfa. Si subes desde el navegador,
> descárgalas antes desde GitHub (`docs/store/`).

Alt text sugerido (opcional, ≤ 140 caracteres):
1. `Pantalla de inicio de PDFoto con los distintivos sin publicidad, sin marca de agua y 100 % offline.`
2. `Editor con cuatro fotos: reordenar, rotar y eliminar antes de generar el PDF.`
3. `Configuración del PDF: nombre, tamaño de página, orientación, márgenes y calidad.`
4. `PDF generado: cuatro páginas y 424 KB, con botones Abrir y Compartir.`
5. `Historial de PDF generados con nombre, fecha, páginas y tamaño.`
6. `Pantalla Acerca de: versión, ventajas, soporte y licencias de código abierto.`

## 4. Data safety (formulario)

- **¿Recopila o comparte datos de usuario obligatorios?** → **No**
- **¿Recopila datos?** → **No**
- **¿Comparte datos?** → **No**
- **¿Los datos se cifran en tránsito?** → No aplica (no hay transmisión)
- **Eliminación de datos**: no hay datos que eliminar; el usuario puede **desinstalar** la app y
  borrar los PDF de *Downloads*.
- **Permisos**: solo **cámara** (se pide al escanear; se procesa en el dispositivo). Sin permisos
  de almacenamiento. Sin `INTERNET`.

## 5. Clasificación de contenido (cuestionario IARC)

- **Categoría**: Utilidad / Productividad.
- Violencia, contenido sexual, lenguaje fuerte, drogas, apuestas, terror → **No** en todo.
- **¿Permite interacción entre usuarios / contenido generado por usuarios?** → **No**
- **¿Comparte ubicación?** → **No**
- **¿Permite compras digitales?** → **No**
- **¿Contiene anuncios?** → **No**
- Resultado esperado: **Todos / 3+**.

## 6. Público objetivo y contenido

- **¿App diseñada para niños?** → **No**
- **Grupo de edad objetivo**: **18 años o más** (utilidad general; evita la política de Familias).
- **App access**: *Toda la funcionalidad está disponible sin acceso especial* (sin login; la
  cámara se pide en tiempo de uso).
- **Government app**: No · **Financial features**: No · **Health**: No · **News app**: No.

## 7. Firma y subida del AAB

Generar la clave de subida (guárdala y no la pierdas):
```bash
keytool -genkeypair -v -keystore release.jks -alias pdfoto \
  -keyalg RSA -keysize 2048 -validity 10000
```
Rellenar `keystore.properties` (a partir de `keystore.properties.example`, no versionado):
```
RELEASE_STORE_FILE=/ruta/absoluta/release.jks
RELEASE_STORE_PASSWORD=...
RELEASE_KEY_ALIAS=pdfoto
RELEASE_KEY_PASSWORD=...
```
Generar el bundle:
```bash
./gradlew bundleRelease
# → app/build/outputs/bundle/release/app-release.aab
```
Al subir el primer AAB, activar **Play App Signing** (Google guarda la clave de firma; tú
conservas la de subida).

## 8. Orden recomendado en Play Console

1. **Crear la app**: nombre `PDFoto`, idioma `es-ES`, app gratuita, no es juego.
2. **Ficha principal**: pegar textos (apartado 2), categoría, etiquetas, correo, política
   (apartado 1) y subir gráficos (apartado 3).
3. **App content** (menú izquierdo): Privacy policy, Ads = No, App access, Content rating
   (apartado 5), Target audience (apartado 6), Data safety (apartado 4), Government/Financial/
   Health/News = No.
4. **Versión**: subir `app-release.aab` y las novedades (apartado 2).
5. **Prueba interna** primero: instalar desde Play y probar galería, cámara, rotar/borrar/
   reordenar, configuración, generar, abrir, compartir, historial y borrado.
6. Si la cuenta es personal y nueva: **prueba cerrada con testers durante 14 días** antes de
   habilitar producción.

## 9. Checklist rápido (marcar al subir)

- [ ] App creada (`PDFoto`, `com.pdfoto`, gratis, no juego).
- [ ] Descripción corta (es/en) pegada.
- [ ] Descripción completa (es/en) pegada.
- [ ] Texto promocional y novedades pegados.
- [ ] Categoría, etiquetas y correo configurados.
- [ ] Icono 512×512 subido.
- [ ] Gráfico destacado 1024×500 subido.
- [ ] 6 capturas subidas.
- [ ] Política de privacidad (URL) pegada.
- [ ] Data safety: "no recopila datos".
- [ ] Clasificación de contenido completada.
- [ ] Público objetivo: no para niños; 18+.
- [ ] `app-release.aab` firmado y subido.
- [ ] Prueba interna superada.
