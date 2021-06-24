package com.tcc.find_me.adapter;

import android.content.Context;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.tcc.find_me.R;
import com.tcc.find_me.model.Usuario;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class Adapter extends RecyclerView.Adapter<Adapter.MyViewHolder>{

    private List<Usuario> listaUsuario;
    private Context context;

    public Adapter(List<Usuario> listaUsuario, Context context) {
        this.listaUsuario = listaUsuario;
        this.context = context;
    }

    @NonNull
    @NotNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {

        View itemLista = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_pesquisa_usuario, parent, false);
        return new MyViewHolder(itemLista);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull Adapter.MyViewHolder holder, int position) {

        Usuario usuario = listaUsuario.get(position);

        holder.nome.setText(usuario.getNome() );
        holder.cargo.setText(usuario.getCargo());
        holder.bairro.setText(usuario.getBairro());
        holder.foto.setImageResource(R.drawable.avatar);



    }

    @Override
    public int getItemCount() {
        return listaUsuario.size();
    }

    public class MyViewHolder extends  RecyclerView.ViewHolder{

        ImageView foto;
        TextView nome;
        TextView cargo;
        TextView bairro;


        public MyViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);

            foto = itemView.findViewById(R.id.imageFoto);
            nome = itemView.findViewById(R.id.textoNome);
            cargo = itemView.findViewById(R.id.textoCargo);
            bairro = itemView.findViewById(R.id.textoBairro);

        }
    }
}
