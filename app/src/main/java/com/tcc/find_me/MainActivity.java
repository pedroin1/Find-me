package com.tcc.find_me;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.tcc.find_me.helper.UsuarioFirebase;
import com.tcc.find_me.model.Usuario;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void botaoEntrar(View view){
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onStart() {
        super.onStart();
        //UsuarioFirebase.redirecionaUsuarioLogado(MainActivity.this);
    }

    // AULAS 284 , 285 E 286

}