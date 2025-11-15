package com.example.demo.service;

import com.example.demo.dto.RouteResponse;
import com.example.demo.model.Location;
import com.example.demo.model.Route;
import com.example.demo.repository.LocationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * Algoritmo Greedy - Problema del Viajante (TSP) con aproximación greedy
 * Selecciona siempre la siguiente ubicación más cercana
 */
@Service
public class GreedyService {

    @Autowired
    private LocationRepository locationRepository;
    
    @Autowired
    private GraphService graphService;

    public RouteResponse solveTSPGreedy(String startName) {
        Optional<Location> startOpt = locationRepository.findByName(startName);
        if (startOpt.isEmpty()) {
            RouteResponse response = new RouteResponse();
            response.setMessage("Ubicación de inicio no encontrada: " + startName);
            response.setAlgorithm("Greedy TSP");
            return response;
        }

        Location start = startOpt.get();
        List<Location> allLocations = locationRepository.findAll();
        if (allLocations.size() < 2) {
            RouteResponse response = new RouteResponse();
            response.setMessage("Se necesitan al menos 2 ubicaciones para TSP");
            response.setAlgorithm("Greedy TSP");
            return response;
        }

        // Asegurar que start esté en la lista con sus relaciones
        start = allLocations.stream()
            .filter(l -> l.getId().equals(startOpt.get().getId()))
            .findFirst()
            .orElse(start);

        Set<Location> visited = new HashSet<>();
        List<String> path = new ArrayList<>();
        Location current = start;
        path.add(current.getName());
        visited.add(current);

        double totalDistance = 0.0;
        int totalDuration = 0;
        double totalCost = 0.0;

        // Algoritmo Greedy: siempre elegir la ubicación más cercana no visitada
        while (visited.size() < allLocations.size()) {
            // Cargar relaciones si no están cargadas
            if (current.getRoutes() == null || current.getRoutes().isEmpty()) {
                current = graphService.loadLocationWithRoutes(current);
            }
            
            Location nearest = null;
            Route nearestRoute = null;
            double minDistance = Double.MAX_VALUE;

            for (Route route : current.getRoutes()) {
                Location neighbor = route.getDestination();
                if (!visited.contains(neighbor) && route.getDistance() < minDistance) {
                    minDistance = route.getDistance();
                    nearest = neighbor;
                    nearestRoute = route;
                }
            }

            if (nearest == null) {
                // Si no hay conexión directa, buscar la más cercana en general
                for (Location loc : allLocations) {
                    if (!visited.contains(loc)) {
                        double dist = current.distanceTo(loc);
                        if (dist < minDistance) {
                            minDistance = dist;
                            nearest = loc;
                            // Crear ruta estimada
                            nearestRoute = new Route(nearest, dist, (int)(dist * 0.7), dist * 0.1, "estimated");
                        }
                    }
                }
            }

            if (nearest != null) {
                path.add(nearest.getName());
                visited.add(nearest);
                totalDistance += minDistance;
                if (nearestRoute != null) {
                    totalDuration += nearestRoute.getDuration();
                    totalCost += nearestRoute.getCost();
                }
                current = nearest;
            } else {
                break; // No hay más ubicaciones alcanzables
            }
        }

        // Volver al inicio si es posible
        for (Route route : current.getRoutes()) {
            if (route.getDestination().getId().equals(start.getId())) {
                path.add(start.getName());
                totalDistance += route.getDistance();
                totalDuration += route.getDuration();
                totalCost += route.getCost();
                break;
            }
        }

        RouteResponse response = new RouteResponse();
        response.setPath(path);
        response.setTotalDistance(totalDistance);
        response.setTotalDuration(totalDuration);
        response.setTotalCost(totalCost);
        response.setAlgorithm("Greedy TSP");
        response.setMessage("Recorrido greedy completado. Visitadas " + visited.size() + " ubicaciones.");
        return response;
    }
}

