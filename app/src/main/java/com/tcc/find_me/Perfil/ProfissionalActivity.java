package com.tcc.find_me.Perfil;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.firebase.auth.FirebaseAuth;
import com.tcc.find_me.LoginActivity;
import com.tcc.find_me.R;
import com.tcc.find_me.config.ConfiguracaoFirebase;
import com.tcc.find_me.config.ConfiguracaoProfissional;

public class ProfissionalActivity extends AppCompatActivity {

    private FirebaseAuth autenticacao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profissional);

        autenticacao = ConfiguracaoFirebase.getFirebaseAutenticacao();

        //configurando toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Bem vindo Profissional");
        setSupportActionBar(toolbar);
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