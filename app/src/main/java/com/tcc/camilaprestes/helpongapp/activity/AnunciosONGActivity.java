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
import com.tcc.camilaprestes.helpongapp.helper.ConfiguracaoFirebase;
import com.tcc.camilaprestes.helpongapp.helper.OrganizacaoFirebase;
import com.tcc.camilaprestes.helpongapp.listener.RecyclerItemClickListener;
import com.tcc.camilaprestes.helpongapp.model.Anuncio;

import java.util.ArrayList;
import java.util.List;

public class AnunciosONGActivity extends AppCompatActivity {

    private RecyclerView recyclerItens;
    private AdapterAnuncio adapterAnuncio;
    private List<Anuncio> anuncios = new ArrayList<>();
    private DatabaseReference firebaseRef;
    private String idONGLogado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anuncios_ong);

        firebaseRef = ConfiguracaoFirebase.getFirebase();
        idONGLogado = OrganizacaoFirebase.getIdONG();

        inicializarComponentes();

        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Meus anúncios");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        recyclerItens.setLayoutManager(new LinearLayoutManager(this));
        recyclerItens.setHasFixedSize(true);
        adapterAnuncio = new AdapterAnuncio(anuncios,this);
        recyclerItens.setAdapter(adapterAnuncio);

        recuperarAnuncios();

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
                                Anuncio anuncioSelecionado = anuncios.get(position);
                                anuncioSelecionado.removerAnuncio();
                                Toast.makeText(AnunciosONGActivity.this,
                                        "Anúncio excluido com sucesso!",
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

    private void recuperarAnuncios(){
        DatabaseReference anunciosRef = firebaseRef
                .child("anuncios")
                .child(idONGLogado);

        anunciosRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                anuncios.clear();
                for(DataSnapshot ds: dataSnapshot.getChildren()){
                    anuncios.add(ds.getValue(Anuncio.class));
                }

                adapterAnuncio.notifyDataSetChanged();
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
