package com.sv.adminrentbike;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class BerandaActivity extends AppCompatActivity {
    private CardView custom;
    private CardView logout;
    private CardView bike;
    private CardView insert;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        custom = findViewById(R.id.custom);
        bike = findViewById(R.id.bike);
        logout = findViewById(R.id.logout);
        insert = findViewById(R.id.insert);
        custom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(BerandaActivity.this, UserActivity.class));
            }
        });
        bike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(BerandaActivity.this, BikeActivity.class));
            }
        });
        insert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(BerandaActivity.this, InsertActivity.class));
            }
        });
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(BerandaActivity.this, LoginActivity.class));
            }
        });
    }
}