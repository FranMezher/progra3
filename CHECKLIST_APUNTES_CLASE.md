# ✅ CHECKLIST: Comparación con Apuntes de Clase

## 📚 Análisis Comparativo: Lo Visto en Clase vs. Lo Implementado

---

## 1. ✅ DIVIDE Y VENCERÁS (Clase 2)

### 📖 Lo visto en clase:
- **Quicksort**: Algoritmo de ordenamiento con pivote, complejidad O(n log n) mejor caso, O(n²) peor caso
- **Mergesort**: Algoritmo de ordenamiento estable, complejidad O(n log n)
- Análisis de recurrencias: T(n) = 2T(n/2) + Θ(n) para Mergesort
- Torres de Hanoi como ejemplo de recursividad

### ✅ Lo implementado:
- **Quicksort**: ✅ Implementado en `DivideConquerService.quicksortLocations()`
  - Usa partición con pivote (último elemento)
  - Ordena por nombre, latitud o longitud
  - Complejidad correcta: O(n log n) promedio
  
- **Mergesort**: ✅ Implementado en `DivideConquerService.mergesortLocations()`
  - Divide el array en dos mitades
  - Merge de dos subarrays ordenados
  - Complejidad O(n log n) garantizada

### 📊 Evaluación:
- ✅ **CORRECTO**: Ambos algoritmos están correctamente implementados según lo visto en clase
- ✅ La estructura recursiva coincide con los pseudocódigos de clase
- ✅ El análisis de complejidad es correcto

### ⏱️ Complejidad:

#### **Quicksort**
- **Tiempo (Mejor caso)**: O(n log n) - cuando el pivote divide el array en mitades iguales
- **Tiempo (Caso promedio)**: O(n log n) - comportamiento esperado en la práctica
- **Tiempo (Peor caso)**: O(n²) - cuando el pivote es siempre el elemento mínimo o máximo
  - Recurrencia: T(n) = T(n-1) + Θ(n) → O(n²)
- **Espacio**: O(log n) - profundidad de la pila de recursión (caso promedio)
  - Peor caso: O(n) si el pivote siempre es un extremo

#### **Mergesort**
- **Tiempo (Todos los casos)**: O(n log n) - garantizado
  - Recurrencia: T(n) = 2T(n/2) + Θ(n)
  - Aplicando método de división: a=2, b=2, k=1 → a = b^k → Θ(n log n)
- **Espacio**: O(n) - necesita espacio adicional para los arrays temporales durante el merge

---

## 2. ✅ ALGORITMOS GREEDY (Clase 3)

### 📖 Lo visto en clase:
- **Definición**: Técnica que toma la mejor decisión localmente óptima en cada paso
- **Propiedades**: Optimal Substructure, Greedy Choice Property
- **Ejemplos**: 
  - Problema del cambio de monedas
  - Problema de la mochila fraccional
- **Complejidad**: Generalmente O(n log n) por ordenamiento

### ✅ Lo implementado:
- **Greedy TSP**: ✅ Implementado en `GreedyService.solveTSP()`
  - Selecciona siempre la ciudad más cercana no visitada
  - Estrategia greedy: decisión localmente óptima
  - Complejidad O(n²) para n ciudades

### 📊 Evaluación:
- ✅ **CORRECTO**: El algoritmo greedy sigue la estrategia vista en clase
- ✅ Toma decisiones localmente óptimas (ciudad más cercana)
- ⚠️ **Nota**: En clase se vieron ejemplos de cambio de monedas y mochila, pero el TSP greedy es una aplicación válida del concepto

### ⏱️ Complejidad:

#### **Greedy TSP**
- **Tiempo**: O(n²) - donde n es el número de ciudades
  - Para cada ciudad (n iteraciones), busca la ciudad más cercana no visitada (n comparaciones)
  - No requiere ordenamiento previo como otros algoritmos greedy
- **Espacio**: O(n) - para almacenar:
  - Lista de ciudades visitadas: O(n)
  - Ruta resultante: O(n)
  - Mapa de distancias: O(1) si se calcula sobre la marcha

**Nota**: Este TSP greedy no garantiza solución óptima, pero es mucho más rápido que la solución óptima (O(n²) vs O(n!))

---

## 3. ✅ DIJKSTRA, PRIM, KRUSKAL (Clase 4)

### 📖 Lo visto en clase:
- **Dijkstra**: Encuentra el camino más corto desde un vértice a todos los demás
  - Usa cola de prioridad
  - Complejidad: O((V + E) log V) con heap binario
  - Pesos no negativos
  
