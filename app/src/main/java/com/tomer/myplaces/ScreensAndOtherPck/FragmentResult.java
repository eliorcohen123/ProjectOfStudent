package com.tomer.myplaces.ScreensAndOtherPck;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.tomer.myplaces.DataPck.PlaceModel;
import com.tomer.myplaces.R;

public class FragmentResult extends Fragment {

    private View mView;
    private PlaceModel placeModelSearch;
    private TextView minDet1, minDet2, minDet3;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_layout_favorites, container, false);

        Bundle bundle = this.getArguments();
        if (bundle != null) {
            placeModelSearch = (PlaceModel) bundle.getSerializable(getString(R.string.map_search_key));
        }

        minDet1 = mView.findViewById(R.id.minDet1);
        minDet2 = mView.findViewById(R.id.minDet2);
        minDet3 = mView.findViewById(R.id.minDet3);

        if (placeModelSearch != null) {
            minDet1.setText(placeModelSearch.getName());
            minDet2.setText(placeModelSearch.getVicinity());
            if (String.valueOf(placeModelSearch.getOpening_hours()).equals("true")) {
                minDet3.setText("Open");
            } else if (String.valueOf(placeModelSearch.getOpening_hours()).equals("false")) {
                minDet3.setText("Close");
            } else {
                minDet3.setText("No information");
            }
        }

        return mView;
    }

}
