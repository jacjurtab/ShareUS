package com.example.shareus.ui.publicar;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.speech.tts.SynthesisRequest;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.android.volley.RequestQueue;
import com.example.shareus.R;
import com.example.shareus.api.ApiREST;
import com.example.shareus.model.Ubicacion;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.sql.Timestamp;
import java.util.stream.Collectors;
import  com.example.shareus.Utils;

public class PublicarFragment extends Fragment {

    List<Ubicacion> ubicaciones;
    List<Ubicacion> universidades;
    RequestQueue mRequestQueue;
    Button volveratras;
    Button publicar;
    EditText etPlannedDate;
    EditText etPlannedHour;
    EditText numpPasajeros;
    EditText precio;
    Spinner origen;
    Spinner destino;

    private PublicarViewModel publicarViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        publicarViewModel =
                new ViewModelProvider(this).get(PublicarViewModel.class);
        View root = inflater.inflate(R.layout.fragment_publicar, container, false);
        Context context = this.getContext();


        volveratras = root.findViewById(R.id.atras);
        publicar = root.findViewById(R.id.publicar);
        etPlannedDate = (EditText) root.findViewById(R.id.etPlannedDate);
        etPlannedHour = (EditText) root.findViewById(R.id.etPlannedHour);
        numpPasajeros = root.findViewById(R.id.plazasTotales);
        precio = root.findViewById(R.id.precio);
        origen = root.findViewById(R.id.Spinnerorigen);
        destino = root.findViewById(R.id.Spinnerdestino);

        mRequestQueue = ApiREST.getInstance(getContext()).getQueue();
        ApiREST.obtenerUbicaciones(0,  mRequestQueue, res -> {
            ubicaciones = (List<Ubicacion>) res;
            ApiREST.obtenerUbicaciones(1, mRequestQueue, res1 -> {
                universidades = (List<Ubicacion>) res1;
                ubicaciones.addAll(universidades);
                String[] ubs = Utils.getListaNombres(ubicaciones);
                ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, ubs);
                origen.setAdapter(adapter);
                destino.setAdapter(adapter);
            });
    });

        /*origen.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                Toast.makeText(adapterView.getContext(), (String) adapterView.getItemAtPosition(position), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        destino.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                Toast.makeText(adapterView.getContext(), (String) adapterView.getItemAtPosition(position), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });*/


        etPlannedDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (v.getId() == R.id.etPlannedDate) {
                    showDatePickerDialog(etPlannedDate);
                }
            }
        });
        etPlannedHour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (v.getId() == R.id.etPlannedHour) {
                    showTimePickerDialog(etPlannedHour);
                }
            }
        });
        volveratras.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "¡Publicación de viaje cancelada!", Toast.LENGTH_SHORT).show();
                NavController navController = Navigation.findNavController((Activity) context, R.id.nav_host_fragment);
                navController.navigate(R.id.nav_misviajes);
            }
        });
        publicar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    onClickPublicar(v);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        });
        return root;
    }


    public  void onClickPublicar(View w) throws ParseException {
        Integer idOrigen;
        Integer idDestino;
        String fecha;
        String hora;
        String fechora;
        long fecha_hora = 0;
        int max_plazas = 0;
        Float dinero = 0f;
        boolean isAllFill = true;


        idOrigen = Utils.getIdUbi(ubicaciones, (String) origen.getSelectedItem());
        idDestino = Utils.getIdUbi(ubicaciones, (String) destino.getSelectedItem());
        fecha = etPlannedDate.getText().toString();
        hora = etPlannedHour.getText().toString();
        fechora = fecha + " " + hora;

        if (etPlannedHour.getText().toString().isEmpty() || etPlannedHour.getText().toString().isEmpty()
            || numpPasajeros.getText().toString().isEmpty() || precio.getText().toString().isEmpty()){
            isAllFill = false;
        }
        else {
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");
            Date date = dateFormat.parse(fechora);
            Timestamp timeStamp = new Timestamp(date.getTime());
            fecha_hora = timeStamp.getTime();
            max_plazas = Integer.parseInt(numpPasajeros.getText().toString());
            dinero = Float.parseFloat(precio.getText().toString());
        }

        if (isAllFill) {
            Toast.makeText(getContext(), "¡Viaje publicado correctamente!", Toast.LENGTH_SHORT).show();
            ApiREST.crearViaje(2, idOrigen, idDestino, fecha_hora, max_plazas, dinero, mRequestQueue, new ApiREST.Callback() {
                @Override
                public void onResult(Object res) {

                }
            });
            NavController navController = Navigation.findNavController((Activity) getContext(), R.id.nav_host_fragment);
            navController.navigate(R.id.nav_misviajes);
        }
        else {
            Toast.makeText(getContext(),"¡Rellena todos los campos!",Toast.LENGTH_SHORT).show();
        }


    }

    private void showTimePickerDialog(EditText etPlannedHour) {
        final Calendar getTime = Calendar.getInstance();
        TimePickerFragment newFragment = TimePickerFragment.newInstance(new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                getTime.set(Calendar.HOUR_OF_DAY, hourOfDay);
                getTime.set(Calendar.MINUTE, minute);
                SimpleDateFormat timeformat=new SimpleDateFormat("HH:mm", Locale.getDefault());
                String formatedDate = timeformat.format(getTime.getTime());
                etPlannedHour.setText(formatedDate);
            }
        });
        newFragment.show(getActivity().getSupportFragmentManager(), "timePicker");
    }

    public static class TimePickerFragment extends DialogFragment {

        private TimePickerDialog.OnTimeSetListener listener;

        public static TimePickerFragment newInstance(TimePickerDialog.OnTimeSetListener listener) {
            TimePickerFragment fragment = new TimePickerFragment();
            fragment.setListener(listener);
            return fragment;
        }

        public void setListener(TimePickerDialog.OnTimeSetListener listener) {
            this.listener = listener;
        }

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the current time as the default values for the picker
            final Calendar c = Calendar.getInstance();
            int hour = c.get(Calendar.HOUR_OF_DAY);
            int minute = c.get(Calendar.MINUTE);

            TimePickerDialog hora_actual = new TimePickerDialog(getActivity(), listener, hour, minute,DateFormat.is24HourFormat(getActivity()));

            return hora_actual;
        }
    }

    private void showDatePickerDialog(EditText etPlannedDate) {
        DatePickerFragment newFragment = DatePickerFragment.newInstance(new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                // +1 because January is zero
                final String selectedDate = day + "/" + (month+1) + "/" + year;
                etPlannedDate.setText(selectedDate);
            }
        });

        newFragment.show(getActivity().getSupportFragmentManager(), "datePicker");
    }

    public static class DatePickerFragment extends DialogFragment {

        private DatePickerDialog.OnDateSetListener listener;

        public static DatePickerFragment newInstance(DatePickerDialog.OnDateSetListener listener) {
            DatePickerFragment fragment = new DatePickerFragment();
            fragment.setListener(listener);
            return fragment;
        }

        public void setListener(DatePickerDialog.OnDateSetListener listener) {
            this.listener = listener;
        }

        @Override
        @NonNull
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            final Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);

            DatePickerDialog dia_actual = new DatePickerDialog(getActivity(), listener, year - 18, month, day);

            c.set(Calendar.DAY_OF_MONTH, day + 1);
            dia_actual.getDatePicker().setMinDate(c.getTimeInMillis());
            c.set(Calendar.MONTH, month + 1);
            dia_actual.getDatePicker().setMaxDate(c.getTimeInMillis());

            return dia_actual;
        }

    }

}