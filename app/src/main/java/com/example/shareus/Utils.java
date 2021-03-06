package com.example.shareus;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.RatingBar;

import com.android.volley.RequestQueue;
import com.example.shareus.dialog.CustomDialog;
import com.example.shareus.dialog.DialogCallback;
import com.example.shareus.model.Ubicacion;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class Utils {

    public static String prettyParse(Date given) {
        SimpleDateFormat full = new SimpleDateFormat("dd/MM/yyyy", Locale.forLanguageTag("es-ES"));
        SimpleDateFormat time = new SimpleDateFormat("HH:mm", Locale.forLanguageTag("es-ES"));
        SimpleDateFormat date = new SimpleDateFormat("dd MMMM", Locale.forLanguageTag("es-ES"));

        Date current = new Date();
        Calendar currentCal = Calendar.getInstance();
        currentCal.setTime(current);
        Calendar givenCal = Calendar.getInstance();
        givenCal.setTime(given);

        int givenYear = givenCal.get(Calendar.YEAR);
        int currentYear = currentCal.get(Calendar.YEAR);
        int givenDay = givenCal.get(Calendar.DAY_OF_YEAR);
        int currentDay = currentCal.get(Calendar.DAY_OF_YEAR);

        if (givenYear == currentYear) {
            int value = givenDay - currentDay;
            switch (value) {
                case -1:
                    return "ayer a las " + time.format(given);
                case 0:
                    return "hoy a las " + time.format(given);
                case 1:
                    return "mañana a las " + time.format(given);
                default:
                    String format = date.format(given);
                    if(format.startsWith("0"))
                        format = format.substring(1);
                    return format + " a las " + time.format(given);
            }
        } else {
            return full.format(given) + " a las " + time.format(given);
        }
    }

    public static String[] getListaNombres(List<Ubicacion> lista) {
        String nombres= "";
        for (Ubicacion i : lista ){
            nombres += (i.getNombre() + ",");
        }
        String[] ubs = nombres.split(",");
        return ubs;
    }

    public  static Integer getIdUbi(List<Ubicacion> lista, String ubi){
        Integer id = null;
        for (Ubicacion i : lista ){
            if (i.getNombre().equals(ubi)){
                id = i.getId();
            }
        }
        return id;
    }
}
