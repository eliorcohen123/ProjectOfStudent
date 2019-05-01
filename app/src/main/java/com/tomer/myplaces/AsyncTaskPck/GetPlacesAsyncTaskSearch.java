package com.tomer.myplaces.AsyncTaskPck;

import android.os.AsyncTask;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.tomer.myplaces.ScreensAndOtherPck.ConApp;
import com.tomer.myplaces.DataPck.PlaceModel;
import com.tomer.myplaces.ScreensAndOtherPck.FragmentSearch;
import com.tomer.myplaces.DataPck.PlacesDBHelperSearch;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class GetPlacesAsyncTaskSearch extends AsyncTask<String, Integer, ArrayList<PlaceModel>> {

    private PlacesDBHelperSearch placesDBHelperSearch;

    // startShowingProgressBar of FragmentSearch
    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        FragmentSearch.startShowingProgressBar();
    }

    // DoInBackground of the JSON
    @Override
    protected ArrayList<PlaceModel> doInBackground(String... urls) {
        OkHttpClient client = new OkHttpClient();
        String urlQuery = urls[0];
        Request request = new Request.Builder()
                .url(urlQuery)
                .build();
        Response response = null;
        try {
            response = client.newCall(request).execute();
        } catch (IOException e) {
            e.printStackTrace();
        }
        assert response != null;
        if (!response.isSuccessful()) try {
            throw new IOException("Unexpected code " + response);
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            assert response.body() != null;
            return getPlacesListFromJson(response.body().string());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    // Get places from the JSON
    private ArrayList<PlaceModel> getPlacesListFromJson(String jsonResponse) {
        List<PlaceModel> stubPlaceData = new ArrayList<>();
        Gson gson = new GsonBuilder().create();
        PlaceResponse response = gson.fromJson(jsonResponse, PlaceResponse.class);
        stubPlaceData = response.results;
        ArrayList<PlaceModel> arrList = new ArrayList<>();
        arrList.addAll(stubPlaceData);

        return arrList;
    }

    //The response of the JSON
    public class PlaceResponse {
        List<PlaceModel> results;

        public PlaceResponse() {
            results = new ArrayList<>();
        }
    }

    // execute the following:
    @Override
    protected void onPostExecute(ArrayList<PlaceModel> placeModels) {
        super.onPostExecute(placeModels);
        FragmentSearch.stopShowingProgressBar();
        try {
            placesDBHelperSearch = new PlacesDBHelperSearch(ConApp.getmContext());
            FragmentSearch.setPlaces(placesDBHelperSearch.getAllPlaces());
            FragmentSearch.setPlaces(placeModels);
        } catch (Exception e) {
            FragmentSearch.setPlaces(placeModels);
        }
    }

}
