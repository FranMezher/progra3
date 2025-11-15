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

    /**
     * DFS: Explora todas las rutas posibles desde un origen hasta un destino
     * (exploración profunda)
     */
    public RouteResponse findAllRoutes(String startName, String endName) {
        Optional<Location> startOpt = locationRepository.findByName(startName);
        Optional<Location> endOpt = locationRepository.findByName(endName);

        if (startOpt.isEmpty() || endOpt.isEmpty()) {
            RouteResponse response = new RouteResponse();
            response.setMessage("Ubicación no encontrada");
            response.setAlgorithm("DFS");
            return response;
        }

        Location start = startOpt.get();
        Location end = endOpt.get();

        List<List<String>> allPaths = new ArrayList<>();
        Set<String> visited = new HashSet<>();
        List<String> currentPath = new ArrayList<>();

        dfsRecursive(start, end, visited, currentPath, allPaths);

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
        response.setMessage("DFS encontró " + allPaths.size() + " rutas posibles. Mostrando la más corta.");
        return response;
    }

    private void dfsRecursive(Location current, Location target, Set<String> visited,
                             List<String> currentPath, List<List<String>> allPaths) {
        if (current.getId().equals(target.getId())) {
            // Found a path
            List<String> path = new ArrayList<>(currentPath);
            path.add(current.getName());
            allPaths.add(path);
            return;
        }

        visited.add(current.getName());
        currentPath.add(current.getName());

        for (Route route : current.getRoutes()) {
            Location neighbor = route.getDestination();
            if (!visited.contains(neighbor.getName())) {
                dfsRecursive(neighbor, target, visited, currentPath, allPaths);
            }
        }

        // Backtrack
        visited.remove(current.getName());
        currentPath.remove(currentPath.size() - 1);
    }
}

