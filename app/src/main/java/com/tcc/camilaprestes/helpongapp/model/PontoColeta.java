package com.tcc.camilaprestes.helpongapp.model;

import com.google.firebase.database.DatabaseReference;
import com.tcc.camilaprestes.helpongapp.helper.ConfiguracaoFirebase;

public class PontoColeta {
    private String idONG;
    private String idPontoColeta;
    private String nomePonto;
    private String enderecoPonto;

    public PontoColeta() {
        DatabaseReference firebaseRef = ConfiguracaoFirebase.getFirebase();
        DatabaseReference itemRef = firebaseRef
                .child("pontoscoleta");
        setIdPontoColeta(itemRef.push().getKey() );
    }

    public void salvarPontoColeta(){

        DatabaseReference firebaseRef = ConfiguracaoFirebase.getFirebase();
        DatabaseReference pontosRef = firebaseRef
                .child("pontoscoleta")
                .child( getIdONG() )
                .child(getIdPontoColeta());
        pontosRef.setValue(this);

    }

    public void removerPontoColeta(){
        DatabaseReference firebaseRef = ConfiguracaoFirebase.getFirebase();
        DatabaseReference pontosRef = firebaseRef
                .child("pontoscoleta")
                .child( getIdONG() )
                .child( getIdPontoColeta() );
        pontosRef.removeValue();
    }

    public String getIdONG() {
        return idONG;
    }

    public void setIdONG(String idONG) {
        this.idONG = idONG;
    }

    public String getIdPontoColeta() {
        return idPontoColeta;
    }

    public void setIdPontoColeta(String idPontoColeta) {
        this.idPontoColeta = idPontoColeta;
    }

    public String getNomePonto() {
        return nomePonto;
    }

    public void setNomePonto(String nomePonto) {
        this.nomePonto = nomePonto;
    }

    public String getEnderecoPonto() {
        return enderecoPonto;
    }

    public void setEnderecoPonto(String enderecoPonto) {
        this.enderecoPonto = enderecoPonto;
    }
}
