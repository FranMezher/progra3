# Documentación de Algoritmos - Sistema de Navegación Urbana

## Datos de Prueba Cargados

**8 Ubicaciones (sin tildes para evitar problemas de encoding):**
- Buenos Aires
- Cordoba
- Rosario
- Mendoza
- La Plata
- Mar del Plata
- Tucuman
- Salta

**20 Relaciones Bidireccionales (10 rutas × 2 direcciones):**
- Buenos Aires ↔ Cordoba (700 km)
- Buenos Aires ↔ Rosario (300 km)
- Buenos Aires ↔ La Plata (60 km)
- Buenos Aires ↔ Mar del Plata (400 km)
- Rosario ↔ Cordoba (400 km)
- Cordoba ↔ Mendoza (400 km)
- Cordoba ↔ Tucuman (500 km)
- Tucuman ↔ Salta (200 km)
- La Plata ↔ Mar del Plata (400 km)
- Rosario ↔ La Plata (350 km)

---

## 1. BFS (Breadth First Search) - 2 puntos

### ¿Qué hace?
Explora el grafo por niveles, encontrando todas las ubicaciones alcanzables desde un punto de inicio hasta una distancia máxima.

### Cómo funciona:
1. Comienza desde una ubicación inicial
2. Explora todos los vecinos directos (nivel 1)
3. Luego explora los vecinos de esos vecinos (nivel 2)
4. Continúa hasta alcanzar la distancia máxima

### Endpoint:
```
GET /api/algorithms/bfs?start={ubicacion}&maxDistance={km}
```

### Ejemplo de uso:
```
GET /api/algorithms/bfs?start=Buenos Aires&maxDistance=500
```

---

## 2. DFS (Depth First Search) - 2 puntos

### ¿Qué hace?
Explora el grafo en profundidad, encontrando todas las rutas posibles entre dos puntos.

### Cómo funciona:
1. Comienza desde el origen
2. Sigue una ruta hasta el final (profundidad)
3. Si no encuentra el destino, retrocede (backtracking)
4. Prueba otra ruta
5. Continúa hasta encontrar todas las rutas posibles

### Endpoint:
```
GET /api/algorithms/dfs?start={ubicacion}&end={ubicacion}
```

### Ejemplo de uso:
```
GET /api/algorithms/dfs?start=Buenos Aires&end=Córdoba
```

---

## 3. Dijkstra - 3 puntos

### ¿Qué hace?
Encuentra la ruta más corta entre dos ubicaciones considerando la distancia como peso.

### Cómo funciona:
1. Inicializa distancias: origen = 0, todas las demás = infinito
2. Usa una cola de prioridad (menor distancia primero)
3. Para cada nodo visitado, actualiza las distancias de sus vecinos
4. Continúa hasta llegar al destino
5. Reconstruye el camino desde el destino hasta el origen

### Endpoint:
```
GET /api/algorithms/dijkstra?start={ubicacion}&end={ubicacion}
```

### Ejemplo de uso:
```
GET /api/algorithms/dijkstra?start=Buenos Aires&end=Córdoba
```

---

## 4. Prim - 3 puntos

### ¿Qué hace?
Encuentra el árbol de expansión mínima: conecta todas las ubicaciones con el menor costo total.

### Cómo funciona:
1. Comienza desde un nodo arbitrario
2. Agrega la arista más corta que conecte un nodo del árbol con uno fuera
3. Repite hasta conectar todos los nodos
4. Resultado: red mínima que conecta todas las ciudades

### Endpoint:
```
GET /api/algorithms/prim
```

---

## 5. Kruskal - 3 puntos

### ¿Qué hace?
Similar a Prim, pero usa Union-Find para encontrar el árbol de expansión mínima.

### Cómo funciona:
1. Ordena todas las aristas por peso (distancia)
2. Agrega aristas de menor a mayor peso
3. Usa Union-Find para evitar ciclos
4. Continúa hasta conectar todos los nodos

### Endpoint:
```
GET /api/algorithms/kruskal
```

---

