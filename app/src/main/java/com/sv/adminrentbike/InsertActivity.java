package com.sv.adminrentbike;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;

import org.json.JSONException;
import org.json.JSONObject;

public class InsertActivity extends AppCompatActivity {
    public static final String TAG = InsertActivity.class.getSimpleName();
    private EditText edtKode, edtMerk, edtJenis, edtWarna, edtHargasewa;
    private Button btnInsert;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert);
        edtKode = findViewById(R.id.edt_kode);
        edtMerk = findViewById(R.id.edt_merk);
        edtJenis = findViewById(R.id.edt_jenis);
        edtWarna = findViewById(R.id.edt_warna);
        edtHargasewa = findViewById(R.id.edt_hargasewa);
        btnInsert = findViewById(R.id.btn_insert);

        btnInsert.setOnClickListener(new View.OnClickListener(){

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
                    AndroidNetworking.post("http://192.168.0.107/bikeapi/insert.php")
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
                                            Toast.makeText(InsertActivity.this, message, Toast.LENGTH_SHORT).show();
                                            Intent intent = new Intent(InsertActivity.this, BikeActivity.class);
                                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                            startActivity(intent);
                                            finish();

                                        } else {
                                            Toast.makeText(InsertActivity.this, message, Toast.LENGTH_SHORT).show();
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