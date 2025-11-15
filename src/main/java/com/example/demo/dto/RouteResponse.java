package com.example.demo.dto;

import java.util.List;

public class RouteResponse {
    private List<String> path;
    private Double totalDistance;
    private Integer totalDuration;
    private Double totalCost;
    private String algorithm;
    private String message;

    public RouteResponse() {
    }

    public RouteResponse(List<String> path, Double totalDistance, Integer totalDuration, Double totalCost, String algorithm) {
        this.path = path;
        this.totalDistance = totalDistance;
        this.totalDuration = totalDuration;
        this.totalCost = totalCost;
        this.algorithm = algorithm;
    }

    // Getters and Setters
    public List<String> getPath() {
        return path;
    }

    public void setPath(List<String> path) {
        this.path = path;
    }

    public Double getTotalDistance() {
        return totalDistance;
    }

    public void setTotalDistance(Double totalDistance) {
        this.totalDistance = totalDistance;
    }

    public Integer getTotalDuration() {
        return totalDuration;
    }

    public void setTotalDuration(Integer totalDuration) {
        this.totalDuration = totalDuration;
    }

    public Double getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(Double totalCost) {
        this.totalCost = totalCost;
    }

    public String getAlgorithm() {
        return algorithm;
    }

    public void setAlgorithm(String algorithm) {
        this.algorithm = algorithm;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}

