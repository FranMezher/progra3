package com.example.demo.controller;

import com.example.demo.model.GraphNode;
import com.example.demo.repository.NodeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/test")
@CrossOrigin(origins = "*")
public class Neo4jTestController {

    @Autowired
    private NodeRepository nodeRepository;

    @GetMapping("/health")
    public ResponseEntity<Map<String, String>> healthCheck() {
        Map<String, String> response = new HashMap<>();
        try {
            Long count = nodeRepository.countAllNodes();
            response.put("status", "connected");
            response.put("message", "Neo4j connection successful");
            response.put("totalNodes", count.toString());
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("status", "error");
            response.put("message", "Neo4j connection failed: " + e.getMessage());
            return ResponseEntity.status(500).body(response);
        }
    }

    @PostMapping("/nodes")
    public ResponseEntity<GraphNode> createNode(@RequestBody Map<String, String> request) {
        String name = request.get("name");
        GraphNode node = new GraphNode(name);
        GraphNode savedNode = nodeRepository.save(node);
        return ResponseEntity.ok(savedNode);
    }

    @GetMapping("/nodes")
    public ResponseEntity<List<GraphNode>> getAllNodes() {
        List<GraphNode> nodes = nodeRepository.findAll();
        return ResponseEntity.ok(nodes);
    }
}

