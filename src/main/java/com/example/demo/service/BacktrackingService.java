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

        List<List<String>> allPaths = new ArrayList<>();
        List<String> currentPath = new ArrayList<>();
        Set<Long> visited = new HashSet<>();

        backtrack(start, end, currentPath, visited, allPaths, maxDepthValue);

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
                          Set<Long> visited, List<List<String>> allPaths, int maxDepth) {
        // Condición de parada: profundidad máxima alcanzada
        if (currentPath.size() >= maxDepth) {
            return;
        }

        // Si llegamos al destino
        if (current.getId().equals(target.getId()) && !currentPath.isEmpty()) {
            List<String> path = new ArrayList<>(currentPath);
            path.add(current.getName());
            allPaths.add(path);
            return;
        }

        // Marcar como visitado
        visited.add(current.getId());
        currentPath.add(current.getName());

        // Explorar vecinos
        for (Route route : current.getRoutes()) {
            Location neighbor = route.getDestination();
            if (!visited.contains(neighbor.getId())) {
                backtrack(neighbor, target, currentPath, visited, allPaths, maxDepth);
            }
        }

        // Backtrack: desmarcar y remover del camino
        visited.remove(current.getId());
        currentPath.remove(currentPath.size() - 1);
    }

    private double calculatePathDistance(List<String> path) {
        double total = 0.0;
        for (int i = 0; i < path.size() - 1; i++) {
            Optional<Location> loc1Opt = locationRepository.findByName(path.get(i));
            Optional<Location> loc2Opt = locationRepository.findByName(path.get(i + 1));
            if (loc1Opt.isPresent() && loc2Opt.isPresent()) {
                Location loc1 = loc1Opt.get();
                Location loc2 = loc2Opt.get();
                for (Route route : loc1.getRoutes()) {
                    if (route.getDestination().getId().equals(loc2.getId())) {
                        total += route.getDistance();
                        break;
                    }
                }
            }
        }
        return total;
    }
}

