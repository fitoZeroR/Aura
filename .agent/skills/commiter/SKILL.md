---
name: commiter
description: Use it when you're asked to create un commit
---

# Commiter Skill

Este skill define cómo realizar commits siguiendo el estándar de **Conventional Commits** y el uso de **Gitmoji**.

## Estructura del Commit

Los mensajes de commit deben seguir la siguiente estructura:

```
<emoji> <tipo>(<alcance>): <descripción corta>

[cuerpo opcional]

[pie de página opcional]
```

- **emoji**: El emoji correspondiente al tipo de cambio.
- **tipo**: El tipo de cambio (feat, fix, etc.).
- **alcance** (opcional): Una breve descripción del área afectada (ej: UI, DB, Auth).
- **descripción**: Una explicación concisa en tiempo presente (ej: "añade botón de inicio").

## Tipos de Commit y Emojis

| Emoji | Tipo | Descripción |
| :--- | :--- | :--- |
| ✨ | `feat` | Nueva funcionalidad |
| 🐛 | `fix` | Corrección de errores |
| 📝 | `docs` | Cambios en la documentación |
| 🎨 | `style` | Cambios de formato, espacios, etc. (no afectan la lógica) |
| ♻️ | `refactor` | Refactorización de código |
| ⚡️ | `perf` | Mejoras de rendimiento |
| ✅ | `test` | Añadir o modificar tests |
| 🏗️ | `build` | Cambios en el sistema de construcción o dependencias |
| 👷 | `ci` | Cambios en archivos y scripts de CI |
| 🧹 | `chore` | Otros cambios que no modifican `src` ni `test` |
| ⏪ | `revert` | Revertir un commit anterior |

## Reglas Críticas

1. **Compilación Previa**: Antes de cada commit, es obligatorio compilar el código (`gradle_build`) para asegurar que los cambios no rompen el proyecto.
2. **Sin Push Automático**: No realices `git push` a menos que se te indique explícitamente.
3. **Idioma**: Los mensajes de commit deben estar en español, a menos que el proyecto use inglés por defecto.
