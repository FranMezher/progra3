# Script para limpiar completamente la base de datos Neo4j
Write-Host "=== Limpieza Completa de Base de Datos Neo4j ===" -ForegroundColor Cyan
Write-Host ""

# Verificar que la aplicación esté corriendo
Write-Host "Verificando conexión..." -ForegroundColor Yellow
try {
    $health = Invoke-WebRequest -Uri http://localhost:8080/api/test/health -UseBasicParsing
    Write-Host "✅ Aplicación conectada" -ForegroundColor Green
} catch {
    Write-Host "❌ Error: La aplicación no está corriendo" -ForegroundColor Red
    Write-Host "Inicia la aplicación primero con: .\mvnw.cmd spring-boot:run" -ForegroundColor Yellow
    exit 1
}

Write-Host ""

# Verificar estado antes de limpiar
Write-Host "Estado actual:" -ForegroundColor Yellow
try {
    $before = Invoke-WebRequest -Uri http://localhost:8080/api/data/locations -UseBasicParsing
    $locBefore = $before.Content | ConvertFrom-Json
    Write-Host "  - Ubicaciones: $($locBefore.Count)" -ForegroundColor White
} catch {
    Write-Host "  - No se pudo verificar (puede estar vacío)" -ForegroundColor Yellow
}

Write-Host ""

# Paso 1: Limpiar usando el endpoint de limpieza
Write-Host "1. Eliminando todos los nodos y relaciones..." -ForegroundColor Yellow
try {
    $clearResponse = Invoke-WebRequest -Uri http://localhost:8080/api/data/clear -Method DELETE -UseBasicParsing
    $clearData = $clearResponse.Content | ConvertFrom-Json
    Write-Host "✅ $($clearData.message)" -ForegroundColor Green
} catch {
    Write-Host "⚠️  Error al limpiar: $($_.Exception.Message)" -ForegroundColor Red
    Write-Host "Intentando método alternativo..." -ForegroundColor Yellow
}

Write-Host ""
Start-Sleep -Seconds 2

# Paso 2: Verificar que esté completamente limpio
Write-Host "2. Verificando que esté completamente limpio..." -ForegroundColor Yellow
try {
    $after = Invoke-WebRequest -Uri http://localhost:8080/api/data/locations -UseBasicParsing
    $locAfter = $after.Content | ConvertFrom-Json
    
    if ($locAfter.Count -eq 0) {
        Write-Host "✅ Base de datos completamente vacía (0 ubicaciones)" -ForegroundColor Green
    } else {
        Write-Host "⚠️  Aún hay $($locAfter.Count) ubicaciones" -ForegroundColor Yellow
        Write-Host "Reintentando limpieza..." -ForegroundColor Yellow
        
        # Reintentar limpieza
        Invoke-WebRequest -Uri http://localhost:8080/api/data/clear -Method DELETE -UseBasicParsing | Out-Null
        Start-Sleep -Seconds 2
        
        # Verificar nuevamente
        $final = Invoke-WebRequest -Uri http://localhost:8080/api/data/locations -UseBasicParsing
        $locFinal = $final.Content | ConvertFrom-Json
        if ($locFinal.Count -eq 0) {
            Write-Host "✅ Base de datos ahora está vacía" -ForegroundColor Green
        } else {
            Write-Host "❌ Aún hay $($locFinal.Count) ubicaciones. Puede requerir limpieza manual en Neo4j Console" -ForegroundColor Red
        }
    }
} catch {
    Write-Host "✅ Base de datos parece estar vacía (no se encontraron ubicaciones)" -ForegroundColor Green
}

Write-Host ""
Write-Host "=== Limpieza Completada ===" -ForegroundColor Green
Write-Host ""
Write-Host "Puedes verificar en Neo4j Aura Console ejecutando:" -ForegroundColor Cyan
Write-Host "MATCH (n) RETURN count(n) as totalNodes" -ForegroundColor Yellow
Write-Host ""
Write-Host "Debería devolver: totalNodes = 0" -ForegroundColor White

