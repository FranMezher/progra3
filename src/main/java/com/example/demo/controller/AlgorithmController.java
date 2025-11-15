package com.example.demo.controller;

import com.example.demo.dto.BFSResponse;
import com.example.demo.dto.RouteResponse;
import com.example.demo.model.Location;
import com.example.demo.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Controlador REST para algoritmos de grafos
 * Sistema de Navegación Urbana - Trabajo Práctico
 */
@RestController
@RequestMapping("/api/algorithms")
@CrossOrigin(origins = "*")
public class AlgorithmController {

    @Autowired
    private BFSService bfsService;

    @Autowired
    private DFSService dfsService;

    @Autowired
    private DijkstraService dijkstraService;

    @Autowired
    private PrimService primService;

    @Autowired
    private KruskalService kruskalService;

    @Autowired
    private GreedyService greedyService;

    @Autowired
    private DivideConquerService divideConquerService;

    @Autowired
    private DynamicProgrammingService dynamicProgrammingService;

    @Autowired
    private BacktrackingService backtrackingService;

    @Autowired
    private BranchBoundService branchBoundService;

    /**
     * BFS - Breadth First Search
     * Encuentra todas las ubicaciones alcanzables desde un punto de inicio
     * hasta una distancia máxima (exploración por niveles)
     * 
     * Ejemplo: GET /api/algorithms/bfs?start=Buenos Aires&maxDistance=500
     */
    @GetMapping("/bfs")
    public ResponseEntity<BFSResponse> bfs(
            @RequestParam String start,
            @RequestParam(defaultValue = "500") Integer maxDistance) {
        BFSResponse response = bfsService.findReachableLocations(start, maxDistance);
        return ResponseEntity.ok(response);
    }

    /**
     * DFS - Depth First Search
     * Explora todas las rutas posibles desde un origen hasta un destino
     * (exploración profunda)
     * 
     * Ejemplo: GET /api/algorithms/dfs?start=Buenos Aires&end=Córdoba
     */
    @GetMapping("/dfs")
    public ResponseEntity<RouteResponse> dfs(
            @RequestParam String start,
            @RequestParam String end) {
        RouteResponse response = dfsService.findAllRoutes(start, end);
        return ResponseEntity.ok(response);
    }

    /**
     * Dijkstra - Algoritmo de ruta más corta
     * Encuentra la ruta más corta entre dos ubicaciones considerando la distancia
     * 
     * Ejemplo: GET /api/algorithms/dijkstra?start=Buenos Aires&end=Córdoba
     */
    @GetMapping("/dijkstra")
    public ResponseEntity<RouteResponse> dijkstra(
            @RequestParam String start,
            @RequestParam String end) {
        RouteResponse response = dijkstraService.findShortestPath(start, end);
        return ResponseEntity.ok(response);
    }

    /**
     * Prim - Árbol de expansión mínima
     * Encuentra la red de conexiones mínima para conectar todas las ubicaciones
     * 
     * Ejemplo: GET /api/algorithms/prim
     */
    @GetMapping("/prim")
    public ResponseEntity<RouteResponse> prim() {
        RouteResponse response = primService.findMinimumSpanningTree();
        return ResponseEntity.ok(response);
    }

    /**
     * Kruskal - Árbol de expansión mínima (alternativa)
     * Similar a Prim pero usando Union-Find
     * 
     * Ejemplo: GET /api/algorithms/kruskal
     */
    @GetMapping("/kruskal")
    public ResponseEntity<RouteResponse> kruskal() {
        RouteResponse response = kruskalService.findMinimumSpanningTree();
        return ResponseEntity.ok(response);
    }

    /**
     * Greedy - Problema del Viajante (TSP)
     * Selecciona siempre la siguiente ubicación más cercana
     * 
     * Ejemplo: GET /api/algorithms/greedy/tsp?start=Buenos Aires
     */
    @GetMapping("/greedy/tsp")
    public ResponseEntity<RouteResponse> greedyTSP(@RequestParam String start) {
        RouteResponse response = greedyService.solveTSPGreedy(start);
        return ResponseEntity.ok(response);
    }

    /**
     * Divide y Vencerás - Quicksort
     * Ordena ubicaciones por nombre, latitud o longitud
     * 
     * Ejemplo: GET /api/algorithms/divide-conquer/quicksort?sortBy=name
     */
    @GetMapping("/divide-conquer/quicksort")
    public ResponseEntity<List<Location>> quicksort(@RequestParam(defaultValue = "name") String sortBy) {
        List<Location> sorted = divideConquerService.quicksortLocations(sortBy);
        return ResponseEntity.ok(sorted);
    }

    /**
     * Divide y Vencerás - Mergesort
     * Ordena ubicaciones por nombre, latitud o longitud
     * 
     * Ejemplo: GET /api/algorithms/divide-conquer/mergesort?sortBy=latitude
     */
    @GetMapping("/divide-conquer/mergesort")
    public ResponseEntity<List<Location>> mergesort(@RequestParam(defaultValue = "name") String sortBy) {
        List<Location> sorted = divideConquerService.mergesortLocations(sortBy);
        return ResponseEntity.ok(sorted);
    }