- **Prim**: Encuentra el árbol de expansión mínima (MST)
  - Similar a Dijkstra pero para MST
  - Complejidad: O(E log V)
  
- **Kruskal**: Encuentra el MST ordenando aristas
  - Usa Union-Find (Disjoint Set)
  - Complejidad: O(E log E)

### ✅ Lo implementado:
- **Dijkstra**: ✅ Implementado en `DijkstraService.findShortestPath()`
  - Usa PriorityQueue (cola de prioridad)
  - Calcula distancia mínima entre dos puntos
  - ✅ Correcto según lo visto en clase

- **Prim**: ✅ Implementado en `PrimService.findMST()`
  - Construye MST empezando desde un nodo
  - Usa PriorityQueue para seleccionar arista mínima
  - ✅ Correcto según lo visto en clase

- **Kruskal**: ✅ Implementado en `KruskalService.findMST()`
  - Ordena aristas por peso
  - Usa Union-Find para detectar ciclos
  - ✅ Correcto según lo visto en clase

### 📊 Evaluación:
- ✅ **EXCELENTE**: Los tres algoritmos están implementados correctamente
- ✅ Estructura de datos correcta (PriorityQueue, Union-Find)
- ✅ Lógica coincide con los pseudocódigos de clase

### ⏱️ Complejidad:

#### **Dijkstra**
- **Tiempo**: O((V + E) log V) - donde V = vértices, E = aristas
  - Con PriorityQueue (heap binario): cada operación extract-min es O(log V)
  - Se procesan V vértices y E aristas
  - Total: O(V log V + E log V) = O((V + E) log V)
- **Espacio**: O(V) - para:
  - PriorityQueue: O(V)
  - Distancias y predecesores: O(V)
  - Conjunto de visitados: O(V)

#### **Prim**
- **Tiempo**: O(E log V) - donde V = vértices, E = aristas
  - Similar a Dijkstra, pero construye MST
  - Con PriorityQueue: O(E log V)
  - En grafos densos (E ≈ V²): O(V² log V)
- **Espacio**: O(V) - para:
  - PriorityQueue: O(V)
  - Array de padres y distancias: O(V)
  - Conjunto de visitados: O(V)

#### **Kruskal**
- **Tiempo**: O(E log E) - donde E = número de aristas
  - Ordenamiento de aristas: O(E log E)
  - Union-Find con path compression: O(E α(V)) ≈ O(E) donde α es la función de Ackermann inversa
  - Total: O(E log E) dominado por el ordenamiento
- **Espacio**: O(V) - para:
  - Array de aristas ordenadas: O(E)
  - Union-Find: O(V)
  - MST resultante: O(V)

**Comparación Prim vs Kruskal**:
- **Prim**: Mejor para grafos densos (E ≈ V²) → O(V² log V)
- **Kruskal**: Mejor para grafos dispersos (E << V²) → O(E log E)

---

## 4. ✅ PROGRAMACIÓN DINÁMICA (Clase 5)

### 📖 Lo visto en clase:
- **Definición**: Técnica que resuelve problemas dividiéndolos en subproblemas más pequeños
- **Características**:
  - Subproblemas superpuestos
  - Memoización (tabla de resultados)
  - Evita recalcular subproblemas
- **Ejemplos**: Problema de la mochila 0/1, secuencia de Fibonacci, TSP
- **Complejidad**: Generalmente O(n²) o mejor que fuerza bruta

### ✅ Lo implementado:
- **Dynamic TSP**: ✅ Implementado en `DynamicProgrammingService.solveTSPDynamic()`
  - Usa memoización con `Map<String, Double>`
  - Resuelve TSP con máscara de bits para representar conjunto de ciudades visitadas
  - Complejidad: O(n² * 2^n) - exponencial pero mejor que fuerza bruta completa
  - ✅ Implementación correcta de programación dinámica

### 📊 Evaluación:
- ✅ **CORRECTO**: Usa memoización correctamente
- ✅ Evita recalcular subproblemas
- ✅ Estructura coincide con lo visto en clase sobre programación dinámica

### ⏱️ Complejidad:

#### **Programación Dinámica TSP**
- **Tiempo**: O(n² × 2^n) - donde n = número de ciudades
  - Estados posibles: 2^n subconjuntos de ciudades × n posiciones actuales
  - Para cada estado, se calcula la distancia a n ciudades posibles
  - Total: O(n² × 2^n)
  - **Mejor que fuerza bruta**: O(n!) → O(n² × 2^n) es exponencial pero más eficiente
