package com.example.demo.model;

import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Property;
import org.springframework.data.neo4j.core.schema.RelationshipProperties;
import org.springframework.data.neo4j.core.schema.TargetNode;

import java.util.Objects;

@RelationshipProperties
public class Route {

    @Id
    private Long id;

    @Property("distance")
    private Double distance; // in kilometers

    @Property("duration")
    private Integer duration; // in minutes

    @Property("cost")
    private Double cost; // cost of the route

    @Property("roadType")
    private String roadType; // "highway", "city", "rural"

    @TargetNode
    private Location destination;

    // Constructors
    public Route() {
    }

    public Route(Location destination, Double distance, Integer duration) {
        this.destination = destination;
        this.distance = distance;
        this.duration = duration;
        this.cost = distance * 0.1; // Default cost calculation
        this.roadType = "city";
    }

    public Route(Location destination, Double distance, Integer duration, Double cost, String roadType) {
        this.destination = destination;
        this.distance = distance;
        this.duration = duration;
        this.cost = cost;
        this.roadType = roadType;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getDistance() {
        return distance;
    }

    public void setDistance(Double distance) {
        this.distance = distance;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public Double getCost() {
        return cost;
    }

    public void setCost(Double cost) {
        this.cost = cost;
    }

    public String getRoadType() {
        return roadType;
    }

    public void setRoadType(String roadType) {
        this.roadType = roadType;
    }

    public Location getDestination() {
        return destination;
    }

    public void setDestination(Location destination) {
        this.destination = destination;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Route route = (Route) o;
        return Objects.equals(id, route.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}