## 6. Greedy TSP - 1 punto

### ¿Qué hace?
Resuelve el Problema del Viajante (TSP) usando una aproximación greedy: siempre elige la siguiente ubicación más cercana.

### Cómo funciona:
1. Comienza desde una ubicación inicial
2. En cada paso, elige la ubicación no visitada más cercana
3. Continúa hasta visitar todas
4. Intenta volver al inicio

### Endpoint:
```
GET /api/algorithms/greedy/tsp?start={ubicacion}
```

---

## 7. Quicksort - 1 punto

### ¿Qué hace?
Ordena las ubicaciones usando el algoritmo Quicksort (Divide y Vencerás).

### Cómo funciona:
1. Elige un pivote
2. Divide: elementos menores a la izquierda, mayores a la derecha
3. Aplica recursivamente a cada parte
4. Combina los resultados

### Endpoint:
```
GET /api/algorithms/divide-conquer/quicksort?sortBy={name|latitude|longitude}
```

---

## 8. Mergesort - 1 punto

### ¿Qué hace?
Ordena las ubicaciones usando el algoritmo Mergesort (Divide y Vencerás).

### Cómo funciona:
1. Divide la lista en mitades
2. Ordena cada mitad recursivamente
3. Fusiona (merge) las mitades ordenadas

### Endpoint:
```
GET /api/algorithms/divide-conquer/mergesort?sortBy={name|latitude|longitude}
```

---

## 9. Programación Dinámica TSP - 1 punto

### ¿Qué hace?
Resuelve el TSP usando programación dinámica con memoización (óptimo pero costoso).

### Cómo funciona:
1. Usa memoización para almacenar subproblemas resueltos
2. Calcula el costo mínimo para cada subconjunto de ciudades
3. Combina soluciones de subproblemas
4. Limitado a ~15 ubicaciones por complejidad

### Endpoint:
```
GET /api/algorithms/dynamic/tsp?start={ubicacion}
```

---

## 10. Backtracking - 1 punto

### ¿Qué hace?
Encuentra todas las rutas posibles entre dos puntos usando backtracking.

### Cómo funciona:
1. Explora sistemáticamente todas las posibilidades
2. Si una ruta no lleva al destino, retrocede (backtrack)
3. Prueba otra ruta
4. Encuentra todas las soluciones posibles

### Endpoint:
```
GET /api/algorithms/backtracking/routes?start={ubicacion}&end={ubicacion}&maxDepth={n}
```

---

## 11. Branch & Bound - 1 punto

### ¿Qué hace?
Encuentra la mejor ruta considerando restricciones (distancia máxima, tiempo máximo, costo máximo).

### Cómo funciona:
1. Usa límites (bounds) para podar ramas no prometedoras
2. Si una ruta excede las restricciones, la descarta
3. Si una ruta es peor que la mejor conocida, la descarta
4. Encuentra la solución óptima dentro de las restricciones

### Endpoint:
```
POST /api/algorithms/branch-bound/optimize
Body: {
  "start": "Buenos Aires",
  "end": "Córdoba",
  "maxDistance": 800,
  "maxDuration": 600,
  "maxCost": 100
}
```

---

## Pruebas y Resultados

### Resultados con Datos Cargados (8 ubicaciones, 20 relaciones)

#### 1. BFS - Breadth First Search
**Prueba:** `GET /api/algorithms/bfs?start=Buenos Aires&maxDistance=500`

**Resultado esperado:**
- Encuentra todas las ubicaciones alcanzables desde Buenos Aires hasta 500 km
- Debería encontrar: Rosario (300 km), La Plata (60 km), Mar del Plata (400 km)

**Cómo funciona con nuestros datos:**
- Nivel 0: Buenos Aires (inicio)
- Nivel 1: Rosario (300 km), La Plata (60 km), Mar del Plata (400 km), Córdoba (700 km - excede)
- Resultado: 3 ubicaciones dentro del rango de 500 km

---

#### 2. DFS - Depth First Search
**Prueba:** `GET /api/algorithms/dfs?start=Buenos Aires&end=Cordoba`

