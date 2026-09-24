# 04 (bis) - Detalle de pantallas

## HomeScreen
- Título "PDFoto" y tagline
- **"Crear PDF"** → inicia un documento nuevo y abre el Photo Picker
- **"Escanear con cámara"** → inicia un documento nuevo y abre `CameraScreen`
- **"Historial"** → `HistoryScreen`
- **"Privacidad"** → abre la política en el navegador

## EditorScreen
- `LazyColumn` de tarjetas: miniatura, índice, grados de rotación, botones **rotar** y
  **eliminar**, y **handle de arrastre**
- FAB **"Añadir más fotos"** → menú con **"Elegir de la galería"** y **"Hacer una foto"**
- Estado vacío: "No hay fotos, añade algunas" + botones de galería y cámara
- Botón **"Continuar"** (habilitado si hay ≥ 1 foto)
- **Tocar una foto** abre una **vista previa a pantalla completa** (fondo negro, se cierra
  tocando la imagen o la X). No incluye zoom ni swipe entre fotos.

## ConfigScreen
- Nombre del archivo (TextField, por defecto `pdfoto_yyyyMMdd_HHmm`)
- Chips: tamaño de página, orientación, márgenes y calidad (dpi)
- Botón **"Generar PDF"**

## GeneratingScreen
- `CircularProgressIndicator` y "Procesando imagen X de Y"
- Botón **"Cancelar"**; si falla, **"Reintentar"**

## ResultScreen
- Icono de éxito, nombre del archivo y "N páginas · tamaño"
- Botones **"Abrir"**, **"Compartir"** y **"Volver al inicio"**

## HistoryScreen
- Lista con nombre, fecha, páginas y tamaño
- **Swipe para eliminar**; toque para abrir; botón para compartir

## Componentes reutilizables
- `ConfigChipRow(title, options, selected, label, onSelect)`
- `PhotoCard` (privada de `EditorScreen`)
