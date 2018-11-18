package com.tcc.camilaprestes.helpongapp.model;

import com.google.firebase.database.DatabaseReference;
import com.tcc.camilaprestes.helpongapp.helper.ConfiguracaoFirebase;

public class Anuncio {
    private String idONG;
    private String idAnuncio;
    private String titulo;
    private String descricao;

    public Anuncio() {
        DatabaseReference firebaseRef = ConfiguracaoFirebase.getFirebase();
        DatabaseReference itemRef = firebaseRef
                .child("anuncios");
        setIdAnuncio(itemRef.push().getKey() );
    }

    public void salvarAnuncio(){

        DatabaseReference firebaseRef = ConfiguracaoFirebase.getFirebase();
        DatabaseReference anuncioRef = firebaseRef
                .child("anuncios")
                .child( getIdONG() )
                .child(getIdAnuncio());
        anuncioRef.setValue(this);

    }

    public void removerAnuncio(){
        DatabaseReference firebaseRef = ConfiguracaoFirebase.getFirebase();
        DatabaseReference anuncioRef = firebaseRef
                .child("anuncios")
                .child( getIdONG() )
                .child( getIdAnuncio() );
        anuncioRef.removeValue();
    }

    public String getIdONG() {
        return idONG;
    }

    public void setIdONG(String idONG) {
        this.idONG = idONG;
    }

    public String getIdAnuncio() {
        return idAnuncio;
    }

    public void setIdAnuncio(String idAnuncio) {
        this.idAnuncio = idAnuncio;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
}
