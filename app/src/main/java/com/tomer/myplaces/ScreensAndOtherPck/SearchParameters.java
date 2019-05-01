package com.tomer.myplaces.ScreensAndOtherPck;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;

import com.tomer.myplaces.R;

public class SearchParameters extends AppCompatActivity {

    private CheckBox checkRes, checkCaf, checkBar;
    private Button btnSearch;
    private EditText mySearch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_parameters);

        checkRes = findViewById(R.id.checkboxRes);
        checkCaf = findViewById(R.id.checkboxCaf);
        checkBar = findViewById(R.id.checkboxBar);

        mySearch = findViewById(R.id.mySearch);

        btnSearch = findViewById(R.id.btnSearch);

        checkRes.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    SharedPreferences settings = getSharedPreferences("mysettings1",
                            Context.MODE_PRIVATE);

                    SharedPreferences.Editor editor = settings.edit();
                    editor.putString("mystring1", "restaurant");
                    editor.apply();
                }
                if (!isChecked) {
                    SharedPreferences settings = getSharedPreferences("mysettings1",
                            Context.MODE_PRIVATE);

                    SharedPreferences.Editor editor = settings.edit();
                    editor.putString("mystring1", "");
                    editor.apply();
                }
            }
        });

        checkCaf.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    SharedPreferences settings = getSharedPreferences("mysettings2",
                            Context.MODE_PRIVATE);

                    SharedPreferences.Editor editor = settings.edit();
                    editor.putString("mystring2", "cafe");
                    editor.apply();
                }
                if (!isChecked) {
                    SharedPreferences settings = getSharedPreferences("mysettings2",
                            Context.MODE_PRIVATE);

                    SharedPreferences.Editor editor = settings.edit();
                    editor.putString("mystring2", "");
                    editor.apply();
                }
            }
        });

        checkBar.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    SharedPreferences settings = getSharedPreferences("mysettings3",
                            Context.MODE_PRIVATE);

                    SharedPreferences.Editor editor = settings.edit();
                    editor.putString("mystring3", "bar");
                    editor.apply();
                }
                if (!isChecked) {
                    SharedPreferences settings = getSharedPreferences("mysettings3",
                            Context.MODE_PRIVATE);

                    SharedPreferences.Editor editor = settings.edit();
                    editor.putString("mystring3", "");
                    editor.apply();
                }
            }
        });

        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences settings = getSharedPreferences("mysettings4",
                        Context.MODE_PRIVATE);

                SharedPreferences.Editor editor = settings.edit();
                editor.putString("mystring4", mySearch.getText().toString());
                editor.apply();

                Intent intNew = new Intent(SearchParameters.this, SearchResults.class);
                startActivity(intNew);
            }
        });
    }

}
