package com.example.shareus;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.telecom.Call;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.shareus.api.ApiREST;

public class RegisterActivity extends AppCompatActivity {

    @SuppressLint("ShowToast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        Button submit = findViewById(R.id.submit_form);
        submit.setOnClickListener(view -> {
            TextView name = findViewById(R.id.nombre);
            TextView telf = findViewById(R.id.telefono);

            if(name.getText().toString().isEmpty() || telf.getText().toString().isEmpty()) {
                Toast.makeText(this, "¡Es obligatorio rellenar todos los campos!", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Actualizando datos...", Toast.LENGTH_SHORT).show();
                ApiREST.completarUsuario(Session.get(getApplicationContext()).getUserId(), name.getText().toString(),
                        telf.getText().toString(), ApiREST.getInstance(getApplicationContext()).getQueue(), res -> {
                    boolean result = Boolean.parseBoolean(String.valueOf(res));
                    if(!result) {
                        Toast.makeText(getApplicationContext(), "Hemos encontrado algún problema actualizando tus datos :(", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(this, "¡Datos actualizados correctamente!", Toast.LENGTH_SHORT).show();
                    }
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(intent);
                    finish();
                });
            }
        });
    }
}