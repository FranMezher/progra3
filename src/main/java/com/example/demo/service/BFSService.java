package com.example.demo.service;

import com.example.demo.dto.BFSResponse;
import com.example.demo.model.Location;
import com.example.demo.model.Route;
import com.example.demo.repository.LocationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class BFSService {

    @Autowired
    private LocationRepository locationRepository;

    /**
     * BFS: Encuentra todas las ubicaciones alcanzables desde un punto de inicio
     * hasta una distancia máxima (exploración por niveles)
     */
    public BFSResponse findReachableLocations(String startLocationName, Integer maxDistanceKm) {
        Optional<Location> startOpt = locationRepository.findByName(startLocationName);
        if (startOpt.isEmpty()) {
            BFSResponse response = new BFSResponse();
            response.setMessage("Ubicación de inicio no encontrada: " + startLocationName);
            return response;
        }

        Location start = startOpt.get();
        Set<String> visited = new HashSet<>();
        List<String> reachable = new ArrayList<>();
        Queue<Location> queue = new LinkedList<>();
        Map<Location, Double> distances = new HashMap<>();

        queue.offer(start);
        distances.put(start, 0.0);
        visited.add(start.getName());

        int levelsExplored = 0;

        while (!queue.isEmpty()) {
            Location current = queue.poll();
            double currentDistance = distances.get(current);

            if (currentDistance <= maxDistanceKm) {
                if (!current.getName().equals(startLocationName)) {
                    reachable.add(current.getName());
                }
            } else {
                continue; // Skip if already exceeded max distance
            }

            levelsExplored++;

            // Explore neighbors
            for (Route route : current.getRoutes()) {
                Location neighbor = route.getDestination();
                double newDistance = currentDistance + route.getDistance();

                if (!visited.contains(neighbor.getName()) && newDistance <= maxDistanceKm) {
                    visited.add(neighbor.getName());
                    distances.put(neighbor, newDistance);
                    queue.offer(neighbor);
                }
            }
        }

        BFSResponse response = new BFSResponse(
            startLocationName,
            reachable,
            maxDistanceKm,
            levelsExplored
        );
        response.setMessage("BFS completado. Ubicaciones encontradas: " + reachable.size());
        return response;
    }
}

