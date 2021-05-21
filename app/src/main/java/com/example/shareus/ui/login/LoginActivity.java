package com.example.shareus.ui.login;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;

import com.example.shareus.MainActivity;
import com.example.shareus.R;
import com.example.shareus.Session;
import com.example.shareus.api.ApiREST;
import com.example.shareus.api.AzureHandler;
import com.example.shareus.model.Usuario;
import com.microsoft.identity.client.IAccount;

public class LoginActivity extends AppCompatActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        setTitle("Inicio de Sesión");

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
                String userName = account.getUsername();
                loginWithAPI(userName);
            });
        });
    }


    public void loginWithAPI(String userName) {
        ApiREST.loginOrRegister(userName, ApiREST.getInstance(getApplicationContext()).getQueue(), res -> {
            Usuario usuario = (Usuario) res;
            Log.d("LOGIN", "Se obtiene o crea el usuario (" + userName + ") desde el API");
            Session session = new Session(usuario.getId(), usuario.getUsuario());
            Session.save(session, getApplicationContext());

            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);
            finish();
        });
    }
}