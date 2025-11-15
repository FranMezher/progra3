# Ejemplos Completos de Entrada/Salida - API de Algoritmos

## 📋 Requisito Cumplido

> "Cada algoritmo implementado debe estar accesible a través de un endpoint en el backend y documentado con ejemplos de entrada/salida"

---

## 1. BFS (Breadth First Search)

### Endpoint
```
GET /api/algorithms/bfs?start={ubicacion}&maxDistance={km}
```

### Ejemplo de Entrada
```
GET /api/algorithms/bfs?start=Buenos Aires&maxDistance=500
```

### Ejemplo de Salida
```json
{
  "algorithm": "BFS",
  "startLocation": "Buenos Aires",
  "maxDistance": 500,
  "reachableLocations": [
    "Rosario",
    "La Plata",
    "Mar del Plata"
  ],
  "message": "BFS completado. 3 ubicaciones encontradas dentro del rango de 500 km"
}
```

### Explicación
Encuentra todas las ubicaciones alcanzables desde Buenos Aires hasta 500 km de distancia, explorando por niveles. Encontró 3 ubicaciones: Rosario (300 km), La Plata (60 km) y Mar del Plata (400 km).

---

## 2. DFS (Depth First Search)

### Endpoint
```
GET /api/algorithms/dfs?start={ubicacion}&end={ubicacion}
```

### Ejemplo de Entrada
```
GET /api/algorithms/dfs?start=Buenos Aires&end=Cordoba
```

### Ejemplo de Salida
```json
{
  "algorithm": "DFS",
  "path": [
    "Buenos Aires",
    "Cordoba"
  ],
  "totalDistance": 700.0,
  "totalDuration": 480,
  "totalCost": 70.0,
  "message": "DFS encontró 1 rutas posibles. Mostrando la más corta."
}
```

### Explicación
Explora todas las rutas posibles entre Buenos Aires y Cordoba en profundidad. Encontró la ruta directa de 700 km.

---

## 3. Dijkstra

### Endpoint
```
GET /api/algorithms/dijkstra?start={ubicacion}&end={ubicacion}
```

### Ejemplo de Entrada
```
GET /api/algorithms/dijkstra?start=Buenos Aires&end=Cordoba
```

### Ejemplo de Salida
```json
{
  "algorithm": "Dijkstra",
  "path": [
    "Buenos Aires",
    "Cordoba"
  ],
  "totalDistance": 700.0,
  "totalDuration": 480,
  "totalCost": 70.0,
  "message": "Ruta más corta encontrada usando Dijkstra"
}
```

### Explicación
Encuentra la ruta más corta entre dos ubicaciones considerando la distancia como peso. En este caso, la ruta directa es la más corta (700 km).

---

## 4. Prim - Árbol de Expansión Mínima

### Endpoint
```
GET /api/algorithms/prim
```

### Ejemplo de Entrada
```
GET /api/algorithms/prim
```

### Ejemplo de Salida
```json
{
  "algorithm": "Prim",
  "path": [
    "Buenos Aires -> La Plata",
    "Buenos Aires -> Rosario",
    "Rosario -> Cordoba",
    "Cordoba -> Mendoza",
    "Cordoba -> Tucuman",
    "Tucuman -> Salta",
    "Buenos Aires -> Mar del Plata"
  ],
  "totalDistance": 2260.0,
  "totalDuration": 2460,
  "totalCost": 226.0,
  "message": "Árbol de expansión mínima encontrado. Conecta 8 ubicaciones."
}
```

### Explicación
Encuentra la red de conexiones mínima para conectar todas las 8 ubicaciones con el menor costo total (2260 km). Conecta todas las ciudades con 7 rutas (n-1 aristas para un árbol).

---

## 5. Kruskal - Árbol de Expansión Mínima

### Endpoint
```
GET /api/algorithms/kruskal
```

### Ejemplo de Entrada
```
GET /api/algorithms/kruskal
```

### Ejemplo de Salida
```json
{
  "algorithm": "Kruskal",
  "path": [
    "Buenos Aires -> La Plata",
    "Tucuman -> Salta",
    "Buenos Aires -> Rosario",
    "Cordoba -> Mendoza",
    "Rosario -> Cordoba",
    "Buenos Aires -> Mar del Plata",
    "Cordoba -> Tucuman"
  ],
  "totalDistance": 2260.0,
  "totalDuration": 2460,
  "totalCost": 226.0,
  "message": "Árbol de expansión mínima encontrado usando Kruskal. 10 aristas procesadas, 7 aristas en MST."
}
```

