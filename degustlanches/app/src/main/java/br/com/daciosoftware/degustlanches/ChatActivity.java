package br.com.daciosoftware.degustlanches;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import br.com.daciosoftware.degustlanches.dao.ClienteDAO;
import br.com.daciosoftware.degustlanches.model.Chat;
import br.com.daciosoftware.degustlanches.model.Cliente;
import br.com.daciosoftware.degustlanches.util.DeviceInformation;
import br.com.daciosoftware.degustlanches.util.DialogBox;
import br.com.daciosoftware.degustlanches.webservice.ActionType;
import br.com.daciosoftware.degustlanches.webservice.CallBackTask;
import br.com.daciosoftware.degustlanches.webservice.ChatsTask;

public class ChatActivity extends AppCompatActivity implements CallBackTask, OnClickItemChat {

    private RecyclerView recyclerViewChat;
    private ChatAdapter chatAdapter;
    private Cliente cliente;
    private EditText editTextMensagem;
    private List<Chat> listChat;
    public ProgressBar progressBarChat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        recyclerViewChat = findViewById(R.id.recyclerViewChat);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(ChatActivity.this);
        mLayoutManager.setReverseLayout(false);
        mLayoutManager.setStackFromEnd(true);
        recyclerViewChat.setLayoutManager(mLayoutManager);
        progressBarChat = findViewById(R.id.progressBarChat);
        progressBarChat.setVisibility(View.VISIBLE);

        editTextMensagem = findViewById(R.id.editTextMensagem);
        ImageButton buttonEnvia = findViewById(R.id.imageButtonEnvia);
        buttonEnvia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                enviaChat();
            }
        });

        cliente = new ClienteDAO(ChatActivity.this).getCliente();

        Timer();

    }

    public void Timer() {
        Task task = new Task();
        new Timer().schedule(task, 1000, 5000);
    }

    class Task extends TimerTask {
        @Override
        public void run() {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {

                        carregaChats();

                }
            });
        }
    }

    @Override
    public void post(JSONObject jsonObject) {
        try {

            if (jsonObject == null) {
                throw new Exception(getResources().getString(R.string.error_geral));
            }

            if (!jsonObject.getBoolean("success")) {
                throw new Exception(jsonObject.getString("message"));
            }

            if (jsonObject.has("chat")) {
                carregaChats();
            }

        } catch (Exception e) {
            new DialogBox(ChatActivity.this, DialogBox.DialogBoxType.INFORMATION, getResources().getString(R.string.app_name), e.getMessage(), false).show();
            e.printStackTrace();
        }
    }

    @Override
    public void get(JSONObject jsonObject) {

        try {

            if (jsonObject == null) {
                throw new Exception(getResources().getString(R.string.error_geral));
            }

            if (!jsonObject.getBoolean("success")) {
                throw new Exception(jsonObject.getString("message"));
            }

            if (jsonObject.has("chats")) {

                listChat = new ArrayList<>();
                JSONArray jsonChats = jsonObject.getJSONArray("chats");

                for (int i = 0 ; i < jsonChats.length(); i++) {

                    JSONObject chatJson = jsonChats.getJSONObject(i);
                    Chat chat = new Chat(chatJson);
                    listChat.add(chat);

                }

                Collections.reverse(listChat);
                chatAdapter = new ChatAdapter(ChatActivity.this, listChat, ChatActivity.this);
                recyclerViewChat.setAdapter(chatAdapter);
                int position = chatAdapter.getItemCount() - 1;
                recyclerViewChat.scrollToPosition(position);
                progressBarChat.setVisibility(View.GONE);
            }

            if (jsonObject.has("chat")) {
                if (jsonObject.getString("chat") == "delete") {
                    carregaChats();
                }
            }

        } catch (Exception e) {
            new DialogBox(ChatActivity.this, DialogBox.DialogBoxType.INFORMATION, getResources().getString(R.string.app_name), e.getMessage(), false).show();
            e.printStackTrace();
        }

    }


    private void carregaChats() {

        if (DeviceInformation.isNetwork(ChatActivity.this)) {
            Chat chat = new Chat();
            chat.setCliente(cliente.getCodigo());
            new ChatsTask(ChatActivity.this, ActionType.READ, ChatActivity.this).execute(chat);
        } else {
            new DialogBox(ChatActivity.this, DialogBox.DialogBoxType.INFORMATION, getResources().getString(R.string.app_name), getResources().getString(R.string.no_connection), true).show();
        }
    }

    private void enviaChat() {

        if (DeviceInformation.isNetwork(ChatActivity.this)) {
            String mensagem = editTextMensagem.getText().toString();
            editTextMensagem.getText().clear();
            if (mensagem.isEmpty()) return;
            Chat chat = new Chat();
            chat.setCliente(cliente.getCodigo());
            chat.setMensagem(mensagem);
            chat.setTipo(1);
            chat.setDataHora(Calendar.getInstance());
            ChatsTask task = new ChatsTask(ChatActivity.this, ActionType.CREATE, ChatActivity.this);
            task.execute(chat);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_degust, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void deleteChat(Chat chat) {
        new ChatsTask(ChatActivity.this, ActionType.DELETE, ChatActivity.this).execute(chat);
    }
}
