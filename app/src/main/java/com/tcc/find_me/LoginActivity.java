package com.tcc.find_me;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;
import com.tcc.find_me.config.ConfiguracaoFirebase;
import com.tcc.find_me.helper.UsuarioFirebase;
import com.tcc.find_me.model.Usuario;

public class LoginActivity extends AppCompatActivity {

    private EditText campoEmail, campoSenha;
    private FirebaseAuth autenticacao;
    private Usuario usuario;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        campoEmail = findViewById(R.id.editEmail);
        campoSenha = findViewById(R.id.editSenha);


    }

    public void validarUsuario(View view){

        String textoEmail = campoEmail.getText().toString();
        String textoSenha = campoSenha.getText().toString();

        //Validar se o Email e senha foram digitados

        if( !textoEmail.isEmpty() ){
            if( !textoSenha.isEmpty() ){

                //Apos verificar se os campos foram preenchidos corretamente
                // vamos autenticar o usuario
                usuario = new Usuario();
                usuario.setEmail(textoEmail);
                usuario.setSenha(textoSenha);
                logarUsuario(usuario);


            }else{
                Toast.makeText(getApplicationContext(),"Preencha a Senha !",Toast.LENGTH_LONG).show();
            }
        }else{
            Toast.makeText(getApplicationContext(),"Preencha o Email !",Toast.LENGTH_LONG).show();
        }
    }




    // metodo para logar o usuario
    public void logarUsuario(Usuario usuario){

        // recuperando a instancia de autenticacao do firebase
        autenticacao = ConfiguracaoFirebase.getFirebaseAutenticacao();

        // metodo para pegar os dados que foram utilizados no cadastro do usuario
        autenticacao.signInWithEmailAndPassword(
                usuario.getEmail(),
                usuario.getSenha()

        ).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                // verificando se a autenticacao deu certo
                if (task.isSuccessful()) {
                    Toast.makeText(getApplicationContext(),"Login Concluido", Toast.LENGTH_LONG).show();
                    UsuarioFirebase.redirecionaUsuarioLogado(LoginActivity.this);
                } else {

                    //pegando Exceptions do link https://firebase.google.com/docs/reference/android/com/google/firebase/auth/package-summary?authuser=0
                    // para verificar se o usuario digitou o email ou a senha errada

                    String excessao = "";
                    try {
                        throw task.getException();
                    } catch (FirebaseAuthInvalidUserException e) { //excessao para verificar se o usuario existe no banco
                        excessao = "Usuario Nao Cadastrado";
                    } catch (FirebaseAuthInvalidCredentialsException e) {  //excessao para ver se o email e senha estao corretos
                        excessao = "Email ou Senha Incorretos";
                    } catch (Exception e) {
                        excessao = "Erro ao Logar Na Conta !";
                        e.printStackTrace();
                    }

                    Toast.makeText(getApplicationContext(), excessao, Toast.LENGTH_LONG).show();
                }
            }
        });
    }


    public void botaoCadastrese(View view) {
        Intent intent = new Intent(this, CadastrarLoginActivity.class);
        startActivity(intent);
    }
}
















