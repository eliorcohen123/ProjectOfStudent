package com.tomer.myplaces.DataPck;

import java.io.Serializable;
import java.util.List;

public class PlaceModel implements Serializable {

    private double lat;
    private double lng;
    private String vicinity;
    private String name;
    private String id;
    private String photo_reference;
    private OpeningHours opening_hours;
    private double rating;
    private int user_ratings_total;
    private List<Photos> photos;
    private Geometry geometry;
    private String distance;

    // Subject class to the SQLiteHelper
    public PlaceModel(String name, String vicinity, double rating, String distance, List<Photos> photos) {
        this.name = name;
        this.vicinity = vicinity;
        this.rating = rating;
        this.distance = distance;
        this.photos = photos;
        this.photo_reference = photos.get(0).getPhoto_reference();
    }

    public PlaceModel() {

    }


    public String getPhoto_reference() {
        return photos.get(0).getPhoto_reference();
    }

    public void setPhoto_reference(String photo_reference) {
        this.photos.get(0).setPhoto_reference(photo_reference);
    }

    public boolean getOpening_hours() {
        return opening_hours.isOpen_now();
    }

    public void setOpening_hours(OpeningHours opening_hours) {
        this.opening_hours.isOpen_now();
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public int getUser_ratings_total() {
        return user_ratings_total;
    }

    public void setUser_ratings_total(int user_ratings_total) {
        this.user_ratings_total = user_ratings_total;
    }

    public double getLng() {
        return geometry.getLocation().getLng();
    }

    public void setLng(double lng) {
        this.geometry.getLocation().setLng(lng);
    }

    public double getLat() {
        return geometry.getLocation().getLat();
    }

    public void setLat(double lat) {
        this.geometry.getLocation().setLat(lat);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getVicinity() {
        return vicinity;
    }

    public void setVicinity(String vicinity) {
        this.vicinity = vicinity;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }

}
