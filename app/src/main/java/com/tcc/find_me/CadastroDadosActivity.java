package com.tcc.find_me;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Switch;

public class CadastroDadosActivity extends AppCompatActivity {

    private Switch switchTipoUsuario;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);
    }










    public String verificaTipoUsuario(){
        return switchTipoUsuario.isChecked() ? "C" : "P" ;
    }
}