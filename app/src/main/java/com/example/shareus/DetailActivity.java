package com.example.shareus;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class DetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Activity cxt = this;
        setContentView(R.layout.activity_detail);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        int id = getIntent().getIntExtra("id", -1);
        Log.d("PRUEBA", "Esta es la id: " + id);
        FloatingActionButton back = findViewById(R.id.backwards);
        back.setOnClickListener(view -> {
            finish();
            cxt.overridePendingTransition(R.anim.left_to_right, R.anim.right_to_left);
        });
    }
}