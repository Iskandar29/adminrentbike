package com.sv.adminrentbike;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.sv.adminrentbike.model.Bike;

import org.json.JSONException;
import org.json.JSONObject;

public class BikeDetailActivity extends AppCompatActivity {
    public static final String TAG = BikeDetailActivity.class.getSimpleName();
    private EditText edtKode, edtMerk, edtJenis, edtWarna, edtHargasewa;
    private Button btnUpdate;
    private String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_bike);

        edtKode = findViewById(R.id.edt_kode);
        edtMerk = findViewById(R.id.edt_merk);
        edtJenis = findViewById(R.id.edt_jenis);
        edtWarna = findViewById(R.id.edt_warna);
        edtHargasewa = findViewById(R.id.edt_hargasewa);
        btnUpdate = findViewById(R.id.btn_update);

        Intent intent = getIntent();
        Bike itemData = intent.getParcelableExtra("Item Data");

        id = itemData.getId();
        edtKode.setText(itemData.getKode());
        edtMerk.setText(itemData.getJenis());
        edtJenis.setText(itemData.getMerk());
        edtWarna.setText(itemData.getWarna());
        edtHargasewa.setText(itemData.getHargasewa());

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String inputKode= edtKode.getText().toString().trim();
                String inputMerk= edtMerk.getText().toString().trim();
                String inputJenis = edtJenis.getText().toString().trim();
                String inputWarna = edtWarna.getText().toString().trim();
                String inputHargasewa = edtHargasewa.getText().toString().trim();

                boolean isEmpty = false;

                if (inputKode.isEmpty()) {
                    isEmpty = true;
                    edtKode.setError("Kode harus diisi");
                }

                if (inputMerk.isEmpty()) {
                    isEmpty = true;
                    edtMerk.setError("Merk harus diisi");
                }

                if (inputJenis.isEmpty()) {
                    isEmpty = true;
                    edtJenis.setError("Jenis harus diisi");
                }

                if (inputWarna.isEmpty()) {
                    isEmpty = true;
                    edtWarna.setError("Warna harus diisi");
                }

                if (inputHargasewa.isEmpty()) {
                    isEmpty = true;
                    edtHargasewa.setError("Harga Sewa harus diisi");
                }

                if (!isEmpty) {
                    AndroidNetworking.post("http://192.168.0.107/bikeapi/updatebike.php")
                            .addBodyParameter("id", id)
                            .addBodyParameter("kode", inputKode)
                            .addBodyParameter("merk", inputMerk)
                            .addBodyParameter("jenis", inputJenis)
                            .addBodyParameter("warna", inputWarna)
                            .addBodyParameter("hargasewa", inputHargasewa)
                            .setPriority(Priority.MEDIUM)
                            .build()
                            .getAsJSONObject(new JSONObjectRequestListener() {
                                @Override
                                public void onResponse(JSONObject response) {
                                    try {
                                        String status = response.getString("status");
                                        String message = response.getString("message");

                                        if (status.equals("success")) {
                                            Toast.makeText(BikeDetailActivity.this, message, Toast.LENGTH_SHORT).show();
                                            finish();
                                        } else {
                                            Toast.makeText(BikeDetailActivity.this, message, Toast.LENGTH_SHORT).show();
                                        }
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                }

                                @Override
                                public void onError(ANError error) {
                                    // handle error
                                    Log.e(TAG, "onError: " + error.getLocalizedMessage());
                                }
                            });
                }
            }
        });
    }
}