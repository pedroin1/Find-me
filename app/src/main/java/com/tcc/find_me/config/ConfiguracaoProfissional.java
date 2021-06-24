package com.tcc.find_me.config;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.tcc.find_me.R;
import com.tcc.find_me.helper.UsuarioFirebase;
import com.tcc.find_me.model.Usuario;

import org.jetbrains.annotations.NotNull;

public class ConfiguracaoProfissional extends AppCompatActivity {

    private EditText editNome,editCargo,editBairro,editTelefone,editEmailProfissional,editTipoProfissional;
    private String idUsuario,email,tipo;

    private FirebaseAuth autenticacao;
    private DatabaseReference firebaseRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_configuracao_profissional);

        //configuracoes iniciais
        idUsuario = UsuarioFirebase.getIdentificadorUsuario();
        firebaseRef = ConfiguracaoFirebase.getFirebaseDatabase();
        autenticacao = ConfiguracaoFirebase.getFirebaseAutenticacao();

        editNome = findViewById(R.id.editNomeProfissional);
        editBairro = findViewById(R.id.editBairroProfissional);
        editTelefone = findViewById(R.id.editTelefoneProfissional);
        editCargo = findViewById(R.id.editCargoProfissional);


        editEmailProfissional = findViewById(R.id.editEmailProfissional);
        editTipoProfissional = findViewById(R.id.editTipoClienteP);
        editEmailProfissional.setFocusable(false);
        editTipoProfissional.setFocusable(false);

        recuperarDadosUsuario();
    }

    public void recuperarDadosUsuario(){

        DatabaseReference usuarioRef = firebaseRef
                .child("usuarios")
                .child(idUsuario);

        usuarioRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {

                if( snapshot.getValue() != null ){

                    Usuario usuario = snapshot.getValue(Usuario.class);
                    editNome.setText(usuario.getNome());
                    editCargo.setText(usuario.getCargo().toUpperCase() );
                    editBairro.setText(usuario.getBairro());
                    editTelefone.setText(usuario.getCelular());
                    editEmailProfissional.setText(usuario.getEmail());
                    editTipoProfissional.setText(usuario.getTipo());
                }
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });
    }

    public void validarDadosUsuario(View view){

        String textoNome = editNome.getText().toString();
        String textoCargo = editCargo.getText().toString();
        String textoBairro = editBairro.getText().toString();
        String textoTelefone = editTelefone.getText().toString();
        String textoEmail = editEmailProfissional.getText().toString();
        String textoTipo = editTipoProfissional.getText().toString();

        if (!textoNome.isEmpty()){
            if(!textoCargo.isEmpty()){
                if(!textoBairro.isEmpty()){
                    if(!textoTelefone.isEmpty()){

                    Usuario usuario = new Usuario();
                    usuario.setIdUsuario(idUsuario);
                    usuario.setNome(textoNome);
                    usuario.setCargo(textoCargo);
                    usuario.setBairro(textoBairro);
                    usuario.setCelular(textoTelefone);
                    usuario.setEmail(textoEmail);
                    usuario.setTipo(textoTipo);
                    usuario.salvar();

                    Toast.makeText(getApplicationContext(),"Dados Atualizados!",Toast.LENGTH_LONG).show();
                    finish();

                }else {
                    Toast.makeText(getApplicationContext(),"Preencha o Telefone !",Toast.LENGTH_LONG).show();
                }
            }else{
                Toast.makeText(getApplicationContext(),"Preencha o Bairro !",Toast.LENGTH_LONG).show();
            }

          }else{
                Toast.makeText(getApplicationContext(),"Preencha o Cargo !",Toast.LENGTH_LONG).show();
         }

        }else{
            Toast.makeText(getApplicationContext(),"Preencha o Nome !",Toast.LENGTH_LONG).show();
        }
    }

}