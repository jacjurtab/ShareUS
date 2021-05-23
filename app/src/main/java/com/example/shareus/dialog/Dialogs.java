package com.example.shareus.dialog;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.shareus.R;
import com.example.shareus.Utils;
import com.example.shareus.model.Viaje;

public class Dialogs {

    public static void showValoracionDialog(Context context, DialogCallback dialogCallback) {
        final CustomDialog dialog = new CustomDialog(context);
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.dialog_valoracion, null);
        dialog.setContentView(view);
        RatingBar ratingBar = view.findViewById(R.id.ratingBar);
        Button valorar = view.findViewById(R.id.enviarvaloracion);
        valorar.setOnClickListener(v -> {
            float numEstrellas = ratingBar.getRating();
            int valoracion = (int) (numEstrellas * 2);
            if (dialogCallback != null) {
                dialogCallback.callBack(valoracion);
            }
            dialog.dismiss();
        });
        dialog.show();
    }

    @SuppressLint("SetTextI18n")
    public static void showViajeInfoDialog(Context context, Viaje viaje) {
        final CustomDialog dialog = new CustomDialog(context);
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.dialog_viaje, null);
        dialog.setContentView(view);
        Button confirm = view.findViewById(R.id.confirmar);
        confirm.setOnClickListener(view1 -> dialog.dismiss());
        TextView nombre = view.findViewById(R.id.driver_name);
        nombre.setText(viaje.getConductorObj().getNombre() + " (" + viaje.getConductorObj().getTelefono() + ")");
        TextView origen = view.findViewById(R.id.origin_name);
        origen.setText(viaje.getOrigen());
        TextView fecha = view.findViewById(R.id.time_name);
        fecha.setText(Utils.prettyParse(viaje.getFecha_hora()));
        dialog.show();
    }

    public static void showAvisoViajeDialog(Context context) {
        final CustomDialog dialog = new CustomDialog(context);
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.dialog_aviso, null);
        dialog.setContentView(view);
        Button confirm = view.findViewById(R.id.confirmar);
        confirm.setOnClickListener(view1 -> dialog.dismiss());
        dialog.show();
    }

}
