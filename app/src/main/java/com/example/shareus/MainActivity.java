package com.example.shareus;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.shareus.api.ApiREST;
import com.example.shareus.api.AzureHandler;
import com.example.shareus.model.Usuario;
import com.google.android.material.navigation.NavigationView;


public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.Theme_ShareUS_NoActionBar);
        super.onCreate(savedInstanceState);

        AzureHandler.getInstance().init(getApplicationContext(), () -> {
            if(Session.get(getApplicationContext()) == null) {
                Log.d("LOGIN", "No hay sesión en SharedPreferences");
                AzureHandler handler = AzureHandler.getInstance();
                handler.checkAccount(() -> {
                    Log.d("LOGIN", "Termina el checkAccount");
                    Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                    startActivity(intent);
                    finish();
                });
            } else {
                setContentView(R.layout.activity_main);
                Toolbar toolbar = findViewById(R.id.toolbar);
                setSupportActionBar(toolbar);
                DrawerLayout drawer = findViewById(R.id.drawer_layout);
                NavigationView navigationView = findViewById(R.id.nav_view);
                mAppBarConfiguration = new AppBarConfiguration.Builder(
                        R.id.nav_misviajes, R.id.nav_logout, R.id.nav_publicar,R.id.nav_encontrar, R.id.nav_prueba)
                        .setDrawerLayout(drawer)
                        .build();
                NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
                NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
                NavigationUI.setupWithNavController(navigationView, navController);
                navigationView.setNavigationItemSelectedListener(menuItem -> {
                    int id = menuItem.getItemId();

                    if (id == R.id.nav_logout) {
                        Toast.makeText(this, "Cerrando sesión...", Toast.LENGTH_SHORT).show();
                        Session.destroy(getApplicationContext());
                        AzureHandler.getInstance().destroy(this::recreate);
                        return false;
                    }

                    NavigationUI.onNavDestinationSelected(menuItem, navController);
                    drawer.closeDrawer(GravityCompat.START);
                    return true;
                });
                ApiREST.getInstance(this);
                ApiREST.obtenerUsuario(Session.get(this).getUserId(), ApiREST.getInstance(this).getQueue(), res -> {
                    TextView welcome = findViewById(R.id.welcome_user);
                    Usuario usuario = (Usuario) res;
                    welcome.setText("¡Bienvenido " + usuario.getNombre() + " a ShareUS!");
                    setNeededHeight();
                });
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    public void setNeededHeight() {
        Rect rectangle = new Rect();
        Window window = getWindow();
        window.getDecorView().getWindowVisibleDisplayFrame(rectangle);
        int statusBarHeight = rectangle.top;
        View spacer = findViewById(R.id.nav_header);
        spacer.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, statusBarHeight));
    }
}