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
    
    @Autowired
    private GraphService graphService;

    /**
     * Dijkstra: Encuentra la ruta más corta entre dos ubicaciones
     * Considera la distancia como peso
     */
    public RouteResponse findShortestPath(String startName, String endName) {
        // Cargar todas las ubicaciones con sus relaciones
        List<Location> allLocations = locationRepository.findAll();
        Optional<Location> startOpt = allLocations.stream()
            .filter(l -> l.getName().equals(startName))
            .findFirst();
        Optional<Location> endOpt = allLocations.stream()
            .filter(l -> l.getName().equals(endName))
            .findFirst();

        if (startOpt.isEmpty() || endOpt.isEmpty()) {
            RouteResponse response = new RouteResponse();
            response.setMessage("Ubicación no encontrada. Start: " + startName + ", End: " + endName);
            response.setAlgorithm("Dijkstra");
            return response;
        }

        Location start = startOpt.get();
        Location end = endOpt.get();

        // Cargar relaciones para todas las ubicaciones de una vez
        Map<String, Location> nameToLocation = new HashMap<>();
        for (Location loc : allLocations) {
            nameToLocation.put(loc.getName(), loc);
            if (loc.getRoutes() == null || loc.getRoutes().isEmpty()) {
                graphService.loadLocationWithRoutes(loc);
            }
        }

        // Dijkstra's algorithm - usar nombres como claves para evitar problemas con objetos
        Map<String, Double> distances = new HashMap<>();
        Map<String, String> previous = new HashMap<>();
        Set<String> visited = new HashSet<>();
        PriorityQueue<String> queue = new PriorityQueue<>(
            Comparator.comparingDouble(name -> distances.getOrDefault(name, Double.MAX_VALUE))
        );

        // Initialize distances
        for (Location loc : allLocations) {
            distances.put(loc.getName(), Double.MAX_VALUE);
        }
        distances.put(start.getName(), 0.0);
        queue.offer(start.getName());

        while (!queue.isEmpty()) {
            String currentName = queue.poll();
            if (visited.contains(currentName)) continue;
            visited.add(currentName);

            if (currentName.equals(end.getName())) {
                break; // Found destination
            }

            Location current = nameToLocation.get(currentName);
            if (current == null || current.getRoutes() == null) continue;
            
            // Explore neighbors
            for (Route route : current.getRoutes()) {
                Location neighbor = route.getDestination();
                String neighborName = neighbor.getName();
                
                if (visited.contains(neighborName)) continue;
                
                // Obtener la ubicación original del mapa
                Location neighborOriginal = nameToLocation.get(neighborName);
                if (neighborOriginal == null) continue;

                double newDistance = distances.get(currentName) + route.getDistance();
                double currentDistance = distances.getOrDefault(neighborName, Double.MAX_VALUE);
                
                if (newDistance < currentDistance) {
                    distances.put(neighborName, newDistance);
                    previous.put(neighborName, currentName);
                    queue.offer(neighborName);
                }
            }
        }

        // Reconstruct path usando nombres
        if (distances.getOrDefault(end.getName(), Double.MAX_VALUE) == Double.MAX_VALUE) {
            RouteResponse response = new RouteResponse();
            response.setMessage("No se encontró ruta entre " + startName + " y " + endName);
            response.setAlgorithm("Dijkstra");
            return response;
        }

        List<String> path = new ArrayList<>();
        String currentName = end.getName();
        double totalDistance = 0.0;
        int totalDuration = 0;
        double totalCost = 0.0;

        while (currentName != null) {
            path.add(0, currentName);
            String prevName = previous.get(currentName);
            if (prevName != null) {
                Location prev = nameToLocation.get(prevName);
                Location current = nameToLocation.get(currentName);
                
                if (prev != null && prev.getRoutes() != null && current != null) {
                    // Find route between prev and current usando nombres
                    for (Route route : prev.getRoutes()) {
                        if (route.getDestination().getName().equals(currentName)) {
                            totalDistance += route.getDistance();
                            totalDuration += route.getDuration();
                            totalCost += route.getCost();
                            break;
                        }
                    }
                }
            }
            currentName = prevName;
        }

        RouteResponse response = new RouteResponse(path, totalDistance, totalDuration, totalCost, "Dijkstra");
        response.setMessage("Ruta más corta encontrada usando Dijkstra");
        return response;
    }
}

