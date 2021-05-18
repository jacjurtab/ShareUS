package com.example.shareus.ui;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.android.volley.RequestQueue;
import com.example.shareus.MainActivity;
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
        CONDUCTOR
    }

    private MutableLiveData<List<Viaje>> viajesPasados;
    private MutableLiveData<List<Viaje>> viajesPasajero;
    private MutableLiveData<List<Viaje>> viajesConductor;
    RequestQueue mRequestQueue = ApiREST.getInstance(null).getQueue();

    public MutableLiveData<List<Viaje>> getViajes(Tipo tipo) {
        switch (tipo) {
            case PASADOS:
                if (viajesPasados == null) {
                    viajesPasados = new MutableLiveData<>();
                    actualizarViajes(Tipo.PASADOS);
                }
                return viajesPasados;
            case PASAJERO:
                if (viajesPasajero == null) {
                    viajesPasajero = new MutableLiveData<>();
                    actualizarViajes(Tipo.PASAJERO);
                }
                return viajesPasajero;
            case CONDUCTOR:
                if (viajesConductor == null) {
                    viajesConductor = new MutableLiveData<>();
                    actualizarViajes(Tipo.CONDUCTOR);
                }
                return viajesConductor;
        }
        return null;
    }

    public void actualizarViajes(Tipo tipo) {
        switch (tipo) {
            case PASADOS:
                ApiREST.obtenerViajesCond(MainActivity.getUserId(), true, mRequestQueue, new ApiREST.Callback() {
                    @Override
                    public void onResult(Object res) {
                        List<Viaje> viajes_api = new ArrayList<>();
                        List<Viaje> viajes = (List<Viaje>) res;
                        viajes_api.addAll(viajes);

                        ApiREST.obtenerViajesPas(MainActivity.getUserId(), true, mRequestQueue, new ApiREST.Callback() {
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
                ApiREST.obtenerViajesPas(MainActivity.getUserId(), false, mRequestQueue, new ApiREST.Callback() {
                    @Override
                    public void onResult(Object res) {
                        List<Viaje> viajes_api = (List<Viaje>) res;
                        viajesPasajero.setValue(viajes_api);
                    }
                });

                break;
            case CONDUCTOR:
                ApiREST.obtenerViajesCond(MainActivity.getUserId(), false, mRequestQueue, new ApiREST.Callback() {
                    @Override
                    public void onResult(Object res) {
                        List<Viaje> viajes_api = (List<Viaje>) res;
                        viajesConductor.setValue(viajes_api);
                    }
                });
                break;
        }
    }

}
