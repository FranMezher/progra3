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

        // Asegurar que start esté en la lista con sus relaciones - usar nombre en lugar de ID
        start = allLocations.stream()
            .filter(l -> l.getName().equals(startName))
            .findFirst()
            .orElse(start);
        
        // Cargar relaciones para todas las ubicaciones
        Map<String, Location> nameToLocation = new HashMap<>();
        for (Location loc : allLocations) {
            nameToLocation.put(loc.getName(), loc);
            if (loc.getRoutes() == null || loc.getRoutes().isEmpty()) {
                graphService.loadLocationWithRoutes(loc);
            }
        }
        
        // Asegurar que start tenga sus relaciones cargadas
        if (start.getRoutes() == null || start.getRoutes().isEmpty()) {
            start = graphService.loadLocationWithRoutes(start);
        }

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
            // Asegurar que current tenga relaciones cargadas
            if (current.getRoutes() == null || current.getRoutes().isEmpty()) {
                current = nameToLocation.get(current.getName());
                if (current != null && (current.getRoutes() == null || current.getRoutes().isEmpty())) {
                    current = graphService.loadLocationWithRoutes(current);
                }
            }
            
            if (current == null || current.getRoutes() == null || current.getRoutes().isEmpty()) {
                break; // No hay más conexiones disponibles
            }
            
            Location nearest = null;
            Route nearestRoute = null;
            double minDistance = Double.MAX_VALUE;

            for (Route route : current.getRoutes()) {
                if (route == null || route.getDestination() == null || route.getDistance() == null) {
                    continue;
                }
                Location neighbor = route.getDestination();
                // Usar el objeto original del mapa para comparar
                Location neighborOriginal = nameToLocation.get(neighbor.getName());
                if (neighborOriginal == null || visited.contains(neighborOriginal)) {
                    continue;
                }
                if (route.getDistance() < minDistance) {
                    minDistance = route.getDistance();
                    nearest = neighborOriginal;
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
                    if (nearestRoute.getDuration() != null) {
                        totalDuration += nearestRoute.getDuration();
                    }
                    if (nearestRoute.getCost() != null) {
                        totalCost += nearestRoute.getCost();
                    }
                }
                current = nearest;
            } else {
                break; // No hay más ubicaciones alcanzables
            }
        }

        // Volver al inicio si es posible - comparar por nombre
        if (current != null && current.getRoutes() != null) {
            for (Route route : current.getRoutes()) {
                if (route != null && route.getDestination() != null && 
                    route.getDestination().getName().equals(startName)) {
                    path.add(startName);
                    if (route.getDistance() != null) {
                        totalDistance += route.getDistance();
                    }
                    if (route.getDuration() != null) {
                        totalDuration += route.getDuration();
                    }
                    if (route.getCost() != null) {
                        totalCost += route.getCost();
                    }
                    break;
                }
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

