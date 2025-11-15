package com.example.demo.service;

import com.example.demo.dto.RouteResponse;
import com.example.demo.model.Location;
import com.example.demo.model.Route;
import com.example.demo.repository.LocationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * Branch & Bound - Optimización de rutas con restricciones
 * Encuentra la mejor ruta considerando límites de distancia, tiempo o costo
 */
@Service
public class BranchBoundService {

    @Autowired
    private LocationRepository locationRepository;
    
    @Autowired
    private GraphService graphService;

    public RouteResponse findOptimalRouteWithConstraints(String startName, String endName,
                                                         Double maxDistance, Integer maxDuration,
                                                         Double maxCost) {
        Optional<Location> startOpt = locationRepository.findByName(startName);
        Optional<Location> endOpt = locationRepository.findByName(endName);

        if (startOpt.isEmpty() || endOpt.isEmpty()) {
            RouteResponse response = new RouteResponse();
            response.setMessage("Ubicación no encontrada");
            response.setAlgorithm("Branch & Bound");
            return response;
        }

        Location start = startOpt.get();
        Location end = endOpt.get();
        
        // Cargar relaciones para todas las ubicaciones
        Map<String, Location> nameToLocation = new HashMap<>();
        List<Location> allLocations = locationRepository.findAll();
        for (Location loc : allLocations) {
            nameToLocation.put(loc.getName(), loc);
            if (loc.getRoutes() == null || loc.getRoutes().isEmpty()) {
                graphService.loadLocationWithRoutes(loc);
            }
        }
        
        // Asegurar que start y end tengan relaciones
        start = nameToLocation.get(startName);
        end = nameToLocation.get(endName);
        if (start == null || end == null) {
            RouteResponse response = new RouteResponse();
            response.setMessage("Ubicación no encontrada");
            response.setAlgorithm("Branch & Bound");
            return response;
        }
        
        if (start.getRoutes() == null || start.getRoutes().isEmpty()) {
            start = graphService.loadLocationWithRoutes(start);
        }

        // Usar Dijkstra modificado con poda de ramas - usar nombres como claves
        PriorityQueue<State> queue = new PriorityQueue<>(Comparator.comparingDouble(State::getCost));
        Map<String, Double> bestCost = new HashMap<>();
        
        queue.offer(new State(start, new ArrayList<>(), 0.0, 0, 0.0));
        bestCost.put(start.getName(), 0.0);

        RouteResponse bestSolution = null;
        double bestSolutionCost = Double.MAX_VALUE;

        while (!queue.isEmpty()) {
            State current = queue.poll();
            Location loc = current.location;
            List<String> path = current.path;

            // Poda: si ya tenemos una solución mejor, descartar
            if (current.cost > bestSolutionCost) {
                continue;
            }

            // Verificar restricciones
            if (maxDistance != null && current.distance > maxDistance) continue;
            if (maxDuration != null && current.duration > maxDuration) continue;
            if (maxCost != null && current.cost > maxCost) continue;

            // Cargar relaciones si no están cargadas
            if (loc.getRoutes() == null || loc.getRoutes().isEmpty()) {
                loc = graphService.loadLocationWithRoutes(loc);
                if (loc == null) continue;
            }

            // Si llegamos al destino - comparar por nombre
            if (loc.getName().equals(end.getName())) {
                if (current.cost < bestSolutionCost) {
                    bestSolutionCost = current.cost;
                    List<String> finalPath = new ArrayList<>(path);
                    finalPath.add(loc.getName());
                    
                    bestSolution = new RouteResponse();
                    bestSolution.setPath(finalPath);
                    bestSolution.setTotalDistance(current.distance);
                    bestSolution.setTotalDuration(current.duration);
                    bestSolution.setTotalCost(current.cost);
                    bestSolution.setAlgorithm("Branch & Bound");
                    bestSolution.setMessage("Ruta óptima encontrada con restricciones aplicadas");
                }
                continue;
            }

            // Explorar vecinos
            if (loc.getRoutes() != null) {
                for (Route route : loc.getRoutes()) {
                    if (route == null || route.getDestination() == null) continue;
                    
                    Location neighbor = route.getDestination();
                    Location neighborOriginal = nameToLocation.get(neighbor.getName());
                    if (neighborOriginal == null) continue;
                    
                    double newDistance = current.distance + (route.getDistance() != null ? route.getDistance() : 0.0);
                    int newDuration = current.duration + (route.getDuration() != null ? route.getDuration() : 0);
                    double newCost = current.cost + (route.getCost() != null ? route.getCost() : 0.0);

                    // Poda: verificar si es mejor que la mejor solución conocida
                    if (newCost >= bestSolutionCost) continue;

                    // Verificar restricciones antes de agregar
                    if (maxDistance != null && newDistance > maxDistance) continue;
                    if (maxDuration != null && newDuration > maxDuration) continue;
                    if (maxCost != null && newCost > maxCost) continue;

                    String neighborKey = neighborOriginal.getName();
                    if (!bestCost.containsKey(neighborKey) || newCost < bestCost.get(neighborKey)) {
                        bestCost.put(neighborKey, newCost);
                        List<String> newPath = new ArrayList<>(path);
                        newPath.add(loc.getName());
                        queue.offer(new State(neighborOriginal, newPath, newDistance, newDuration, newCost));
                    }
                }
            }
        }

        if (bestSolution == null) {
            RouteResponse response = new RouteResponse();
            response.setMessage("No se encontró ruta que cumpla las restricciones");
            response.setAlgorithm("Branch & Bound");
            return response;
        }

        return bestSolution;
    }

    private static class State {
        Location location;
        List<String> path;
        double distance;
        int duration;
        double cost;

        State(Location location, List<String> path, double distance, int duration, double cost) {
            this.location = location;
            this.path = path;
            this.distance = distance;
            this.duration = duration;
            this.cost = cost;
        }

        double getCost() {
            return cost;
        }
    }
}

