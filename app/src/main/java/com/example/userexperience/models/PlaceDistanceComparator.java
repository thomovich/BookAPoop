package com.example.userexperience.models;

import java.util.Comparator;

public class PlaceDistanceComparator implements Comparator<PlacesToBook> {
    @Override
    public int compare(PlacesToBook left, PlacesToBook right) {
        return Double.compare(left.getDistancetouser(),right.getDistancetouser());
    }
}
