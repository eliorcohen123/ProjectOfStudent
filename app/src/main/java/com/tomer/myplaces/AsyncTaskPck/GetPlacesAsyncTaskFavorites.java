package com.tomer.myplaces.AsyncTaskPck;

import android.os.AsyncTask;
import android.widget.ListView;

import com.tomer.myplaces.DataPck.PlaceModel;
import com.tomer.myplaces.CustomAdapterPck.PlaceCustomAdapterFavorites;
import com.tomer.myplaces.DataPck.PlaceDBHelperFavorites;

import java.util.ArrayList;

public class GetPlacesAsyncTaskFavorites extends AsyncTask<PlaceDBHelperFavorites, Integer, ArrayList<PlaceModel>> {

    private ListView mListView;  // Initialize of ListView
    private PlaceCustomAdapterFavorites mPlaceCustomAdapterFavorites;  // Initialize of PlaceCustomAdapterFavorites
    private ArrayList<PlaceModel> mPlaceList;  // Initialize of ArrayList of PlaceModel

    // AsyncTask to the ListView
    public GetPlacesAsyncTaskFavorites(ListView list) {
        mListView = list;
    }

    // DoInBackground of the ArrayList of PlaceModel that put the getAllPlaces in the SQLiteHelper in the ArrayList of PlaceModel
    @Override
    protected ArrayList<PlaceModel> doInBackground(PlaceDBHelperFavorites... mapDBHelperFavorites) {
        PlaceDBHelperFavorites myDb = mapDBHelperFavorites[0];
        mPlaceList = myDb.getAllPlaces();

        return mPlaceList;
    }

    // execute to add maps manually
    @Override
    protected void onPostExecute(ArrayList<PlaceModel> placeModels) {
        super.onPostExecute(placeModels);
        mPlaceCustomAdapterFavorites = new PlaceCustomAdapterFavorites(mListView.getContext(), placeModels);
        mListView.setAdapter(mPlaceCustomAdapterFavorites);
    }

}
