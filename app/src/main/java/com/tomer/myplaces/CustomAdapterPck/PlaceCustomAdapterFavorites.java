package com.tomer.myplaces.CustomAdapterPck;

import android.content.Context;
import android.support.annotation.NonNull;
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

public class PlaceCustomAdapterFavorites extends ArrayAdapter<PlaceModel> {

    private Context mContext;  //Context
    private ArrayList<PlaceModel> mPlaceList;  // ArrayList of PlaceModel

    public PlaceCustomAdapterFavorites(Context context_, ArrayList<PlaceModel> places_) {
        super(context_, 0, places_);
        mContext = context_;
        mPlaceList = places_;
    }

    @NonNull
    @Override
    public View getView(int position, @NonNull View convertView, @NonNull ViewGroup parent) {
        View listItem = convertView;
        if (listItem == null) {
            listItem = LayoutInflater.from(mContext).inflate(R.layout.place_item_row_favorites, parent, false);
        }

        PlaceModel currentMap = getItem(position);  // Position of items
        if (currentMap != null) {  // If the position of the items not null
            try {
                // Put the image in image3
                ImageView image1 = listItem.findViewById(R.id.image3);
                Picasso.get().load("https://maps.googleapis.com/maps/api/place/photo?maxwidth=400&photoreference="
                        + currentMap.getPhoto_reference() +
                        "&key=" + mContext.getString(R.string.api_key_search)).into(image1);
            } catch (Exception e) {

            }

            // Put the text in name3
            TextView name1 = listItem.findViewById(R.id.name3);
            name1.setText(String.valueOf(currentMap.getName()));

            // Put the text in address3
            TextView address1 = listItem.findViewById(R.id.address3);
            address1.setText(String.valueOf(currentMap.getVicinity()));
        }
        return listItem;
    }

}
