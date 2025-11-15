package com.example.demo.service;

import com.example.demo.model.Location;
import com.example.demo.repository.LocationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * Divide y Vencerás - Quicksort y Mergesort
 * Ordena ubicaciones por diferentes criterios
 */
@Service
public class DivideConquerService {

    @Autowired
    private LocationRepository locationRepository;

    /**
     * Quicksort - Ordena ubicaciones por nombre, distancia o coordenadas
     */
    public List<Location> quicksortLocations(String sortBy) {
        List<Location> locations = new ArrayList<>(locationRepository.findAll());
        quicksort(locations, 0, locations.size() - 1, sortBy);
        return locations;
    }

    private void quicksort(List<Location> arr, int low, int high, String sortBy) {
        if (low < high) {
            int pi = partition(arr, low, high, sortBy);
            quicksort(arr, low, pi - 1, sortBy);
            quicksort(arr, pi + 1, high, sortBy);
        }
    }

    private int partition(List<Location> arr, int low, int high, String sortBy) {
        Location pivot = arr.get(high);
        int i = low - 1;

        for (int j = low; j < high; j++) {
            if (compare(arr.get(j), pivot, sortBy) <= 0) {
                i++;
                Collections.swap(arr, i, j);
            }
        }
        Collections.swap(arr, i + 1, high);
        return i + 1;
    }

    /**
     * Mergesort - Ordena ubicaciones por nombre, distancia o coordenadas
     */
    public List<Location> mergesortLocations(String sortBy) {
        List<Location> locations = new ArrayList<>(locationRepository.findAll());
        mergesort(locations, 0, locations.size() - 1, sortBy);
        return locations;
    }

    private void mergesort(List<Location> arr, int left, int right, String sortBy) {
        if (left < right) {
            int mid = left + (right - left) / 2;
            mergesort(arr, left, mid, sortBy);
            mergesort(arr, mid + 1, right, sortBy);
            merge(arr, left, mid, right, sortBy);
        }
    }

    private void merge(List<Location> arr, int left, int mid, int right, String sortBy) {
        int n1 = mid - left + 1;
        int n2 = right - mid;

        List<Location> leftArr = new ArrayList<>();
        List<Location> rightArr = new ArrayList<>();

        for (int i = 0; i < n1; i++) leftArr.add(arr.get(left + i));
        for (int j = 0; j < n2; j++) rightArr.add(arr.get(mid + 1 + j));

        int i = 0, j = 0, k = left;

        while (i < n1 && j < n2) {
            if (compare(leftArr.get(i), rightArr.get(j), sortBy) <= 0) {
                arr.set(k, leftArr.get(i));
                i++;
            } else {
                arr.set(k, rightArr.get(j));
                j++;
            }
            k++;
        }

        while (i < n1) {
            arr.set(k, leftArr.get(i));
            i++;
            k++;
        }

        while (j < n2) {
            arr.set(k, rightArr.get(j));
            j++;
            k++;
        }
    }

    private int compare(Location a, Location b, String sortBy) {
        switch (sortBy.toLowerCase()) {
            case "name":
                return a.getName().compareToIgnoreCase(b.getName());
            case "latitude":
                return Double.compare(a.getLatitude(), b.getLatitude());
            case "longitude":
                return Double.compare(a.getLongitude(), b.getLongitude());
            default:
                return a.getName().compareToIgnoreCase(b.getName());
        }
    }
}

