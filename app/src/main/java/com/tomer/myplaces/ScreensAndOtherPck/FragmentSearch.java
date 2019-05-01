package com.tomer.myplaces.ScreensAndOtherPck;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.tomer.myplaces.AsyncTaskPck.GetPlacesAsyncTaskSearch;
import com.tomer.myplaces.CustomAdapterPck.PlaceCustomAdapterSearch;
import com.tomer.myplaces.DataPck.PlaceModel;
import com.tomer.myplaces.DataPck.PlacesDBHelperSearch;
import com.tomer.myplaces.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class FragmentSearch extends Fragment {

    private View mView;
    private static ArrayList<PlaceModel> mPlaceList;
    private static ListView mListView;
    private static Location location;
    private static LocationManager locationManager;
    private static Criteria criteria;
    private static PlaceCustomAdapterSearch mAdapter;
    private static PlacesDBHelperSearch mPlacesDBHelperSearch;
    private static FragmentSearch mFragmentSearch;
    private GetPlacesAsyncTaskSearch mGetPlacesAsyncTaskSearch;
    private static ProgressDialog mProgressDialogInternet;  // ProgressDialog

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_layout_search, container, false);

        mListView = mView.findViewById(R.id.listSearch);

        mFragmentSearch = this;

        mPlacesDBHelperSearch = new PlacesDBHelperSearch(getActivity());
        mPlaceList = mPlacesDBHelperSearch.getAllPlaces();
        mAdapter = new PlaceCustomAdapterSearch(getActivity(), mPlaceList);

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                SearchInterface searchInterface = (SearchInterface) getActivity();
                searchInterface.onLocationItemClick(mPlaceList.get(position));
            }
        });

        mListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intentSearchToAddInternet = new Intent(getContext(), PlaceDetails.class);
                intentSearchToAddInternet.putExtra(getString(R.string.key_screen_details), mPlaceList.get(position));
                startActivity(intentSearchToAddInternet);
                return false;
            }
        });

        locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
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
            // Search places from that URL and put them in the SQLiteHelper
            if (location != null) {
                if (mAdapter != null) {
                    SharedPreferences settings1 = getActivity().getSharedPreferences("mysettings1",
                            Context.MODE_PRIVATE);
                    String myString1 = settings1.getString("mystring1", "");

                    SharedPreferences settings2 = getActivity().getSharedPreferences("mysettings2",
                            Context.MODE_PRIVATE);
                    String myString2 = settings2.getString("mystring2", "");

                    SharedPreferences settings3 = getActivity().getSharedPreferences("mysettings3",
                            Context.MODE_PRIVATE);
                    String myString3 = settings3.getString("mystring3", "");

                    SharedPreferences settings4 = getActivity().getSharedPreferences("mysettings4",
                            Context.MODE_PRIVATE);
                    String myString4 = settings4.getString("mystring4", "");

                    if (!myString1.equals("") && myString2.equals("") && myString3.equals("")) {
                        String myQuery = "https://maps.googleapis.com/maps/api/place/nearbysearch/json?location=" +
                                location.getLatitude() + "," + location.getLongitude() +
                                "&radius=50000&sensor=true&rankby=prominence&types=" + myString1 + "&keyword=" + myString4 + "&key=" +
                                getString(R.string.api_key_search);
                        mGetPlacesAsyncTaskSearch = new GetPlacesAsyncTaskSearch();
                        mGetPlacesAsyncTaskSearch.execute(myQuery);
                    } else if (!myString2.equals("") && myString1.equals("") && myString3.equals("")) {
                        String myQuery = "https://maps.googleapis.com/maps/api/place/nearbysearch/json?location=" +
                                location.getLatitude() + "," + location.getLongitude() +
                                "&radius=50000&sensor=true&rankby=prominence&types=" + myString2 + "&keyword=" + myString4 + "&key=" +
                                getString(R.string.api_key_search);
                        mGetPlacesAsyncTaskSearch = new GetPlacesAsyncTaskSearch();
                        mGetPlacesAsyncTaskSearch.execute(myQuery);
                    } else if (!myString3.equals("") && myString1.equals("") && myString2.equals("")) {
                        String myQuery = "https://maps.googleapis.com/maps/api/place/nearbysearch/json?location=" +
                                location.getLatitude() + "," + location.getLongitude() +
                                "&radius=50000&sensor=true&rankby=prominence&types=" + myString3 + "&keyword=" + myString4 + "&key=" +
                                getString(R.string.api_key_search);
                        mGetPlacesAsyncTaskSearch = new GetPlacesAsyncTaskSearch();
                        mGetPlacesAsyncTaskSearch.execute(myQuery);
                    } else if (!myString1.equals("") && !myString2.equals("") && myString3.equals("")) {
                        String myQuery = "https://maps.googleapis.com/maps/api/place/nearbysearch/json?location=" +
                                location.getLatitude() + "," + location.getLongitude() +
                                "&radius=50000&sensor=true&rankby=prominence&types=" + myString1 + "|" + myString2 + "&keyword=" + myString4 + "&key=" +
                                getString(R.string.api_key_search);
                        mGetPlacesAsyncTaskSearch = new GetPlacesAsyncTaskSearch();
                        mGetPlacesAsyncTaskSearch.execute(myQuery);
                    } else if (!myString2.equals("") && !myString3.equals("") && myString1.equals("")) {
                        String myQuery = "https://maps.googleapis.com/maps/api/place/nearbysearch/json?location=" +
                                location.getLatitude() + "," + location.getLongitude() +
                                "&radius=50000&sensor=true&rankby=prominence&types=" + myString2 + "|" + myString3 + "&keyword=" + myString4 + "&key=" +
                                getString(R.string.api_key_search);
                        mGetPlacesAsyncTaskSearch = new GetPlacesAsyncTaskSearch();
                        mGetPlacesAsyncTaskSearch.execute(myQuery);
                    } else if (!myString1.equals("") && !myString3.equals("") && myString2.equals("")) {
                        String myQuery = "https://maps.googleapis.com/maps/api/place/nearbysearch/json?location=" +
                                location.getLatitude() + "," + location.getLongitude() +
                                "&radius=50000&sensor=true&rankby=prominence&types=" + myString1 + "|" + myString3 + "&keyword=" + myString4 + "&key=" +
                                getString(R.string.api_key_search);
                        mGetPlacesAsyncTaskSearch = new GetPlacesAsyncTaskSearch();
                        mGetPlacesAsyncTaskSearch.execute(myQuery);
                    } else if (!myString1.equals("") && !myString2.equals("") && !myString3.equals("")) {
                        String myQuery = "https://maps.googleapis.com/maps/api/place/nearbysearch/json?location=" +
                                location.getLatitude() + "," + location.getLongitude() +
                                "&radius=50000&sensor=true&rankby=prominence&types=" + myString1 + "|" + myString2 + "|" + myString3 + "&keyword=" + myString4 + "&key=" +
                                getString(R.string.api_key_search);
                        mGetPlacesAsyncTaskSearch = new GetPlacesAsyncTaskSearch();
                        mGetPlacesAsyncTaskSearch.execute(myQuery);
                    } else {
                        String myQuery = "https://maps.googleapis.com/maps/api/place/nearbysearch/json?location=" +
                                location.getLatitude() + "," + location.getLongitude() +
                                "&radius=50000&sensor=true&rankby=prominence&types=&keyword=" + myString4 + "&key=" +
                                getString(R.string.api_key_search);
                        mGetPlacesAsyncTaskSearch = new GetPlacesAsyncTaskSearch();
                        mGetPlacesAsyncTaskSearch.execute(myQuery);
                    }
                }
            }
        }

        return mView;
    }

    // Set places in FragmentSearch
    public static void setPlaces(ArrayList<PlaceModel> list) {
        mPlaceList = list;
        mAdapter.clear();
        mAdapter.addAll(mPlaceList);
        locationManager = (LocationManager) mFragmentSearch.getActivity().getSystemService(Context.LOCATION_SERVICE);
        criteria = new Criteria();
        String provider = locationManager.getBestProvider(criteria, true);
        if (ActivityCompat.checkSelfPermission(mFragmentSearch.getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.checkSelfPermission(mFragmentSearch.getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION);
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
                Collections.sort(mPlaceList, new Comparator<PlaceModel>() {
                    public int compare(PlaceModel obj1, PlaceModel obj2) {
                        return Double.compare(Math.sqrt(Math.pow(obj1.getLat() - location.getLatitude(), 2) + Math.pow(obj1.getLng() - location.getLongitude(), 2)),
                                Math.sqrt(Math.pow(obj2.getLat() - location.getLatitude(), 2) + Math.pow(obj2.getLng() - location.getLongitude(), 2))); // To compare integer values
                    }
                });
                mAdapter.sort(new Comparator<PlaceModel>() {
                    public int compare(PlaceModel obj1, PlaceModel obj2) {
                        return Double.compare(Math.sqrt(Math.pow(obj1.getLat() - location.getLatitude(), 2) + Math.pow(obj1.getLng() - location.getLongitude(), 2)),
                                Math.sqrt(Math.pow(obj2.getLat() - location.getLatitude(), 2) + Math.pow(obj2.getLng() - location.getLongitude(), 2))); // To compare integer values
                    }
                });
            }
        }
        mListView.setAdapter(mAdapter);
    }

    // stopShowingProgressBar
    public static void stopShowingProgressBar() {
        if (mProgressDialogInternet != null) {
            mProgressDialogInternet.dismiss();
            mProgressDialogInternet = null;
        }
    }

    // startShowingProgressBar
    public static void startShowingProgressBar() {
        mProgressDialogInternet = ProgressDialog.show(mFragmentSearch.getActivity(), "Loading...",
                "Please wait...", true);
        mProgressDialogInternet.show();
    }

}
