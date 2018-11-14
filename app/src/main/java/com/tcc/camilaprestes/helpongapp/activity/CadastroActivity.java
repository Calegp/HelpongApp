package com.tcc.camilaprestes.helpongapp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.tcc.camilaprestes.helpongapp.R;
import com.tcc.camilaprestes.helpongapp.helper.ConfiguracaoFirebase;
import com.tcc.camilaprestes.helpongapp.helper.OrganizacaoFirebase;
import com.tcc.camilaprestes.helpongapp.model.Organizacao;

public class CadastroActivity extends AppCompatActivity {
    private EditText nome, descricao, endereco, email, responsavel, senha;

    private FirebaseAuth autenticacao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);

        nome = findViewById(R.id.nomeONG);
        descricao = findViewById(R.id.descricaoONG);
        endereco = findViewById(R.id.enderecoONG);
        email = findViewById(R.id.emailONG);
        responsavel = findViewById(R.id.responsavelONG);
        senha = findViewById(R.id.senhaONG);

    }

    public void validarCadastroONG(View view){
        String textoNome = nome.getText().toString();
        String textoDescricao = descricao.getText().toString();
        String textoEndereco = endereco.getText().toString();
        String textoEmail = email.getText().toString();
        String textoResponsavel = responsavel.getText().toString();
        String textoSenha = senha.getText().toString();

        if(!textoNome.isEmpty()){
            if(!textoDescricao.isEmpty()){
                if(!textoEndereco.isEmpty()){
                    if(!textoEmail.isEmpty()){
                        if(!textoResponsavel.isEmpty()){
                            if(!textoSenha.isEmpty()){
                                Organizacao ong = new Organizacao();
                                ong.setNome(textoNome);
                                ong.setDescricao(textoDescricao);
                                ong.setEndereco(textoEndereco);
                                ong.setEmail(textoEmail);
                                ong.setResponsavel(textoResponsavel);
                                ong.setSenha(textoSenha);

                                cadastrarONG(ong);

                            }
                            else{
                                Toast.makeText(this, "Preencha a senha", Toast.LENGTH_SHORT).show();
                            }
                        }
                        else{
                            Toast.makeText(this, "Preencha o nome do responsável", Toast.LENGTH_SHORT).show();
                        }
                    }
                    else{
                        Toast.makeText(this, "Preencha o email da ong", Toast.LENGTH_SHORT).show();
                    }
                }
                else{
                    Toast.makeText(this, "Preencha o endereco da ONG", Toast.LENGTH_SHORT).show();
                }
            }
            else{
                Toast.makeText(this, "Preencha a descrição da ONG", Toast.LENGTH_SHORT).show();
            }
        }
        else{
            Toast.makeText(this, "Preencha o nome da ONG", Toast.LENGTH_SHORT).show();
        }



    }

    public void cadastrarONG(final Organizacao ong){

        autenticacao = ConfiguracaoFirebase.getFirebaseAutenticacao();
        autenticacao.createUserWithEmailAndPassword(
          ong.getEmail(),
          ong.getSenha()
        ).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    try {
                        String idONG = task.getResult().getUser().getUid();
                        ong.setId(idONG);
                        ong.salvar();

                        OrganizacaoFirebase.atualizarNomeOrganizacao(ong.getNome());

                        startActivity(new Intent(getApplicationContext(), PerfilOngActivity.class));
                    }
                    catch (Exception e){
                        e.printStackTrace();
                    }
                }
                else{
                    String excecao = "";
                    try{
                        throw  task.getException();
                    }catch (FirebaseAuthWeakPasswordException e){
                        excecao = "Digite uma senha mais forte.";
                    }catch (FirebaseAuthInvalidCredentialsException e){
                        excecao = "Digite um email valido.";
                    }catch (FirebaseAuthUserCollisionException e){
                        excecao = "Esta conta ja foi cadastrada.";
                    }catch (Exception e){
                        excecao = "Erro ao cadastrar usuário: " + e.getMessage();
                        e.printStackTrace();
                    }

                    Toast.makeText(CadastroActivity.this,
                            excecao,
                            Toast.LENGTH_SHORT).show();

                }
            }
        });
    }


}