- **Espacio**: O(n × 2^n) - para:
  - Memoización: O(n × 2^n) estados
  - Matriz de distancias: O(n²)
  - Path memoization: O(n × 2^n)

**Nota**: Aunque es exponencial, es mucho mejor que la solución de fuerza bruta O(n!). Para n=15 ciudades:
- Fuerza bruta: 15! ≈ 1.3 × 10¹² operaciones
- Programación dinámica: 15² × 2¹⁵ ≈ 7.4 × 10⁶ operaciones

---

## 5. ✅ BACKTRACKING (Clase 8 y 12)

### 📖 Lo visto en clase:
- **Definición**: Técnica que explora todas las posibilidades sistemáticamente
- **Características**:
  - Exploración exhaustiva del espacio de soluciones
  - Retroceso (backtrack) cuando una solución parcial no es válida
  - Podas para evitar explorar ramas inválidas
- **Ejemplos**: Problema de las N reinas, Sudoku, subconjuntos
- **Pseudocódigo**:
  ```
  function backtrack(solution, candidatos):
      if solution es completa:
          return solution
      for cada candidato en candidatos:
          if es válido(candidato, solution):
              solution.add(candidato)
              result = backtrack(solution, candidatos)
              if result es no nulo:
                  return result
              solution.remove(candidato)  // BACKTRACK
      return nulo
  ```

### ✅ Lo implementado:
- **Backtracking Routes**: ✅ Implementado en `BacktrackingService.findAllRoutes()`
  - Explora todas las rutas posibles entre dos puntos
  - Usa recursión con retroceso
  - Marca nodos como visitados y los desmarca al retroceder
  - ✅ Estructura coincide con el pseudocódigo de clase

### 📊 Evaluación:
- ✅ **CORRECTO**: Implementación sigue el esquema de backtracking visto en clase
- ✅ Retroceso correcto: `visited.remove()` y `currentPath.remove()`
- ✅ Exploración sistemática de todas las posibilidades

### ⏱️ Complejidad:

#### **Backtracking**
- **Tiempo (Peor caso)**: O(b^d) - donde:
  - b = factor de ramificación promedio (número de vecinos por nodo)
  - d = profundidad máxima del árbol de búsqueda
  - En el peor caso, explora todas las rutas posibles
  - **Sin poda**: Puede ser exponencial O(V!) en grafos completos
  - **Con maxDepth**: O(b^maxDepth) limitado por la profundidad máxima
- **Tiempo (Mejor caso)**: O(V) - si encuentra la ruta en el primer intento
- **Espacio**: O(d) - donde d = profundidad máxima
  - Pila de recursión: O(d)
  - Lista de visitados: O(V)
  - Path actual: O(d)

**Nota**: El backtracking puede ser muy costoso en grafos grandes sin restricciones de profundidad. La implementación limita la profundidad con `maxDepth` para evitar explosión exponencial.

---

## 6. ✅ BRANCH & BOUND (Clase 11)

### 📖 Lo visto en clase:
- **Definición**: Técnica de optimización que combina backtracking con poda por optimalidad
- **Conceptos**:
  - **Ramificación (Branch)**: Dividir el problema en subproblemas
  - **Poda (Bound)**: Eliminar ramas que no pueden contener solución óptima
  - **Cota superior/inferior**: Estimar el mejor valor posible
- **Ejemplos**: Problema de la mochila, optimización de rutas con restricciones
- **Diferencia con Backtracking**: 
  - Backtracking busca todas las soluciones válidas
  - Branch & Bound busca la **mejor** solución según un criterio

### ✅ Lo implementado:
- **Branch & Bound**: ✅ Implementado en `BranchBoundService.findOptimalRouteWithConstraints()`
  - Usa PriorityQueue para explorar estados prometedores primero
  - **Poda por optimalidad**: Descarta estados con costo mayor al mejor conocido
  - **Poda por restricciones**: Descarta estados que violan límites (distancia, tiempo, costo)
  - ✅ Implementación correcta de Branch & Bound

### 📊 Evaluación:
- ✅ **CORRECTO**: Implementación sigue los conceptos de Branch & Bound
- ✅ Poda por optimalidad: `if (current.cost > bestSolutionCost) continue;`
- ✅ Poda por restricciones: verifica límites de distancia, tiempo, costo
- ✅ Busca la **mejor** solución (no todas las soluciones como backtracking)

