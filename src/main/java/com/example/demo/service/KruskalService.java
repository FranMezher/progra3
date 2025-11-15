package com.example.demo.service;

import com.example.demo.dto.RouteResponse;
import com.example.demo.model.Location;
import com.example.demo.model.Route;
import com.example.demo.repository.LocationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * Algoritmo de Kruskal - Árbol de expansión mínima
 * Alternativa a Prim usando Union-Find
 */
@Service
public class KruskalService {

    @Autowired
    private LocationRepository locationRepository;

    public RouteResponse findMinimumSpanningTree() {
        List<Location> allLocations = locationRepository.findAll();
        if (allLocations.isEmpty()) {
            RouteResponse response = new RouteResponse();
            response.setMessage("No hay ubicaciones en la base de datos");
            response.setAlgorithm("Kruskal");
            return response;
        }

        // Recopilar todas las aristas
        List<Edge> edges = new ArrayList<>();
        
        // Crear mapa de nombres a ubicaciones
        Map<String, Location> nameToLocation = new HashMap<>();
        for (Location loc : allLocations) {
            nameToLocation.put(loc.getName(), loc);
        }
        
        // Recopilar aristas usando queries directas para obtener relaciones
        for (Location loc : allLocations) {
            try {
                List<Object[]> routes = locationRepository.findRoutesFromLocation(loc.getName());
                for (Object[] routeData : routes) {
                    if (routeData.length < 4) continue;
                    
                    // Validar tipos antes de hacer cast
                    Object destObj = routeData[0];
                    Location dest = null;
                    
                    if (destObj instanceof Location) {
                        dest = (Location) destObj;
                    } else if (destObj instanceof Map) {
                        // Si viene como mapa, extraer el nombre
                        @SuppressWarnings("unchecked")
                        Map<String, Object> destMap = (Map<String, Object>) destObj;
                        String destName = (String) destMap.get("name");
                        dest = nameToLocation.get(destName);
                    }
                    
                    if (dest == null) continue;
                    
                    // Validar que los valores no sean null
                    Object distObj = routeData[1];
                    Object durObj = routeData[2];
                    Object costObj = routeData[3];
                    
                    if (!(distObj instanceof Double) || !(durObj instanceof Integer) || !(costObj instanceof Double)) {
                        continue;
                    }
                    
                    Double distance = (Double) distObj;
                    Integer duration = (Integer) durObj;
                    Double cost = (Double) costObj;
                    
                    // Usar nombres para comparar y evitar duplicados (grafo no dirigido)
                    String locName = loc.getName();
                    String destName = dest.getName();
                    
                    // Comparar alfabéticamente para evitar duplicados (A->B y B->A son la misma arista)
                    if (locName.compareTo(destName) < 0) {
                        Route route = new Route(dest, distance, duration, cost, "highway");
                        edges.add(new Edge(loc, dest, distance, route));
                    }
                }
            } catch (Exception e) {
                // Continuar con la siguiente ubicación si hay error
                System.err.println("Error procesando rutas de " + loc.getName() + ": " + e.getMessage());
            }
        }

        // Ordenar aristas por peso
        edges.sort(Comparator.comparingDouble(Edge::getWeight));

        // Union-Find - usar índices basados en posición
        Map<String, Integer> nameToIndex = new HashMap<>();
        int idx = 0;
        for (Location loc : allLocations) {
            nameToIndex.put(loc.getName(), idx++);
        }
        
        UnionFind uf = new UnionFind(allLocations.size());
        List<String> mstPath = new ArrayList<>();
        double totalDistance = 0.0;
        int totalDuration = 0;
        double totalCost = 0.0;

        // Algoritmo de Kruskal
        for (Edge edge : edges) {
            Integer uIndex = nameToIndex.get(edge.from.getName());
            Integer vIndex = nameToIndex.get(edge.to.getName());

            if (uIndex != null && vIndex != null && uf.find(uIndex) != uf.find(vIndex)) {
                uf.union(uIndex, vIndex);
                mstPath.add(edge.from.getName() + " -> " + edge.to.getName());
                totalDistance += edge.weight;
                if (edge.route.getDuration() != null) {
                    totalDuration += edge.route.getDuration();
                }
                if (edge.route.getCost() != null) {
                    totalCost += edge.route.getCost();
                }
            }
        }

        RouteResponse response = new RouteResponse();
        response.setPath(mstPath);
        response.setTotalDistance(totalDistance);
        response.setTotalDuration(totalDuration);
        response.setTotalCost(totalCost);
        response.setAlgorithm("Kruskal");
        response.setMessage("Árbol de expansión mínima encontrado usando Kruskal.");
        return response;
    }

    private static class Edge {
        Location from;
        Location to;
        double weight;
        Route route;

        Edge(Location from, Location to, double weight, Route route) {
            this.from = from;
            this.to = to;
            this.weight = weight;
            this.route = route;
        }

        double getWeight() {
            return weight;
        }
    }

    private static class UnionFind {
        private int[] parent;
        private int[] rank;

        UnionFind(int n) {
            parent = new int[n];
            rank = new int[n];
            for (int i = 0; i < n; i++) {
                parent[i] = i;
                rank[i] = 0;
            }
        }

        int find(int x) {
            if (parent[x] != x) {
                parent[x] = find(parent[x]);
            }
            return parent[x];
        }

        void union(int x, int y) {
            int rootX = find(x);
            int rootY = find(y);
            if (rootX != rootY) {
                if (rank[rootX] < rank[rootY]) {
                    parent[rootX] = rootY;
                } else if (rank[rootX] > rank[rootY]) {
                    parent[rootY] = rootX;
                } else {
                    parent[rootY] = rootX;
                    rank[rootX]++;
                }
            }
        }
    }
}

