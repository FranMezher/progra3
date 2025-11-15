# Resumen de Algoritmos Implementados

## ✅ Todos los Algoritmos Completados

### 1. **BFS y DFS** (2 puntos) ✅
- **BFS**: `GET /api/algorithms/bfs?start={location}&maxDistance={km}`
- **DFS**: `GET /api/algorithms/dfs?start={location}&end={location}`

### 2. **Dijkstra, Prim, Kruskal** (3 puntos) ✅
- **Dijkstra**: `GET /api/algorithms/dijkstra?start={location}&end={location}`
- **Prim**: `GET /api/algorithms/prim`
- **Kruskal**: `GET /api/algorithms/kruskal`

### 3. **Algoritmos Greedy** (1 punto) ✅
- **Greedy TSP**: `GET /api/algorithms/greedy/tsp?start={location}`

### 4. **Divide y Vencerás** (1 punto) ✅
- **Quicksort**: `GET /api/algorithms/divide-conquer/quicksort?sortBy={name|latitude|longitude}`
- **Mergesort**: `GET /api/algorithms/divide-conquer/mergesort?sortBy={name|latitude|longitude}`

### 5. **Programación Dinámica** (1 punto) ✅
- **Dynamic TSP**: `GET /api/algorithms/dynamic/tsp?start={location}`

### 6. **Backtracking** (1 punto) ✅
- **Backtracking Routes**: `GET /api/algorithms/backtracking/routes?start={location}&end={location}&maxDepth={n}`

### 7. **Branch & Bound** (1 punto) ✅
- **Branch & Bound**: `POST /api/algorithms/branch-bound/optimize`

---

## 📊 Puntos Totales: 10/10

---

## 🚀 Cómo Usar

### 1. Cargar Datos de Ejemplo
```bash
POST http://localhost:8080/api/data/load-sample
```

Esto carga 8 ciudades argentinas con rutas bidireccionales:
- Buenos Aires, Córdoba, Rosario, Mendoza, La Plata, Mar del Plata, Tucumán, Salta

### 2. Ver Documentación Completa
```bash
GET http://localhost:8080/api/algorithms/info
```

### 3. Probar Algoritmos

**Ejemplo BFS:**
```bash
GET http://localhost:8080/api/algorithms/bfs?start=Buenos Aires&maxDistance=500
```

**Ejemplo Dijkstra:**
```bash
GET http://localhost:8080/api/algorithms/dijkstra?start=Buenos Aires&end=Córdoba
```

**Ejemplo Prim:**
```bash
GET http://localhost:8080/api/algorithms/prim
```

**Ejemplo Greedy TSP:**
```bash
GET http://localhost:8080/api/algorithms/greedy/tsp?start=Buenos Aires
```

**Ejemplo Quicksort:**
```bash
GET http://localhost:8080/api/algorithms/divide-conquer/quicksort?sortBy=name
```

**Ejemplo Backtracking:**
```bash
GET http://localhost:8080/api/algorithms/backtracking/routes?start=Buenos Aires&end=Córdoba&maxDepth=10
```

**Ejemplo Branch & Bound:**
```bash
POST http://localhost:8080/api/algorithms/branch-bound/optimize
Content-Type: application/json

{
  "start": "Buenos Aires",
  "end": "Córdoba",
  "maxDistance": 800,
  "maxDuration": 600
}
```

---

## 📁 Estructura del Proyecto

```
src/main/java/com/example/demo/
├── model/
│   ├── Location.java          # Nodo: ubicación/ciudad
│   └── Route.java             # Relación: ruta entre ubicaciones
├── repository/
│   └── LocationRepository.java
├── service/
│   ├── BFSService.java
│   ├── DFSService.java
│   ├── DijkstraService.java
│   ├── PrimService.java
│   ├── KruskalService.java
│   ├── GreedyService.java
│   ├── DivideConquerService.java
│   ├── DynamicProgrammingService.java
│   ├── BacktrackingService.java
│   └── BranchBoundService.java
├── controller/
│   ├── AlgorithmController.java  # Todos los endpoints de algoritmos
│   └── DataController.java        # Carga de datos de ejemplo
└── dto/
    ├── RouteResponse.java
    └── BFSResponse.java
```

---

## ✅ Estado del Proyecto

- ✅ Conexión a Neo4j Aura configurada
- ✅ Modelos de datos creados (Location, Route)
- ✅ Todos los algoritmos implementados
- ✅ Endpoints REST documentados
- ✅ Datos de ejemplo listos
- ✅ Compilación exitosa

---

## 📝 Notas Importantes

1. **Dynamic TSP**: Limitado a ~15 ubicaciones por complejidad computacional
2. **Backtracking**: Usa `maxDepth` para limitar la profundidad de búsqueda
3. **Branch & Bound**: Acepta restricciones opcionales (maxDistance, maxDuration, maxCost)
4. **Datos**: Usa `POST /api/data/load-sample` para cargar datos de prueba

---

## 🎯 Listo para la Defensa

Todos los algoritmos están implementados, documentados y accesibles vía endpoints REST. El proyecto cumple con todos los requisitos del trabajo práctico.

