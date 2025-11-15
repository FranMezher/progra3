package com.example.demo.repository;

import com.example.demo.model.Location;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LocationRepository extends Neo4jRepository<Location, Long> {

    Optional<Location> findByName(String name);

    List<Location> findAll();
    
    // Buscar ubicación con todas sus relaciones cargadas
    @Query("MATCH (l:Location {name: $name})-[r:CONNECTED_TO]->(dest:Location) " +
           "RETURN l, collect(r) as routes, collect(dest) as destinations")
    Optional<Location> findByNameWithRoutes(@Param("name") String name);

    @Query("MATCH (l:Location) WHERE l.name = $name RETURN l")
    Optional<Location> findByLocationName(@Param("name") String name);

    @Query("MATCH (l:Location) RETURN count(l) as total")
    Long countAllLocations();

    // Get all neighbors of a location
    @Query("MATCH (l:Location {name: $name})-[:CONNECTED_TO]->(neighbor:Location) RETURN neighbor")
    List<Location> findNeighbors(@Param("name") String name);

    // Get all locations with their routes
    @Query("MATCH (l:Location)-[r:CONNECTED_TO]->(dest:Location) RETURN l, r, dest")
    List<Location> findAllWithRoutes();

    // Find path between two locations (for algorithms)
    @Query("MATCH path = (start:Location {name: $startName})-[*]-(end:Location {name: $endName}) RETURN path LIMIT 1")
    List<Location> findPathBetween(@Param("startName") String startName, @Param("endName") String endName);

    // Get all routes from a location
    @Query("MATCH (l:Location {name: $name})-[r:CONNECTED_TO]->(dest:Location) RETURN dest, r.distance as distance, r.duration as duration, r.cost as cost")
    List<Object[]> findRoutesFromLocation(@Param("name") String name);

    // Crear relación con propiedades usando Cypher (MERGE para evitar duplicados)
    @Query("MATCH (from:Location {name: $fromName}), (to:Location {name: $toName}) " +
           "MERGE (from)-[r:CONNECTED_TO]->(to) " +
           "SET r.distance = $distance, r.duration = $duration, r.cost = $cost, r.roadType = $roadType")
    void createRoute(@Param("fromName") String fromName, 
                     @Param("toName") String toName,
                     @Param("distance") Double distance,
                     @Param("duration") Integer duration,
                     @Param("cost") Double cost,
                     @Param("roadType") String roadType);

    // Eliminar TODOS los nodos y relaciones (limpieza completa)
    @Query("MATCH (n) DETACH DELETE n")
    void deleteAllNodesAndRelationships();
    
    // Eliminar solo nodos Location y sus relaciones
    @Query("MATCH (n:Location) DETACH DELETE n")
    void deleteAllLocations();
    
    // Contar relaciones
    @Query("MATCH ()-[r:CONNECTED_TO]->() RETURN count(r) as total")
    Long countAllRelationships();
}

