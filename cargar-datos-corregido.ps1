# Script corregido para cargar datos (8 ubicaciones + relaciones)
Write-Host "=== Carga de Datos Corregida ===" -ForegroundColor Cyan
Write-Host ""

# Verificar conexión
Write-Host "Verificando conexión..." -ForegroundColor Yellow
try {
    $health = Invoke-WebRequest -Uri http://localhost:8080/api/test/health -UseBasicParsing
    Write-Host "✅ Aplicación conectada" -ForegroundColor Green
} catch {
    Write-Host "❌ Error: La aplicación no está corriendo" -ForegroundColor Red
    exit 1
}

Write-Host ""

# Limpiar primero
Write-Host "1. Limpiando base de datos..." -ForegroundColor Yellow
try {
    $clear = Invoke-WebRequest -Uri http://localhost:8080/api/data/clear -Method DELETE -UseBasicParsing
    Write-Host "✅ Base de datos limpiada" -ForegroundColor Green
    Start-Sleep -Seconds 2
} catch {
    Write-Host "⚠️  Error al limpiar: $($_.Exception.Message)" -ForegroundColor Yellow
}

Write-Host ""

# Cargar datos
Write-Host "2. Cargando 8 ubicaciones con relaciones..." -ForegroundColor Yellow
try {
    $load = Invoke-WebRequest -Uri http://localhost:8080/api/data/load-sample -Method POST -UseBasicParsing
    $data = $load.Content | ConvertFrom-Json
    
    if ($data.status -eq "success") {
        Write-Host "✅ Datos cargados!" -ForegroundColor Green
        Write-Host "  - Ubicaciones: $($data.totalLocations)" -ForegroundColor White
        Write-Host "  - Rutas: $($data.totalRoutes)" -ForegroundColor White
    }
} catch {
    Write-Host "❌ Error: $($_.Exception.Message)" -ForegroundColor Red
    exit 1
}

Write-Host ""
Start-Sleep -Seconds 2

# Verificar resultado
Write-Host "3. Verificando resultado..." -ForegroundColor Yellow
try {
    $status = Invoke-WebRequest -Uri http://localhost:8080/api/data/status -UseBasicParsing
    $final = $status.Content | ConvertFrom-Json
    
    Write-Host ""
    Write-Host "Resultado final:" -ForegroundColor Cyan
    Write-Host "  - Total ubicaciones: $($final.totalLocations)" -ForegroundColor $(if ($final.totalLocations -eq 8) { "Green" } else { "Red" })
    Write-Host "  - Total relaciones: $($final.totalRelationships)" -ForegroundColor $(if ($final.totalRelationships -eq 20) { "Green" } else { "Yellow" })
    
    if ($final.totalLocations -eq 8 -and $final.totalRelationships -eq 20) {
        Write-Host ""
        Write-Host "✅ ¡Perfecto! Tienes exactamente 8 ubicaciones con 20 relaciones" -ForegroundColor Green
    } else {
        Write-Host ""
        Write-Host "⚠️  Hay un problema. Deberías tener 8 ubicaciones y 20 relaciones" -ForegroundColor Yellow
    }
} catch {
    Write-Host "⚠️  No se pudo verificar el estado" -ForegroundColor Yellow
}

Write-Host ""
Write-Host "=== Proceso completado ===" -ForegroundColor Green

