package br.com.daciosoftware.roboarm.ui.roboarm;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import br.com.daciosoftware.roboarm.R;

public class RoboArmFragment extends Fragment {

    private View root;
    private Context mContext;
    private SeekBar seekBarServoBase;
    private SeekBar seekBarServoAltura;
    private SeekBar seekBarServoAngulo;
    private SeekBar seekBarServoGarra;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
    }
    public View onCreateView(@NonNull LayoutInflater inflater,   ViewGroup container, Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_roboarm, container, false);
        seekBarServoBase = root.findViewById(R.id.seekBarServoBase);
        seekBarServoAltura = root.findViewById(R.id.seekBarServoAltura);
        seekBarServoAngulo = root.findViewById(R.id.seekBarServoAngulo);
        seekBarServoGarra = root.findViewById(R.id.seekBarServoGarra);
        if (savedInstanceState != null) {
            seekBarServoBase.setProgress(savedInstanceState.getInt("BASE"));
            seekBarServoAltura.setProgress(savedInstanceState.getInt("ALTURA"));
            seekBarServoAngulo.setProgress(savedInstanceState.getInt("ANGULO"));
            seekBarServoGarra.setProgress(savedInstanceState.getInt("GARRA"));
        }
        return root;
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        int seekBarServoBaseAngle = seekBarServoBase.getProgress();
        int seekBarServoAlturaAngle = seekBarServoAltura.getProgress();
        int seekBarServoAnguloAngle = seekBarServoAngulo.getProgress();
        int seekBarServoGarraAngle = seekBarServoGarra.getProgress();
        outState.putInt("BASE", seekBarServoBaseAngle);
        outState.putInt("ALTURA", seekBarServoAlturaAngle);
        outState.putInt("ANGULO", seekBarServoAnguloAngle);
        outState.putInt("GARRA", seekBarServoGarraAngle);
    }

}