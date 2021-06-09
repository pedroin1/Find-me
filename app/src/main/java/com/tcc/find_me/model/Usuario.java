package com.tcc.find_me.model;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Exclude;
import com.tcc.find_me.config.ConfiguracaoFirebase;

public class Usuario {

    private String idUsuario;
    private String email;
    private String senha;
    private String nome;
    private String celular;
    private String endereco;
    private String bairro;
    private String tipo;

    public Usuario() {
    }
    public void salvar(){

        DatabaseReference firebaseRef = ConfiguracaoFirebase.getFirebaseDatabase();
        DatabaseReference usuarios = firebaseRef.child( "usuarios").child(getIdUsuario());
        usuarios.setValue(this);

        /*firebaseRef.child("Cliente")
                .child(this.getIdUsuario())
                .setValue(this);   */
        /*
        DatabaseReference firebase = ConfiguracaoFirebase.getFirebase();
        firebase.child("Cliente")
                .child(this.IdUsuario)
                .setValue(this);
        */
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Exclude //para nao salvar a senha no BD
    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getIdUsuario() { return idUsuario;  }

    public void setIdUsuario(String idUsuario) {  this.idUsuario = idUsuario; }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCelular() {
        return celular;
    }

    public void setCelular(String celular) {
        this.celular = celular;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public String getBairro() {
        return bairro;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public String getTipo() { return tipo; }

    public void setTipo(String tipo) {  this.tipo = tipo;  }
}
