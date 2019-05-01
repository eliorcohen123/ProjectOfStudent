package com.tomer.myplaces.DataPck;

import java.io.Serializable;

// class top of Photo_reference
class Photos implements Serializable {

    private String photo_reference;

    String getPhoto_reference() {
        return photo_reference;
    }

    void setPhoto_reference(String photo_reference) {
        this.photo_reference = photo_reference;
    }

}
