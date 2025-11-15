# Diseño del Sistema de Navegación y Rutas

## Concepto: Sistema de Navegación Urbana (estilo Google Maps)

### Dominio de Aplicación
Sistema de gestión de rutas y navegación urbana que permite:
- Buscar rutas entre ubicaciones (ciudades, puntos de interés)
- Calcular rutas óptimas considerando distancia, tiempo, costo
- Encontrar conexiones mínimas entre ubicaciones
- Optimizar recorridos y rutas múltiples

---

## Mapeo de Algoritmos a Funcionalidades

### 1. **BFS y DFS** (2 puntos)
**Aplicación:**
- **BFS**: Encontrar todas las ubicaciones a una distancia X de un punto (exploración por niveles)
- **DFS**: Explorar todas las rutas posibles desde un origen hasta un destino (exploración profunda)

**Endpoints:**
- `GET /api/algorithms/bfs?start={location}&maxDistance={km}` - Encontrar ubicaciones cercanas
- `GET /api/algorithms/dfs?start={location}&end={location}` - Explorar todas las rutas posibles

---

### 2. **Dijkstra, Prim, Kruskal** (3 puntos)
**Aplicación:**
- **Dijkstra**: Ruta más corta entre dos puntos considerando distancia/tiempo
- **Prim**: Red de conexiones mínima para conectar todas las ubicaciones (árbol de expansión mínima)
- **Kruskal**: Similar a Prim, pero útil para redes de transporte

**Endpoints:**
- `GET /api/algorithms/dijkstra?start={location}&end={location}` - Ruta más corta
- `GET /api/algorithms/prim` - Red de conexiones mínima
- `GET /api/algorithms/kruskal` - Árbol de expansión mínima alternativo

---

### 3. **Algoritmos Greedy** (1 punto)
**Aplicación:**
- Problema del viajante (TSP) con aproximación greedy
- Seleccionar la siguiente ubicación más cercana en un recorrido

**Endpoints:**
- `POST /api/algorithms/greedy/tsp` - Recorrido greedy para visitar múltiples ubicaciones

---

### 4. **Divide y Vencerás** (1 punto)
**Aplicación:**
- **Quicksort/Mergesort**: Ordenar ubicaciones por distancia, nombre, o coordenadas
- Dividir el mapa en regiones para búsquedas más eficientes

**Endpoints:**
- `POST /api/algorithms/divide-conquer/sort` - Ordenar ubicaciones (quicksort/mergesort)

---

### 5. **Programación Dinámica** (1 punto)
**Aplicación:**
- Problema del viajante (TSP) con programación dinámica
- Optimización de rutas múltiples con memoización

**Endpoints:**
- `POST /api/algorithms/dynamic/tsp` - TSP con programación dinámica

---

### 6. **Backtracking** (1 punto)
**Aplicación:**
- Encontrar todas las rutas posibles entre dos puntos
- Problema de las N reinas adaptado (N ubicaciones sin conflictos)

**Endpoints:**
- `GET /api/algorithms/backtracking/routes?start={location}&end={location}` - Todas las rutas posibles

---

### 7. **Branch & Bound** (1 punto)
**Aplicación:**
- Optimización de rutas con restricciones (tiempo máximo, costo máximo)
- TSP con límites y poda de ramas

**Endpoints:**
- `POST /api/algorithms/branch-bound/optimize` - Ruta óptima con restricciones

---

## Modelo de Datos en Neo4j

### Entidades:
1. **Location** (Nodo)
   - id, name, latitude, longitude, type (city, poi, etc.)

2. **Route** (Relación)
   - distance (km), duration (minutes), cost, roadType

### Relaciones:
- `(Location)-[:CONNECTED_TO {distance, duration, cost}]->(Location)`

---

## Estructura del Proyecto

```
src/main/java/com/example/demo/
├── model/
│   ├── Location.java          # Nodo: ubicación/ciudad
│   └── Route.java             # Relación: ruta entre ubicaciones
├── repository/
│   └── LocationRepository.java
├── service/
│   ├── GraphAlgorithmService.java
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
│   └── AlgorithmController.java  # Endpoints para todos los algoritmos
└── dto/
    └── RouteResponse.java         # DTOs para respuestas
```

---

## Ejemplo de Uso

### Caso de Uso: Sistema de Navegación
1. Usuario ingresa origen y destino
2. Sistema calcula rutas usando diferentes algoritmos
3. Compara resultados (distancia, tiempo, costo)
4. Muestra la mejor opción según criterio

### Datos de Ejemplo:
- Ciudades: Buenos Aires, Córdoba, Rosario, Mendoza, La Plata
- Rutas con distancias y tiempos reales
- Permite comparar algoritmos en el mismo dataset

---

## Ventajas de este Enfoque

✅ **Todos los algoritmos tienen aplicación práctica**
✅ **Fácil de visualizar y entender**
✅ **Permite comparar algoritmos** (ej: Dijkstra vs BFS)
✅ **Escalable** - se pueden agregar más ubicaciones
✅ **Realista** - similar a aplicaciones reales
✅ **Documentable** - cada algoritmo tiene un caso de uso claro

