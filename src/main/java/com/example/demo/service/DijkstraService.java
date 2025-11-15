package com.example.demo.service;

import com.example.demo.dto.RouteResponse;
import com.example.demo.model.Location;
import com.example.demo.model.Route;
import com.example.demo.repository.LocationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class DijkstraService {

    @Autowired
    private LocationRepository locationRepository;

    /**
     * Dijkstra: Encuentra la ruta más corta entre dos ubicaciones
     * Considera la distancia como peso
     */
    public RouteResponse findShortestPath(String startName, String endName) {
        Optional<Location> startOpt = locationRepository.findByName(startName);
        Optional<Location> endOpt = locationRepository.findByName(endName);

        if (startOpt.isEmpty() || endOpt.isEmpty()) {
            RouteResponse response = new RouteResponse();
            response.setMessage("Ubicación no encontrada");
            response.setAlgorithm("Dijkstra");
            return response;
        }

        Location start = startOpt.get();
        Location end = endOpt.get();

        // Dijkstra's algorithm
        Map<Location, Double> distances = new HashMap<>();
        Map<Location, Location> previous = new HashMap<>();
        Set<Location> visited = new HashSet<>();
        PriorityQueue<Location> queue = new PriorityQueue<>(
            Comparator.comparingDouble(distances::get)
        );

        // Initialize distances
        List<Location> allLocations = locationRepository.findAll();
        for (Location loc : allLocations) {
            distances.put(loc, Double.MAX_VALUE);
        }
        distances.put(start, 0.0);
        queue.offer(start);

        while (!queue.isEmpty()) {
            Location current = queue.poll();
            if (visited.contains(current)) continue;
            visited.add(current);

            if (current.getId().equals(end.getId())) {
                break; // Found destination
            }

            // Explore neighbors
            for (Route route : current.getRoutes()) {
                Location neighbor = route.getDestination();
                if (visited.contains(neighbor)) continue;

                double newDistance = distances.get(current) + route.getDistance();
                if (newDistance < distances.get(neighbor)) {
                    distances.put(neighbor, newDistance);
                    previous.put(neighbor, current);
                    queue.offer(neighbor);
                }
            }
        }

        // Reconstruct path
        if (distances.get(end) == Double.MAX_VALUE) {
            RouteResponse response = new RouteResponse();
            response.setMessage("No se encontró ruta entre " + startName + " y " + endName);
            response.setAlgorithm("Dijkstra");
            return response;
        }

        List<String> path = new ArrayList<>();
        Location current = end;
        double totalDistance = 0.0;
        int totalDuration = 0;
        double totalCost = 0.0;

        while (current != null) {
            path.add(0, current.getName());
            Location prev = previous.get(current);
            if (prev != null) {
                // Find route between prev and current
                for (Route route : prev.getRoutes()) {
                    if (route.getDestination().getId().equals(current.getId())) {
                        totalDistance += route.getDistance();
                        totalDuration += route.getDuration();
                        totalCost += route.getCost();
                        break;
                    }
                }
            }
            current = prev;
        }

        RouteResponse response = new RouteResponse(path, totalDistance, totalDuration, totalCost, "Dijkstra");
        response.setMessage("Ruta más corta encontrada usando Dijkstra");
        return response;
    }
}

