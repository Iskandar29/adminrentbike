package com.sv.adminrentbike;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
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

import static android.os.Build.ID;
//import br.com.simplepass.loading_button_lib.customViews.CircularProgressButton;

public class LoginActivity extends AppCompatActivity {
    //    CircularProgressButton circularProgressButton;
    public static final String TAG = LoginActivity.class.getSimpleName();
    private EditText edtEmail, edtPassword;
    private Button btnLogin;
    public static final String SHARED_PREFS = "sharedPrefs";
    public static final String ID = "id";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }
        setContentView(R.layout.activity_login);

        edtEmail = findViewById(R.id.txtmail);
        edtPassword = findViewById(R.id.txtpass);
        btnLogin = findViewById(R.id.btnlogin);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = edtEmail.getText().toString().trim();
                String password = edtPassword.getText().toString().trim();

                boolean isEmpty = false;

                if (email.isEmpty()) {
                    isEmpty = true;
                    edtEmail.setError("Email harus diisi");
                }

                if (password.isEmpty()) {
                    isEmpty = true;
                    edtPassword.setError("Password harus diisi");
                }

                if (!isEmpty) {
                    AndroidNetworking.post("http://192.168.0.107/bikeapi/login.php")
                            .addBodyParameter("email", email)
                            .addBodyParameter("password", password)
                            .setPriority(Priority.MEDIUM)
                            .build()
                            .getAsJSONObject(new JSONObjectRequestListener() {
                                @Override
                                public void onResponse(JSONObject response) {
                                    try {
                                        String status = response.getString("status");
                                        String message = response.getString("message");

                                        if (status.equals("success")) {
                                            Toast.makeText(LoginActivity.this, message, Toast.LENGTH_SHORT).show();

                                            String id = response.getString("id");

                                            SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
                                            SharedPreferences.Editor editor = sharedPreferences.edit();

                                            editor.putString(ID, id);
                                            editor.apply();

                                            Intent intent = new Intent(LoginActivity.this, BerandaActivity.class);
                                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                            startActivity(intent);
                                            finish();
                                        } else {
                                            Toast.makeText(LoginActivity.this, message, Toast.LENGTH_SHORT).show();
                                        }
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                }

                                //ngecek error
                                @Override
                                public void onError(ANError error) {
                                    Log.e(TAG, "onErro+r: " + error.getLocalizedMessage());
                                }
                            });
                }
            }
        });
    }
}