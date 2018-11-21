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

public class AdapterAnuncioUser extends RecyclerView.Adapter<AdapterAnuncioUser.MyViewHolder>{

    private List<Anuncio> anuncios;
    private Context context;

    public AdapterAnuncioUser(List<Anuncio> anuncios, Context context) {
        this.anuncios = anuncios;
        this.context = context;
    }

    @NonNull
    @Override
    public AdapterAnuncioUser.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View anuncioLista = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_anuncio_user, parent, false);
        return new AdapterAnuncioUser.MyViewHolder(anuncioLista);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterAnuncioUser.MyViewHolder holder, int i) {
        Anuncio anuncio = anuncios.get(i);
        holder.tituloUser.setText(anuncio.getTitulo());
        holder.descricaoUser.setText(anuncio.getDescricao());
    }

    @Override
    public int getItemCount() {
        return anuncios.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView tituloUser;
        TextView descricaoUser;

        public MyViewHolder(View anuncioView) {
            super(anuncioView);

            tituloUser = anuncioView.findViewById(R.id.textTituloUser);
            descricaoUser = anuncioView.findViewById(R.id.textDescricaoUser);
        }
    }
}
