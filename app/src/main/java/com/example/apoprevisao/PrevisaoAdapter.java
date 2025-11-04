package com.example.apoprevisao;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.apoprevisao.model.ItemPrevisao;
import java.util.List;

public class PrevisaoAdapter extends RecyclerView.Adapter<PrevisaoAdapter.PrevisaoViewHolder> {

    private List<ItemPrevisao> listaPrevisao;

    public PrevisaoAdapter(List<ItemPrevisao> listaPrevisao) {
        this.listaPrevisao = listaPrevisao;
    }

    @NonNull
    @Override
    public PrevisaoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_previsao_card, parent, false);

        return new PrevisaoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PrevisaoViewHolder holder, int position) {
        ItemPrevisao item = listaPrevisao.get(position);

        holder.tvData.setText(item.getData());
        holder.tvDiaSemana.setText(item.getDiaSemana());
        holder.tvDescricao.setText(item.getDescricao());

        String maxMin = "Max: " + item.getMax() + "° / Min: " + item.getMin() + "°";
        holder.tvMaxMin.setText(maxMin);
    }

    @Override
    public int getItemCount() {
        return listaPrevisao.size();
    }

    public static class PrevisaoViewHolder extends RecyclerView.ViewHolder {

        TextView tvData;
        TextView tvDiaSemana;
        TextView tvDescricao;
        TextView tvMaxMin;

        public PrevisaoViewHolder(@NonNull View itemView) {
            super(itemView);
            tvData = itemView.findViewById(R.id.tv_data);
            tvDiaSemana = itemView.findViewById(R.id.tv_dia_semana);
            tvDescricao = itemView.findViewById(R.id.tv_descricao);
            tvMaxMin = itemView.findViewById(R.id.tv_max_min);
        }
    }
}