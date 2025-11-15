# Solución al Error 500

## Problema
El método `createRoute` tenía `RETURN r` pero el método es `void`, causando error 500.

## Solución Aplicada
- Eliminado `RETURN r` del método `createRoute`
- Simplificado el método `createBidirectionalRoute`
- Mejorado manejo de errores

## Pasos para Aplicar

1. **Reiniciar la aplicación Spring Boot** (necesario para cargar los cambios)
   - Detén la aplicación actual (Ctrl+C)
   - Inicia de nuevo: `.\mvnw.cmd spring-boot:run`

2. **Limpiar la base de datos**
   ```powershell
   .\limpiar-todo.ps1
   ```

3. **Cargar datos corregidos**
   ```powershell
   .\cargar-datos-corregido.ps1
   ```

## Verificación

Después de cargar, deberías tener:
- ✅ 8 ubicaciones (sin duplicados)
- ✅ 20 relaciones bidireccionales

Verificar con:
```powershell
Invoke-WebRequest -Uri http://localhost:8080/api/data/status -UseBasicParsing
```

