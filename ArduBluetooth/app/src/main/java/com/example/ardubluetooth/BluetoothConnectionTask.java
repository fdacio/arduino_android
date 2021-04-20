package com.example.ardubluetooth;

import android.app.Activity;
import android.app.ProgressDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import java.io.IOException;
import java.io.OutputStream;
import java.util.UUID;

public class BluetoothConnectionTask extends AsyncTask<Void, Void, BluetoothDevice> {

    private static BluetoothConnectionTask instance;
    private BluetoothSocket mmSocket;
    private OutputStream mmOutStream;
    private boolean connected = false;
    private BluetoothDevice mmDevice;
    private BluetoothConnectionListener mmListener;
    private Context mmContext;
    private ProgressDialog progressDialog;

    public static BluetoothConnectionTask getInstance(BluetoothDevice device, BluetoothConnectionListener listener, Context context) {
        if (instance == null) {
            instance = new BluetoothConnectionTask(device, listener, context);
        }
        return instance;
    }

    public static BluetoothConnectionTask getInstance() {
        if (instance == null) {
            instance = new BluetoothConnectionTask();
        }
        return instance;
    }

    public BluetoothConnectionTask(BluetoothDevice device, BluetoothConnectionListener listener, Context context) {
        mmDevice = device;
        mmListener = listener;
        mmContext = context;
    }

    public BluetoothConnectionTask() {
    }

    @Override
    protected void onPreExecute() {
        progressDialog = ProgressDialog.show(mmContext, "Bluetooth", "Aguarde, pareando ...");
    }

    @Override
    protected BluetoothDevice doInBackground(Void... params) {
        BluetoothSocket tmp = null;
        try {
            tmp = mmDevice.createRfcommSocketToServiceRecord(UUID.fromString("00001101-0000-1000-8000-00805F9B34FB"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        mmSocket = tmp;
        OutputStream tmpOut = null;
        try {
            tmpOut = mmSocket.getOutputStream();
        } catch (IOException e) {
            e.printStackTrace();
        }
        mmOutStream = tmpOut;
        BluetoothAdapter.getDefaultAdapter().cancelDiscovery();

        try {
            mmSocket.connect();
            connected = true;
        } catch (IOException e) {
            e.printStackTrace();
            connected = false;
            try {
                mmSocket.close();
            } catch (IOException e2) {
                e2.printStackTrace();
            }
        }
        return mmDevice;
    }

    @Override
    protected void onPostExecute(BluetoothDevice device) {
        progressDialog.dismiss();
        if (connected) {
            mmListener.setConnected(device);
        } else {
            Toast.makeText(mmContext, "Não foi possível conectar.", Toast.LENGTH_LONG).show();
        }
    }

    public void write(byte[] buffer) {
        try {
            if (mmOutStream != null) {
                mmOutStream.write(buffer);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void desconnect()
    {
        try {
            if (mmOutStream != null) {
                mmOutStream.close();
                mmOutStream = null;
            }
            if (mmSocket != null) {
                mmSocket.close();
                mmSocket = null;
            }
            connected = false;
            cancel(true);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
