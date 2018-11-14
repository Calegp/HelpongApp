package com.tcc.camilaprestes.helpongapp.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.EditText;

import com.tcc.camilaprestes.helpongapp.R;

public class ConfiguracoesONGActivity extends AppCompatActivity {

    private EditText editNome, editEndereco, editResponsavel, editDescricao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_configuracoes_ong);

        inicializarComponentes();

        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Configurações");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void inicializarComponentes(){
           editNome = findViewById(R.id.editNome);
           editDescricao = findViewById(R.id.descricaoONG);
           editEndereco = findViewById(R.id.enderecoONG);
           editResponsavel = findViewById(R.id.responsavelONG);
    }
}
