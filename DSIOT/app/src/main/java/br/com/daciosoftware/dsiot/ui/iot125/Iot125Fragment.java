package br.com.daciosoftware.dsiot.ui.iot125;

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

import br.com.daciosoftware.dsiot.R;

public class Iot125Fragment extends Fragment {

    private Iot125ViewModel iot125ViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        iot125ViewModel =
                new ViewModelProvider(this).get(Iot125ViewModel.class);
        View root = inflater.inflate(R.layout.fragment_iot125, container, false);
        final TextView textView = root.findViewById(R.id.text_dashboard);
        iot125ViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        return root;
    }
}