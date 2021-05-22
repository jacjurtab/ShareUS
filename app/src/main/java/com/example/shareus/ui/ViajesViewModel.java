package com.example.shareus.ui;

import android.content.Context;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.android.volley.RequestQueue;
import com.example.shareus.MainActivity;
import com.example.shareus.Session;
import com.example.shareus.api.ApiREST;
import com.example.shareus.model.Viaje;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ViajesViewModel extends ViewModel {

    public enum Tipo {
        PASADOS,
        PASAJERO,
        CONDUCTOR,
        OR_DEST
    }

    private MutableLiveData<List<Viaje>> viajesPasados;
    private MutableLiveData<List<Viaje>> viajesPasajero;
    private MutableLiveData<List<Viaje>> viajesConductor;
    private MutableLiveData<List<Viaje>> viajesDisponibles;
    RequestQueue mRequestQueue = ApiREST.getInstance(null).getQueue();

    public MutableLiveData<List<Viaje>> getViajes(Tipo tipo, String origen, String destino, Context cxt) {
        switch (tipo) {
            case PASADOS:
                if (viajesPasados == null) {
                    viajesPasados = new MutableLiveData<>();
                    actualizarViajes(Tipo.PASADOS, null, null, cxt);
                }
                return viajesPasados;
            case PASAJERO:
                if (viajesPasajero == null) {
                    viajesPasajero = new MutableLiveData<>();
                    actualizarViajes(Tipo.PASAJERO, null, null, cxt);
                }
                return viajesPasajero;
            case CONDUCTOR:
                if (viajesConductor == null) {
                    viajesConductor = new MutableLiveData<>();
                    actualizarViajes(Tipo.CONDUCTOR, null, null, cxt);
                }
                return viajesConductor;
            case OR_DEST:
                if (viajesDisponibles == null) {
                    viajesDisponibles = new MutableLiveData<>();
                    actualizarViajes(Tipo.OR_DEST, origen, destino, cxt);
                }
                return viajesDisponibles;
        }
        return null;
    }

    public void actualizarViajes(Tipo tipo, String origen, String destino, Context cxt) {
        switch (tipo) {
            case PASADOS:
                ApiREST.obtenerViajesCond(Session.get(cxt).getUserId(), true, mRequestQueue, new ApiREST.Callback() {
                    @Override
                    public void onResult(Object res) {
                        List<Viaje> viajes_api = new ArrayList<>();
                        List<Viaje> viajes = (List<Viaje>) res;
                        viajes_api.addAll(viajes);

                        ApiREST.obtenerViajesPas(Session.get(cxt).getUserId(), true, mRequestQueue, new ApiREST.Callback() {
                            @Override
                            public void onResult(Object res) {
                                List<Viaje> viajes = (List<Viaje>) res;
                                viajes_api.addAll(viajes);
                                Collections.sort(viajes_api);
                                System.out.println ("[REST][Viajes pasados] Respuesta tamaño: "+viajes_api.toArray().length);
                                viajesPasados.setValue(viajes_api);
                              }
                        });
                    }
                });
                break;
            case PASAJERO:
                ApiREST.obtenerViajesPas(Session.get(cxt).getUserId(), false, mRequestQueue, new ApiREST.Callback() {
                    @Override
                    public void onResult(Object res) {
                        List<Viaje> viajes_api = (List<Viaje>) res;
                        viajesPasajero.setValue(viajes_api);
                    }
                });

                break;
            case CONDUCTOR:
                ApiREST.obtenerViajesCond(Session.get(cxt).getUserId(), false, mRequestQueue, new ApiREST.Callback() {
                    @Override
                    public void onResult(Object res) {
                        List<Viaje> viajes_api = (List<Viaje>) res;
                        viajesConductor.setValue(viajes_api);
                    }
                });
                break;

            case OR_DEST:
                ApiREST.obtenerViajesUbi(origen, destino, true, mRequestQueue, new ApiREST.Callback() {
                    @Override
                    public void onResult(Object res) {
                        List<Viaje> viajes_api = (List<Viaje>) res;
                        viajesDisponibles.setValue(viajes_api);
                        Log.d("DEBUG", "Obtencion viajes disponibles: " + viajesDisponibles.getValue().size());
                    }
                });
                break;

        }
    }

}
