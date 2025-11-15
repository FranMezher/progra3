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
        Map<Long, Long> locationIdToIndex = new HashMap<>();
        long index = 0;
        for (Location loc : allLocations) {
            locationIdToIndex.put(loc.getId(), index++);
        }
        
        // Crear mapa de nombres a ubicaciones para buscar destinos
        Map<String, Location> nameToLocation = new HashMap<>();
        for (Location loc : allLocations) {
            nameToLocation.put(loc.getName(), loc);
        }
        
        // Recopilar aristas usando queries directas para obtener relaciones
        for (Location loc : allLocations) {
            List<Object[]> routes = locationRepository.findRoutesFromLocation(loc.getName());
            for (Object[] routeData : routes) {
                Location dest = (Location) routeData[0];
                Double distance = (Double) routeData[1];
                Integer duration = (Integer) routeData[2];
                Double cost = (Double) routeData[3];
                
                // Evitar duplicados (grafo no dirigido)
                Long locIndex = locationIdToIndex.get(loc.getId());
                Long destIndex = locationIdToIndex.get(dest.getId());
                if (locIndex != null && destIndex != null && locIndex < destIndex) {
                    Route route = new Route(dest, distance, duration, cost, "highway");
                    edges.add(new Edge(loc, dest, distance, route));
                }
            }
        }

        // Ordenar aristas por peso
        edges.sort(Comparator.comparingDouble(Edge::getWeight));

        // Union-Find
        UnionFind uf = new UnionFind(allLocations.size());
        List<String> mstPath = new ArrayList<>();
        double totalDistance = 0.0;
        int totalDuration = 0;
        double totalCost = 0.0;

        // Algoritmo de Kruskal
        for (Edge edge : edges) {
            Long uId = locationIdToIndex.get(edge.from.getId());
            Long vId = locationIdToIndex.get(edge.to.getId());

            if (uId != null && vId != null && uf.find(uId) != uf.find(vId)) {
                uf.union(uId, vId);
                mstPath.add(edge.from.getName() + " -> " + edge.to.getName());
                totalDistance += edge.weight;
                totalDuration += edge.route.getDuration();
                totalCost += edge.route.getCost();
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
        private long[] parent;
        private int[] rank;

        UnionFind(int n) {
            parent = new long[n];
            rank = new int[n];
            for (int i = 0; i < n; i++) {
                parent[i] = i;
                rank[i] = 0;
            }
        }

        long find(long x) {
            if (parent[(int) x] != x) {
                parent[(int) x] = find(parent[(int) x]);
            }
            return parent[(int) x];
        }

        void union(long x, long y) {
            long rootX = find(x);
            long rootY = find(y);
            if (rootX != rootY) {
                if (rank[(int) rootX] < rank[(int) rootY]) {
                    parent[(int) rootX] = rootY;
                } else if (rank[(int) rootX] > rank[(int) rootY]) {
                    parent[(int) rootY] = rootX;
                } else {
                    parent[(int) rootY] = rootX;
                    rank[(int) rootX]++;
                }
            }
        }
    }
}

