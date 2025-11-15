package com.example.demo.dto;

import java.util.List;

public class BFSResponse {
    private String startLocation;
    private List<String> reachableLocations;
    private Integer maxDistance;
    private Integer levelsExplored;
    private String message;

    public BFSResponse() {
    }

    public BFSResponse(String startLocation, List<String> reachableLocations, Integer maxDistance, Integer levelsExplored) {
        this.startLocation = startLocation;
        this.reachableLocations = reachableLocations;
        this.maxDistance = maxDistance;
        this.levelsExplored = levelsExplored;
    }

    // Getters and Setters
    public String getStartLocation() {
        return startLocation;
    }

    public void setStartLocation(String startLocation) {
        this.startLocation = startLocation;
    }

    public List<String> getReachableLocations() {
        return reachableLocations;
    }

    public void setReachableLocations(List<String> reachableLocations) {
        this.reachableLocations = reachableLocations;
    }

    public Integer getMaxDistance() {
        return maxDistance;
    }

    public void setMaxDistance(Integer maxDistance) {
        this.maxDistance = maxDistance;
    }

    public Integer getLevelsExplored() {
        return levelsExplored;
    }

    public void setLevelsExplored(Integer levelsExplored) {
        this.levelsExplored = levelsExplored;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}

