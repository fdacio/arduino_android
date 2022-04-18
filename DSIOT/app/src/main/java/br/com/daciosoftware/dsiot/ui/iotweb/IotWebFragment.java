package br.com.daciosoftware.dsiot.ui.iotweb;

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

public class IotWebFragment extends Fragment {

    private IotWebViewModel iotWebViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        iotWebViewModel =
                new ViewModelProvider(this).get(IotWebViewModel.class);
        View root = inflater.inflate(R.layout.fragment_iotweb, container, false);
        final WebView webView = root.findViewById(R.id.webviewIotWeb);
        webView.setWebViewClient(new WebViewClient());
        webView.getSettings().setJavaScriptEnabled(true);
        webView.loadUrl("https://automacao.daciosoftware.com.br/iot");
        return root;
    }
}