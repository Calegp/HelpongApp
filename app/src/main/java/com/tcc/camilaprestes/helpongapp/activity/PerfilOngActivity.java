package com.tcc.camilaprestes.helpongapp.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.tcc.camilaprestes.helpongapp.R;
import com.tcc.camilaprestes.helpongapp.adapter.AdapterItem;
import com.tcc.camilaprestes.helpongapp.helper.ConfiguracaoFirebase;
import com.tcc.camilaprestes.helpongapp.helper.OrganizacaoFirebase;
import com.tcc.camilaprestes.helpongapp.listener.RecyclerItemClickListener;
import com.tcc.camilaprestes.helpongapp.model.Item;

import java.util.ArrayList;
import java.util.List;

public class PerfilOngActivity extends AppCompatActivity {

    private FirebaseAuth autenticacao;
    private RecyclerView recyclerItens;
    private AdapterItem adapterItem;
    private List<Item> itens = new ArrayList<>();
    private DatabaseReference firebaseRef;
    private String idONGLogado;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil_ong);

        inicializarComponentes();

        autenticacao = ConfiguracaoFirebase.getFirebaseAutenticacao();
        firebaseRef = ConfiguracaoFirebase.getFirebase();
        idONGLogado = OrganizacaoFirebase.getIdONG();

        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Perfil - ONG");
        setSupportActionBar(toolbar);

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
                                Toast.makeText(PerfilOngActivity.this,
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_ong, menu);
        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.menuSair:
                deslogarONG();
                break;
            case R.id.menuConfiguracoes:
                abrirConfiguracoes();
                break;
            case R.id.menuNovoItem:
                abrirNovoItem();
                break;
            case R.id.menuNovoAnuncio:
                abrirNovoAnuncio();
            case R.id.menuNovoPontoColeta:
                abrirNovoPontoColeta();

        }

        return super.onOptionsItemSelected(item);
    }

    private void deslogarONG(){
        try{
            autenticacao.signOut();
            finish();
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    private void abrirConfiguracoes(){
        startActivity(new Intent(PerfilOngActivity.this, ConfiguracoesONGActivity.class));
    }

    private void abrirNovoItem(){
        startActivity(new Intent(PerfilOngActivity.this, NovoItemONGActivity.class));
    }

    private void abrirNovoAnuncio(){
        startActivity(new Intent(PerfilOngActivity.this, NovoAnuncioONGActivity.class));
    }

    private void abrirNovoPontoColeta(){
        startActivity(new Intent(PerfilOngActivity.this, NovoPontoColetaActivity.class));
    }
}
