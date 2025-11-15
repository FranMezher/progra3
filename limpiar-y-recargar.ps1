# Script para limpiar completamente y recargar solo 8 ubicaciones con relaciones
Write-Host "=== Limpieza Completa y Recarga de Datos ===" -ForegroundColor Cyan
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

# Paso 1: Limpiar TODOS los datos
Write-Host "1. Limpiando TODOS los datos (nodos y relaciones)..." -ForegroundColor Yellow
try {
    $clearResponse = Invoke-WebRequest -Uri http://localhost:8080/api/data/clear -Method DELETE -UseBasicParsing
    $clearData = $clearResponse.Content | ConvertFrom-Json
    Write-Host "✅ $($clearData.message)" -ForegroundColor Green
} catch {
    Write-Host "⚠️  Error al limpiar: $($_.Exception.Message)" -ForegroundColor Red
}

Write-Host ""
Start-Sleep -Seconds 2

# Paso 2: Verificar que esté limpio
Write-Host "2. Verificando que esté limpio..." -ForegroundColor Yellow
try {
    $locations = Invoke-WebRequest -Uri http://localhost:8080/api/data/locations -UseBasicParsing
    $locData = $locations.Content | ConvertFrom-Json
    if ($locData.Count -eq 0) {
        Write-Host "✅ Base de datos limpia (0 ubicaciones)" -ForegroundColor Green
    } else {
        Write-Host "⚠️  Aún hay $($locData.Count) ubicaciones. Reintentando limpieza..." -ForegroundColor Yellow
        Invoke-WebRequest -Uri http://localhost:8080/api/data/clear -Method DELETE -UseBasicParsing | Out-Null
        Start-Sleep -Seconds 2
    }
} catch {
    Write-Host "✅ Base de datos limpia" -ForegroundColor Green
}

Write-Host ""

# Paso 3: Cargar datos nuevos (solo 8 ubicaciones con relaciones)
Write-Host "3. Cargando 8 ubicaciones con relaciones..." -ForegroundColor Yellow
try {
    $loadResponse = Invoke-WebRequest -Uri http://localhost:8080/api/data/load-sample -Method POST -UseBasicParsing
    $data = $loadResponse.Content | ConvertFrom-Json
    
    if ($data.status -eq "success") {
        Write-Host "✅ Datos cargados exitosamente!" -ForegroundColor Green
        Write-Host ""
        Write-Host "Resumen:" -ForegroundColor Cyan
        Write-Host "  - Ubicaciones: $($data.totalLocations)" -ForegroundColor White
        Write-Host "  - Rutas: $($data.totalRoutes)" -ForegroundColor White
        Write-Host ""
        Write-Host "Ubicaciones creadas:" -ForegroundColor Cyan
        $data.locations | ForEach-Object { Write-Host "  • $_" -ForegroundColor White }
        Write-Host ""
        Write-Host "✅ Ahora tienes exactamente 8 ubicaciones con sus relaciones" -ForegroundColor Green
        Write-Host ""
        Write-Host "Visualiza en Neo4j Aura Console:" -ForegroundColor Cyan
        Write-Host "https://console-preview.neo4j.io/tools/explore" -ForegroundColor Yellow
    } else {
        Write-Host "❌ Error: $($data.message)" -ForegroundColor Red
    }
} catch {
    Write-Host "❌ Error al cargar datos: $($_.Exception.Message)" -ForegroundColor Red
    Write-Host "Detalles: $($_.Exception)" -ForegroundColor Red
}

Write-Host ""
Write-Host "=== Proceso completado ===" -ForegroundColor Green

