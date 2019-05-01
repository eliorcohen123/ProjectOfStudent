package com.tomer.myplaces.ScreensAndOtherPck;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.tomer.myplaces.DataPck.PlaceDBHelperFavorites;
import com.tomer.myplaces.DataPck.PlaceModel;
import com.tomer.myplaces.R;

public class PlaceDetails extends AppCompatActivity {

    private TextView name1, address1, rating1, km1, isOpen;
    private Location location;
    private LocationManager locationManager;
    private Criteria criteria;
    private PlaceDBHelperFavorites placeDBHelperFavorites;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.screen_details);

        // GetSerializable for the texts
        final PlaceModel item = (PlaceModel) getIntent().getExtras().getSerializable(getString(R.string.key_screen_details));

        name1 = findViewById(R.id.name1);  // ID of the name1
        address1 = findViewById(R.id.address1);  // ID of the address1
        rating1 = findViewById(R.id.rating1);
        km1 = findViewById(R.id.km1);
        isOpen = findViewById(R.id.isOpen1);

        assert item != null;  // If the item not null
        name1.setText(item.getName());  // GetSerializable of name1
        address1.setText(item.getVicinity());  // GetSerializable of address1
        rating1.setText(String.valueOf(item.getRating()));  // GetSerializable of rating1
        if (String.valueOf(item.getOpening_hours()).equals("true")) {
            isOpen.setText("Open");
        } else if (String.valueOf(item.getOpening_hours()).equals("false")) {
            isOpen.setText("Close");
        } else {
            isOpen.setText("No information");
        }

        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        criteria = new Criteria();
        String provider = locationManager.getBestProvider(criteria, true);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION);
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
                android.location.Location locationA = new android.location.Location("Point A");
                locationA.setLatitude(item.getLat());
                locationA.setLongitude(item.getLng());
                android.location.Location locationB = new Location("Point B");
                locationB.setLatitude(location.getLatitude());
                locationB.setLongitude(location.getLongitude());
                distanceMe = locationA.distanceTo(locationB) / 1000;   // in km
                if (distanceMe < 1) {
                    int dis = (int) (distanceMe * 1000);
                    km1.setText(String.valueOf(dis));
                } else if (distanceMe >= 1) {
                    String disM = String.format("%.2f", distanceMe);
                    km1.setText(disM);
                }
            }
        }

        //Initialize the ImageView
        String picture = "https://maps.googleapis.com/maps/api/place/photo?maxwidth=400&photoreference="
                + item.getPhoto_reference() +
                "&key=" +
                getString(R.string.api_key_search);
        final ImageView imageView = findViewById(R.id.image11);
        Picasso.get().load(picture).into(imageView);

        // Button are back to the previous activity
        Button button3 = findViewById(R.id.backBtn);
        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        Button button4 = findViewById(R.id.saveBtn);
        button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = name1.getText().toString();  // GetText of the name1
                String address = address1.getText().toString();  // GetText of the address1
                String rating = "1";  // GetText of the lat
                String km = "1";  // GetText of the lng
                String photo = item.getPhoto_reference();  // GetText of the photo
                double rating2 = Double.parseDouble(rating);
                double km2 = Double.parseDouble(km);

                placeDBHelperFavorites = new PlaceDBHelperFavorites(PlaceDetails.this);
                placeDBHelperFavorites.addPlaceFav(name, address, rating2, km2, photo);

                Intent intNew = new Intent(PlaceDetails.this, MainActivity.class);
                startActivity(intNew);
            }
        });
    }

}
