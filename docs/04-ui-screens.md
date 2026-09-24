# 04 - Pantallas UI

## Navegación

Implementada con **Navigation Compose** en `com.pdffoto.ui.navigation`
(`Destination` + `PdfotoNavHost`). El tema y el grafo se montan en `PdfotoRoot`.

| Ruta | Pantalla | Estado |
|------|----------|--------|
| `home` | `HomeScreen` | ✅ implementada (paso 2) |
| `editor` | `EditorScreen` | ✅ selección, rotar, eliminar y reordenar (pasos 4, 5 y 13) |
| `config` | `ConfigScreen` | ✅ implementada (paso 7) |
| `generating` | `GeneratingScreen` | ✅ implementada (paso 8) |
| `result` | `ResultScreen` | ✅ resultado, abrir y compartir (pasos 9 y 10) |
| `history` | `HistoryScreen` | ✅ implementada (paso 11) |
| `camera` | `CameraScreen` | ✅ implementada (paso 12) |
| `about` | `AboutScreen` | ✅ Acerca de (ventajas, privacidad y comentarios) |

Flujo principal:

```
home ──▶ editor ──▶ config ──▶ generating ──▶ result
                                              │
                                              ▼
                                            home
```

Accesos laterales desde `home`: `history`, `camera` y `about` (que incluye la **política de
privacidad** y **enviar comentarios**). Desde el `editor` también se puede ir a `camera` para
añadir una foto.

El detalle de cada pantalla está en [`04-ui-screens-pantallas.md`](./04-ui-screens-pantallas.md).
