package com.example.demo.service;

import com.example.demo.dto.RouteResponse;
import com.example.demo.model.Location;
import com.example.demo.model.Route;
import com.example.demo.repository.LocationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class DFSService {

    @Autowired
    private LocationRepository locationRepository;
    
    @Autowired
    private GraphService graphService;

    /**
     * DFS: Explora rutas posibles desde un origen hasta un destino
     * (exploración profunda) con límites para evitar OutOfMemoryError
     */
    public RouteResponse findAllRoutes(String startName, String endName) {
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
            response.setAlgorithm("DFS");
            return response;
        }

        Location start = startOpt.get();
        Location end = endOpt.get();
        
        // Cargar con relaciones
        start = graphService.loadLocationWithRoutes(start);
        end = graphService.loadLocationWithRoutes(end);

        // Límites para evitar OutOfMemoryError en grafos completos
        int maxDepth = Math.min(10, allLocations.size()); // Profundidad máxima
        int maxPaths = 100; // Máximo de rutas a encontrar
        
        List<List<String>> allPaths = new ArrayList<>();
        Set<String> visited = new HashSet<>();
        List<String> currentPath = new ArrayList<>();

        dfsRecursive(start, end, visited, currentPath, allPaths, maxDepth, maxPaths);

        if (allPaths.isEmpty()) {
            RouteResponse response = new RouteResponse();
            response.setMessage("No se encontró ninguna ruta entre " + startName + " y " + endName);
            response.setAlgorithm("DFS");
            return response;
        }

        // Return the first path found (or shortest)
        List<String> shortestPath = allPaths.stream()
            .min(Comparator.comparingInt(List::size))
            .orElse(allPaths.get(0));

        RouteResponse response = new RouteResponse();
        response.setPath(shortestPath);
        response.setAlgorithm("DFS");
        String message = "DFS encontró " + allPaths.size() + " ruta(s) posible(s). Mostrando la más corta.";
        if (allPaths.size() >= maxPaths) {
            message += " (Límite de " + maxPaths + " rutas alcanzado)";
        }
        response.setMessage(message);
        return response;
    }

    private void dfsRecursive(Location current, Location target, Set<String> visited,
                             List<String> currentPath, List<List<String>> allPaths,
                             int maxDepth, int maxPaths) {
        // Límite de profundidad para evitar recursión infinita
        if (currentPath.size() >= maxDepth) {
            return;
        }
        
        // Límite de rutas encontradas para evitar OutOfMemoryError
        if (allPaths.size() >= maxPaths) {
            return;
        }
        
        // Cargar relaciones si no están cargadas
        if (current.getRoutes() == null || current.getRoutes().isEmpty()) {
            current = graphService.loadLocationWithRoutes(current);
        }
        
        // Comparar por nombre en lugar de ID (más confiable)
        if (current.getName().equals(target.getName())) {
            // Found a path
            List<String> path = new ArrayList<>(currentPath);
            path.add(current.getName());
            allPaths.add(path);
            return;
        }

        visited.add(current.getName());
        currentPath.add(current.getName());

        for (Route route : current.getRoutes()) {
            // Verificar límites antes de continuar
            if (allPaths.size() >= maxPaths) {
                break;
            }
            
            Location neighbor = route.getDestination();
            if (neighbor != null && !visited.contains(neighbor.getName())) {
                dfsRecursive(neighbor, target, visited, currentPath, allPaths, maxDepth, maxPaths);
            }
        }

        // Backtrack
        visited.remove(current.getName());
        if (!currentPath.isEmpty()) {
            currentPath.remove(currentPath.size() - 1);
        }
    }
}

