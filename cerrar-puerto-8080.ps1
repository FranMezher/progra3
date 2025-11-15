# Script para cerrar todos los procesos usando el puerto 8080
Write-Host "=== Cerrando procesos en el puerto 8080 ===" -ForegroundColor Cyan

# Buscar procesos que usan el puerto 8080
$processes = netstat -ano | findstr :8080

if ($processes) {
    Write-Host "Procesos encontrados en el puerto 8080:" -ForegroundColor Yellow
    $processes | ForEach-Object { Write-Host $_ -ForegroundColor White }
    
    # Extraer PIDs (última columna)
    $processIds = $processes | ForEach-Object {
        ($_ -split '\s+')[-1]
    } | Select-Object -Unique
    
    foreach ($processId in $processIds) {
        try {
            $proc = Get-Process -Id $processId -ErrorAction SilentlyContinue
            if ($proc) {
                Write-Host "Cerrando proceso PID $processId ($($proc.ProcessName))..." -ForegroundColor Yellow
                Stop-Process -Id $processId -Force -ErrorAction SilentlyContinue
                Write-Host "✅ Proceso $processId cerrado" -ForegroundColor Green
            }
        } catch {
            Write-Host "⚠️  No se pudo cerrar el proceso $processId" -ForegroundColor Red
        }
    }
    
    Write-Host ""
    Write-Host "✅ Procesos cerrados. El puerto 8080 debería estar libre ahora." -ForegroundColor Green
} else {
    Write-Host "No se encontraron procesos usando el puerto 8080" -ForegroundColor Yellow
}

Write-Host ""
Write-Host "Verificando puerto 8080..." -ForegroundColor Cyan
Start-Sleep -Seconds 2
$check = netstat -ano | findstr :8080
if ($check) {
    Write-Host "⚠️  Aún hay procesos usando el puerto 8080" -ForegroundColor Red
} else {
    Write-Host "✅ El puerto 8080 está libre" -ForegroundColor Green
}

