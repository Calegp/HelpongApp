package com.tcc.camilaprestes.helpongapp.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.tcc.camilaprestes.helpongapp.R;
import com.tcc.camilaprestes.helpongapp.activity.user_activities.AnuncioOngUsuarioActivity;
import com.tcc.camilaprestes.helpongapp.activity.user_activities.ItemOngUsuarioActivity;
import com.tcc.camilaprestes.helpongapp.activity.user_activities.PontoColetaOngUsuarioActivity;
import com.tcc.camilaprestes.helpongapp.helper.ConfiguracaoFirebase;
import com.tcc.camilaprestes.helpongapp.model.Organizacao;

public class PerfilOngUsuarioActivity extends AppCompatActivity {

    private String idONG;
    private DatabaseReference firebaseRef;
    Organizacao ong = new Organizacao();
    TextView editDescOngUser, editEnderOngUser, editEmailOngUser;
    private Button buttonItensOngUser, buttonAnunciosOngUser, buttonPontosOngUser;

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

        buttonItensOngUser = findViewById(R.id.buttonItensOngUser);
        buttonItensOngUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PerfilOngUsuarioActivity.this, ItemOngUsuarioActivity.class);

                Bundle bundle = new Bundle();

                bundle.putString("idONG", idONG);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

        buttonAnunciosOngUser = findViewById(R.id.buttonAnunciosOngUser);
        buttonAnunciosOngUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PerfilOngUsuarioActivity.this, AnuncioOngUsuarioActivity.class);

                Bundle bundle = new Bundle();

                bundle.putString("idONG", idONG);
                intent.putExtras(bundle);
                startActivity(intent);
            }

        });

        buttonPontosOngUser = findViewById(R.id.buttonPontosOngUser);
        buttonPontosOngUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PerfilOngUsuarioActivity.this, PontoColetaOngUsuarioActivity.class);

                Bundle bundle = new Bundle();

                bundle.putString("idONG", idONG);
                intent.putExtras(bundle);
                startActivity(intent);
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
    }
}
