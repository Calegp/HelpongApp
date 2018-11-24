package com.tcc.camilaprestes.helpongapp.activity.user_activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.tcc.camilaprestes.helpongapp.R;
import com.tcc.camilaprestes.helpongapp.adapter.user.AdapterAnuncioUser;
import com.tcc.camilaprestes.helpongapp.adapter.user.AdapterItemUser;
import com.tcc.camilaprestes.helpongapp.adapter.user.AdapterPontoColetaUser;
import com.tcc.camilaprestes.helpongapp.helper.ConfiguracaoFirebase;
import com.tcc.camilaprestes.helpongapp.model.Anuncio;
import com.tcc.camilaprestes.helpongapp.model.Item;
import com.tcc.camilaprestes.helpongapp.model.Organizacao;
import com.tcc.camilaprestes.helpongapp.model.PontoColeta;

import java.util.ArrayList;
import java.util.List;

public class PerfilOngUsuarioActivity extends AppCompatActivity {

    private RecyclerView recyclerItensUser, recyclerAnunciosUser, recyclerPontosColetaUser;
    private AdapterItemUser adapterItemUser;
    private AdapterAnuncioUser adapterAnuncioUser;
    private AdapterPontoColetaUser adapterPontoColetaUser;
    private List<Item> itens = new ArrayList<>();
    private List<Anuncio> anuncios = new ArrayList<>();
    private List<PontoColeta> pontosColeta = new ArrayList<>();
    private DatabaseReference firebaseRef;
    private String idONG;

    Organizacao ong = new Organizacao();
    TextView editDescOngUser, editEnderOngUser, editEmailOngUser;
    private Button buttonItensOngUser, buttonAnunciosOngUser, buttonPontosOngUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil_ong_usuario);

        inicializarComponentes();

        //itens
        recyclerItensUser.setLayoutManager(new LinearLayoutManager(this));
        recyclerItensUser.setHasFixedSize(true);
        adapterItemUser = new AdapterItemUser(itens, this);
        recyclerItensUser.setAdapter(adapterItemUser);

        //anuncios
        recyclerAnunciosUser.setLayoutManager(new LinearLayoutManager(this));
        recyclerAnunciosUser.setHasFixedSize(true);
        adapterAnuncioUser = new AdapterAnuncioUser(anuncios, this);
        recyclerAnunciosUser.setAdapter(adapterAnuncioUser);

        //pontos de coleta
        recyclerPontosColetaUser.setLayoutManager(new LinearLayoutManager(this));
        recyclerPontosColetaUser.setHasFixedSize(true);
        adapterPontoColetaUser = new AdapterPontoColetaUser(pontosColeta, this);
        recyclerPontosColetaUser.setAdapter(adapterPontoColetaUser);

        firebaseRef = ConfiguracaoFirebase.getFirebase();

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();

        idONG = bundle.getString("idONG");

        recuperarDadosOng();

        recuperarItens();
        recuperarAnuncios();
        recuperarPontosColeta();

    }

    private void recuperarItens() {
        DatabaseReference itensRef = firebaseRef
                .child("itens")
                .child(idONG);

        itensRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                itens.clear();
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    itens.add(ds.getValue(Item.class));
                }

                adapterItemUser.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void recuperarAnuncios() {
        DatabaseReference anunciosRef = firebaseRef
                .child("anuncios")
                .child(idONG);

        anunciosRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                anuncios.clear();
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    anuncios.add(ds.getValue(Anuncio.class));
                }

                adapterAnuncioUser.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void recuperarPontosColeta() {
        DatabaseReference pontosColetaRef = firebaseRef
                .child("pontoscoleta")
                .child(idONG);

        pontosColetaRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                pontosColeta.clear();
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    pontosColeta.add(ds.getValue(PontoColeta.class));
                }

                adapterPontoColetaUser.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void recuperarDadosOng(){
        DatabaseReference ongsRef = firebaseRef
                .child("ongs")
                .child(idONG);

        ongsRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if( dataSnapshot.getValue() != null ) {
                    ong = dataSnapshot.getValue(Organizacao.class);
                    editDescOngUser.setText(ong.getDescricao());
                    editEmailOngUser.setText(ong.getEmail());
                    editEnderOngUser.setText(ong.getEndereco());

                    Toolbar toolbar = findViewById(R.id.toolbar);
                    toolbar.setTitle("ONG " + ong.getNome());
                    setSupportActionBar(toolbar);
                    getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void inicializarComponentes(){
        editDescOngUser = findViewById(R.id.editDescOngUser);
        editEmailOngUser = findViewById(R.id.editEmailOngUser);
        editEnderOngUser = findViewById(R.id.editEnderOngUser);
        recyclerItensUser = findViewById(R.id.recyclerItensUser);
        recyclerPontosColetaUser = findViewById(R.id.recyclerPontosColetaUser);
        recyclerAnunciosUser = findViewById(R.id.recyclerAnunciosUser);
    }
}
