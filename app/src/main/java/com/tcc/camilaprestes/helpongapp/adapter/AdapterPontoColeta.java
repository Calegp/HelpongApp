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

public class AdapterPontoColeta extends RecyclerView.Adapter<AdapterPontoColeta.MyViewHolder>{

    private List<PontoColeta> pontosColeta;
    private Context context;

    public AdapterPontoColeta(List<PontoColeta> pontosColeta, Context context) {
        this.pontosColeta = pontosColeta;
        this.context = context;
    }

    @NonNull
    @Override
    public AdapterPontoColeta.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View pontoColetaLista = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_ponto_coleta, parent, false);
        return new AdapterPontoColeta.MyViewHolder(pontoColetaLista);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterPontoColeta.MyViewHolder holder, int i) {
        PontoColeta pontoColeta = pontosColeta.get(i);
        holder.nomePonto.setText(pontoColeta.getNomePonto());
        holder.enderecoPonto.setText(pontoColeta.getEnderecoPonto());
    }

    @Override
    public int getItemCount() {
        return pontosColeta.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView nomePonto;
        TextView enderecoPonto;

        public MyViewHolder(View itemView) {
            super(itemView);

            nomePonto = itemView.findViewById(R.id.textLocalPonto);
            enderecoPonto = itemView.findViewById(R.id.textEnderecoPonto);
        }
    }
}
