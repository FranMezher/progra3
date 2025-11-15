# Script para cargar datos de ejemplo en Neo4j
# Ejecutar cuando la aplicación Spring Boot esté corriendo

Write-Host "=== Cargando Datos de Ejemplo en Neo4j ===" -ForegroundColor Cyan
Write-Host ""

# Verificar que la aplicación esté corriendo
Write-Host "Verificando conexión..." -ForegroundColor Yellow
try {
    $health = Invoke-WebRequest -Uri http://localhost:8080/api/test/health -UseBasicParsing
    Write-Host "✅ Aplicación conectada a Neo4j" -ForegroundColor Green
    Write-Host $health.Content
    Write-Host ""
} catch {
    Write-Host "❌ Error: La aplicación no está corriendo o no puede conectarse a Neo4j" -ForegroundColor Red
    Write-Host "Asegúrate de que la aplicación Spring Boot esté iniciada" -ForegroundColor Yellow
    exit 1
}

# Cargar datos de ejemplo
Write-Host "Cargando datos de ejemplo..." -ForegroundColor Yellow
try {
    $response = Invoke-WebRequest -Uri http://localhost:8080/api/data/load-sample -Method POST -UseBasicParsing
    Write-Host "✅ Datos cargados exitosamente!" -ForegroundColor Green
    Write-Host ""
    Write-Host "Respuesta del servidor:" -ForegroundColor Cyan
    $response.Content | ConvertFrom-Json | Format-List
    Write-Host ""
    Write-Host "=== Datos cargados ===" -ForegroundColor Green
    Write-Host "Ahora puedes visualizarlos en Neo4j Aura Console:" -ForegroundColor Cyan
    Write-Host "https://console-preview.neo4j.io/tools/explore" -ForegroundColor Yellow
} catch {
    Write-Host "❌ Error al cargar datos: $($_.Exception.Message)" -ForegroundColor Red
    Write-Host "Detalles: $($_.Exception)" -ForegroundColor Red
    exit 1
}

# Verificar datos cargados
Write-Host ""
Write-Host "Verificando datos cargados..." -ForegroundColor Yellow
try {
    $locations = Invoke-WebRequest -Uri http://localhost:8080/api/data/locations -UseBasicParsing
    $data = $locations.Content | ConvertFrom-Json
    Write-Host "✅ Se encontraron $($data.Count) ubicaciones" -ForegroundColor Green
    Write-Host ""
    Write-Host "Ubicaciones cargadas:" -ForegroundColor Cyan
    $data | ForEach-Object { Write-Host "  - $($_.name)" -ForegroundColor White }
} catch {
    Write-Host "⚠️  No se pudieron verificar las ubicaciones" -ForegroundColor Yellow
}

Write-Host ""
Write-Host "=== Proceso completado ===" -ForegroundColor Green

