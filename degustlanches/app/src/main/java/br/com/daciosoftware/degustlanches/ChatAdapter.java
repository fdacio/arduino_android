package br.com.daciosoftware.degustlanches;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import br.com.daciosoftware.degustlanches.model.Chat;
import br.com.daciosoftware.degustlanches.util.MyDateUtil;
import br.com.daciosoftware.degustlanches.webservice.ActionType;
import br.com.daciosoftware.degustlanches.webservice.ChatsTask;
import br.com.daciosoftware.degustlanches.webservice.ChatsWS;

public class ChatAdapter extends RecyclerView.Adapter {

    private static final int VIEW_TYPE_MESSAGE_SENT = 1;
    private static final int VIEW_TYPE_MESSAGE_RECEIVED = 2;

    private List<Chat> listChat;
    private final OnClickItemChat onClickItemChat;
    private Context context;

    public ChatAdapter(Context context, List<Chat> listChat, OnClickItemChat onClickItemChat) {
        this.context = context;
        this.listChat = listChat;
        this.onClickItemChat = onClickItemChat;
    }

    @Override
    public int getItemViewType(int position) {
        Chat chat = listChat.get(position);

        if (chat.getTipo() == 1) {
            // If the current user is the sender of the message
            return VIEW_TYPE_MESSAGE_SENT;
        } else {
            // If some other user sent the message
            return VIEW_TYPE_MESSAGE_RECEIVED;
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view;

        if (viewType == VIEW_TYPE_MESSAGE_SENT) {
            view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.row_adapter_chat_enviado, parent, false);
            return new ChatEnviadoViewHolder(view);
        } else if (viewType == VIEW_TYPE_MESSAGE_RECEIVED) {
            view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.row_adapter_chat_recebido, parent, false);
            return new ChatRecebidoViewHolder(view);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        Chat chat = listChat.get(position);

        switch (holder.getItemViewType()) {
            case VIEW_TYPE_MESSAGE_SENT:
                ((ChatEnviadoViewHolder) holder).bind(chat);
                break;
            case VIEW_TYPE_MESSAGE_RECEIVED:
                ((ChatRecebidoViewHolder) holder).bind(chat);
        }
    }

    @Override
    public int getItemCount() {
        return listChat.size();
    }

    protected class ChatEnviadoViewHolder extends RecyclerView.ViewHolder {

        public TextView textViewMensagem;
        public TextView textViewDataHora;
        public ImageView imageViewLido;
        public ImageView imageViewDelete;

        public ChatEnviadoViewHolder(View itemView) {
            super(itemView);
            textViewMensagem = itemView.findViewById(R.id.textViewTextoMensagem);
            textViewDataHora = itemView.findViewById(R.id.textViewDataHora);
            imageViewLido = itemView.findViewById(R.id.imageViewLido);
            imageViewDelete = itemView.findViewById(R.id.imageViewDelete);
        }

        void bind(final Chat chat) {

            textViewMensagem.setText(chat.getMensagem());
            textViewDataHora.setText(MyDateUtil.calendarToDateTimeBr(chat.getDataHora()));

            if (chat.isLida()) {
                imageViewLido.setImageResource(R.drawable.check_lido);
            } else {
                imageViewLido.setImageResource(R.drawable.check_nao_lido);
            }

            if (listChat.get(listChat.size() - 1).equals(chat)) {
                imageViewDelete.setVisibility(View.VISIBLE);
            } else {
                imageViewDelete.setVisibility(View.GONE);
            }

            imageViewDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onClickItemChat.deleteChat(chat);
                }
            });

        }
    }

    protected class ChatRecebidoViewHolder extends RecyclerView.ViewHolder {

        public TextView textViewMensagem;
        public TextView textViewHora;

        public ChatRecebidoViewHolder(View itemView) {
            super(itemView);
            textViewMensagem = itemView.findViewById(R.id.textViewTextoMensagem);
            textViewHora = itemView.findViewById(R.id.textViewDataHora);
        }

        void bind(Chat chat) {
            textViewMensagem.setText(chat.getMensagem());
            textViewHora.setText(MyDateUtil.calendarToDateTimeBr(chat.getDataHora()));
            if ( chat.getTipo() == 2) {
                //Marca Messagens como lidas
                (new ChatsTask(context, ActionType.UPDATE)).execute(chat);
            }
        }
    }

}
