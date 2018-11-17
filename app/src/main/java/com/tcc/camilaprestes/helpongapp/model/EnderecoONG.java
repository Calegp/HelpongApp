package com.tcc.camilaprestes.helpongapp.model;

import com.google.firebase.database.DatabaseReference;
import com.tcc.camilaprestes.helpongapp.helper.ConfiguracaoFirebase;

public class EnderecoONG {
    private String idONG;
    private String idEndereco;
    private String rua;
    private String numero;
    private String cidade;
    private String bairro;
    private String cep;

    private String latitude;
    private String longitude;

    public EnderecoONG() {
        DatabaseReference firebaseRef = ConfiguracaoFirebase.getFirebase();
        DatabaseReference enderecoRef = firebaseRef
                .child("enderecos");
        setIdEndereco(enderecoRef.push().getKey());
    }

    public void salvarEndereco(){

        DatabaseReference firebaseRef = ConfiguracaoFirebase.getFirebase();
        DatabaseReference itemRef = firebaseRef
                .child("enderecos")
                .child( getIdONG() )
                .child(getIdEndereco());
        itemRef.setValue(this);

    }


    public String getIdONG() {
        return idONG;
    }

    public void setIdONG(String idONG) {
        this.idONG = idONG;
    }

    public String getIdEndereco() {
        return idEndereco;
    }

    public void setIdEndereco(String idEndereco) {
        this.idEndereco = idEndereco;
    }

    public String getRua() {
        return rua;
    }

    public void setRua(String rua) {
        this.rua = rua;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public String getBairro() {
        return bairro;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }
}
