package com.tcc.camilaprestes.helpongapp.activity.ong_activities;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
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
import com.tcc.camilaprestes.helpongapp.adapter.ong.AdapterAnuncio;
import com.tcc.camilaprestes.helpongapp.helper.ConfiguracaoFirebase;
import com.tcc.camilaprestes.helpongapp.helper.OrganizacaoFirebase;
import com.tcc.camilaprestes.helpongapp.listener.RecyclerItemClickListener;
import com.tcc.camilaprestes.helpongapp.model.Anuncio;

import java.util.ArrayList;
import java.util.List;

public class AnunciosONGActivity extends AppCompatActivity {

    private RecyclerView recyclerAnuncios;
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

        recyclerAnuncios.setLayoutManager(new LinearLayoutManager(this));
        recyclerAnuncios.setHasFixedSize(true);
        adapterAnuncio = new AdapterAnuncio(anuncios,this);
        recyclerAnuncios.setAdapter(adapterAnuncio);

        recuperarAnuncios();

        recyclerAnuncios.addOnItemTouchListener(
                new RecyclerItemClickListener(
                        this,
                        recyclerAnuncios,
                        new RecyclerItemClickListener.OnItemClickListener() {
                            @Override
                            public void onItemClick(View view, int position) {

                            }

                            @Override
                            public void onLongItemClick(View view, int position) {
                                final Anuncio anuncioSelecionado = anuncios.get(position);

                                AlertDialog.Builder dialog = new AlertDialog.Builder(AnunciosONGActivity.this);

                                dialog.setTitle("Confirmar exclusão");
                                dialog.setMessage("Deseja excluir esse anúncio?");

                                dialog.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        anuncioSelecionado.removerAnuncio();
                                        Toast.makeText(AnunciosONGActivity.this,
                                                "Anúncio excluido com sucesso!",
                                                Toast.LENGTH_SHORT)
                                                .show();
                                    }
                                });

                                dialog.setNegativeButton("Não", null);

                                dialog.create();
                                dialog.show();
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
        recyclerAnuncios = findViewById(R.id.recyclerAnuncios);
    }
}
