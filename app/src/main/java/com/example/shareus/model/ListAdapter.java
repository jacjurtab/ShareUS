package com.example.shareus.model;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.List;

public abstract class ListAdapter extends BaseAdapter {

    private final List<?> objects;
    private final int R_layout_IdView;
    private final Context context;

    public ListAdapter(Context context, int R_layout_IdView, List<?> objects) {
        super();
        this.context = context;
        this.objects = objects;
        this.R_layout_IdView = R_layout_IdView;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        if(view == null) {
            LayoutInflater vi = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = vi.inflate(R_layout_IdView, null);
        }
        crearEntrada(objects.get(position), view);

        return view;
    }

    @Override
    public int getCount() {
        return objects.size();
    }

    @Override
    public Object getItem(int position) {
        return objects.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    /** Devuelve cada una de las entradas con cada una de las vistas a la que debe de ser asociada
     * @param entrada La entrada que será la asociada a la view. La entrada es del tipo del paquete/handler
     * @param view View particular que contendrá los datos del paquete/handler
     */
    public abstract void crearEntrada(Object entrada, View view);

}
