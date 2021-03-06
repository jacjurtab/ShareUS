package com.example.shareus;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProvider;

import com.android.volley.RequestQueue;
import com.example.shareus.api.ApiREST;
import com.example.shareus.dialog.DialogCallback;
import com.example.shareus.dialog.Dialogs;
import com.example.shareus.model.ListAdapter;
import com.example.shareus.model.Pasajero;
import com.example.shareus.model.Viaje;
import com.example.shareus.ui.ViajesViewModel;
import com.example.shareus.ui.encontrar.ViajesEncontradosFragment;
import com.example.shareus.ui.misviajes.tabs.ViajesConductorFragment;
import com.example.shareus.ui.misviajes.tabs.ViajesPasadosFragment;
import com.example.shareus.ui.misviajes.tabs.ViajesPasajeroFragment;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class DetailActivity extends AppCompatActivity {

    ListView lista;
    Activity cxt;
    RequestQueue mRequestQueue = ApiREST.getInstance(null).getQueue();
    Viaje viaje;
    int veces_insertar = 0;
    boolean avisado = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        cxt = this;
        setContentView(R.layout.activity_detail);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ExtendedFloatingActionButton back = findViewById(R.id.backwards);
        back.setOnClickListener(view -> {
            finish();
            cxt.overridePendingTransition(R.anim.left_to_right, R.anim.right_to_left);
        });

        int id = getIntent().getIntExtra("id", -1);

        ApiREST.obtenerViaje(id, mRequestQueue, res -> {
            viaje = (Viaje) res;
            render(viaje);
        });
    }


    @SuppressLint({"SetTextI18n", "UseCompatLoadingForDrawables"})
    public void render(Viaje viaje) {
        int idUsuario = Session.get(getApplicationContext()).getUserId();
        boolean esConductor = (idUsuario == viaje.getConductorObj().getId());
        boolean esPasajero = viaje.getPasajeros().stream().anyMatch(o -> o.getId() == idUsuario);

        Log.d("DEBUG", viaje.getConductorObj().getTelefono());

        TextView titulo = findViewById(R.id.titulo);
        titulo.setText("Viaje programado para " + Utils.prettyParse(viaje.getFecha_hora()));
        TextView conductor = findViewById(R.id.conductor);
        if(esConductor)
            conductor.setText("T??");
        else
            conductor.setText(viaje.getConductorObj().getNombre());
        TextView valoracion = findViewById(R.id.valoracion);
        valoracion.setText(Float.toString(viaje.getNota_conductor()));
        TextView origen = findViewById(R.id.origen_value);
        origen.setText(viaje.getOrigen());
        TextView destino = findViewById(R.id.destino_value);
        destino.setText(viaje.getDestino());
        TextView precio = findViewById(R.id.precio_value);
        precio.setText(viaje.getPrecio() + "???/plaza");
        TextView numPasajeros = findViewById(R.id.pasajeros_count);
        numPasajeros.setText("Pasajeros (" + viaje.getNum_pasajeros() + "/" + viaje.getMax_plazas() + ")");

        View empty = findViewById(R.id.empty);
        lista = findViewById(R.id.pasajeros_detalle);
        lista.setEmptyView(empty);
        lista.setAdapter(new ListAdapter(this, R.layout.user_list, viaje.getPasajeros()) {
            @Override
            public void crearEntrada(Object entrada, View view) {
                Pasajero pasajero = (Pasajero) entrada;
                if(pasajero != null) {
                    Button btn = view.findViewById(R.id.round_icon);
                    TextView name = view.findViewById(R.id.user_name);
                    if (pasajero.getId() == idUsuario)
                        name.setText(pasajero.getNombre() + " (T??)");
                    else
                        name.setText(pasajero.getNombre());
                    btn.setText(pasajero.getNombre().substring(0, 1));
                } else {
                    view.setVisibility(View.GONE);
                }
            }
        });

        ExtendedFloatingActionButton option = findViewById(R.id.btn_opcion);
        if(viaje.getFecha_hora().getTime() > System.currentTimeMillis()) {
            if(esConductor) {
                option.setIconResource(R.drawable.ic_baseline_delete_forever_24);
                option.setText("Eliminar Viaje");
                option.setExtended(true);
                option.setOnClickListener(view -> eliminarViaje(viaje, view));
            } else if(esPasajero) {
                option.setIconResource(R.drawable.ic_baseline_person_remove_alt_1_24);
                option.setText("Cancelar Viaje");
                option.setExtended(true);
                option.setOnClickListener(view -> quitarPasajero(viaje, idUsuario, view));
                if(!avisado)
                    Dialogs.showViajeInfoDialog(this, viaje);
            } else {
                option.setIconResource(R.drawable.ic_baseline_person_add_alt_1_24);
                option.setText("Inscribir en Viaje");
                option.setExtended(true);
                option.setOnClickListener(view -> insertarPasajero(viaje, idUsuario, view));
            }
            option.show();
            option.setVisibility(View.VISIBLE);
        } else if(esPasajero) {
            option.setIconResource(R.drawable.ic_baseline_rate_review_24);
            option.setText("Valorar Conductor");
            option.setExtended(true);
            option.setOnClickListener(view -> insertarValoracion(viaje, view));
            option.show();
            option.setVisibility(View.VISIBLE);
        }

    }

    public void eliminarViaje(Viaje viaje, View view) {
        ApiREST.eliminarViaje(viaje.getId(), mRequestQueue, res -> {
            boolean result = Boolean.parseBoolean((String) res);

            if(result) {
                finish();
                cxt.overridePendingTransition(R.anim.left_to_right, R.anim.right_to_left);
                ViajesConductorFragment.update(getApplicationContext());
            } else {
                Snackbar.make(view, "No se ha podido eliminar el viaje", Snackbar.LENGTH_SHORT).show();
            }
        });
        Snackbar.make(view, "Viaje eliminado correctamente", Snackbar.LENGTH_SHORT).addCallback(new Snackbar.Callback() {
            @Override
            public void onDismissed(Snackbar snackbar, int event) {
                finish();
                cxt.overridePendingTransition(R.anim.left_to_right, R.anim.right_to_left);
            }
        }).show();
    }

    public void insertarValoracion(Viaje viaje, View view) {
        int idConductor = viaje.getConductorObj().getId();
        int idViaje = viaje.getId();
        int idValorador = Session.get(getApplicationContext()).getUserId();
        Dialogs.showValoracionDialog(this, rating -> ApiREST.crearValoracion(idViaje, idValorador, idConductor, rating, mRequestQueue, res -> {
            boolean resultado = Boolean.parseBoolean(String.valueOf(res));
            if (resultado) {
                ApiREST.obtenerViaje(viaje.getId(), mRequestQueue, res1 -> {
                    Viaje nuevo = (Viaje) res1;
                    render(nuevo);
                    Snackbar.make(view, "??Valoraci??n a??adida correctamente!", Snackbar.LENGTH_SHORT).addCallback(new Snackbar.Callback() {
                        @Override
                        public void onDismissed(Snackbar snackbar, int event) {
                            finish();
                            cxt.overridePendingTransition(R.anim.left_to_right, R.anim.right_to_left);
                        }
                    }).show();
                });
                ViajesPasadosFragment.update(getApplicationContext());

            }
            else {
                Snackbar.make(view, "??No puedes valorar 2 veces el mismo viaje!", Snackbar.LENGTH_SHORT).addCallback(new Snackbar.Callback() {
                    @Override
                    public void onDismissed(Snackbar snackbar, int event) {
                        finish();
                        cxt.overridePendingTransition(R.anim.left_to_right, R.anim.right_to_left);
                    }
                }).show();
            }
        }));
    }

    public void insertarPasajero(Viaje viaje, int pasajero, View view) {
        Log.d("DEBUG", veces_insertar + "");
        if(veces_insertar > 1) {
            Snackbar.make(view, "Se te ha bloqueado el acceso a este viaje por seguridad", Snackbar.LENGTH_SHORT).show();
        } else {
            if(veces_insertar == 1) {
                Dialogs.showAvisoViajeDialog(this);
                avisado = true;
            }
            ApiREST.insertarPasajero(viaje.getId(), pasajero, mRequestQueue, res -> {
                boolean result = Boolean.parseBoolean((String) res);
                String msg;

                if(result) {
                    msg = "A??adido al viaje correctamente";
                    ApiREST.obtenerViaje(viaje.getId(), mRequestQueue, res1 -> {
                        Viaje nuevo = (Viaje) res1;
                        render(nuevo);
                        veces_insertar++;
                        ViajesEncontradosFragment.update(getApplicationContext());
                    });
                } else {
                    msg = "Se ha producido un error al a??adir pasajero";
                }
                Snackbar.make(view, msg, Snackbar.LENGTH_SHORT).show();
            });
        }
    }

    public void quitarPasajero(Viaje viaje, int pasajero, View view) {
        ApiREST.eliminarPasajero(viaje.getId(), pasajero, mRequestQueue, res -> {
            boolean result = Boolean.parseBoolean((String) res);
            String msg;

            if(result) {
                msg = "Eliminado del viaje correctamente";
                ApiREST.obtenerViaje(viaje.getId(), mRequestQueue, res1 -> {
                    Viaje nuevo = (Viaje) res1;
                    render(nuevo);
                    ViajesEncontradosFragment.update(getApplicationContext());
                    ViajesPasajeroFragment.update(getApplicationContext());
                });
            } else {
                msg = "Se ha producido un error al eliminar pasajero";
            }
            Snackbar.make(view, msg, Snackbar.LENGTH_SHORT).show();
        });
    }

}