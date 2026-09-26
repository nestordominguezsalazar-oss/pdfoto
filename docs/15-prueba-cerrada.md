# 15 - Prueba cerrada y acceso a producción

Guía para pasar de **prueba interna** a **producción** en una cuenta de desarrollador
**personal** creada después del 13/11/2023. Fuente oficial:
[Requisitos de pruebas de aplicaciones para las nuevas cuentas personales de desarrollador](https://support.google.com/googleplay/android-developer/answer/14151465).

## 1. Tres mecanismos distintos (no confundir los enlaces)

| Mecanismo | Para qué sirve | Enlace | Clave |
|---|---|---|---|
| **Prueba interna** (pista) | Repartir el build a ≤100 testers sin esperar revisión | `https://play.google.com/apps/testing/com.pdfoto` | Hay que **aceptar la invitación** con la cuenta de tester |
| **Prueba cerrada** (pista) | Requisito previo a producción | `https://play.google.com/apps/testing/com.pdfoto` | Mismo formato; **sí pasa por revisión** |
| **Compartir aplicaciones de forma interna** | Instalar un build concreto sin lista de testers | `https://play.google.com/apps/internaltest/<id>` | Otra función: requiere **activar "Compartir aplicaciones de forma interna"** en Play Store (Ajustes → sección "Información" → tocar "Versión de Play Store" 7 veces). **Caduca a los 60 días** y admite **100 descargas** por enlace |

Regla rápida:

- `.../apps/internaltest/<id>` → *compartir aplicaciones de forma interna*.
- `.../apps/testing/com.pdfoto` → una **pista de pruebas** (interna o cerrada). Cuál de las
  dos depende de en qué lista esté tu cuenta, no del enlace.

No aparecen en la búsqueda de Play: hay que abrir el enlace directo.

## 2. ¿A quién aplica el requisito?

- Cuentas **personales** creadas **después del 13/11/2023**.
- Cuentas de **organización**: exentas.
- La **prueba interna NO cuenta** para el requisito; solo la **cerrada**.

## 3. Requisito

**Mínimo 12 testers opt-in de forma continua durante 14 días.** Al solicitar producción debe
haber 12 testers que lleven **14 días seguidos** sin interrupción. Si un tester sale y vuelve,
su reloj de 14 días **se reinicia**. `docs/11` decía "varios testers"; el número real es **12**.

## 4. Pasos

1. **Completar la configuración de la app** en Play Console (Data safety, clasificación de
   contenido, público objetivo, política de privacidad...). Es requisito para abrir la pista
   cerrada.
2. **Crear la pista de prueba cerrada**: *Probar y publicar → Pruebas cerradas*. Añade una
   versión (puedes **promover** la de prueba interna o subir el AAB; si Play pide un
   `versionCode` nuevo, súbelo).
3. **Añadir 15–20 testers** (margen sobre 12): lista de correos o grupo de Google. Guarda los
   cambios.
4. **Compartir el enlace** `https://play.google.com/apps/testing/com.pdfoto`. Cada tester lo
   abre con **su** cuenta, acepta e instala.
5. **Mantener 14 días continuos**: pídele que **use la app de verdad**; recoge comentarios y
   corrige. Sube actualizaciones durante la prueba.
6. **Solicitar acceso a producción**: *Panel de control → Solicitar acceso a producción*.
   Formulario en 3 partes:
   1. **Sobre tu prueba cerrada**: dificultad para reclutar, si usaron todas las funciones,
      resumen de comentarios y cómo los recogiste.
   2. **Sobre tu app**: público objetivo, propuesta de valor, descargas estimadas el primer año.
   3. **Preparación para producción**: cambios hechos según el feedback y cómo decidiste que
      está lista.
7. **Revisión**: normalmente **≤ 7 días**. Si falta actividad o testers, te piden seguir
   probando.

Timeline realista: reclutamiento + 14 días + revisión ≈ **3–4 semanas**.

### Advertencias

- Un tester **opt-in en prueba interna no puede unirse a la cerrada** hasta salir de la
  interna.
- **No inventes** el feedback del formulario: Play cruza datos de uso reales.
- La pista cerrada **sí pasa por revisión** (la interna no), así que cuenta con unos días.

## 5. Privacidad del desarrollador (datos públicos en la ficha)

Google muestra datos del desarrollador en la ficha, y los testers también los ven:

- **"Datos de contacto de la ficha de Play Store"** (*Play Console → Configuración de la
  tienda*): **correo, teléfono y sitio web**. **Aparecen en la ficha pública.** Solo el correo
  es obligatorio → **borra el teléfono** si no quieres exponerlo.
- **Cuenta de desarrollador → Información sobre ti / Datos de contacto**: el
  **"Número de teléfono de contacto"** y el correo de contacto son **privados** (Google los usa
  para contactarte; no se muestran en Play).
- En cuentas **personales**, "Acerca del desarrollador" puede mostrar además el **nombre
  legal** (y la dirección del perfil de pagos). Evitarlo por completo requiere una **cuenta de
  organización**.
- En el repositorio **no hay ningún teléfono**: la app solo muestra el correo `support_email`
  (`app/src/main/res/values/strings.xml`) y la política de privacidad solo usa correo
  (`PRIVACY.md`).

## 6. Estado

- [x] Prueba interna instalada en dispositivo (2026-09-25).
- [ ] Conjunto de 12+ testers reclutados.
- [ ] 14 días continuos completados.
- [ ] Acceso a producción solicitado.
