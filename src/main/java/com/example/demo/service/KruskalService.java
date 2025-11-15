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
        Map<Location, Long> locationToId = new HashMap<>();
        long id = 0;
        for (Location loc : allLocations) {
            locationToId.put(loc, id++);
            for (Route route : loc.getRoutes()) {
                Location dest = route.getDestination();
                // Evitar duplicados (grafo no dirigido)
                if (locationToId.containsKey(dest) && locationToId.get(loc) < locationToId.get(dest)) {
                    edges.add(new Edge(loc, dest, route.getDistance(), route));
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
            long uId = locationToId.get(edge.from);
            long vId = locationToId.get(edge.to);

            if (uf.find(uId) != uf.find(vId)) {
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

