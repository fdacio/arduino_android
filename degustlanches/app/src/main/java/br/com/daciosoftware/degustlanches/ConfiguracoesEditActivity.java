package br.com.daciosoftware.degustlanches;

import android.os.Bundle;

import android.view.MenuItem;
import android.widget.CompoundButton;
import android.widget.Switch;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import br.com.daciosoftware.degustlanches.dao.NotificacaoDAO;
import br.com.daciosoftware.degustlanches.notificacao.AgendaServicoNotificacaoChat;


public class ConfiguracoesEditActivity extends AppCompatActivity {

    private NotificacaoDAO dao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_configuracoes_edit);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        dao = new NotificacaoDAO(this);

        Switch switchChat = findViewById(R.id.switchChat);
        switchChat.setChecked(dao.isNotificaChat());
        switchChat.setOnCheckedChangeListener(new SwitchNotificaChatOnCheckedChangeListener());

        Switch switchPedidos = findViewById(R.id.switchPedidos);
        switchPedidos.setChecked(dao.isNotificaPedido());
        switchPedidos.setOnCheckedChangeListener(new SwitchNotificaPedidosOnCheckedChangeListener());

        Switch switchPromocoes = findViewById(R.id.switchPromocoes);
        switchPromocoes.setChecked(dao.isNotificaPromocoes());
        switchPromocoes.setOnCheckedChangeListener(new SwitchNotificaPromocoesOnCheckedChangeListener());

    }

    private class SwitchNotificaChatOnCheckedChangeListener implements CompoundButton.OnCheckedChangeListener {

        @Override
        public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {

            dao.setNotificaChat(isChecked);

            if (isChecked) {
                new AgendaServicoNotificacaoChat().agenda(ConfiguracoesEditActivity.this, 5);
            } else {
                new AgendaServicoNotificacaoChat().cancelarAgendamento(ConfiguracoesEditActivity.this);
            }

        }
    }

    private class SwitchNotificaPedidosOnCheckedChangeListener implements CompoundButton.OnCheckedChangeListener {

        @Override
        public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {

            dao.setNotificaPedidos(isChecked);
        }
    }

    private class SwitchNotificaPromocoesOnCheckedChangeListener implements CompoundButton.OnCheckedChangeListener {

        @Override
        public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {

            dao.setNotificaPromocoes(isChecked);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
