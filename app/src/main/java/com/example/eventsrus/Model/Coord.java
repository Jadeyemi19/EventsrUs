package com.example.eventsrus.Model;

public class Coord {
    public double lon;
    public double lat;


    public Coord() {
    }

    public double getLon() {
        return lon;
    }

    public void setLon(double lon) {
        this.lon = lon;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(long lat) {
        this.lat = lat;
    }

    @Override
    public String toString(){
        return new StringBuilder("[").append(this.lat).append(',').append(this.lon).append(']').toString();
    }
}
