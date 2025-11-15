package com.example.demo.service;

import com.example.demo.dto.RouteResponse;
import com.example.demo.model.Location;
import com.example.demo.model.Route;
import com.example.demo.repository.LocationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * Backtracking - Encuentra todas las rutas posibles entre dos puntos
 * Explora sistemáticamente todas las posibilidades
 */
@Service
public class BacktrackingService {

    @Autowired
    private LocationRepository locationRepository;
    
    @Autowired
    private GraphService graphService;

    public RouteResponse findAllRoutes(String startName, String endName, Integer maxDepth) {
        Optional<Location> startOpt = locationRepository.findByName(startName);
        Optional<Location> endOpt = locationRepository.findByName(endName);

        if (startOpt.isEmpty() || endOpt.isEmpty()) {
            RouteResponse response = new RouteResponse();
            response.setMessage("Ubicación no encontrada");
            response.setAlgorithm("Backtracking");
            return response;
        }

        Location start = startOpt.get();
        Location end = endOpt.get();
        int maxDepthValue = (maxDepth != null && maxDepth > 0) ? maxDepth : 10;

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
            response.setAlgorithm("Backtracking");
            return response;
        }
        
        if (start.getRoutes() == null || start.getRoutes().isEmpty()) {
            start = graphService.loadLocationWithRoutes(start);
        }

        List<List<String>> allPaths = new ArrayList<>();
        List<String> currentPath = new ArrayList<>();
        Set<String> visited = new HashSet<>(); // Usar nombres en lugar de IDs

        backtrack(start, end, currentPath, visited, allPaths, maxDepthValue, nameToLocation);

        if (allPaths.isEmpty()) {
            RouteResponse response = new RouteResponse();
            response.setMessage("No se encontraron rutas entre " + startName + " y " + endName);
            response.setAlgorithm("Backtracking");
            return response;
        }

        // Encontrar la ruta más corta
        List<String> shortestPath = allPaths.stream()
            .min(Comparator.comparingInt(List::size))
            .orElse(allPaths.get(0));

        // Calcular distancia total de la ruta más corta
        double totalDistance = calculatePathDistance(shortestPath);

        RouteResponse response = new RouteResponse();
        response.setPath(shortestPath);
        response.setTotalDistance(totalDistance);
        response.setAlgorithm("Backtracking");
        response.setMessage("Backtracking encontró " + allPaths.size() + " rutas posibles. Mostrando la más corta.");
        return response;
    }

    private void backtrack(Location current, Location target, List<String> currentPath,
                          Set<String> visited, List<List<String>> allPaths, int maxDepth,
                          Map<String, Location> nameToLocation) {
        // Cargar relaciones si no están cargadas
        if (current.getRoutes() == null || current.getRoutes().isEmpty()) {
            current = graphService.loadLocationWithRoutes(current);
        }
        
        // Condición de parada: profundidad máxima alcanzada
        if (currentPath.size() >= maxDepth) {
            return;
        }

        // Si llegamos al destino - comparar por nombre
        if (current.getName().equals(target.getName()) && !currentPath.isEmpty()) {
            List<String> path = new ArrayList<>(currentPath);
            path.add(current.getName());
            allPaths.add(path);
            return;
        }

        // Marcar como visitado usando nombre
        String currentName = current.getName();
        visited.add(currentName);
        currentPath.add(currentName);

        // Explorar vecinos
        if (current.getRoutes() != null) {
            for (Route route : current.getRoutes()) {
                if (route == null || route.getDestination() == null) continue;
                
                Location neighbor = route.getDestination();
                Location neighborOriginal = nameToLocation.get(neighbor.getName());
                
                if (neighborOriginal == null || visited.contains(neighbor.getName())) {
                    continue;
                }
                
                backtrack(neighborOriginal, target, currentPath, visited, allPaths, maxDepth, nameToLocation);
            }
        }

        // Backtrack: desmarcar y remover del camino
        visited.remove(currentName);
        if (!currentPath.isEmpty()) {
            currentPath.remove(currentPath.size() - 1);
        }
    }

    private double calculatePathDistance(List<String> path) {
        double total = 0.0;
        for (int i = 0; i < path.size() - 1; i++) {
            Optional<Location> loc1Opt = locationRepository.findByName(path.get(i));
            Optional<Location> loc2Opt = locationRepository.findByName(path.get(i + 1));
            if (loc1Opt.isPresent() && loc2Opt.isPresent()) {
                Location loc1 = loc1Opt.get();
                Location loc2 = loc2Opt.get();
                
                // Cargar relaciones si es necesario
                if (loc1.getRoutes() == null || loc1.getRoutes().isEmpty()) {
                    loc1 = graphService.loadLocationWithRoutes(loc1);
                }
                
                if (loc1.getRoutes() != null) {
                    for (Route route : loc1.getRoutes()) {
                        if (route != null && route.getDestination() != null &&
                            route.getDestination().getName().equals(loc2.getName())) {
                            if (route.getDistance() != null) {
                                total += route.getDistance();
                            }
                            break;
                        }
                    }
                }
            }
        }
        return total;
    }
}

