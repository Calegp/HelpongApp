package com.tcc.camilaprestes.helpongapp.activity.ong_activities;

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
import com.tcc.camilaprestes.helpongapp.adapter.ong.AdapterItem;
import com.tcc.camilaprestes.helpongapp.helper.ConfiguracaoFirebase;
import com.tcc.camilaprestes.helpongapp.helper.OrganizacaoFirebase;
import com.tcc.camilaprestes.helpongapp.listener.RecyclerItemClickListener;
import com.tcc.camilaprestes.helpongapp.model.Item;

import java.util.ArrayList;
import java.util.List;

public class ItensOngActivity extends AppCompatActivity {

    private RecyclerView recyclerItens;
    private AdapterItem adapterItem;
    private List<Item> itens = new ArrayList<>();
    private DatabaseReference firebaseRef;
    private String idONGLogado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_itens_ong);

        firebaseRef = ConfiguracaoFirebase.getFirebase();
        idONGLogado = OrganizacaoFirebase.getIdONG();

        inicializarComponentes();

        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Meus itens necessarios");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        recyclerItens.setLayoutManager(new LinearLayoutManager(this));
        recyclerItens.setHasFixedSize(true);
        adapterItem = new AdapterItem(itens,this);
        recyclerItens.setAdapter(adapterItem);

        recuperarItens();

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
                                Item itemSelecionado = itens.get(position);
                                itemSelecionado.remover();
                                Toast.makeText(ItensOngActivity.this,
                                        "Item excluido com sucesso!",
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

    private void recuperarItens(){
        DatabaseReference itensRef = firebaseRef
                .child("itens")
                .child(idONGLogado);

        itensRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                itens.clear();
                for(DataSnapshot ds: dataSnapshot.getChildren()){
                    itens.add(ds.getValue(Item.class));
                }

                adapterItem.notifyDataSetChanged();
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