### Explicación
Similar a Prim, pero usando Union-Find. Debería dar el mismo resultado (2260 km) ya que ambos encuentran el mismo MST.

---

## 6. Greedy TSP

### Endpoint
```
GET /api/algorithms/greedy/tsp?start={ubicacion}
```

### Ejemplo de Entrada
```
GET /api/algorithms/greedy/tsp?start=Buenos Aires
```

### Ejemplo de Salida
```json
{
  "algorithm": "Greedy TSP",
  "path": [
    "Buenos Aires",
    "La Plata",
    "Mar del Plata",
    "Rosario",
    "Cordoba",
    "Mendoza",
    "Tucuman",
    "Salta",
    "Buenos Aires"
  ],
  "totalDistance": 2860.0,
  "totalDuration": 3420,
  "totalCost": 286.0,
  "message": "Recorrido greedy completado. Visitadas 8 ubicaciones."
}
```

### Explicación
Problema del Viajante con aproximación greedy. Visita todas las ciudades empezando desde Buenos Aires, siempre eligiendo la ciudad más cercana no visitada. Al final intenta volver al inicio.

---

## 7. Quicksort (Divide y Vencerás)

### Endpoint
```
GET /api/algorithms/divide-conquer/quicksort?sortBy={name|latitude|longitude}
```

### Ejemplo de Entrada
```
GET /api/algorithms/divide-conquer/quicksort?sortBy=name
```

### Ejemplo de Salida
```json
[
  {
    "id": null,
    "name": "Buenos Aires",
    "latitude": -34.6037,
    "longitude": -58.3816,
    "type": "city"
  },
  {
    "id": null,
    "name": "Cordoba",
    "latitude": -31.4201,
    "longitude": -64.1888,
    "type": "city"
  },
  {
    "id": null,
    "name": "La Plata",
    "latitude": -34.9215,
    "longitude": -57.9545,
    "type": "city"
  },
  ...
]
```

### Explicación
Ordena las 8 ubicaciones alfabéticamente usando el algoritmo Quicksort (Divide y Vencerás).

---

## 8. Mergesort (Divide y Vencerás)

### Endpoint
```
GET /api/algorithms/divide-conquer/mergesort?sortBy={name|latitude|longitude}
```

### Ejemplo de Entrada
```
GET /api/algorithms/divide-conquer/mergesort?sortBy=latitude
```

### Ejemplo de Salida
```json
[
  {
    "id": null,
    "name": "Salta",
    "latitude": -24.7859,
    "longitude": -65.4117,
    "type": "city"
  },
  {
    "id": null,
    "name": "Tucuman",
    "latitude": -26.8083,
    "longitude": -65.2176,
    "type": "city"
  },
  ...
]
```

### Explicación
Ordena las ubicaciones por latitud (de norte a sur) usando el algoritmo Mergesort (Divide y Vencerás).

---

## 9. Programación Dinámica TSP

### Endpoint
```
GET /api/algorithms/dynamic/tsp?start={ubicacion}
```

### Ejemplo de Entrada
```
GET /api/algorithms/dynamic/tsp?start=Buenos Aires
```

### Ejemplo de Salida
```json
{
  "algorithm": "Dynamic Programming TSP",
  "path": [
    "Buenos Aires",
    "La Plata",
    "Mar del Plata",
    "Rosario",
    "Cordoba",
    "Mendoza",
    "Tucuman",
    "Salta",
    "Buenos Aires"
  ],
  "totalDistance": 2800.0,
  "message": "TSP resuelto con programación dinámica. Costo mínimo: 2800.00"
}
```

### Explicación
Resuelve el Problema del Viajante de forma óptima usando memoización. Limitado a ~15 ubicaciones por complejidad. Encuentra la ruta óptima (mejor que greedy).

---

## 10. Backtracking

### Endpoint
```
GET /api/algorithms/backtracking/routes?start={ubicacion}&end={ubicacion}&maxDepth={n}
```

