package br.com.daciosoftware.dsiot.ui.iot124;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import br.com.daciosoftware.dsiot.R;

public class Iot124Fragment extends Fragment {

    private Iot124ViewModel iot124ViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        iot124ViewModel = new ViewModelProvider(this).get(Iot124ViewModel.class);
        View root = inflater.inflate(R.layout.fragment_iot124, container, false);
        final WebView webView = root.findViewById(R.id.webviewIot124);
        webView.setWebViewClient(new WebViewClient());
        webView.loadUrl("http://192.168.1.124");
        return root;
    }
}