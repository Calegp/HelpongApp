package com.tcc.camilaprestes.helpongapp.model;

import com.google.firebase.database.DatabaseReference;
import com.tcc.camilaprestes.helpongapp.helper.ConfiguracaoFirebase;

public class Item  {
    private String idONG;
    private String idItem;
    private String nome;
    private String tipo;

    public Item() {
        DatabaseReference firebaseRef = ConfiguracaoFirebase.getFirebase();
        DatabaseReference produtoRef = firebaseRef
                .child("produtos");
        setIdItem(produtoRef.push().getKey() );
    }

    public void salvar(){

        DatabaseReference firebaseRef = ConfiguracaoFirebase.getFirebase();
        DatabaseReference produtoRef = firebaseRef
                .child("produtos")
                .child( getIdONG() )
                .child( getIdItem() );
        produtoRef.setValue(this);

    }

    public void remover(){
        DatabaseReference firebaseRef = ConfiguracaoFirebase.getFirebase();
        DatabaseReference produtoRef = firebaseRef
                .child("produtos")
                .child( getIdONG() )
                .child( getIdItem() );
        produtoRef.removeValue();
    }

    public String getIdONG() {
        return idONG;
    }

    public void setIdONG(String idONG) {
        this.idONG = idONG;
    }

    public String getIdItem() {
        return idItem;
    }

    public void setIdItem(String idItem) {
        this.idItem = idItem;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
}
