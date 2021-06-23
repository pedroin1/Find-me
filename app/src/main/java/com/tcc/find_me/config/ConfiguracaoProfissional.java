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

    private EditText editNome,editBairro,editTelefone,editEmailCliente,editTipoCliente;
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

        editNome = findViewById(R.id.editNomeP);
        editBairro = findViewById(R.id.editBairroP);
        editTelefone = findViewById(R.id.editTelefoneP);
        editEmailCliente = findViewById(R.id.editEmailClienteP);
        editTipoCliente = findViewById(R.id.editTipoClienteP);
        editEmailCliente.setFocusable(false);
        editTipoCliente.setFocusable(false);

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
                    editBairro.setText(usuario.getBairro());
                    editTelefone.setText(usuario.getCelular());
                    editEmailCliente.setText(usuario.getEmail());
                    editTipoCliente.setText(usuario.getTipo());
                }
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });
    }

    public void validarDadosUsuario(View view){

        String textoNome = editNome.getText().toString();
        String textoBairro = editBairro.getText().toString();
        String textoTelefone = editTelefone.getText().toString();
        String textoEmail = editEmailCliente.getText().toString();
        String textoTipo = editTipoCliente.getText().toString();

        if (!textoNome.isEmpty()){
            if(!textoBairro.isEmpty()){
                if(!textoTelefone.isEmpty()){

                    Usuario usuario = new Usuario();
                    usuario.setIdUsuario(idUsuario);
                    usuario.setNome(textoNome);
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
            Toast.makeText(getApplicationContext(),"Preencha o Nome !",Toast.LENGTH_LONG).show();
        }
    }

}