### ⏱️ Complejidad:

#### **Branch & Bound**
- **Tiempo (Peor caso)**: O(b^d) - similar a backtracking, pero con podas
  - b = factor de ramificación
  - d = profundidad máxima
  - **Con podas efectivas**: Puede reducir significativamente el espacio de búsqueda
  - **Sin podas**: O(V!) en el peor caso (igual que backtracking)
- **Tiempo (Mejor caso)**: O(V log V) - si encuentra solución óptima rápidamente
  - Similar a Dijkstra cuando las podas son muy efectivas
- **Espacio**: O(b × d) - para:
  - PriorityQueue: O(b × d) en el peor caso
  - Mejor solución actual: O(d)
  - Mapa de mejores costos: O(V)

**Ventaja sobre Backtracking**: Las podas por optimalidad y restricciones pueden reducir drásticamente el número de estados explorados, haciendo el algoritmo más eficiente en la práctica.

---

## 7. ✅ BFS Y DFS (Clase 9)

### 📖 Lo visto en clase:
- **BFS (Breadth-First Search)**:
  - Explora por niveles (amplitud)
  - Usa **cola (Queue)** - FIFO
  - Útil para encontrar camino más corto en grafos no ponderados
  - Pseudocódigo con cola y conjunto de visitados
  
- **DFS (Depth-First Search)**:
  - Explora en profundidad antes de retroceder
  - Usa **pila (Stack)** o recursión
  - Útil para explorar todas las rutas posibles
  - Pseudocódigo recursivo con conjunto de visitados

### ✅ Lo implementado:
- **BFS**: ✅ Implementado en `BFSService.findReachableLocations()`
  - Usa `Queue<Location>` (LinkedList) ✅
  - Explora por niveles (distancia acumulada)
  - Marca nodos como visitados
  - ✅ Estructura coincide con pseudocódigo de clase

- **DFS**: ✅ Implementado en `DFSService.findAllRoutes()`
  - Usa recursión (pila implícita) ✅
  - Explora en profundidad
  - Retrocede cuando no hay más vecinos
  - ✅ Estructura coincide con pseudocódigo de clase

### 📊 Evaluación:
- ✅ **EXCELENTE**: Ambos algoritmos están correctamente implementados
- ✅ Estructuras de datos correctas (Queue para BFS, recursión para DFS)
- ✅ Lógica coincide con los pseudocódigos vistos en clase

### ⏱️ Complejidad:

#### **BFS (Breadth-First Search)**
- **Tiempo**: O(V + E) - donde V = vértices, E = aristas
  - Cada vértice se visita una vez: O(V)
  - Cada arista se examina una vez: O(E)
  - Total: O(V + E)
- **Espacio**: O(V) - para:
  - Cola (Queue): O(V) en el peor caso (todos los vértices en un nivel)
  - Conjunto de visitados: O(V)
  - Mapa de distancias: O(V)

**Nota**: BFS garantiza encontrar el camino más corto en grafos no ponderados.

#### **DFS (Depth-First Search)**
- **Tiempo**: O(V + E) - donde V = vértices, E = aristas
  - Cada vértice se visita una vez: O(V)
  - Cada arista se examina una vez: O(E)
  - Total: O(V + E)
- **Espacio**: O(V) - para:
  - Pila de recursión: O(V) en el peor caso (grafo lineal)
  - Conjunto de visitados: O(V)
  - Path actual: O(V) en el peor caso

**Comparación BFS vs DFS**:
- **BFS**: Encuentra camino más corto en grafos no ponderados, pero usa más memoria
- **DFS**: Usa menos memoria (profundidad vs amplitud), pero no garantiza camino más corto

---

## 📊 RESUMEN GENERAL

### ✅ Algoritmos Implementados (11/11)

