# Instrucciones de Operación del Agente

Cada vez que se cree código o se solicite un commit, se deben seguir estos pasos obligatoriamente:

## Lista de Tareas Pre-Commit
- [ ] **Compilar**: Ejecutar `gradle_build` para asegurar que el proyecto compile sin errores.
- [ ] **Analizar**: Realizar un análisis de los ficheros modificados o creados (`analyze_file`) y corregir cualquier warning o error detectado.
- [ ] **Commit**: Crear un commit siguiendo el estándar de **Conventional Commits** y **Gitmoji** (usar la skill `commiter`).
- [ ] **Push**: No realizar `git push` a menos que el usuario lo solicite explícitamente.
