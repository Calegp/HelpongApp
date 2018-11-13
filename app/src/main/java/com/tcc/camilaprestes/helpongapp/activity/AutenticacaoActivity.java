package com.tcc.camilaprestes.helpongapp.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Switch;
import android.widget.TextView;

import com.tcc.camilaprestes.helpongapp.R;

public class AutenticacaoActivity extends AppCompatActivity {

    private Button botaoAcesso, botaoAcessoONG;
    private Switch tipoAcesso;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_autenticacao);
        getSupportActionBar().hide();

        botaoAcesso = findViewById(R.id.buttonAcesso);
        botaoAcesso.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), MapsActivity.class));
            }
        });

        botaoAcessoONG = findViewById(R.id.buttonAcessoONG);
        tipoAcesso = findViewById(R.id.tipoAcesso);
        botaoAcessoONG.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(tipoAcesso.isChecked()){
                    startActivity(new Intent(getApplicationContext(), CadastroActivity.class));
                }
                else{
                    startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                }

            }
        });
    }
}
