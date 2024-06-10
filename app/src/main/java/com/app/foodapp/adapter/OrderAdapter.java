package com.app.foodapp.adapter;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.app.foodapp.databinding.ListOrderBinding;
import com.app.foodapp.models.DataOrder;

public class OrderAdapter extends ListAdapter<DataOrder, OrderAdapter.ViewHolder> {

    public OrderAdapter(){
        super(DIFF_CALLBACK);
    }

    @NonNull
    @Override
    public OrderAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ListOrderBinding binding = ListOrderBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new OrderAdapter.ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderAdapter.ViewHolder holder, int position) {
        DataOrder item = getItem(position);
        holder.bind(item);
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        private final ListOrderBinding binding;

        public ViewHolder(ListOrderBinding binding){
            super(binding.getRoot());
            this.binding = binding;
        }
        @SuppressLint("SetTextI18n")
        public void bind(DataOrder item) {
            binding.nama.setText(item.getNama());
            binding.jumlah.setText("Jumlah : " + item.getJumlah());
            binding.total.setText("Total : " + item.getTotalHarga());
            binding.date.setText("Tanggal : " + item.getCreatedAt());
        }
    }

    private static final DiffUtil.ItemCallback<DataOrder> DIFF_CALLBACK = new DiffUtil.ItemCallback<DataOrder>() {
        @Override
        public boolean areItemsTheSame(@NonNull DataOrder oldItem, @NonNull DataOrder newItem) {
            return oldItem.equals(newItem);
        }

        @SuppressLint("DiffUtilEquals")
        @Override
        public boolean areContentsTheSame(@NonNull DataOrder oldItem, @NonNull DataOrder newItem) {
            return oldItem.equals(newItem);
        }
    };
}


