# Análisis de Errores 500

## Problemas Identificados

### 1. **DFS - NullPointerException**
**Línea 81:** `if (current.getId().equals(target.getId()))`
- **Problema**: Si `current.getId()` o `target.getId()` es `null`, lanza `NullPointerException`
- **Causa**: Cuando `GraphService` crea nuevas instancias de `Location` sin relaciones, los IDs pueden ser `null`

### 2. **Dijkstra - Múltiples Problemas**

#### a) PriorityQueue con comparador problemático
**Línea 49-51:**
```java
PriorityQueue<Location> queue = new PriorityQueue<>(
    Comparator.comparingDouble(distances::get)
);
```
- **Problema**: El comparador usa `distances::get`, pero cuando se actualiza `current` en la línea 71, se crea un nuevo objeto `Location`, y el mapa `distances` no tiene ese nuevo objeto
- **Causa**: `current = graphService.loadLocationWithRoutes(current)` crea un nuevo objeto, pero `distances` sigue usando el objeto anterior

#### b) NullPointerException al comparar IDs
**Línea 65:** `if (current.getId().equals(end.getId()))`
**Línea 108:** `if (route.getDestination().getId().equals(current.getId()))`
- **Problema**: Si los IDs son `null`, lanza excepción

### 3. **Kruskal - ClassCastException**
**Línea 49:** `Location dest = (Location) routeData[0];`
- **Problema**: El query puede devolver un tipo diferente, causando `ClassCastException`
- **Causa**: La query `findRoutesFromLocation` devuelve `Object[]`, pero el cast puede fallar

### 4. **Greedy TSP - NullPointerException**
**Línea 96:** `if (route.getDestination().getId().equals(start.getId()))`
- **Problema**: Similar a los anteriores, IDs pueden ser `null`

## Soluciones Necesarias

1. **Validar IDs antes de comparar**: Usar `Objects.equals()` o verificar null
2. **No crear nuevos objetos Location**: Modificar el objeto existente en lugar de crear uno nuevo
3. **Usar nombres en lugar de IDs para comparar**: Más confiable que IDs que pueden ser null
4. **Manejar excepciones**: Agregar try-catch en los servicios
5. **Verificar tipos antes de hacer cast**: En Kruskal, verificar el tipo antes de hacer cast

