package com.example.esp32bluetooth.ui.roboarm;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.esp32bluetooth.BluetoothConnection;
import com.example.esp32bluetooth.BluetoothInstance;
import com.example.esp32bluetooth.R;

public class RoboArmFragment extends Fragment {

    private View root;
    private Context mContext;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
    }

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        root = inflater.inflate(R.layout.fragment_roboarm, container, false);

        Button buttonStart = root.findViewById(R.id.button_start);
        Button buttonStop = root.findViewById(R.id.button_stop);
        Button buttonSpeed1 = root.findViewById(R.id.button_speed1);
        Button buttonSpeed2 = root.findViewById(R.id.button_speed2);

        buttonStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!BluetoothInstance.isConnected()) {
                    Toast.makeText(mContext, "Não há dispositivo conectado", Toast.LENGTH_LONG).show();
                    return;
                }
                BluetoothConnection bluetoothConnection = BluetoothInstance.getInstance();
                String command = "start";
                bluetoothConnection.write(command.getBytes());
            }
        });

        buttonStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!BluetoothInstance.isConnected()) {
                    Toast.makeText(mContext, "Não há dispositivo conectado", Toast.LENGTH_LONG).show();
                    return;
                }
                BluetoothConnection bluetoothConnection = BluetoothInstance.getInstance();
                String command = "stop";
                bluetoothConnection.write(command.getBytes());
            }
        });

        buttonSpeed1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!BluetoothInstance.isConnected()) {
                    Toast.makeText(mContext, "Não há dispositivo conectado", Toast.LENGTH_LONG).show();
                    return;
                }
                BluetoothConnection bluetoothConnection = BluetoothInstance.getInstance();
                String command = "speed1";
                bluetoothConnection.write(command.getBytes());
            }
        });

        buttonSpeed2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!BluetoothInstance.isConnected()) {
                    Toast.makeText(mContext, "Não há dispositivo conectado", Toast.LENGTH_LONG).show();
                    return;
                }
                BluetoothConnection bluetoothConnection = BluetoothInstance.getInstance();
                String command = "speed2";
                bluetoothConnection.write(command.getBytes());
            }
        });

        return root;

    }
}
