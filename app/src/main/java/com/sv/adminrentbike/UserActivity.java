package com.sv.adminrentbike;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.sv.adminrentbike.adapter.adapteruser;
import com.sv.adminrentbike.model.User;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class UserActivity extends AppCompatActivity {
    public static final String TAG = UserActivity.class.getSimpleName();

    private ArrayList<User> list = new ArrayList<>();
    private RecyclerView rvCustomer;
    private adapteruser adapteruser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customers);


        rvCustomer = findViewById(R.id.rv_customers);
        rvCustomer.setHasFixedSize(true);

        getCustomers();
        showRecycler();
    }

    private void getCustomers() {
        AndroidNetworking.get("http://192.168.0.107/bikeapi/list.php")
                .setPriority(Priority.LOW)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            String status = response.getString("status");

                            if (status.equals("success")) {
                                JSONArray result = response.getJSONArray("result");

                                for (int i = 0; i < result.length(); i++) {
                                    JSONObject item = result.getJSONObject(i);

                                    User user = new User();
                                    user.setId(item.getString("id"));
                                    user.setName(item.getString("nama"));
                                    user.setPhone(item.getString("nohp"));
                                    user.setNoktp(item.getString("noktp"));
                                    user.setEmail(item.getString("email"));
                                    user.setAddress(item.getString("alamat"));
                                    list.add(user);
                                }

                                adapteruser.notifyDataSetChanged();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(ANError error) {
//                        Log.e(TAG, "onError: " + error.getLocalizedMessage());

                    }
                });
    }

    private void showRecycler() {
        rvCustomer.setLayoutManager(new LinearLayoutManager(this));
        adapteruser = new adapteruser(list);
        rvCustomer.setAdapter(adapteruser);

        adapteruser.setOnItemClickCallback(new adapteruser.OnItemClickCallback() {
            @Override
            public void onItemClicked(User data) {
                showSelectedCustomer(data);
            }
        });
    }

    private void showSelectedCustomer(User user) {
        Intent intent = new Intent(this, UserDetailActivity.class);
        intent.putExtra("Item Data", user);
        startActivity(intent);
    }
}