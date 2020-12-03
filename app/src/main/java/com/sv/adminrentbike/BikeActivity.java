package com.sv.adminrentbike;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.sv.adminrentbike.model.Bike;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class BikeActivity extends AppCompatActivity {
    public static final String TAG = BikeActivity.class.getSimpleName();

    private ArrayList<Bike> list = new ArrayList<>();
    private RecyclerView rvBike;
    private adapterbike adapterbike;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bike);
        rvBike = findViewById(R.id.rv_bike);
        rvBike.setHasFixedSize(true);
        getBike();
        showRecycler();
    }

    private void getBike() {
        AndroidNetworking.get("http://192.168.0.107/bikeapi/listbike.php")
                .setPriority(Priority.LOW)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            String status = response.getString("status");

                            if (status.equals("success")) {
                                list.clear();
                                JSONArray result = response.getJSONArray("result");

                                for (int i = 0; i < result.length(); i++) {
                                    JSONObject item = result.getJSONObject(i);
                                    Bike bike = new Bike();
                                    bike.setId(item.getString("id"));
                                    bike.setJenis(item.getString("jenis"));
                                    bike.setKode(item.getString("kode"));
                                    bike.setMerk(item.getString("merk"));
                                    bike.setWarna(item.getString("warna"));
                                    bike.setHargasewa(item.getString("hargasewa"));
                                    list.add(bike);
                                }
                                adapterbike.notifyDataSetChanged();

                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(ANError error) {
                        Log.e(TAG, "onError: " + error.getLocalizedMessage());

                    }
                });
    }

    private void showRecycler() {
        rvBike.setLayoutManager(new LinearLayoutManager(this));
        adapterbike = new adapterbike(list);
        rvBike.setAdapter(adapterbike);

        adapterbike.setOnItemClickCallback(
                new adapterbike.OnItemClickCallback() {
            @Override
            public void onItemClicked(Bike data) {
                showSelectedCustomer(data);
            }
        });
    }

    private void showSelectedCustomer(Bike bike) {
        Intent intent = new Intent(this, BikeDetailActivity.class);
        intent.putExtra("Item Data", bike);
        startActivity(intent);
    }
    @Override
    protected void onRestart() {
        super.onRestart();
        getBike();
    }
}