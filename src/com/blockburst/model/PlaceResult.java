package com.blockburst.model;

public class PlaceResult {
    private final boolean valid;
    private final int pointsGained;
    private final ClearResult clearResult;

    private PlaceResult(boolean valid, int points, ClearResult clear) {
        this.valid = valid;
        this.pointsGained = points;
        this.clearResult = clear;
    }

    public static PlaceResult invalid() { return new PlaceResult(false, 0, new ClearResult()); }
    public static PlaceResult ok(int points, ClearResult clear) { return new PlaceResult(true, points, clear); }

    public boolean isValid() { return valid; }
    public int getPointsGained() { return pointsGained; }
    public ClearResult getClearResult() { return clearResult; }
}