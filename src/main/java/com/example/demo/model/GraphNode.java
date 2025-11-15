package com.example.demo.model;

import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Property;
import org.springframework.data.neo4j.core.schema.Relationship;

import java.util.HashSet;
import java.util.Set;

@Node("Node")
public class GraphNode {

    @Id
    private Long id;

    @Property("name")
    private String name;

    @Property("value")
    private Double value;

    @Relationship(type = "CONNECTED_TO", direction = Relationship.Direction.OUTGOING)
    private Set<GraphNode> connectedNodes = new HashSet<>();

    // Constructors
    public GraphNode() {
    }

    public GraphNode(String name) {
        this.name = name;
    }

    public GraphNode(String name, Double value) {
        this.name = name;
        this.value = value;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }

    public Set<GraphNode> getConnectedNodes() {
        return connectedNodes;
    }

    public void setConnectedNodes(Set<GraphNode> connectedNodes) {
        this.connectedNodes = connectedNodes;
    }
}

