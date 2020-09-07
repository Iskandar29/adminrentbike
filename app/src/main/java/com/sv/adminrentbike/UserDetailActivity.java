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
import com.sv.adminrentbike.model.User;

import org.json.JSONException;
import org.json.JSONObject;

public class UserDetailActivity extends AppCompatActivity {
    public static final String TAG = UserDetailActivity.class.getSimpleName();
    private EditText edtName, edtNoktp, edtEmail, edtPhone, edtAddress;
    private Button btnUpdate;
    private String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_user);

        edtName = findViewById(R.id.edt_name);
        edtNoktp = findViewById(R.id.edt_noktp);
        edtEmail = findViewById(R.id.edt_email);
        edtPhone = findViewById(R.id.edt_phone);
        edtAddress = findViewById(R.id.edt_address);
        btnUpdate = findViewById(R.id.btn_update);

        Intent intent = getIntent();
        User itemData = intent.getParcelableExtra("Item Data");

        id = itemData.getId();
        edtName.setText(itemData.getName());
        edtNoktp.setText(itemData.getNoktp());
        edtEmail.setText(itemData.getEmail());
        edtPhone.setText(itemData.getPhone());
        edtAddress.setText(itemData.getAddress());

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String inputNama= edtName.getText().toString().trim();
                String inputNoktp= edtNoktp.getText().toString().trim();
                String inputEmail = edtEmail.getText().toString().trim();
                String inputnohp = edtPhone.getText().toString().trim();
                String inputAlamat = edtAddress.getText().toString().trim();

                boolean isEmpty = false;

                if (inputNama.isEmpty()) {
                    isEmpty = true;
                    edtName.setError("Nama lengkap harus diisi");
                }

                if (inputNoktp.isEmpty()) {
                    isEmpty = true;
                    edtNoktp.setError("No KTP harus diisi");
                }

                if (inputEmail.isEmpty()) {
                    isEmpty = true;
                    edtEmail.setError("Email harus diisi");
                }

                if (inputnohp.isEmpty()) {
                    isEmpty = true;
                    edtPhone.setError("Nomor telepon harus diisi");
                }

                if (inputAlamat.isEmpty()) {
                    isEmpty = true;
                    edtAddress.setError("Alamat Number harus diisi");
                }

                if (!isEmpty) {
                    AndroidNetworking.post("http://192.168.0.107/bikeapi/update.php")
                            .addBodyParameter("id", id)
                            .addBodyParameter("nama", inputNama)
                            .addBodyParameter("noktp", inputNoktp)
                            .addBodyParameter("email", inputEmail)
                            .addBodyParameter("nohp", inputnohp)
                            .addBodyParameter("alamat", inputAlamat)
                            .setPriority(Priority.MEDIUM)
                            .build()
                            .getAsJSONObject(new JSONObjectRequestListener() {
                                @Override
                                public void onResponse(JSONObject response) {
                                    try {
                                        String status = response.getString("status");
                                        String message = response.getString("message");

                                        if (status.equals("success")) {
                                            Toast.makeText(UserDetailActivity.this, message, Toast.LENGTH_SHORT).show();
                                            finish();
                                        } else {
                                            Toast.makeText(UserDetailActivity.this, message, Toast.LENGTH_SHORT).show();
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