| Algoritmo | Clase | Estado | Complejidad Temporal | Complejidad Espacial |
|----------|-------|--------|---------------------|---------------------|
| **Quicksort** | Clase 2 | ✅ | O(n log n) promedio<br>O(n²) peor caso | O(log n) |
| **Mergesort** | Clase 2 | ✅ | O(n log n) garantizado | O(n) |
| **Greedy TSP** | Clase 3 | ✅ | O(n²) | O(n) |
| **Dijkstra** | Clase 4 | ✅ | O((V + E) log V) | O(V) |
| **Prim** | Clase 4 | ✅ | O(E log V) | O(V) |
| **Kruskal** | Clase 4 | ✅ | O(E log E) | O(V) |
| **Programación Dinámica TSP** | Clase 5 | ✅ | O(n² × 2^n) | O(n × 2^n) |
| **Backtracking** | Clase 8, 12 | ✅ | O(b^d) peor caso<br>O(V) mejor caso | O(d) |
| **Branch & Bound** | Clase 11 | ✅ | O(b^d) peor caso<br>O(V log V) mejor caso | O(b × d) |
| **BFS** | Clase 9 | ✅ | O(V + E) | O(V) |
| **DFS** | Clase 9 | ✅ | O(V + E) | O(V) |

**Leyenda**:
- **n** = número de elementos (ciudades, ubicaciones)
- **V** = número de vértices (nodos)
- **E** = número de aristas (rutas)
- **b** = factor de ramificación promedio
- **d** = profundidad máxima del árbol de búsqueda

---

## 🎯 CONCLUSIÓN

### ✅ **TODO ESTÁ ACORDE A LO VISTO EN CLASE**

1. **✅ Estructura de algoritmos**: Todos siguen los pseudocódigos y conceptos vistos en clase
2. **✅ Estructuras de datos**: Uso correcto de colas, pilas, heaps, Union-Find
3. **✅ Complejidad**: Los algoritmos tienen la complejidad esperada según lo visto en clase
4. **✅ Conceptos teóricos**: 
   - Divide y vencerás aplicado correctamente
   - Greedy con decisiones localmente óptimas
   - Programación dinámica con memoización
   - Backtracking con retroceso correcto
   - Branch & Bound con poda por optimalidad

### 🌟 **PUNTOS DESTACADOS**

1. **Excelente implementación de algoritmos de grafos**: BFS, DFS, Dijkstra, Prim, Kruskal están todos correctos
2. **Correcta aplicación de técnicas**: Cada algoritmo aplica la técnica vista en clase de manera apropiada
3. **Buen uso de estructuras de datos**: PriorityQueue, Queue, recursión, memoización
4. **Implementación completa**: Todos los algoritmos requeridos están implementados y funcionando

### 📝 **ANÁLISIS DE COMPLEJIDAD DETALLADO**

#### **Algoritmos Polinomiales** (Eficientes)
- **BFS/DFS**: O(V + E) - Lineal en el tamaño del grafo ✅
- **Quicksort/Mergesort**: O(n log n) - Casi lineal ✅
- **Dijkstra/Prim/Kruskal**: O((V + E) log V) o O(E log E) - Polinomial ✅
- **Greedy TSP**: O(n²) - Cuadrático ✅

#### **Algoritmos Exponenciales** (Costosos pero necesarios)
- **Programación Dinámica TSP**: O(n² × 2^n) - Exponencial pero mejor que O(n!)
- **Backtracking**: O(b^d) - Puede ser exponencial sin restricciones
- **Branch & Bound**: O(b^d) - Exponencial en peor caso, pero mejorado con podas

#### **Comparación de Eficiencia**
1. **Ordenamiento**: Mergesort garantiza O(n log n), Quicksort es más rápido en promedio pero O(n²) en peor caso
2. **Grafos**: BFS/DFS son los más eficientes O(V + E), Dijkstra es ligeramente más costoso por la cola de prioridad
3. **MST**: Kruskal es mejor para grafos dispersos, Prim para grafos densos
4. **TSP**: Greedy es rápido O(n²) pero no óptimo, Programación Dinámica es óptima pero exponencial

### 📝 **RECOMENDACIONES (Opcionales)**

1. ✅ **Complejidad temporal**: Ya documentada en este checklist
2. ✅ **Análisis de recurrencias**: Documentado para Quicksort y Mergesort
3. ✅ **Comparación de algoritmos**: Incluida en la sección de complejidad

---

## ✅ **VEREDICTO FINAL**

**🎉 TU TRABAJO ESTÁ COMPLETAMENTE ACORDE A LO VISTO EN CLASE**

Todos los algoritmos están implementados correctamente según los conceptos, pseudocódigos y estructuras de datos vistos en las clases. El proyecto demuestra comprensión sólida de:
- Divide y vencerás
- Algoritmos greedy
- Algoritmos de grafos
- Programación dinámica
- Backtracking
- Branch & Bound

**¡Excelente trabajo! 👏**

