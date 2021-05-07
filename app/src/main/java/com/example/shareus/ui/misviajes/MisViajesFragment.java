package com.example.shareus.ui.misviajes;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.shareus.R;

public class MisViajesFragment extends Fragment {

    private MisViajesViewModel misViajesViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        misViajesViewModel =
                new ViewModelProvider(this).get(MisViajesViewModel.class);
        View root = inflater.inflate(R.layout.fragment_misviajes, container, false);
        final TextView textView = root.findViewById(R.id.text_misviajes);
        misViajesViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        return root;
    }
}