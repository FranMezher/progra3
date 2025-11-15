# Script simple para probar Kruskal
Write-Host "=== Probando Kruskal ===" -ForegroundColor Cyan
Write-Host ""

try {
    Write-Host "Llamando al endpoint..." -ForegroundColor Yellow
    $response = Invoke-WebRequest -Uri "http://localhost:8080/api/algorithms/kruskal" -UseBasicParsing
    $data = $response.Content | ConvertFrom-Json
    
    Write-Host "✅ Respuesta recibida" -ForegroundColor Green
    Write-Host ""
    Write-Host "Algoritmo: $($data.algorithm)" -ForegroundColor White
    Write-Host "Mensaje: $($data.message)" -ForegroundColor Cyan
    Write-Host "Distancia total: $($data.totalDistance) km" -ForegroundColor Yellow
    Write-Host "Rutas en MST: $($data.path.Count)" -ForegroundColor White
    
    if ($data.path.Count -gt 0) {
        Write-Host ""
        Write-Host "Rutas del MST:" -ForegroundColor Cyan
        $data.path | ForEach-Object { Write-Host "  - $_" -ForegroundColor Gray }
    }
} catch {
    Write-Host "❌ Error: $($_.Exception.Message)" -ForegroundColor Red
}

