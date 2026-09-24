# 01 - Requisitos funcionales y no funcionales

## Funcionales
| ID   | Descripción                                            | Prioridad |
|------|--------------------------------------------------------|-----------|
| RF1  | Seleccionar múltiples imágenes desde galería           | Alta      |
| RF2  | Tomar fotos con la cámara integrada                    | Alta      |
| RF3  | Reordenar imágenes (drag & drop)                       | Alta      |
| RF4  | Rotar imagen 90°                                        | Media     |
| RF5  | Eliminar imagen de la lista                            | Alta      |
| RF6  | Configurar tamaño de página (A4, Carta, Auto)          | Media     |
| RF7  | Configurar orientación (Retrato, Paisaje, Auto)        | Media     |
| RF8  | Configurar márgenes (Ninguno, Pequeño, Grande)         | Baja      |
| RF9  | Ajustar calidad JPEG (Baja, Media, Alta)               | Media     |
| RF10 | Vista previa del PDF antes de generar                  | Baja      |
| RF11 | Guardar en carpeta Downloads                           | Alta      |
| RF12 | Compartir PDF vía Intent                               | Alta      |
| RF13 | Historial de PDFs generados                            | Baja      |
| RF14 | Nombre personalizado del archivo                       | Media     |

## No funcionales
- **Rendimiento**: procesamiento en background con progreso visible
- **Memoria**: no cargar bitmaps full-res; usar sampling
- **Compatibilidad**: minSdk 24, targetSdk 36 (requisito de Google Play para apps nuevas desde 2026-08-31)
- **Idioma**: español (base), inglés (traducción)
- **Tema**: Material 3, claro/oscuro automático
- **Accesibilidad**: content descriptions, tamaños mínimos 48dp
- **Sin publicidad ni telemetría**: prohibido incluir anuncios, analytics o SDKs de tracking (no negociable)