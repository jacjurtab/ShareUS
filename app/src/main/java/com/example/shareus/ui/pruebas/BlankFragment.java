package com.example.shareus.ui.pruebas;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.android.volley.RequestQueue;
import com.example.shareus.MainActivity;
import com.example.shareus.R;
import com.example.shareus.api.ApiREST;
import com.example.shareus.model.Ubicacion;
import com.example.shareus.model.Valoracion;
import com.example.shareus.model.Viaje;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;


public class BlankFragment extends Fragment {

    private BlanckFragmentViewModel blanckFragmentViewModel;
    private TextView origen;
    private TextView destino;
    private TextView fecha_hora;
    private TextView conductor;
    private TextView nota;
    private TextView num_pasajeros;
    private RequestQueue mRequestQueue;



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View vista = inflater.inflate(R.layout.fragment_blank, container, false);
        origen = (TextView) vista.findViewById(R.id.origen);
        destino = (TextView) vista.findViewById(R.id.destino);
        fecha_hora = (TextView) vista.findViewById(R.id.fecha_hora);
        conductor = (TextView) vista.findViewById(R.id.conductor);
        nota = (TextView) vista.findViewById(R.id.nota);
        num_pasajeros = (TextView) vista.findViewById(R.id.num_pasajeros);
        mRequestQueue = ApiREST.getInstance(getContext()).getQueue();
        //Consulta obtener viaje concreto

        ApiREST.obtenerViaje(4, mRequestQueue, new ApiREST.Callback() {
            @Override
            public void onResult(Object res) {
                Viaje viaje = (Viaje) res;
                origen.setText(viaje.getOrigen());
                destino.setText(viaje.getDestino());
                fecha_hora.setText(viaje.getFecha_hora().toString());
                conductor.setText(viaje.getConductor());
                nota.setText(Float.toString(viaje.getNota_conductor()));
                num_pasajeros.setText(viaje.getNum_pasajeros()+"/"+ viaje.getMax_plazas());
            }
        });

/*
        //Consulta obtener viajes disponibles
        ApiREST.obtenerViajes(true, mRequestQueue, new ApiREST.Callback() {
            @Override
            public void onResult(Object res) {
                List<Viaje> viajes = (List<Viaje>) res;
            }
        });

        //Consulta obtener viajes FUTUROS conductor
        ApiREST.obtenerViajesCond(2, false, mRequestQueue, new ApiREST.Callback() {
            @Override
            public void onResult(Object res) {
                List<Viaje> viajes = (List<Viaje>) res;
            }
        });

       //Consulta obtener viajes FUTUROS pasajero
        ApiREST.obtenerViajesPas(2, false, mRequestQueue, new ApiREST.Callback() {
            @Override
            public void onResult(Object res) {
                List<Viaje> viajes = (List<Viaje>) res;
            }
        });
*/
/*
        //Consulta viajes PASADOS de un usuario
        List<Viaje> viajesPasados = new ArrayList<>();
        ApiREST.obtenerViajesCond(2, true, mRequestQueue, new ApiREST.Callback() {
            @Override
            public void onResult(Object res) {
                List<Viaje> viajes = (List<Viaje>) res;
                viajesPasados.addAll(viajes);

                ApiREST.obtenerViajesPas(2, true, mRequestQueue, new ApiREST.Callback() {
                    @Override
                    public void onResult(Object res) {
                        List<Viaje> viajes = (List<Viaje>) res;
                        viajesPasados.addAll(viajes);
                        Collections.sort(viajesPasados);
                        System.out.println ("[REST][Viajes pasados] Respuesta tamaño: "+viajesPasados.toArray().length);

                        for (Viaje i : viajesPasados){
                            System.out.println("i " + i.getFecha_hora().toString());
                        }
                    }
                });
            }
        });


        //Consulta vubicaciones universidades
        ApiREST.obtenerUbicaciones(0,  mRequestQueue, new ApiREST.Callback() {
            @Override
            public void onResult(Object res) {
                List<Ubicacion> ubicaciones = (List<Ubicacion>) res;
            }
        });
*/
        //Consulta ubicaciones lugares
        ApiREST.obtenerUbicaciones(1,  mRequestQueue, new ApiREST.Callback() {
            @Override
            public void onResult(Object res) {
                List<Ubicacion> ubicaciones = (List<Ubicacion>) res;
                System.out.println ("Probando Ubicaciones" + ubicaciones.toArray().length);
            }
        });
        /*

        //Consulta valoraciones de un usuario
        ApiREST.obtenerValoracion(1, 3,  mRequestQueue, new ApiREST.Callback() {
            @Override
            public void onResult(Object res) {
                List<Valoracion> valoraciones = (List<Valoracion>) res;
            }
        });


        //Insertar pasajero a un viaje
        ApiREST.insertarPasajero(3, 2,  mRequestQueue, new ApiREST.Callback() {
            @Override
            public void onResult(Object res) {

            }
        });


        //Eliminar pasajero a un viaje
        ApiREST.eliminarPasajero(3, 2,  mRequestQueue, new ApiREST.Callback() {
            @Override
            public void onResult(Object res) {

            }
        });

        //Eliminar viaje
        ApiREST.eliminarViaje(3,   mRequestQueue, new ApiREST.Callback() {
            @Override
            public void onResult(Object res) {

            }
        });


      //Crear viaje
        ApiREST.crearViaje(1, 5, 10, 1622152800000L, 4, 0.4F, mRequestQueue, new ApiREST.Callback() {
            @Override
            public void onResult(Object res) {

            }
        });


     //Crear Valoración
        ApiREST.crearValoracion(7, 4 , 3, 9.6F,   mRequestQueue, new ApiREST.Callback() {
            @Override
            public void onResult(Object res) {

            }
        });
*/

        //Consulta viajes PASADOS de un usuario
        List<Viaje> viajesPasados = new ArrayList<>();
        ApiREST.obtenerViajesUbi("La Macarena", "ETSI", true, mRequestQueue, new ApiREST.Callback() {
            @Override
            public void onResult(Object res) {
                List<Viaje> viajes = (List<Viaje>) res;
                viajesPasados.addAll(viajes);

                ApiREST.obtenerViajesPas(2, true, mRequestQueue, new ApiREST.Callback() {
                    @Override
                    public void onResult(Object res) {
                        List<Viaje> viajes = (List<Viaje>) res;
                        viajesPasados.addAll(viajes);
                        Collections.sort(viajesPasados);
                        System.out.println ("[REST][Viajes pasados] Respuesta tamaño: "+viajesPasados.toArray().length);

                        for (Viaje i : viajesPasados){
                            System.out.println("i " + i.getFecha_hora().toString());
                        }
                    }
                });
            }
        });


        //Viajes disponibles
        ApiREST.obtenerViajesUbi("Sevilla Este", "ETSI", true, mRequestQueue, new ApiREST.Callback() {
            @Override
            public void onResult(Object res) {
                List<Viaje> viajes_api = (List<Viaje>) res;
                for (Viaje i : viajes_api){
                    System.out.println("i " + i.getConductor());
                }
            }
        });

        return vista;
    }
}