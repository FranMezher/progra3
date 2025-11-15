package com.example.demo.controller;

import com.example.demo.model.Location;
import com.example.demo.model.Route;
import com.example.demo.repository.LocationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

/**
 * Controlador para cargar datos de ejemplo en Neo4j
 * Útil para probar los algoritmos
 */
@RestController
@RequestMapping("/api/data")
@CrossOrigin(origins = "*")
public class DataController {

    @Autowired
    private LocationRepository locationRepository;

    /**
     * Carga datos de ejemplo: Red de ciudades argentinas con rutas bidireccionales
     * Grafo completo para probar todos los algoritmos
     */
    @PostMapping("/load-sample")
    public ResponseEntity<Map<String, Object>> loadSampleData() {
        Map<String, Object> response = new HashMap<>();

        try {
            // Limpiar datos existentes
            locationRepository.deleteAll();

            // Crear ubicaciones (ciudades argentinas principales)
            Location buenosAires = new Location("Buenos Aires", -34.6037, -58.3816, "city");
            Location cordoba = new Location("Córdoba", -31.4201, -64.1888, "city");
            Location rosario = new Location("Rosario", -32.9442, -60.6505, "city");
            Location mendoza = new Location("Mendoza", -32.8895, -68.8458, "city");
            Location laPlata = new Location("La Plata", -34.9215, -57.9545, "city");
            Location marDelPlata = new Location("Mar del Plata", -38.0055, -57.5426, "city");
            Location tucuman = new Location("Tucumán", -26.8083, -65.2176, "city");
            Location salta = new Location("Salta", -24.7859, -65.4117, "city");

            // Guardar todas las ubicaciones primero
            buenosAires = locationRepository.save(buenosAires);
            cordoba = locationRepository.save(cordoba);
            rosario = locationRepository.save(rosario);
            mendoza = locationRepository.save(mendoza);
            laPlata = locationRepository.save(laPlata);
            marDelPlata = locationRepository.save(marDelPlata);
            tucuman = locationRepository.save(tucuman);
            salta = locationRepository.save(salta);

            // Crear rutas bidireccionales (grafo no dirigido)
            // Distancias en km, duración en minutos, costo aproximado
            
            // Buenos Aires <-> Córdoba (700 km, 8 horas)
            createBidirectionalRoute(buenosAires, cordoba, 700.0, 480, 70.0, "highway");
            
            // Buenos Aires <-> Rosario (300 km, 3.5 horas)
            createBidirectionalRoute(buenosAires, rosario, 300.0, 210, 30.0, "highway");
            
            // Buenos Aires <-> La Plata (60 km, 1 hora)
            createBidirectionalRoute(buenosAires, laPlata, 60.0, 60, 6.0, "city");
            
            // Buenos Aires <-> Mar del Plata (400 km, 4.5 horas)
            createBidirectionalRoute(buenosAires, marDelPlata, 400.0, 270, 40.0, "highway");
            
            // Rosario <-> Córdoba (400 km, 4.5 horas)
            createBidirectionalRoute(rosario, cordoba, 400.0, 270, 40.0, "highway");
            
            // Córdoba <-> Mendoza (400 km, 5 horas)
            createBidirectionalRoute(cordoba, mendoza, 400.0, 300, 40.0, "highway");
            
            // Córdoba <-> Tucumán (500 km, 6 horas)
            createBidirectionalRoute(cordoba, tucuman, 500.0, 360, 50.0, "highway");
            
            // Tucumán <-> Salta (200 km, 2.5 horas)
            createBidirectionalRoute(tucuman, salta, 200.0, 150, 20.0, "highway");
            
            // La Plata <-> Mar del Plata (400 km, 4.5 horas)
            createBidirectionalRoute(laPlata, marDelPlata, 400.0, 270, 40.0, "highway");
            
            // Rosario <-> La Plata (350 km, 4 horas)
            createBidirectionalRoute(rosario, laPlata, 350.0, 240, 35.0, "highway");

            response.put("status", "success");
            response.put("message", "Datos de ejemplo cargados correctamente");
            response.put("locations", Arrays.asList(
                "Buenos Aires", "Córdoba", "Rosario", "Mendoza", 
                "La Plata", "Mar del Plata", "Tucumán", "Salta"
            ));
            response.put("totalLocations", 8);
            response.put("totalRoutes", 20); // 10 rutas bidireccionales = 20 conexiones
            response.put("description", "Red de ciudades argentinas con rutas bidireccionales para probar algoritmos de grafos");

            return ResponseEntity.ok(response);

        } catch (Exception e) {
            response.put("status", "error");
            response.put("message", "Error al cargar datos: " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.status(500).body(response);
        }
    }

    /**
     * Helper method para crear rutas bidireccionales
     */
    private void createBidirectionalRoute(Location from, Location to, 
                                          Double distance, Integer duration, 
                                          Double cost, String roadType) {
        // Ruta de ida
        Route route1 = new Route(to, distance, duration, cost, roadType);
        from.getRoutes().add(route1);
        locationRepository.save(from);

        // Ruta de vuelta
        Route route2 = new Route(from, distance, duration, cost, roadType);
        to.getRoutes().add(route2);
        locationRepository.save(to);
    }

    /**
     * Limpia todos los datos
     */
    @DeleteMapping("/clear")
    public ResponseEntity<Map<String, String>> clearAllData() {
        Map<String, String> response = new HashMap<>();
        try {
            locationRepository.deleteAll();
            response.put("status", "success");
            response.put("message", "Todos los datos han sido eliminados");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("status", "error");
            response.put("message", "Error al limpiar datos: " + e.getMessage());
            return ResponseEntity.status(500).body(response);
        }
    }

    /**
     * Lista todas las ubicaciones
     */
    @GetMapping("/locations")
    public ResponseEntity<List<Location>> getAllLocations() {
        List<Location> locations = locationRepository.findAll();
        return ResponseEntity.ok(locations);
    }
}

