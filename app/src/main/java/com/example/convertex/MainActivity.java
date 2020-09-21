package com.example.convertex;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.content.ContextCompat;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.Toast;

import com.google.gson.JsonObject;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MainActivity extends AppCompatActivity {

    View view;

    Switch switch1;


    EditText amount;
    EditText result;
    Spinner from;
    Spinner to;
    ArrayList<data> list = new ArrayList<>();
    ArrayList<data> list2 = new ArrayList<>();
    JsonObject rates;
    boolean net ;
    boolean done;

    public static final String MyPref = "nightMode";
    public static final String Key = "isNm";
    SharedPreferences sharedPreferences;

    public boolean connection() {
        retroFitInterface retroFitInterface = retroFitBuilder.getRetrofitInstance().create(retroFitInterface.class);
        Call<JsonObject> call = retroFitInterface.getExchangeCurrency("USD");
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                JsonObject res = response.body();
                rates = res.getAsJsonObject("conversion_rates");
                net = true;
                done = true;
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                net = false;
            }
        });
        return net;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_main);

        sharedPreferences = getSharedPreferences(MyPref, Context.MODE_PRIVATE);

        view = findViewById(R.id.light);

        switch1 = findViewById(R.id.switch1);

        checkNMActivated();

        amount = findViewById(R.id.amount);
        result = findViewById(R.id.result);
        from = (Spinner) findViewById(R.id.spinner_from);
        to = (Spinner) findViewById(R.id.spinner_to);


        list.add(new data(R.drawable.us,"USD"));
        list.add(new data(R.drawable.can,"CAD"));
        list.add(new data(R.drawable.eur,"EUR"));
        list.add(new data(R.drawable.gb,"GBP"));
        list.add(new data(R.drawable.ind,"INR"));
        list.add(new data(R.drawable.japan,"JPY"));
        list.add(new data(R.drawable.nz,"NZD"));
        list.add(new data(R.drawable.pak,"PKR"));
        list.add(new data(R.drawable.sa,"SAR"));


        list2.add(new data(R.drawable.pak,"PKR"));
        list2.add(new data(R.drawable.can,"CAD"));
        list2.add(new data(R.drawable.eur,"EUR"));
        list2.add(new data(R.drawable.gb,"GBP"));
        list2.add(new data(R.drawable.ind,"INR"));
        list2.add(new data(R.drawable.japan,"JPY"));
        list2.add(new data(R.drawable.nz,"NZD"));
        list2.add(new data(R.drawable.sa,"SAR"));
        list2.add(new data(R.drawable.us,"USD"));

        customAdapter ca = new customAdapter(this,list);
        customAdapter ca2 = new customAdapter(this,list2);

        from.setAdapter(ca);
        to.setAdapter(ca2);

        connection();

        //String[] list = {"USD","CAD","EUR","GBP", "INR","JPY","NZD","PKR","SAR"};
        //String[] list2 = {"PKR", "USD","CAD","EUR", "GBP", "INR","JPY","NZD","SAR"};
        //ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,R.layout.support_simple_spinner_dropdown_item, list);
        //ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(this,R.layout.support_simple_spinner_dropdown_item, list2);

        //from.setAdapter(adapter);
        //to.setAdapter(adapter2);



    }

    public void convert(View view) {

        //result.setText("hi");

        if (amount.getText().toString().isEmpty()) {
            Toast.makeText(this, "Enter Amount First", Toast.LENGTH_SHORT).show();
        }
        else {
            if (done) {


                String f = list.get(from.getSelectedItemPosition()).getName();
                String t = list2.get(to.getSelectedItemPosition()).getName();

                //String f = from.getSelectedItem().toString();
                //String t = to.getSelectedItem().toString();

                double f_rate = Double.parseDouble(rates.get(f).toString());
                double t_rate = Double.parseDouble(rates.get(t).toString());


                double a = Double.parseDouble(amount.getText().toString());

                if (f.equals("USD")) {
                    double r = a * t_rate;
                    //result.setText(String.valueOf(a));


                    result.setText(String.valueOf(r));
                } else if (t.equals("USD")) {
                    double r = a / f_rate;
                    result.setText(String.valueOf(r));
                } else {
                    double r = a / f_rate * t_rate;
                    result.setText(String.valueOf(r));
                }

            }
            else if (connection()) {

                String f = list.get(from.getSelectedItemPosition()).getName();
                String t = list2.get(to.getSelectedItemPosition()).getName();

                //String f = from.getSelectedItem().toString();
                //String t = to.getSelectedItem().toString();

                double f_rate = Double.parseDouble(rates.get(f).toString());
                double t_rate = Double.parseDouble(rates.get(t).toString());


                double a = Double.parseDouble(amount.getText().toString());

                if (f.equals("USD")) {
                    double r = a * t_rate;
                    //result.setText(String.valueOf(a));


                    result.setText(String.valueOf(r));
                } else if (t.equals("USD")) {
                    double r = a / f_rate;
                    result.setText(String.valueOf(r));
                } else {
                    double r = a / f_rate * t_rate;
                    result.setText(String.valueOf(r));
                }

            }
            else {
                Toast.makeText(this, "Check your Internet Connection", Toast.LENGTH_SHORT).show();
            }
        }



        }

   public void darkmode(View view) {



            if(switch1.isChecked()) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                saveState(true);
                recreate();
            }
            else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                saveState(false);
                recreate();
            }

    }
    private void saveState(boolean nightMode) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(Key, nightMode);
        editor.apply();
    }
    public void checkNMActivated() {
        if(sharedPreferences.getBoolean(Key,false)){
            switch1.setChecked(true);
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        }
        else {
            switch1.setChecked(false);
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        }
    }

}






