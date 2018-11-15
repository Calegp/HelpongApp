package com.tcc.camilaprestes.helpongapp.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.tcc.camilaprestes.helpongapp.R;
import com.tcc.camilaprestes.helpongapp.model.Item;

import java.util.List;

public class AdapterItem extends RecyclerView.Adapter<AdapterItem.MyViewHolder>{

    private List<Item> itens;
    private Context context;

    public AdapterItem(List<Item> itens, Context context) {
        this.itens = itens;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View itemLista = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_item, parent, false);
        return new MyViewHolder(itemLista);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int i) {
        Item item = itens.get(i);
        holder.nome.setText(item.getNome());
        holder.tipo.setText(item.getTipo());
    }

    @Override
    public int getItemCount() {
        return itens.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView nome;
        TextView tipo;

        public MyViewHolder(View itemView) {
            super(itemView);

            nome = itemView.findViewById(R.id.textNomeItem);
            tipo = itemView.findViewById(R.id.textTipo);
        }
    }
}
