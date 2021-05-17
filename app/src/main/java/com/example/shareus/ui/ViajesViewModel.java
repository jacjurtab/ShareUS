package com.example.shareus.ui;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.shareus.model.Viaje;

import java.sql.Timestamp;
import java.util.ArrayList;
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
        List<Viaje> viajes_api = new ArrayList<>();

        switch (tipo) {
            case PASADOS:
                this.viajesPasados.setValue(viajes_api);
                break;
            case PASAJERO:
                viajes_api.add(new Viaje(10, "Celia", "Sevilla Este", "CAMPUS ETSI", new Timestamp(1620117000000L), 2, 4, null, 5.7f));
                this.viajesPasajero.setValue(viajes_api);
                break;
            case CONDUCTOR:
                viajes_api.add(new Viaje(6, "Ángel", "La Macarena", "CAMPUS VIAPOL", new Timestamp(1620117000000L), 2, 4, null, 5.7f));
                viajes_api.add(new Viaje(5, "Celia", "Sevilla Este", "FACULTAD COMUNICACIÓN", new Timestamp(1621060200000L), 0, 3, null, 8.4f));
                this.viajesConductor.setValue(viajes_api);
                break;
        }
    }

}
