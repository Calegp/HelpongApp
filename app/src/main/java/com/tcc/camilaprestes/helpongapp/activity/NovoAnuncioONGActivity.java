package com.tcc.camilaprestes.helpongapp.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.tcc.camilaprestes.helpongapp.R;
import com.tcc.camilaprestes.helpongapp.helper.OrganizacaoFirebase;
import com.tcc.camilaprestes.helpongapp.model.Anuncio;

public class NovoAnuncioONGActivity extends AppCompatActivity {

    private EditText editTitulo, editDescricao;

    private String idONGLogado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_novo_anuncio_ong);
        inicializarComponentes();

        idONGLogado = OrganizacaoFirebase.getIdONG();

        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Novo anuncio");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    public void validarDadosAnuncio(View view){

        //Valida se os campos foram preenchidos
        String titulo = editTitulo.getText().toString();
        String descricao = editDescricao.getText().toString();

        if( !titulo.isEmpty()){
            if( !descricao.isEmpty()){
                Anuncio anuncio = new Anuncio();
                anuncio.setIdONG( idONGLogado );
                anuncio.setTitulo( titulo );
                anuncio.setDescricao(descricao);
                anuncio.salvarAnuncio();
                finish();

            }else{
                exibirMensagem("Digite a descrição do item");
            }
        }else{
            exibirMensagem("Digite o título do item");
        }
    }

    private void exibirMensagem(String texto){
        Toast.makeText(this, texto, Toast.LENGTH_SHORT)
                .show();
    }

    private void inicializarComponentes(){
        editTitulo = findViewById(R.id.editTitulo);
        editDescricao = findViewById(R.id.editDescricao);

    }
}
