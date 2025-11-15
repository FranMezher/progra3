package com.example.demo.repository;

import com.example.demo.model.GraphNode;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface NodeRepository extends Neo4jRepository<GraphNode, Long> {

    Optional<GraphNode> findByName(String name);

    List<GraphNode> findAll();

    @Query("MATCH (n:Node) WHERE n.name = $name RETURN n")
    Optional<GraphNode> findByNodeName(@Param("name") String name);

    @Query("MATCH (n:Node) RETURN count(n) as total")
    Long countAllNodes();

    @Query("MATCH (a:Node)-[:CONNECTED_TO]->(b:Node) RETURN a, b")
    List<GraphNode> findAllConnected();
}

