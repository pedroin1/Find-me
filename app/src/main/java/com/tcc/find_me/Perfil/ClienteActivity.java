package com.tcc.find_me.Perfil;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.tcc.find_me.LoginActivity;
import com.tcc.find_me.R;
import com.tcc.find_me.RecyclerItemClickListener;
import com.tcc.find_me.adapter.Adapter;
import com.tcc.find_me.config.ConfiguracaoCliente;
import com.tcc.find_me.config.ConfiguracaoFirebase;
import com.tcc.find_me.model.Usuario;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class ClienteActivity extends AppCompatActivity {

    private FirebaseAuth autenticacao;
    private DatabaseReference usuariosRef;

    private SearchView searchViewCliente;
    private RecyclerView recyclerViewCliente;

    private Adapter adapter;
    private List<Usuario> listaUsuarios;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cliente);

        autenticacao = ConfiguracaoFirebase.getFirebaseAutenticacao();
        listaUsuarios = new ArrayList<>();
        usuariosRef = ConfiguracaoFirebase.getFirebaseDatabase().child("usuarios");

        //configurando Recycler View
        recyclerViewCliente = findViewById(R.id.RecyclerViewCliente);
        recyclerViewCliente.setHasFixedSize(true);
        recyclerViewCliente.setLayoutManager(new LinearLayoutManager(this));

        //evento de click
        recyclerViewCliente.addOnItemTouchListener(
                new RecyclerItemClickListener(
                        getApplicationContext(),
                        recyclerViewCliente,
                        new RecyclerItemClickListener.OnItemClickListener() {
                            @Override
                            public void onItemClick(View view, int position) {
                                Intent i = new Intent(ClienteActivity.this, ChatActivity.class);
                                startActivity(i);

                            }

                            @Override
                            public void onLongItemClick(View view, int position) {

                            }

                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                            }
                        }
                )
        );


        //configurando adpter para o recycler
        adapter = new Adapter(listaUsuarios, this);
        recyclerViewCliente.setAdapter(adapter);

        //configurando Search View
        searchViewCliente = findViewById(R.id.SearchViewCliente);
        searchViewCliente.setQueryHint("Buscar Tipo de Profissional");
        searchViewCliente.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                //verfica quando clica no botao

                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                //verifica sem precisar pressionar o botao, verica letra por letra
                String textoDigitado = s.toUpperCase(); // letras mais
                pesquisarUsuarios(textoDigitado);

                return true;
            }
        });


        //configurando toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Bem vindo cliente");
        setSupportActionBar(toolbar);

    }

    private void pesquisarUsuarios(String texto){

        //limpar lista
        listaUsuarios.clear();

        //pesquisar usuario caso tenha texto na pesquisa
        if(texto.length() > 0){

            Query query = usuariosRef.orderByChild("cargo")
                    .startAt(texto)
                    .endAt(texto + "\uf8ff");

            query.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {

                    listaUsuarios.clear();

                    for(DataSnapshot ds: snapshot.getChildren() ){

                        listaUsuarios.add(ds.getValue(Usuario.class) ); //recuperando usuario do firebase
                    }

                    adapter.notifyDataSetChanged();
                    /*
                    int total = listaUsuarios.size(); //
                    Log.i("totalUsu", "total: " +total);

                     */
                }

                @Override
                public void onCancelled(@NonNull @NotNull DatabaseError error) {

                }
            });
        }
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_cliente, menu);


        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId() ){
            case R.id.menuSairC:
                deslogarUsuario();
                startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                Toast.makeText(getApplicationContext(),"Usuario Deslogado", Toast.LENGTH_LONG).show();
                break;

            case R.id.menuConfigC:
                abrirConfiguracoes();
                break;
        }


        return super.onOptionsItemSelected(item);
    }

    private void deslogarUsuario(){

        try {
            autenticacao.signOut();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void abrirConfiguracoes(){
        startActivity(new Intent(ClienteActivity.this,ConfiguracaoCliente.class ));
    }
}