package com.tcc.camilaprestes.helpongapp.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.tcc.camilaprestes.helpongapp.R;
import com.tcc.camilaprestes.helpongapp.helper.OrganizacaoFirebase;
import com.tcc.camilaprestes.helpongapp.model.Item;

public class NovoItemONGActivity extends AppCompatActivity {

    private EditText editNome, editTipo;

    private String idONGLogado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_novo_item_ong);

        inicializarComponentes();

        idONGLogado = OrganizacaoFirebase.getIdONG();

        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Novo item");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    public void validarDadosItem(View view){

        //Valida se os campos foram preenchidos
        String nome = editNome.getText().toString();
        String tipo = editTipo.getText().toString();

        if( !nome.isEmpty()){
            if( !tipo.isEmpty()){
                Item item = new Item();
                item.setIdONG( idONGLogado );
                item.setNome( nome );
                item.setTipo(tipo);
                item.salvar();
                finish();

            }else{
                exibirMensagem("Digite o tipo do item");
            }
        }else{
            exibirMensagem("Digite o nome do item");
        }

    }

    private void exibirMensagem(String texto){
        Toast.makeText(this, texto, Toast.LENGTH_SHORT)
                .show();
    }

    private void inicializarComponentes(){
        editNome = findViewById(R.id.editNomePonto);
        editTipo = findViewById(R.id.editTipo);

    }
}
