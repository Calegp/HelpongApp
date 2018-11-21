package com.tcc.camilaprestes.helpongapp.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.tcc.camilaprestes.helpongapp.R;
import com.tcc.camilaprestes.helpongapp.model.PontoColeta;

import java.util.List;

public class AdapterPontoColetaUser extends RecyclerView.Adapter<AdapterPontoColetaUser.MyViewHolder>{

    private List<PontoColeta> pontosColeta;
    private Context context;

    public AdapterPontoColetaUser(List<PontoColeta> pontosColeta, Context context) {
        this.pontosColeta = pontosColeta;
        this.context = context;
    }

    @NonNull
    @Override
    public AdapterPontoColetaUser.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View pontoColetaLista = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_ponto_coleta_user, parent, false);
        return new AdapterPontoColetaUser.MyViewHolder(pontoColetaLista);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterPontoColetaUser.MyViewHolder holder, int i) {
        PontoColeta pontoColeta = pontosColeta.get(i);
        holder.nomePontoUser.setText(pontoColeta.getNomePonto());
        holder.enderecoPontoUser.setText(pontoColeta.getEnderecoPonto());
    }

    @Override
    public int getItemCount() {
        return pontosColeta.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView nomePontoUser;
        TextView enderecoPontoUser;

        public MyViewHolder(View itemView) {
            super(itemView);

            nomePontoUser = itemView.findViewById(R.id.textLocalPontoUser);
            enderecoPontoUser = itemView.findViewById(R.id.textEnderecoPontoUser);
        }
    }
}
