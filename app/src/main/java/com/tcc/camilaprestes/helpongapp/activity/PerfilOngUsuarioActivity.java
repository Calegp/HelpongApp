package com.tcc.camilaprestes.helpongapp.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.tcc.camilaprestes.helpongapp.R;
import com.tcc.camilaprestes.helpongapp.helper.ConfiguracaoFirebase;
import com.tcc.camilaprestes.helpongapp.model.Organizacao;

public class PerfilOngUsuarioActivity extends AppCompatActivity {

    private String idONG;
    private DatabaseReference firebaseRef;
    Organizacao ong = new Organizacao();
    TextView editDescOngUser, editEnderOngUser, editEmailOngUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil_ong_usuario);

        inicializarComponentes();

        firebaseRef = ConfiguracaoFirebase.getFirebase();

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();

        idONG = bundle.getString("idONG");

        recuperarDadosOng();


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
    }
}
