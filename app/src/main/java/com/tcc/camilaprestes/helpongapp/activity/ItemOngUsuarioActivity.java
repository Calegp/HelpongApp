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
import com.tcc.camilaprestes.helpongapp.adapter.AdapterItemUser;
import com.tcc.camilaprestes.helpongapp.helper.ConfiguracaoFirebase;
import com.tcc.camilaprestes.helpongapp.helper.OrganizacaoFirebase;
import com.tcc.camilaprestes.helpongapp.model.Item;

import java.util.ArrayList;
import java.util.List;

public class ItemOngUsuarioActivity extends AppCompatActivity {

    private RecyclerView recyclerItensUser;
    private AdapterItemUser adapterItemUser;
    private List<Item> itens = new ArrayList<>();
    private DatabaseReference firebaseRef;
    private String idONG;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_ong_usuario);

        firebaseRef = ConfiguracaoFirebase.getFirebase();
        idONG = OrganizacaoFirebase.getIdONG();

        inicializarComponentes();

        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Itens necessarios");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        recyclerItensUser.setLayoutManager(new LinearLayoutManager(this));
        recyclerItensUser.setHasFixedSize(true);
        adapterItemUser = new AdapterItemUser(itens,this);
        recyclerItensUser.setAdapter(adapterItemUser);

        recuperarItens();
    }

    private void recuperarItens(){
        DatabaseReference itensRef = firebaseRef
                .child("itens")
                .child(idONG);

        itensRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                itens.clear();
                for(DataSnapshot ds: dataSnapshot.getChildren()){
                    itens.add(ds.getValue(Item.class));
                }

                adapterItemUser.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void inicializarComponentes(){
        recyclerItensUser = findViewById(R.id.recyclerItensUser);
    }
}
