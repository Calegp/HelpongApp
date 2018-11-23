package com.tcc.camilaprestes.helpongapp.activity;

import android.content.Intent;
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
import com.tcc.camilaprestes.helpongapp.adapter.AdapterAnuncioUser;
import com.tcc.camilaprestes.helpongapp.helper.ConfiguracaoFirebase;
import com.tcc.camilaprestes.helpongapp.model.Anuncio;

import java.util.ArrayList;
import java.util.List;

public class AnuncioOngUsuarioActivity extends AppCompatActivity {

    private RecyclerView recyclerAnunciosUser;
    private AdapterAnuncioUser adapterAnuncioUser;
    private List<Anuncio> anuncios = new ArrayList<>();
    private DatabaseReference firebaseRef;
    private String idONG;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anuncio_ong_usuario);

        firebaseRef = ConfiguracaoFirebase.getFirebase();

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();

        idONG = bundle.getString("idONG");

        inicializarComponentes();

        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Anúncios");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        recyclerAnunciosUser.setLayoutManager(new LinearLayoutManager(this));
        recyclerAnunciosUser.setHasFixedSize(true);
        adapterAnuncioUser = new AdapterAnuncioUser(anuncios,this);
        recyclerAnunciosUser.setAdapter(adapterAnuncioUser);

        recuperarAnuncios();

    }

    private void recuperarAnuncios(){
        DatabaseReference anunciosRef = firebaseRef
                .child("anuncios")
                .child(idONG);

        anunciosRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                anuncios.clear();
                for(DataSnapshot ds: dataSnapshot.getChildren()){
                    anuncios.add(ds.getValue(Anuncio.class));
                }

                adapterAnuncioUser.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void inicializarComponentes(){
        recyclerAnunciosUser = findViewById(R.id.recyclerAnunciosUser);
    }

}
