package sg.edu.rp.c346.rpwebsites;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity {

    Spinner spnCat, spnSubCat;
    Button btnGo;
    ArrayList<String> alItems;
    ArrayAdapter<String> aaItems;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        spnCat = findViewById(R.id.spinnerCat);
        spnSubCat = findViewById(R.id.spinnerSubCat);
        btnGo = findViewById(R.id.buttonGo);

        alItems = new ArrayList<>();
        aaItems = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, alItems);

        spnCat.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                alItems.clear();
                switch (position) {
                    case 0:
                        String[] strNumbers = getResources().getStringArray(R.array.sub_cat_RP);
                        alItems.addAll(Arrays.asList(strNumbers));
                        spnSubCat.setAdapter(aaItems);
                        break;
                    case 1:
                        String[] strNumbers1 = getResources().getStringArray(R.array.sub_cat_SOI);
                        alItems.addAll(Arrays.asList(strNumbers1));
                        spnSubCat.setAdapter(aaItems);
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        btnGo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int posCat = spnCat.getSelectedItemPosition();
                int posSubCat = spnSubCat.getSelectedItemPosition();
                String [][] sites ={
                        {"https://www.rp.edu.sg/",
                                "https://www.rp.edu.sg/student-life",
                        },
                        {"https://www.rp.edu.sg/soi/full-time-diplomas/details/r47/" ,
                                "https://www.rp.edu.sg/soi/full-time-diplomas/details/r12/"},

                } ;
                String URL = sites[posCat][posSubCat];
                Intent intent = new Intent(getBaseContext(),webView.class);
                intent.putExtra("URL", URL+"");
                startActivity(intent);
//                if (posCat == 0 && posSubCat == 0){
//                    intent.putExtra("URL", "https://www.rp.edu.sg/");
//                } else if (posCat == 0 && posSubCat == 1){
//                    intent.putExtra("URL", "https://www.rp.edu.sg/student-life");
//                } else if (posCat == 1 && posSubCat == 0){
//                    intent.putExtra("URL", "https://www.rp.edu.sg/soi/full-time-diplomas/details/r47");
//                } else if (posCat == 1 && posSubCat == 1){
//                    intent.putExtra("URL", "https://www.rp.edu.sg/soi/full-time-diplomas/details/r12");
//                }
//                startActivity(intent);
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor prefEdit = prefs.edit();
        int posCat = spnCat.getSelectedItemPosition();
        int posSubCat = spnSubCat.getSelectedItemPosition();
        prefEdit.putInt("cat",posCat);
        prefEdit.putInt("subCat",posSubCat);
        prefEdit.commit();
    }

    @Override
    protected void onResume() {
        super.onResume();
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        int catPos = prefs.getInt("cat",0);
        int catSubPos = prefs.getInt("subCat",0);
        spnCat.setSelection(catPos);
        spnSubCat.setSelection(catSubPos);
    }
}
