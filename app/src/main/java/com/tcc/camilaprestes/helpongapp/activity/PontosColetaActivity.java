package com.tcc.camilaprestes.helpongapp.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.tcc.camilaprestes.helpongapp.R;
import com.tcc.camilaprestes.helpongapp.adapter.AdapterAnuncio;
import com.tcc.camilaprestes.helpongapp.adapter.AdapterPontoColeta;
import com.tcc.camilaprestes.helpongapp.helper.ConfiguracaoFirebase;
import com.tcc.camilaprestes.helpongapp.helper.OrganizacaoFirebase;
import com.tcc.camilaprestes.helpongapp.listener.RecyclerItemClickListener;
import com.tcc.camilaprestes.helpongapp.model.Anuncio;
import com.tcc.camilaprestes.helpongapp.model.PontoColeta;

import java.util.ArrayList;
import java.util.List;

public class PontosColetaActivity extends AppCompatActivity {

    private RecyclerView recyclerItens;
    private AdapterPontoColeta adapterPontoColeta;
    private List<PontoColeta> pontosColeta = new ArrayList<>();
    private DatabaseReference firebaseRef;
    private String idONGLogado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pontos_coleta);

        firebaseRef = ConfiguracaoFirebase.getFirebase();
        idONGLogado = OrganizacaoFirebase.getIdONG();

        inicializarComponentes();

        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Meus pontos de coleta");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        recyclerItens.setLayoutManager(new LinearLayoutManager(this));
        recyclerItens.setHasFixedSize(true);
        adapterPontoColeta = new AdapterPontoColeta(pontosColeta,this);
        recyclerItens.setAdapter(adapterPontoColeta);

        recuperarPontosColeta();

        recyclerItens.addOnItemTouchListener(
                new RecyclerItemClickListener(
                        this,
                        recyclerItens,
                        new RecyclerItemClickListener.OnItemClickListener() {
                            @Override
                            public void onItemClick(View view, int position) {

                            }

                            @Override
                            public void onLongItemClick(View view, int position) {
                                PontoColeta pontoSelecionado = pontosColeta.get(position);
                                pontoSelecionado.removerPontoColeta();
                                Toast.makeText(PontosColetaActivity.this,
                                        "Ponto de coleta excluido com sucesso!",
                                        Toast.LENGTH_SHORT)
                                        .show();
                            }

                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                            }
                        }
                )
        );
    }

    private void recuperarPontosColeta(){
        DatabaseReference anunciosRef = firebaseRef
                .child("pontosColeta")
                .child(idONGLogado);

        anunciosRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                pontosColeta.clear();
                for(DataSnapshot ds: dataSnapshot.getChildren()){
                    pontosColeta.add(ds.getValue(PontoColeta.class));
                }

                adapterPontoColeta.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void inicializarComponentes(){
        recyclerItens = findViewById(R.id.recyclerItens);
    }
}
