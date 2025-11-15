# Script para limpiar y recargar datos con relaciones
Write-Host "=== Recargando Datos en Neo4j ===" -ForegroundColor Cyan
Write-Host ""

# Paso 1: Limpiar datos existentes
Write-Host "1. Limpiando datos existentes..." -ForegroundColor Yellow
try {
    $clearResponse = Invoke-WebRequest -Uri http://localhost:8080/api/data/clear -Method DELETE -UseBasicParsing
    Write-Host "✅ Datos limpiados" -ForegroundColor Green
} catch {
    Write-Host "⚠️  Error al limpiar: $($_.Exception.Message)" -ForegroundColor Red
}

Write-Host ""

# Paso 2: Cargar datos nuevos con relaciones
Write-Host "2. Cargando datos con relaciones..." -ForegroundColor Yellow
try {
    $loadResponse = Invoke-WebRequest -Uri http://localhost:8080/api/data/load-sample -Method POST -UseBasicParsing
    $data = $loadResponse.Content | ConvertFrom-Json
    Write-Host "✅ Datos cargados exitosamente!" -ForegroundColor Green
    Write-Host ""
    Write-Host "Resumen:" -ForegroundColor Cyan
    Write-Host "  - Ubicaciones: $($data.totalLocations)" -ForegroundColor White
    Write-Host "  - Rutas: $($data.totalRoutes)" -ForegroundColor White
    Write-Host ""
    Write-Host "Ahora puedes visualizar las relaciones en Neo4j Aura Console" -ForegroundColor Green
    Write-Host "https://console-preview.neo4j.io/tools/explore" -ForegroundColor Yellow
} catch {
    Write-Host "❌ Error al cargar datos: $($_.Exception.Message)" -ForegroundColor Red
}

Write-Host ""
Write-Host "=== Proceso completado ===" -ForegroundColor Green

