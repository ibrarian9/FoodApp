package com.app.foodapp.adapter;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.app.foodapp.DetailFoodActivity;
import com.app.foodapp.databinding.BuyFoodsBinding;
import com.app.foodapp.models.DataItem;
import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

public class MainAdapter extends ListAdapter<DataItem, MainAdapter.ViewHolder> {

    public MainAdapter(){
        super(DIFF_CALLBACK);
    }

    @NonNull
    @Override
    public MainAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        BuyFoodsBinding binding = BuyFoodsBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull MainAdapter.ViewHolder holder, int position) {
        DataItem item = getItem(position);
        holder.bind(item);
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        private final BuyFoodsBinding binding;

        public ViewHolder(BuyFoodsBinding binding){
            super(binding.getRoot());
            this.binding = binding;
        }
        public void bind(DataItem item) {
            binding.nama.setText(item.getNama());
            binding.harga.setText(item.getHarga());
            Glide.with(itemView.getContext()).load(item.getImage()).into(binding.imageView);

            binding.beli.setOnClickListener( v -> {
                Intent intent = new Intent(itemView.getContext(), DetailFoodActivity.class);
                intent.putExtra("id", item.getId());
                itemView.getContext().startActivity(intent);
            });
        }
    }

    private static final DiffUtil.ItemCallback<DataItem> DIFF_CALLBACK = new DiffUtil.ItemCallback<DataItem>() {
        @Override
        public boolean areItemsTheSame(@NonNull DataItem oldItem, @NonNull DataItem newItem) {
            return oldItem.equals(newItem);
        }

        @SuppressLint("DiffUtilEquals")
        @Override
        public boolean areContentsTheSame(@NonNull DataItem oldItem, @NonNull DataItem newItem) {
            return oldItem.equals(newItem);
        }
    };
}