    /**
     * Programación Dinámica - Problema del Viajante (TSP)
     * Resuelve TSP usando memoización (limitado a ~15 ubicaciones)
     * 
     * Ejemplo: GET /api/algorithms/dynamic/tsp?start=Buenos Aires
     */
    @GetMapping("/dynamic/tsp")
    public ResponseEntity<RouteResponse> dynamicTSP(@RequestParam String start) {
        RouteResponse response = dynamicProgrammingService.solveTSPDynamic(start);
        return ResponseEntity.ok(response);
    }

    /**
     * Backtracking - Todas las rutas posibles
     * Encuentra todas las rutas entre dos puntos usando backtracking
     * 
     * Ejemplo: GET /api/algorithms/backtracking/routes?start=Buenos Aires&end=Córdoba&maxDepth=10
     */
    @GetMapping("/backtracking/routes")
    public ResponseEntity<RouteResponse> backtrackingRoutes(
            @RequestParam String start,
            @RequestParam String end,
            @RequestParam(required = false) Integer maxDepth) {
        RouteResponse response = backtrackingService.findAllRoutes(start, end, maxDepth);
        return ResponseEntity.ok(response);
    }

    /**
     * Branch & Bound - Optimización con restricciones
     * Encuentra la mejor ruta considerando límites de distancia, tiempo o costo
     * 
     * Ejemplo: POST /api/algorithms/branch-bound/optimize
     * Body: {"start": "Buenos Aires", "end": "Córdoba", "maxDistance": 800, "maxDuration": 600}
     */
    @PostMapping("/branch-bound/optimize")
    public ResponseEntity<RouteResponse> branchBound(@RequestBody Map<String, Object> request) {
        String start = (String) request.get("start");
        String end = (String) request.get("end");
        Double maxDistance = request.get("maxDistance") != null ? 
            Double.valueOf(request.get("maxDistance").toString()) : null;
        Integer maxDuration = request.get("maxDuration") != null ? 
            Integer.valueOf(request.get("maxDuration").toString()) : null;
        Double maxCost = request.get("maxCost") != null ? 
            Double.valueOf(request.get("maxCost").toString()) : null;

        RouteResponse response = branchBoundService.findOptimalRouteWithConstraints(
            start, end, maxDistance, maxDuration, maxCost);
        return ResponseEntity.ok(response);
    }

    /**
     * Endpoint de documentación
     * Muestra todos los algoritmos disponibles
     */
    @GetMapping("/info")
    public ResponseEntity<Object> getAlgorithmsInfo() {
        Map<String, Object> info = new HashMap<>();
        info.put("title", "Sistema de Navegación Urbana - Algoritmos de Grafos");
        info.put("description", "API completa para algoritmos de grafos aplicados a navegación y rutas");
        
        List<Map<String, String>> algorithms = new ArrayList<>();
        
        addAlgorithm(algorithms, "BFS", "GET /api/algorithms/bfs?start={location}&maxDistance={km}",
            "Breadth First Search - Encuentra ubicaciones alcanzables por niveles");
        addAlgorithm(algorithms, "DFS", "GET /api/algorithms/dfs?start={location}&end={location}",
            "Depth First Search - Explora todas las rutas posibles");
        addAlgorithm(algorithms, "Dijkstra", "GET /api/algorithms/dijkstra?start={location}&end={location}",
            "Algoritmo de ruta más corta");
        addAlgorithm(algorithms, "Prim", "GET /api/algorithms/prim",
            "Árbol de expansión mínima - Conecta todas las ubicaciones con costo mínimo");
        addAlgorithm(algorithms, "Kruskal", "GET /api/algorithms/kruskal",
            "Árbol de expansión mínima - Alternativa a Prim usando Union-Find");
        addAlgorithm(algorithms, "Greedy TSP", "GET /api/algorithms/greedy/tsp?start={location}",
            "Problema del Viajante con aproximación greedy");
        addAlgorithm(algorithms, "Quicksort", "GET /api/algorithms/divide-conquer/quicksort?sortBy={name|latitude|longitude}",
            "Divide y Vencerás - Ordena ubicaciones con Quicksort");
        addAlgorithm(algorithms, "Mergesort", "GET /api/algorithms/divide-conquer/mergesort?sortBy={name|latitude|longitude}",
            "Divide y Vencerás - Ordena ubicaciones con Mergesort");
        addAlgorithm(algorithms, "Dynamic TSP", "GET /api/algorithms/dynamic/tsp?start={location}",
            "Problema del Viajante con Programación Dinámica (máx 15 ubicaciones)");
        addAlgorithm(algorithms, "Backtracking", "GET /api/algorithms/backtracking/routes?start={location}&end={location}&maxDepth={n}",
            "Encuentra todas las rutas posibles usando backtracking");
        addAlgorithm(algorithms, "Branch & Bound", "POST /api/algorithms/branch-bound/optimize",
            "Optimización de rutas con restricciones (distancia, tiempo, costo)");
        
        info.put("algorithms", algorithms);
        info.put("totalAlgorithms", algorithms.size());
        info.put("status", "Completo - Todos los algoritmos implementados");
        info.put("dataEndpoint", "POST /api/data/load-sample - Cargar datos de ejemplo");
        
        return ResponseEntity.ok(info);
    }

    private void addAlgorithm(List<Map<String, String>> algorithms, String name, String endpoint, String description) {
        Map<String, String> algo = new HashMap<>();
        algo.put("name", name);
        algo.put("endpoint", endpoint);
        algo.put("description", description);
        algorithms.add(algo);
    }
}

