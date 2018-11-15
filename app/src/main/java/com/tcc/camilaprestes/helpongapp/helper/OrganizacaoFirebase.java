package com.tcc.camilaprestes.helpongapp.helper;

import android.support.annotation.NonNull;
import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

public class OrganizacaoFirebase {

    public static String getIdONG(){

        FirebaseAuth autenticacao = ConfiguracaoFirebase.getFirebaseAutenticacao();
        return autenticacao.getCurrentUser().getUid();

    }

    public static FirebaseUser getOngAtual(){
        FirebaseAuth ong = ConfiguracaoFirebase.getFirebaseAutenticacao();
        return ong.getCurrentUser();
    }
    public static boolean atualizarNomeOrganizacao(String nome){
        try{
            FirebaseUser ong = getOngAtual();
            UserProfileChangeRequest profile = new UserProfileChangeRequest.Builder()
                    .setDisplayName(nome)
                    .build();
            ong.updateProfile(profile).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if(!task.isSuccessful()) {
                        Log.d("Perfil", "Erro ao atualizar nome de perfil.");
                    }
                }
            });

            return true;
        }
        catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }
}
