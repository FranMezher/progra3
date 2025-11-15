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
    
    @Autowired
    private GraphService graphService;

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
        
        // Cargar relaciones para todas las ubicaciones (similar a Prim)
        for (Location loc : allLocations) {
            if (loc.getRoutes() == null || loc.getRoutes().isEmpty()) {
                graphService.loadLocationWithRoutes(loc);
            }
        }
        
        // Recopilar aristas directamente de las relaciones cargadas (más confiable, como Prim)
        for (Location loc : allLocations) {
            if (loc.getRoutes() == null || loc.getRoutes().isEmpty()) continue;
            
            for (Route route : loc.getRoutes()) {
                if (route == null || route.getDestination() == null || route.getDistance() == null) {
                    continue;
                }
                
                Location dest = route.getDestination();
                Location destOriginal = nameToLocation.get(dest.getName());
                
                if (destOriginal == null) continue;
                
                // Usar nombres para comparar y evitar duplicados (grafo no dirigido)
                String locName = loc.getName();
                String destName = destOriginal.getName();
                
                // Comparar alfabéticamente para evitar duplicados (A->B y B->A son la misma arista)
                if (locName.compareTo(destName) < 0) {
                    edges.add(new Edge(loc, destOriginal, route.getDistance(), route));
                }
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
        
        if (edges.isEmpty()) {
            response.setMessage("No se encontraron aristas. Verifique que las relaciones estén cargadas.");
        } else if (mstPath.isEmpty()) {
            response.setMessage("Se encontraron " + edges.size() + " aristas, pero no se pudo construir el MST. Posible grafo desconectado.");
        } else {
            response.setMessage("Árbol de expansión mínima encontrado usando Kruskal. " + 
                              edges.size() + " aristas procesadas, " + mstPath.size() + " aristas en MST.");
        }
        
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

