
## HomeScreen
- Título "PDFoto"
- Botón principal "Crear PDF" → abre Photo Picker
- Botón secundario "Escanear con cámara" → CameraScreen
- Acceso a "Historial"
- Última config usada como chip informativo

## EditorScreen
- `LazyColumn` con tarjetas de imagen
- Cada tarjeta: thumbnail, índice, botones (rotar, borrar), handle drag
- Botón flotante "Añadir más fotos"
- Botón "Continuar" (habilitado si ≥1 foto)
- Estado vacío: "No hay fotos, añade algunas"

## ConfigScreen
- Nombre del archivo (TextField, default `pdfoto_yyyyMMdd_HHmm`)
- Selector tamaño de página (chips o dropdown)
- Selector orientación
- Selector márgenes
- Selector calidad
- Botón "Generar PDF"

## GeneratingScreen
- CircularProgressIndicator
- Texto "Procesando imagen 3 de 10..."
- Botón "Cancelar"

## ResultScreen
- Ícono de éxito
- Nombre y tamaño del PDF
- Botón "Abrir"
- Botón "Compartir"
- Botón "Volver al inicio"

## HistoryScreen
- Lista de PDFs con nombre, fecha, páginas, tamaño
- Swipe para eliminar
- Click para abrir o compartir

## Componentes reutilizables
- `PhotoCard(photo, onRotate, onDelete, onDrag)`
- `ConfigChipRow(options, selected, onSelect)`
- `PrimaryButton(text, onClick, enabled)`