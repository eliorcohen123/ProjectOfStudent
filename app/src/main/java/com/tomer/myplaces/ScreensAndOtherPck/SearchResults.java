package com.tomer.myplaces.ScreensAndOtherPck;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import com.tomer.myplaces.DataPck.PlaceModel;
import com.tomer.myplaces.R;

public class SearchResults extends AppCompatActivity implements SearchInterface {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_results);

        FragmentSearch fragmentSearch = new FragmentSearch();
        FragmentResult fragmentResult = new FragmentResult();
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragmentSh, fragmentSearch);
        fragmentTransaction.replace(R.id.fragmentLt, fragmentResult);
        fragmentTransaction.commit();
    }

    @Override
    public void onLocationItemClick(PlaceModel placeModel) {
        FragmentResult fragmentResult = new FragmentResult();
        Bundle bundle = new Bundle();
        bundle.putSerializable(getString(R.string.map_search_key), placeModel);
        fragmentResult.setArguments(bundle);
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragmentLt, fragmentResult);
        fragmentTransaction.addToBackStack(null);
        try {
            fragmentTransaction.commit();
        } catch (Exception e) {

        }
    }

}
