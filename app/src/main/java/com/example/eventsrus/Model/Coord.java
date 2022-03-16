package com.example.eventsrus.Model;

public class Coord {
    public int lon;
    public int lat;


    public Coord() {
    }

    public int getLon() {
        return lon;
    }

    public void setLon(int lon) {
        this.lon = lon;
    }

    public int getLat() {
        return lat;
    }

    public void setLat(int lat) {
        this.lat = lat;
    }

    @Override
    public String toString(){
        return new StringBuilder("[").append(this.lat).append(',').append(this.lon).append(']').toString();
    }
}
