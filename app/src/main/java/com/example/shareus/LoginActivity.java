package com.example.shareus;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.shareus.MainActivity;
import com.example.shareus.R;
import com.example.shareus.RegisterActivity;
import com.example.shareus.Session;
import com.example.shareus.api.ApiREST;
import com.example.shareus.api.AzureHandler;
import com.example.shareus.model.Usuario;

public class LoginActivity extends AppCompatActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        AzureHandler handler = AzureHandler.getInstance();

        if(handler.getAccount() != null) {
            Log.d("LOGIN", "Hay cuenta guardada en AzureHandler");
            loginWithAPI(handler.getAccount().getUsername());
        } else {
            Log.d("LOGIN", "No hay cuenta guardada en AzureHandler, necesario el login interactivo");
        }

        Button btn = findViewById(R.id.login);
        btn.setOnClickListener(view -> {
            findViewById(R.id.signing_in).setVisibility(View.VISIBLE);
            findViewById(R.id.login).setVisibility(View.GONE);
            AzureHandler.getInstance().authorize(this, account -> {
                if(account != null) {
                    String userName = account.getUsername();
                    loginWithAPI(userName);
                } else {
                    findViewById(R.id.signing_in).setVisibility(View.GONE);
                    findViewById(R.id.login).setVisibility(View.VISIBLE);
                }
            });
        });
    }


    public void loginWithAPI(String userName) {
        ApiREST.loginOrRegister(userName, ApiREST.getInstance(getApplicationContext()).getQueue(), res -> {
            Usuario usuario = (Usuario) res;
            Log.d("LOGIN", "Se obtiene o crea el usuario (" + usuario.getId() + "," + usuario.getUsuario() + ",nuevo=" + usuario.isFirstTime() + ") desde el API");
            Session session = new Session(usuario.getId(), usuario.getUsuario());
            Session.save(session, getApplicationContext());
            Intent intent;

            if(usuario.isFirstTime()) {
                intent = new Intent(getApplicationContext(), RegisterActivity.class);
            } else {
                intent = new Intent(getApplicationContext(), MainActivity.class);
            }

            startActivity(intent);
            finish();
        });
    }
}