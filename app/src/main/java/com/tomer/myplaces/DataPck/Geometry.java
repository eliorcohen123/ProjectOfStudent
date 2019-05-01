package com.tomer.myplaces.DataPck;

import java.io.Serializable;

  // class top of Location class
public class Geometry implements Serializable {

    private Location location;

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }


}
