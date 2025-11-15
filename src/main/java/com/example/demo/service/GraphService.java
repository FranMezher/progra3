package com.example.demo.service;

import com.example.demo.model.Location;
import com.example.demo.model.Route;
import com.example.demo.repository.LocationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * Servicio auxiliar para cargar ubicaciones con sus relaciones
 */
@Service
public class GraphService {

    @Autowired
    private LocationRepository locationRepository;

    /**
     * Carga una ubicación con todas sus relaciones desde la base de datos
     */
    public Location loadLocationWithRoutes(Location location) {
        if (location == null) return null;
        
        // Obtener todas las rutas desde esta ubicación usando query directa
        List<Object[]> routesData = locationRepository.findRoutesFromLocation(location.getName());
        
        // Crear objetos Route y agregarlos a la ubicación
        Set<Route> routes = new HashSet<>();
        Map<String, Location> nameToLocation = new HashMap<>();
        
        // Primero cargar todas las ubicaciones para mapear nombres
        List<Location> allLocations = locationRepository.findAll();
        for (Location loc : allLocations) {
            nameToLocation.put(loc.getName(), loc);
        }
        
        // Crear las rutas (sin cargar relaciones de destinos para evitar recursión)
        for (Object[] routeData : routesData) {
            Location dest = (Location) routeData[0];
            Double distance = (Double) routeData[1];
            Integer duration = (Integer) routeData[2];
            Double cost = (Double) routeData[3];
            
            // Buscar el destino en el mapa
            Location destination = nameToLocation.get(dest.getName());
            if (destination != null) {
                // Crear destino sin relaciones para evitar recursión infinita
                Location destWithoutRoutes = new Location(destination.getName(), 
                    destination.getLatitude(), destination.getLongitude(), destination.getType());
                destWithoutRoutes.setId(destination.getId());
                destWithoutRoutes.setRoutes(new HashSet<>()); // Sin relaciones para evitar recursión
                
                Route route = new Route(destWithoutRoutes, distance, duration, cost, "highway");
                routes.add(route);
            }
        }
        
        location.setRoutes(routes);
        return location;
    }
    
    /**
     * Carga todas las ubicaciones con sus relaciones
     */
    public List<Location> loadAllLocationsWithRoutes() {
        List<Location> allLocations = locationRepository.findAll();
        List<Location> locationsWithRoutes = new ArrayList<>();
        
        for (Location loc : allLocations) {
            locationsWithRoutes.add(loadLocationWithRoutes(loc));
        }
        
        return locationsWithRoutes;
    }
}

