package com.tcc.camilaprestes.helpongapp.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.tcc.camilaprestes.helpongapp.R;
import com.tcc.camilaprestes.helpongapp.helper.OrganizacaoFirebase;
import com.tcc.camilaprestes.helpongapp.model.PontoColeta;

public class NovoPontoColetaActivity extends AppCompatActivity{

        private EditText editNomePonto, editEnderecoPonto;

        private String idONGLogado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_novo_ponto_coleta);
        inicializarComponentes();

        idONGLogado = OrganizacaoFirebase.getIdONG();

        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Novo ponto de coleta");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    public void validarDadosPontoColeta(View view){

        //Valida se os campos foram preenchidos
        String nomePonto = editNomePonto.getText().toString();
        String enderecoPonto = editEnderecoPonto.getText().toString();

        if( !nomePonto.isEmpty()){
            if( !enderecoPonto.isEmpty()){
                PontoColeta pontoColeta = new PontoColeta();
                pontoColeta.setIdONG( idONGLogado );
                pontoColeta.setNomePonto( nomePonto );
                pontoColeta.setEnderecoPonto(enderecoPonto);
                pontoColeta.salvarPontoColeta();
                finish();

            }else{
                exibirMensagem("Digite o endereço do ponto de coleta");
            }
        }else{
            exibirMensagem("Digite o nome do ponto de coleta");
        }

    }

    private void exibirMensagem(String texto){
        Toast.makeText(this, texto, Toast.LENGTH_SHORT)
                .show();
    }

    private void inicializarComponentes(){
        editNomePonto = findViewById(R.id.editNomePonto);
        editEnderecoPonto = findViewById(R.id.editEnderecoPonto);

    }
}
