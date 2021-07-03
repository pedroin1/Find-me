package com.tcc.find_me.Perfil;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.tcc.find_me.LoginActivity;
import com.tcc.find_me.R;
import com.tcc.find_me.RecyclerItemClickListener;
import com.tcc.find_me.adapter.AdapterConversas;
import com.tcc.find_me.config.ConfiguracaoFirebase;
import com.tcc.find_me.config.ConfiguracaoProfissional;
import com.tcc.find_me.helper.UsuarioFirebase;
import com.tcc.find_me.model.Conversa;
import com.tcc.find_me.model.Usuario;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class ProfissionalActivity extends AppCompatActivity {

    private DatabaseReference database;
    private DatabaseReference conversasRef;
    private String idUsuarioRemetente;
    private String idUsuarioDestinatario;
    private FirebaseAuth autenticacao;
    private RecyclerView recyclerViewConversas;
    private List<Conversa> listaConversas = new ArrayList<>();
    private AdapterConversas adapter;
    private ChildEventListener childEventListenerConversas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profissional);


        autenticacao = ConfiguracaoFirebase.getFirebaseAutenticacao();
        recyclerViewConversas = findViewById(R.id.recyclerListaConversas);


        //configurando toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Bem vindo Profissional");
        setSupportActionBar(toolbar);

        //configurando adapter e recyler
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerViewConversas.setLayoutManager(layoutManager);
        recyclerViewConversas.setHasFixedSize(true);
        adapter = new AdapterConversas(listaConversas,getApplicationContext());
        recyclerViewConversas.setAdapter(adapter);

        //configura evento de clique
        recyclerViewConversas.addOnItemTouchListener(
                new RecyclerItemClickListener(
                        getApplicationContext(),
                        recyclerViewConversas,
                                new RecyclerItemClickListener.OnItemClickListener() {
                                    @Override
                                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                                    }

                                    @Override
                                    public void onItemClick(View view, int position) {

                                        Conversa conversaSelecionada = listaConversas.get(position);

                                        Intent i = new Intent(getApplicationContext(), ChatActivity.class);
                                        i.putExtra("chatContato", conversaSelecionada.getUsuarioExibicao());
                                        startActivity(i);
                                    }

                                    @Override
                                    public void onLongItemClick(View view, int position) {

                                    }
                                }
                )
        );


        //configura referencia da conversa
        idUsuarioRemetente = UsuarioFirebase.getIdentificadorUsuario();
        String identificadorUsuario = UsuarioFirebase.getIdentificadorUsuario();
        database = ConfiguracaoFirebase.getFirebaseDatabase();
        conversasRef = database.child("conversas")
                .child(identificadorUsuario);

        recuperarConversas();

    }

    /*
    @Override
    protected void onStart() {
        super.onStart();
        recuperarConversas();
    }

    @Override
    protected void onStop() {
        super.onStop();
        conversasRef.removeEventListener(childEventListenerConversas);
    }

     */

    public void recuperarConversas(){

        childEventListenerConversas = conversasRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull @NotNull DataSnapshot snapshot, @Nullable @org.jetbrains.annotations.Nullable String previousChildName) {

                //recuperar Conversas
                Conversa conversa = snapshot.getValue(Conversa.class);
                listaConversas.add(conversa);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(@NonNull @NotNull DataSnapshot snapshot, @Nullable @org.jetbrains.annotations.Nullable String previousChildName) {

            }

            @Override
            public void onChildRemoved(@NonNull @NotNull DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved(@NonNull @NotNull DataSnapshot snapshot, @Nullable @org.jetbrains.annotations.Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_profissional, menu);


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
        startActivity(new Intent(ProfissionalActivity.this, ConfiguracaoProfissional.class ));
    }

    public void abrindoPesuisaDesejada(View view){
        startActivity(new Intent(ProfissionalActivity.this, ConfiguracaoProfissional.class ));
    }
}