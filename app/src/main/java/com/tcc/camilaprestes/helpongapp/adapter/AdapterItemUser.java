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

public class AdapterItemUser extends RecyclerView.Adapter<AdapterItemUser.MyViewHolder>{

    private List<Item> itens;
    private Context context;

    public AdapterItemUser(List<Item> itens, Context context) {
        this.itens = itens;
        this.context = context;
    }

    @NonNull
    @Override
    public AdapterItemUser.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View itemLista = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_item_user, parent, false);
        return new AdapterItemUser.MyViewHolder(itemLista);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterItemUser.MyViewHolder holder, int i) {
        Item item = itens.get(i);
        holder.nomeUser.setText(item.getNome());
        holder.tipoUser.setText(item.getTipo());
    }

    @Override
    public int getItemCount() {
        return itens.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView nomeUser;
        TextView tipoUser;

        public MyViewHolder(View itemView) {
            super(itemView);

            nomeUser = itemView.findViewById(R.id.textNomeItemUser);
            tipoUser = itemView.findViewById(R.id.textTipoUser);
        }
    }
}
