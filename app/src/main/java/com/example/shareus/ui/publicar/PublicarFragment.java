package com.example.shareus.ui.publicar;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.shareus.R;

import java.util.Calendar;

public class PublicarFragment extends Fragment {

    private PublicarViewModel publicarViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        publicarViewModel =
                new ViewModelProvider(this).get(PublicarViewModel.class);
        View root = inflater.inflate(R.layout.fragment_publicar, container, false);
        EditText etPlannedDate = (EditText) root.findViewById(R.id.etPlannedDate);
        EditText etPlannedHour = (EditText) root.findViewById(R.id.etPlannedHour);
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
        return root;
    }

    private void showTimePickerDialog(EditText etPlannedHour) {
        TimePickerFragment newFragment = TimePickerFragment.newInstance(new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                final String selectedTime = hourOfDay + ":" + minute;
                etPlannedHour.setText(selectedTime);
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
                final String selectedDate = day + " / " + (month+1) + " / " + year;
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