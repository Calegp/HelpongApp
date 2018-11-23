package com.tcc.camilaprestes.helpongapp.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.tcc.camilaprestes.helpongapp.R;
import com.tcc.camilaprestes.helpongapp.adapter.AdapterPontoColetaUser;
import com.tcc.camilaprestes.helpongapp.helper.ConfiguracaoFirebase;
import com.tcc.camilaprestes.helpongapp.helper.OrganizacaoFirebase;
import com.tcc.camilaprestes.helpongapp.model.PontoColeta;

import java.util.ArrayList;
import java.util.List;

public class PontoColetaOngUsuarioActivity extends AppCompatActivity {

    private RecyclerView recyclerPontosColetaUser;
    private AdapterPontoColetaUser adapterPontoColetaUser;
    private List<PontoColeta> pontosColeta = new ArrayList<>();
    private DatabaseReference firebaseRef;
    private String idONG;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ponto_coleta_ong_usuario);

        firebaseRef = ConfiguracaoFirebase.getFirebase();
        idONG = OrganizacaoFirebase.getIdONG();

        inicializarComponentes();

        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Pontos de coleta");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        recyclerPontosColetaUser.setLayoutManager(new LinearLayoutManager(this));
        recyclerPontosColetaUser.setHasFixedSize(true);
        adapterPontoColetaUser = new AdapterPontoColetaUser(pontosColeta,this);
        recyclerPontosColetaUser.setAdapter(adapterPontoColetaUser);

        recuperarPontosColeta();

    }

    private void recuperarPontosColeta(){
        DatabaseReference pontosColetaRef = firebaseRef
                .child("pontoscoleta")
                .child(idONG);

        pontosColetaRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                pontosColeta.clear();
                for(DataSnapshot ds: dataSnapshot.getChildren()){
                    pontosColeta.add(ds.getValue(PontoColeta.class));
                }

                adapterPontoColetaUser.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void inicializarComponentes(){
        recyclerPontosColetaUser = findViewById(R.id.recyclerPontosColetaUser);
    }
}
