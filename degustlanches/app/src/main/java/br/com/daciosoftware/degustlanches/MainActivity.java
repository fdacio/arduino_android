package br.com.daciosoftware.degustlanches;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;

import org.json.JSONObject;

import br.com.daciosoftware.degustlanches.dao.ClienteDAO;
import br.com.daciosoftware.degustlanches.model.Cliente;
import br.com.daciosoftware.degustlanches.model.Loja;
import br.com.daciosoftware.degustlanches.util.DialogBox;
import br.com.daciosoftware.degustlanches.webservice.ActionType;
import br.com.daciosoftware.degustlanches.webservice.CallBackTask;
import br.com.daciosoftware.degustlanches.webservice.LojasTask;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, CallBackTask {

    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle actionBarDrawerToggle;
    private Button btnCadastrse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        drawerLayout = findViewById(R.id.drawer_layout);
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.app_name, R.string.app_name);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
        NavigationView navigationView = findViewById(R.id.navigationView);
        navigationView.setNavigationItemSelectedListener(this);

        Button btnIniciaPedido = findViewById(R.id.btnIniciaPedido);
        btnIniciaPedido.setOnClickListener(new IniciaPedidoOnClickListener());

        btnCadastrse = findViewById(R.id.btnCadastrese);
        btnCadastrse.setOnClickListener(new CadastrseOnClickListener());

        /**
         * Obter versao atual do aplicativo
         */
        View headerView = navigationView.getHeaderView(0);
        TextView textViewVesaoApp = headerView.findViewById(R.id.textViewVersaoApp);

        try {
            PackageManager manager = this.getPackageManager();
            PackageInfo info = manager.getPackageInfo(this.getPackageName(), 0);
            if (info != null) {
                String versaoApp = info.versionName;
                textViewVesaoApp.setText(String.format("Versão: %s", versaoApp));
            }
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
    }

    private boolean checkCliente() {
        Cliente cliente = new ClienteDAO(MainActivity.this).getCliente();
        return (cliente != null);
    }

    private boolean hasClienteCadastrado() {
        if (checkCliente()) {
            return true;
        } else {
            new DialogBox(MainActivity.this, DialogBox.DialogBoxType.INFORMATION, getResources().getString(R.string.app_name), getResources().getString(R.string.cliente_nao_cadastrado), false).show();
            return false;
        }
    }

    public void checkHorarioFuncionamento() {
        Loja loja = new Loja();
        loja.setId(1);
        new LojasTask(MainActivity.this, ActionType.READ_HF, MainActivity.this).execute(loja);
    }

    private class IniciaPedidoOnClickListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {

            if (hasClienteCadastrado()) {
                checkHorarioFuncionamento();
            }
        }
    }

    private class CadastrseOnClickListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(MainActivity.this, BuscarClienteActivity.class);
            startActivity(intent);
        }
    }

    @Override
    public void post(JSONObject jsonObject) {

    }

    @Override
    public void get(JSONObject jsonObject) {
        try {

            if (jsonObject == null) {
                throw new Exception(getResources().getString(R.string.error_geral));
            }

            // Lança um exeption caso não esteja no horário de funcionamento.
            //if (false) {
            if (!jsonObject.getBoolean("success")) {
                throw new Exception(jsonObject.getString("message"));
            }

            startActivity(new Intent(getApplicationContext(), CardapioActivity.class));

        } catch (Exception e) {
            new DialogBox(MainActivity.this, DialogBox.DialogBoxType.INFORMATION, getResources().getString(R.string.app_name), e.getMessage(), false).show();
            e.printStackTrace();
        }
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        switch (item.getItemId()) {

            case R.id.meus_dados: {
                showCadastroCliente();
                break;
            }

            case R.id.meus_pedidos: {
                showMeusPedidos();
                break;
            }

            case R.id.chat: {
                showChat();
                break;
            }

            case R.id.configuracoes: {
                showConfiguracoes();
                break;
            }

            case R.id.sair: {
                this.exitApp(MainActivity.this);
                break;
            }
        }
        //close navigation drawer
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        return actionBarDrawerToggle.onOptionsItemSelected(item) || super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        exitApp(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        btnCadastrse.setVisibility(checkCliente() ? View.GONE : View.VISIBLE);
    }

    private void exitApp(final Activity activity) {
        new DialogBox(MainActivity.this,
                DialogBox.DialogBoxType.QUESTION,
                getResources().getString(R.string.app_name),
                getResources().getString(R.string.app_exit),
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        activity.finish();
                    }
                },
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }).show();
    }

    private void showCadastroCliente() {
        if (hasClienteCadastrado()) {
            Intent intent = new Intent(MainActivity.this, CadastroClienteActivity.class);
            intent.putExtra("ACAO", CadastroClienteActivity.UPDATE);
            startActivity(intent);
        }
    }

    private void showMeusPedidos() {
        if (hasClienteCadastrado()) {
            Intent intent = new Intent(MainActivity.this, MeusPedidosActivity.class);
            startActivity(intent);
        }
    }

    private void showConfiguracoes() {
        Intent intent = new Intent(MainActivity.this, ConfiguracoesEditActivity.class);
        startActivity(intent);
    }

    private void showChat() {
        if (hasClienteCadastrado()) {
            Intent intent = new Intent(MainActivity.this, ChatActivity.class);
            startActivity(intent);
        }
    }

}
