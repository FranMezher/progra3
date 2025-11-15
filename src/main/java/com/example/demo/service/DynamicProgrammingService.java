package com.example.demo.service;

import com.example.demo.dto.RouteResponse;
import com.example.demo.model.Location;
import com.example.demo.model.Route;
import com.example.demo.repository.LocationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * Programación Dinámica - Problema del Viajante (TSP)
 * Usa memoización para optimizar el cálculo
 */
@Service
public class DynamicProgrammingService {

    @Autowired
    private LocationRepository locationRepository;

    public RouteResponse solveTSPDynamic(String startName) {
        Optional<Location> startOpt = locationRepository.findByName(startName);
        if (startOpt.isEmpty()) {
            RouteResponse response = new RouteResponse();
            response.setMessage("Ubicación de inicio no encontrada: " + startName);
            response.setAlgorithm("Dynamic Programming TSP");
            return response;
        }

        Location start = startOpt.get();
        List<Location> allLocations = locationRepository.findAll();
        
        if (allLocations.size() > 15) {
            RouteResponse response = new RouteResponse();
            response.setMessage("TSP con programación dinámica es muy costoso para más de 15 ubicaciones. Use Greedy en su lugar.");
            response.setAlgorithm("Dynamic Programming TSP");
            return response;
        }

        // Crear matriz de distancias
        int n = allLocations.size();
        double[][] dist = new double[n][n];
        Map<Location, Integer> locationIndex = new HashMap<>();
        
        for (int i = 0; i < n; i++) {
            locationIndex.put(allLocations.get(i), i);
        }

        // Llenar matriz de distancias
        for (int i = 0; i < n; i++) {
            Location loc1 = allLocations.get(i);
            for (int j = 0; j < n; j++) {
                if (i == j) {
                    dist[i][j] = 0;
                } else {
                    Location loc2 = allLocations.get(j);
                    double minDist = Double.MAX_VALUE;
                    for (Route route : loc1.getRoutes()) {
                        if (route.getDestination().getId().equals(loc2.getId())) {
                            minDist = Math.min(minDist, route.getDistance());
                        }
                    }
                    if (minDist == Double.MAX_VALUE) {
                        minDist = loc1.distanceTo(loc2); // Distancia geográfica
                    }
                    dist[i][j] = minDist;
                }
            }
        }

        int startIdx = locationIndex.get(start);
        int mask = 1 << startIdx;
        
        // Memoización: dp[mask][last] = distancia mínima
        Map<String, Double> memo = new HashMap<>();
        Map<String, List<Integer>> pathMemo = new HashMap<>();

        double minCost = tspDP(mask, startIdx, startIdx, n, dist, memo, pathMemo);
        List<Integer> path = pathMemo.get(mask + "," + startIdx + "," + startIdx);

        if (path == null || path.isEmpty()) {
            RouteResponse response = new RouteResponse();
            response.setMessage("No se pudo encontrar una ruta completa");
            response.setAlgorithm("Dynamic Programming TSP");
            return response;
        }

        List<String> pathNames = new ArrayList<>();
        double totalDistance = 0.0;
        for (int i = 0; i < path.size() - 1; i++) {
            pathNames.add(allLocations.get(path.get(i)).getName());
            totalDistance += dist[path.get(i)][path.get(i + 1)];
        }
        pathNames.add(allLocations.get(path.get(path.size() - 1)).getName());

        RouteResponse response = new RouteResponse();
        response.setPath(pathNames);
        response.setTotalDistance(totalDistance);
        response.setAlgorithm("Dynamic Programming TSP");
        response.setMessage("TSP resuelto con programación dinámica. Costo mínimo: " + String.format("%.2f", minCost));
        return response;
    }

    private double tspDP(int mask, int pos, int start, int n, double[][] dist, 
                         Map<String, Double> memo, Map<String, List<Integer>> pathMemo) {
        String key = mask + "," + pos + "," + start;
        
        if (memo.containsKey(key)) {
            return memo.get(key);
        }

        // Si todos los nodos han sido visitados, volver al inicio
        if (mask == (1 << n) - 1) {
            double cost = dist[pos][start];
            memo.put(key, cost);
            List<Integer> path = new ArrayList<>();
            path.add(pos);
            path.add(start);
            pathMemo.put(key, path);
            return cost;
        }

        double minCost = Double.MAX_VALUE;
        List<Integer> bestPath = null;

        for (int next = 0; next < n; next++) {
            if ((mask & (1 << next)) == 0) {
                int newMask = mask | (1 << next);
                double cost = dist[pos][next] + tspDP(newMask, next, start, n, dist, memo, pathMemo);
                
                if (cost < minCost) {
                    minCost = cost;
                    String nextKey = newMask + "," + next + "," + start;
                    List<Integer> nextPath = pathMemo.get(nextKey);
                    if (nextPath != null) {
                        bestPath = new ArrayList<>();
                        bestPath.add(pos);
                        bestPath.addAll(nextPath);
                    }
                }
            }
        }

        memo.put(key, minCost);
        if (bestPath != null) {
            pathMemo.put(key, bestPath);
        }
        return minCost;
    }
}