**Resultado esperado:**
- Explora todas las rutas posibles entre Buenos Aires y Cordoba
- Rutas posibles:
  1. Directa: Buenos Aires → Cordoba (700 km)
  2. Vía Rosario: Buenos Aires → Rosario → Cordoba (300 + 400 = 700 km)

**Cómo funciona con nuestros datos:**
- Explora en profundidad desde Buenos Aires
- Sigue una ruta hasta el final antes de probar otra
- Encuentra múltiples rutas posibles

---

#### 3. Dijkstra - Ruta Más Corta
**Prueba:** `GET /api/algorithms/dijkstra?start=Buenos Aires&end=Cordoba`

**Resultado esperado:**
- Encuentra la ruta más corta considerando distancia
- Ambas rutas tienen 700 km, pero puede elegir la directa

**Cómo funciona con nuestros datos:**
- Calcula distancias mínimas desde Buenos Aires
- Actualiza distancias cuando encuentra rutas más cortas
- Reconstruye el camino óptimo

---

#### 4. Prim - Árbol de Expansión Mínima
**Prueba:** `GET /api/algorithms/prim`

**Resultado esperado:**
- Conecta las 8 ubicaciones con 7 rutas (árbol tiene n-1 aristas)
- Distancia total mínima para conectar todas las ciudades

**Cómo funciona con nuestros datos:**
- Comienza desde una ubicación arbitraria
- Agrega la arista más corta que conecte un nodo del árbol con uno fuera
- Resultado: Red mínima que conecta todas las ciudades

---

#### 5. Kruskal - Árbol de Expansión Mínima
**Prueba:** `GET /api/algorithms/kruskal`

**Resultado esperado:**
- Similar a Prim, pero usando Union-Find
- Debería dar el mismo resultado que Prim (mismo árbol de expansión mínima)

---

#### 6. Greedy TSP - Problema del Viajante
**Prueba:** `GET /api/algorithms/greedy/tsp?start=Buenos Aires`

**Resultado esperado:**
- Recorrido que visita todas las ciudades empezando desde Buenos Aires
- Siempre elige la ciudad más cercana no visitada
- Intenta volver al inicio al final

---

#### 7. Quicksort - Ordenamiento
**Prueba:** `GET /api/algorithms/divide-conquer/quicksort?sortBy=name`

**Resultado esperado:**
- Ordena las 8 ubicaciones alfabéticamente
- Usa Divide y Vencerás

---

#### 8. Mergesort - Ordenamiento
**Prueba:** `GET /api/algorithms/divide-conquer/mergesort?sortBy=latitude`

**Resultado esperado:**
- Ordena las ubicaciones por latitud (norte a sur)
- Usa Divide y Vencerás

---

#### 9. Programación Dinámica TSP
**Prueba:** `GET /api/algorithms/dynamic/tsp?start=Buenos Aires`

**Resultado esperado:**
- Resuelve TSP de forma óptima usando memoización
- Limitado a ~15 ubicaciones por complejidad

---

#### 10. Backtracking
**Prueba:** `GET /api/algorithms/backtracking/routes?start=Buenos Aires&end=Cordoba&maxDepth=10`

**Resultado esperado:**
- Encuentra todas las rutas posibles entre dos puntos
- Usa backtracking para explorar sistemáticamente

---

#### 11. Branch & Bound
**Prueba:** `POST /api/algorithms/branch-bound/optimize`
```json
{
  "start": "Buenos Aires",
  "end": "Cordoba",
  "maxDistance": 800,
  "maxDuration": 600
}
```

**Resultado esperado:**
- Encuentra la mejor ruta dentro de las restricciones
- Poda ramas que excedan los límites

---

## Notas Importantes

- **Reiniciar aplicación:** Después de cambios en el código, reinicia la aplicación Spring Boot
- **Cargar datos primero:** Asegúrate de tener los 8 nodos y 20 relaciones cargados
- **Verificar en Neo4j:** Puedes verificar los datos en Neo4j Aura Console

