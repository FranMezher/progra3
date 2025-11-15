# Script para probar todos los algoritmos y documentar resultados
Write-Host "=== Prueba de Algoritmos ===" -ForegroundColor Cyan
Write-Host ""

$results = @()

# 1. BFS
Write-Host "1. Probando BFS..." -ForegroundColor Yellow
try {
    $bfs = Invoke-WebRequest -Uri "http://localhost:8080/api/algorithms/bfs?start=Buenos Aires&maxDistance=500" -UseBasicParsing
    $bfsData = $bfs.Content | ConvertFrom-Json
    Write-Host "✅ BFS completado" -ForegroundColor Green
    Write-Host "  - Ubicaciones encontradas: $($bfsData.reachableLocations.Count)" -ForegroundColor White
    Write-Host "  - Ubicaciones: $($bfsData.reachableLocations -join ', ')" -ForegroundColor Cyan
    $results += [PSCustomObject]@{Algoritmo="BFS"; Estado="OK"; Resultado="$($bfsData.reachableLocations.Count) ubicaciones encontradas"}
} catch {
    Write-Host "❌ Error: $($_.Exception.Message)" -ForegroundColor Red
    $results += [PSCustomObject]@{Algoritmo="BFS"; Estado="ERROR"; Resultado=$_.Exception.Message}
}

Write-Host ""

# 2. DFS
Write-Host "2. Probando DFS..." -ForegroundColor Yellow
try {
    $dfs = Invoke-WebRequest -Uri "http://localhost:8080/api/algorithms/dfs?start=Buenos Aires&end=Cordoba" -UseBasicParsing
    $dfsData = $dfs.Content | ConvertFrom-Json
    Write-Host "✅ DFS completado" -ForegroundColor Green
    if ($dfsData.path) {
        Write-Host "  - Ruta: $($dfsData.path -join ' -> ')" -ForegroundColor White
        Write-Host "  - Distancia: $($dfsData.totalDistance) km" -ForegroundColor White
        $results += [PSCustomObject]@{Algoritmo="DFS"; Estado="OK"; Resultado="Ruta encontrada: $($dfsData.path.Count) pasos"}
    } else {
        Write-Host "  - Mensaje: $($dfsData.message)" -ForegroundColor Yellow
        $results += [PSCustomObject]@{Algoritmo="DFS"; Estado="OK"; Resultado=$dfsData.message}
    }
} catch {
    Write-Host "❌ Error: $($_.Exception.Message)" -ForegroundColor Red
    $results += [PSCustomObject]@{Algoritmo="DFS"; Estado="ERROR"; Resultado=$_.Exception.Message}
}

Write-Host ""

# 3. Dijkstra
Write-Host "3. Probando Dijkstra..." -ForegroundColor Yellow
try {
    $dijkstra = Invoke-WebRequest -Uri "http://localhost:8080/api/algorithms/dijkstra?start=Buenos Aires&end=Cordoba" -UseBasicParsing
    $dijkData = $dijkstra.Content | ConvertFrom-Json
    Write-Host "✅ Dijkstra completado" -ForegroundColor Green
    if ($dijkData.path) {
        Write-Host "  - Ruta más corta: $($dijkData.path -join ' -> ')" -ForegroundColor White
        Write-Host "  - Distancia: $($dijkData.totalDistance) km" -ForegroundColor White
        Write-Host "  - Duración: $($dijkData.totalDuration) minutos" -ForegroundColor White
        $results += [PSCustomObject]@{Algoritmo="Dijkstra"; Estado="OK"; Resultado="Ruta: $($dijkData.totalDistance) km"}
    } else {
        Write-Host "  - Mensaje: $($dijkData.message)" -ForegroundColor Yellow
        $results += [PSCustomObject]@{Algoritmo="Dijkstra"; Estado="OK"; Resultado=$dijkData.message}
    }
} catch {
    Write-Host "❌ Error: $($_.Exception.Message)" -ForegroundColor Red
    $results += [PSCustomObject]@{Algoritmo="Dijkstra"; Estado="ERROR"; Resultado=$_.Exception.Message}
}

Write-Host ""

# 4. Prim
Write-Host "4. Probando Prim..." -ForegroundColor Yellow
try {
    $prim = Invoke-WebRequest -Uri "http://localhost:8080/api/algorithms/prim" -UseBasicParsing
    $primData = $prim.Content | ConvertFrom-Json
    Write-Host "✅ Prim completado" -ForegroundColor Green
    Write-Host "  - Distancia total: $($primData.totalDistance) km" -ForegroundColor White
    Write-Host "  - Rutas: $($primData.path.Count)" -ForegroundColor White
    $results += [PSCustomObject]@{Algoritmo="Prim"; Estado="OK"; Resultado="MST: $($primData.totalDistance) km"}
} catch {
    Write-Host "❌ Error: $($_.Exception.Message)" -ForegroundColor Red
    $results += [PSCustomObject]@{Algoritmo="Prim"; Estado="ERROR"; Resultado=$_.Exception.Message}
}

Write-Host ""

# 5. Kruskal
Write-Host "5. Probando Kruskal..." -ForegroundColor Yellow
try {
    $kruskal = Invoke-WebRequest -Uri "http://localhost:8080/api/algorithms/kruskal" -UseBasicParsing
    $kruskalData = $kruskal.Content | ConvertFrom-Json
    Write-Host "✅ Kruskal completado" -ForegroundColor Green
    Write-Host "  - Distancia total: $($kruskalData.totalDistance) km" -ForegroundColor White
    $results += [PSCustomObject]@{Algoritmo="Kruskal"; Estado="OK"; Resultado="MST: $($kruskalData.totalDistance) km"}
} catch {
    Write-Host "❌ Error: $($_.Exception.Message)" -ForegroundColor Red
    $results += [PSCustomObject]@{Algoritmo="Kruskal"; Estado="ERROR"; Resultado=$_.Exception.Message}
}

Write-Host ""

# 6. Greedy TSP
Write-Host "6. Probando Greedy TSP..." -ForegroundColor Yellow
try {
    $greedy = Invoke-WebRequest -Uri "http://localhost:8080/api/algorithms/greedy/tsp?start=Buenos Aires" -UseBasicParsing
    $greedyData = $greedy.Content | ConvertFrom-Json
    Write-Host "✅ Greedy TSP completado" -ForegroundColor Green
    if ($greedyData.path) {
        Write-Host "  - Recorrido: $($greedyData.path -join ' -> ')" -ForegroundColor White
        Write-Host "  - Distancia total: $($greedyData.totalDistance) km" -ForegroundColor White
        $results += [PSCustomObject]@{Algoritmo="Greedy TSP"; Estado="OK"; Resultado="Recorrido: $($greedyData.totalDistance) km"}
    }
} catch {
    Write-Host "❌ Error: $($_.Exception.Message)" -ForegroundColor Red
    $results += [PSCustomObject]@{Algoritmo="Greedy TSP"; Estado="ERROR"; Resultado=$_.Exception.Message}
}

Write-Host ""
Write-Host "=== Resumen ===" -ForegroundColor Cyan
$results | Format-Table -AutoSize

