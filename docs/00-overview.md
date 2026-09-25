# 00 - Overview

## Objetivo
App Android que permite al usuario:
1. Seleccionar fotos desde galería O tomarlas con la cámara
2. Reordenarlas y rotarlas
3. Generar un PDF configurable (tamaño de página, márgenes, calidad)
4. Guardarlo en el dispositivo o compartirlo

Sin publicidad ni trackers: la app funciona 100 % offline.

## Alcance MVP
- Selección múltiple de imágenes (hasta 50)
- Reordenamiento drag & drop
- Rotación 90° incremental
- Generación de PDF en tamaño A4 / Carta / ajustar a imagen
- Guardado en Downloads
- Compartir vía Intent
- **Sin marca de agua**: el PDF generado es 100 % del usuario

## Fuera de alcance (v2+)
- Recorte de imágenes
- OCR
- Escaneo con detección de bordes
- Firma digital
- Cifrado con contraseña
- Fusión con PDFs existentes

## Casos de uso principales
1. Escanear apuntes: tomar 10 fotos → PDF → enviar por email
2. Digitalizar documentos: seleccionar de galería → reordenar → guardar
3. Compartir fotos como documento en WhatsApp/Telegram

## Criterios de éxito
- Generar PDF de 20 imágenes en < 5 segundos en dispositivo gama media
- Sin crashes en Android 7 (API 24) hasta Android 16 (API 36)
- APK < 15 MB
- Sin publicidad ni recolección de datos
