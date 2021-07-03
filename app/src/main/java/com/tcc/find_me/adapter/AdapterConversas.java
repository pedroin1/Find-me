package com.tcc.find_me.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.tcc.find_me.R;
import com.tcc.find_me.model.Conversa;
import com.tcc.find_me.model.Usuario;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class AdapterConversas extends RecyclerView.Adapter<AdapterConversas.MyViewHolder> {

    private List<Conversa> conversas;
    private Context context;

    public AdapterConversas(List<Conversa> lista, Context c) {
        this.conversas = lista;
        this.context = c;
    }

    @NonNull
    @NotNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {

        View itemLista = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_conversas,parent,false);
        return new MyViewHolder(itemLista);
    }


    @Override
    public void onBindViewHolder(@NonNull @NotNull AdapterConversas.MyViewHolder holder, int position) {

        Conversa conversa = conversas.get(position);
        holder.ultimaMensagem.setText(conversa.getUltimaMensagem());

        Usuario usuario = conversa.getUsuarioExibicao();
        holder.nome.setText(usuario.getNome());
        holder.foto.setImageResource(R.drawable.avatar);


    }

    @Override
    public int getItemCount() {
        return conversas.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        private CircleImageView foto;
        private TextView nome,ultimaMensagem;

        public MyViewHolder(View itemView){
            super(itemView);
            foto = itemView.findViewById(R.id.imageFoto);
            nome = itemView.findViewById(R.id.textTitulo);
            ultimaMensagem = itemView.findViewById(R.id.textUltimaMensagem);
        }
    }
}
