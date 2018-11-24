package com.tcc.camilaprestes.helpongapp.activity.ong_activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.tcc.camilaprestes.helpongapp.R;
import com.tcc.camilaprestes.helpongapp.helper.ConfiguracaoFirebase;
import com.tcc.camilaprestes.helpongapp.helper.OrganizacaoFirebase;
import com.tcc.camilaprestes.helpongapp.model.Organizacao;

public class ConfiguracoesONGActivity extends AppCompatActivity {

    private EditText editNome, editEnderecoONG, editResponsavelONG, editDescricaoONG;
    private DatabaseReference firebaseRef;
    private String idONGLogado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_configuracoes_ong);

        inicializarComponentes();
        firebaseRef = ConfiguracaoFirebase.getFirebase();
        idONGLogado = OrganizacaoFirebase.getIdONG();

        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Configurações");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        recuperarDadosONG();
    }

    private void recuperarDadosONG(){

        DatabaseReference ongRef = firebaseRef
                .child("ongs")
                .child( idONGLogado );

        ongRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                if( dataSnapshot.getValue() != null ){
                    Organizacao ong = dataSnapshot.getValue(Organizacao.class);
                    editNome.setText(ong.getNome());
                    editDescricaoONG.setText(ong.getDescricao());
                    editResponsavelONG.setText(ong.getResponsavel());
                    editEnderecoONG.setText(ong.getEndereco());
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    public void validarDadosONG(View view){

        String nome = editNome.getText().toString();
        String endereco = editEnderecoONG.getText().toString();
        String responsavel = editResponsavelONG.getText().toString();
        String descricao = editDescricaoONG.getText().toString();

        if( !nome.isEmpty()){
            if(!endereco.isEmpty()){
                if(!responsavel.isEmpty()){
                    if(!descricao.isEmpty()){
                        Organizacao ong = new Organizacao();
                        ong.setId( idONGLogado );
                        ong.setNome( nome );
                        ong.setDescricao(descricao);
                        ong.setEndereco(endereco);
                        ong.setResponsavel(responsavel);
                        ong.salvar();
                        finish();

                    }else{
                        exibirMensagem("Digite a descrição da ONG");
                    }
                }else{
                    exibirMensagem("Digite o responsável da ONG");
                }
            }else{
                exibirMensagem("Digite o endereço da ONG");
            }
        }else{
            exibirMensagem("Digite o nome da ONG");
        }

    }

    private void exibirMensagem(String texto){
        Toast.makeText(this, texto, Toast.LENGTH_SHORT)
                .show();
    }

    private void inicializarComponentes(){
           editNome = findViewById(R.id.editNomePonto);
           editDescricaoONG = findViewById(R.id.editDescricao);
           editEnderecoONG = findViewById(R.id.editEndereco);
           editResponsavelONG = findViewById(R.id.editResponsavel);
    }
}
