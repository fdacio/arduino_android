package br.com.daciosoftware.dsiot.ui.iot124;

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

public class Iot124Fragment extends Fragment {

    private Iot124ViewModel iot124ViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        iot124ViewModel =
                new ViewModelProvider(this).get(Iot124ViewModel.class);
        View root = inflater.inflate(R.layout.fragment_iot124, container, false);
        final TextView textView = root.findViewById(R.id.text_home);
        iot124ViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        return root;
    }
}