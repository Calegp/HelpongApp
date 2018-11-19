package com.tcc.camilaprestes.helpongapp.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.tcc.camilaprestes.helpongapp.R;
import com.tcc.camilaprestes.helpongapp.model.Anuncio;

import java.util.List;

public class AdapterAnuncio extends RecyclerView.Adapter<AdapterAnuncio.MyViewHolder>{

    private List<Anuncio> anuncios;
    private Context context;

    public AdapterAnuncio(List<Anuncio> anuncios, Context context) {
        this.anuncios = anuncios;
        this.context = context;
    }

    @NonNull
    @Override
    public AdapterAnuncio.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View anuncioLista = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_anuncio, parent, false);
        return new AdapterAnuncio.MyViewHolder(anuncioLista);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterAnuncio.MyViewHolder holder, int i) {
        Anuncio anuncio = anuncios.get(i);
        holder.titulo.setText(anuncio.getTitulo());
        holder.descricao.setText(anuncio.getDescricao());
    }

    @Override
    public int getItemCount() {
        return anuncios.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView titulo;
        TextView descricao;

        public MyViewHolder(View anuncioView) {
            super(anuncioView);

            titulo = anuncioView.findViewById(R.id.textTitulo);
            descricao = anuncioView.findViewById(R.id.textDescricao);
        }
    }
}
