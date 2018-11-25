package com.tcc.camilaprestes.helpongapp.activity.ong_activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.tcc.camilaprestes.helpongapp.R;
import com.tcc.camilaprestes.helpongapp.helper.ConfiguracaoFirebase;

public class PerfilOngActivity extends AppCompatActivity {

    private FirebaseAuth autenticacao;
    private Button botaoItens, botaoAnuncios, botaoPontosColeta;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil_ong);

        autenticacao = ConfiguracaoFirebase.getFirebaseAutenticacao();

        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Perfil - ONG");
        setSupportActionBar(toolbar);

        botaoItens = findViewById(R.id.buttonMeusItens);
        botaoItens.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), ItensOngActivity.class));
            }
        });

        botaoAnuncios = findViewById(R.id.buttonMeusAnuncios);
        botaoAnuncios.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), AnunciosONGActivity.class));
            }

        });

        botaoPontosColeta = findViewById(R.id.buttonMeusPontosColeta);
        botaoPontosColeta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), PontosColetaActivity.class));
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_ong, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
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
                break;
            case R.id.menuNovoPontoColeta:
                abrirNovoPontoColeta();
                break;

        }

        return super.onOptionsItemSelected(item);
    }

    private void deslogarONG() {
        try {
            autenticacao.signOut();
            finish();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void abrirConfiguracoes() {
        startActivity(new Intent(PerfilOngActivity.this, ConfiguracoesONGActivity.class));
    }

    private void abrirNovoItem() {
        startActivity(new Intent(PerfilOngActivity.this, NovoItemONGActivity.class));
    }

    private void abrirNovoAnuncio() {
        startActivity(new Intent(PerfilOngActivity.this, NovoAnuncioONGActivity.class));
    }

    private void abrirNovoPontoColeta() {
        startActivity(new Intent(PerfilOngActivity.this, NovoPontoColetaActivity.class));
    }

}