### Ejemplo de Entrada
```
GET /api/algorithms/backtracking/routes?start=Buenos Aires&end=Cordoba&maxDepth=5
```

### Ejemplo de Salida
```json
{
  "algorithm": "Backtracking",
  "path": [
    "Buenos Aires",
    "Cordoba"
  ],
  "totalDistance": 700.0,
  "message": "Backtracking encontró 1 rutas posibles. Mostrando la más corta."
}
```

### Explicación
Encuentra todas las rutas posibles entre dos puntos usando backtracking sistemático. Explora todas las posibilidades y retrocede cuando una ruta no lleva al destino.

---

## 11. Branch & Bound

### Endpoint
```
POST /api/algorithms/branch-bound/optimize
```

### Ejemplo de Entrada
```json
{
  "start": "Buenos Aires",
  "end": "Cordoba",
  "maxDistance": 800,
  "maxDuration": 600,
  "maxCost": 100
}
```

### Ejemplo de Salida
```json
{
  "algorithm": "Branch & Bound",
  "path": [
    "Buenos Aires",
    "Cordoba"
  ],
  "totalDistance": 700.0,
  "totalDuration": 480,
  "totalCost": 70.0,
  "message": "Ruta óptima encontrada con restricciones aplicadas"
}
```

### Explicación
Encuentra la mejor ruta considerando restricciones (distancia máxima, tiempo máximo, costo máximo). Poda ramas que excedan los límites o que sean peores que la mejor solución conocida.

---

## 📝 Notas sobre los Ejemplos

- Todos los ejemplos usan los datos cargados: 8 ubicaciones argentinas
- Las distancias están en kilómetros
- Las duraciones están en minutos
- Los costos son aproximados
- Los IDs pueden ser null (por eso usamos nombres para comparar)

---

## 🧪 Cómo Probar

### Desde PowerShell:
```powershell
# BFS
Invoke-WebRequest -Uri "http://localhost:8080/api/algorithms/bfs?start=Buenos Aires&maxDistance=500" -UseBasicParsing

# DFS
Invoke-WebRequest -Uri "http://localhost:8080/api/algorithms/dfs?start=Buenos Aires&end=Cordoba" -UseBasicParsing

# Dijkstra
Invoke-WebRequest -Uri "http://localhost:8080/api/algorithms/dijkstra?start=Buenos Aires&end=Cordoba" -UseBasicParsing

# Prim
Invoke-WebRequest -Uri "http://localhost:8080/api/algorithms/prim" -UseBasicParsing

# Kruskal
Invoke-WebRequest -Uri "http://localhost:8080/api/algorithms/kruskal" -UseBasicParsing

# Greedy TSP
Invoke-WebRequest -Uri "http://localhost:8080/api/algorithms/greedy/tsp?start=Buenos Aires" -UseBasicParsing

# Quicksort
Invoke-WebRequest -Uri "http://localhost:8080/api/algorithms/divide-conquer/quicksort?sortBy=name" -UseBasicParsing

# Mergesort
Invoke-WebRequest -Uri "http://localhost:8080/api/algorithms/divide-conquer/mergesort?sortBy=latitude" -UseBasicParsing

# Dynamic TSP
Invoke-WebRequest -Uri "http://localhost:8080/api/algorithms/dynamic/tsp?start=Buenos Aires" -UseBasicParsing

# Backtracking
Invoke-WebRequest -Uri "http://localhost:8080/api/algorithms/backtracking/routes?start=Buenos Aires&end=Cordoba&maxDepth=5" -UseBasicParsing

# Branch & Bound
$body = @{
    start = "Buenos Aires"
    end = "Cordoba"
    maxDistance = 800
    maxDuration = 600
} | ConvertTo-Json
Invoke-WebRequest -Uri "http://localhost:8080/api/algorithms/branch-bound/optimize" -Method POST -Body $body -ContentType "application/json" -UseBasicParsing
```

### Desde el Navegador:
Simplemente abre los endpoints GET en tu navegador:
- `http://localhost:8080/api/algorithms/bfs?start=Buenos Aires&maxDistance=500`
- `http://localhost:8080/api/algorithms/prim`
- etc.

---

## 📚 Documentación Adicional

Ver `DOCUMENTACION_ALGORITMOS.md` para más detalles sobre cómo funciona cada algoritmo.

