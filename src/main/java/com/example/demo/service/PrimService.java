package com.example.demo.service;

import com.example.demo.dto.RouteResponse;
import com.example.demo.model.Location;
import com.example.demo.model.Route;
import com.example.demo.repository.LocationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * Algoritmo de Prim - Árbol de expansión mínima
 * Encuentra la red de conexiones mínima para conectar todas las ubicaciones
 */
@Service
public class PrimService {

    @Autowired
    private LocationRepository locationRepository;

    public RouteResponse findMinimumSpanningTree() {
        List<Location> allLocations = locationRepository.findAll();
        if (allLocations.isEmpty()) {
            RouteResponse response = new RouteResponse();
            response.setMessage("No hay ubicaciones en la base de datos");
            response.setAlgorithm("Prim");
            return response;
        }

        // Estructuras para Prim
        Set<Location> mst = new HashSet<>();
        PriorityQueue<Edge> queue = new PriorityQueue<>(Comparator.comparingDouble(Edge::getWeight));
        Map<Location, Double> minWeight = new HashMap<>();
        Map<Location, Location> parent = new HashMap<>();
        List<String> mstPath = new ArrayList<>();
        double totalDistance = 0.0;
        int totalDuration = 0;
        double totalCost = 0.0;

        // Inicializar
        Location start = allLocations.get(0);
        mst.add(start);
        minWeight.put(start, 0.0);

        // Agregar todas las aristas del nodo inicial
        for (Route route : start.getRoutes()) {
            queue.offer(new Edge(start, route.getDestination(), route.getDistance(), route));
        }

        // Algoritmo de Prim
        while (!queue.isEmpty() && mst.size() < allLocations.size()) {
            Edge edge = queue.poll();
            Location u = edge.from;
            Location v = edge.to;

            if (mst.contains(v)) continue;

            mst.add(v);
            mstPath.add(u.getName() + " -> " + v.getName());
            totalDistance += edge.weight;
            totalDuration += edge.route.getDuration();
            totalCost += edge.route.getCost();
            parent.put(v, u);

            // Agregar aristas del nuevo nodo
            for (Route route : v.getRoutes()) {
                Location neighbor = route.getDestination();
                if (!mst.contains(neighbor)) {
                    queue.offer(new Edge(v, neighbor, route.getDistance(), route));
                }
            }
        }

        RouteResponse response = new RouteResponse();
        response.setPath(mstPath);
        response.setTotalDistance(totalDistance);
        response.setTotalDuration(totalDuration);
        response.setTotalCost(totalCost);
        response.setAlgorithm("Prim");
        response.setMessage("Árbol de expansión mínima encontrado. Conecta " + mst.size() + " ubicaciones.");
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
}

