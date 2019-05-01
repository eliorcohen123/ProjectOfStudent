package com.tomer.myplaces.CustomAdapterPck;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.tomer.myplaces.DataPck.PlaceModel;
import com.tomer.myplaces.R;

import java.util.ArrayList;

public class PlaceCustomAdapterSearch extends ArrayAdapter<PlaceModel> {

    private Context mContext;  //Context
    private ArrayList<PlaceModel> mPlaceList;  // ArrayList of PlaceModel
    private Location location;
    private LocationManager locationManager;
    private Criteria criteria;

    public PlaceCustomAdapterSearch(Context context_, ArrayList<PlaceModel> places_) {
        super(context_, 0, places_);
        mContext = context_;
        mPlaceList = places_;
    }

    @NonNull
    @Override
    public View getView(int position, @NonNull View convertView, @NonNull ViewGroup parent) {
        View listItem = convertView;
        if (listItem == null) {
            listItem = LayoutInflater.from(mContext).inflate(R.layout.place_item_row_search, parent, false);
        }

        PlaceModel currentPlaces = getItem(position);  // Position of items
        if (currentPlaces != null) {  // If the position of the items not null
            locationManager = (LocationManager) getContext().getSystemService(Context.LOCATION_SERVICE);
            criteria = new Criteria();
            String provider = locationManager.getBestProvider(criteria, true);
            if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION);
            }// TODO: Consider calling
//    ActivityCompat#requestPermissions
// here to request the missing permissions, and then overriding
//   public void onRequestPermissionsResult(int requestCode, String[] permissions,
//                                          int[] grantResults)
// to handle the case where the user grants the permission. See the documentation
// for ActivityCompat#requestPermissions for more details.
            if (provider != null) {
                location = locationManager.getLastKnownLocation(provider);
                if (location != null) {
                    double distanceMe;
                    Location locationA = new Location("Point A");
                    locationA.setLatitude(currentPlaces.getLat());
                    locationA.setLongitude(currentPlaces.getLng());
                    Location locationB = new Location("Point B");
                    locationB.setLatitude(location.getLatitude());
                    locationB.setLongitude(location.getLongitude());
                    distanceMe = locationA.distanceTo(locationB) / 1000;   // in km

                    try {
                        ImageView image1 = listItem.findViewById(R.id.image1);
                        Picasso.get().load("https://maps.googleapis.com/maps/api/place/photo?maxwidth=400&photoreference="
                                + currentPlaces.getPhoto_reference() +
                                "&key=" + mContext.getString(R.string.api_key_search)).into(image1);
                    } catch (Exception e) {

                    }

                    // Put the text in name1
                    TextView name1 = listItem.findViewById(R.id.name1);
                    name1.setText(String.valueOf(currentPlaces.getName()));

                    // Put the text in address1
                    TextView address1 = listItem.findViewById(R.id.address1);
                    address1.setText(String.valueOf(currentPlaces.getVicinity()));

                    String distanceKm1;
                    if (distanceMe < 1) {
                        int dis = (int) (distanceMe * 1000);
                        distanceKm1 = "\n" + "Meters: " + String.valueOf(dis);
                        TextView km1 = listItem.findViewById(R.id.kmMe1);
                        km1.setText(distanceKm1);
                    } else if (distanceMe >= 1) {
                        String disM = String.format("%.2f", distanceMe);
                        distanceKm1 = "\n" + "Km: " + String.valueOf(disM);
                        // Put the text in kmMe1
                        TextView km1 = listItem.findViewById(R.id.kmMe1);
                        km1.setText(distanceKm1);
                    }
                }
            }
        }
        return listItem;
    }

}
