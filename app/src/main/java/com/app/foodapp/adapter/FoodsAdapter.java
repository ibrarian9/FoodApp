package com.app.foodapp.adapter;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.app.foodapp.EditFoodActivity;
import com.app.foodapp.api.ApiCore;
import com.app.foodapp.databinding.ListFoodsBinding;
import com.app.foodapp.models.DataItem;
import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FoodsAdapter extends ListAdapter<DataItem, FoodsAdapter.ViewHolder> {

    public FoodsAdapter(){
        super(DIFF_CALLBACK);
    }

    @NonNull
    @Override
    public FoodsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ListFoodsBinding binding = ListFoodsBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull FoodsAdapter.ViewHolder holder, int position) {
        DataItem item = getItem(position);
        holder.bind(item);
    }

    public void removeItem(int position) {
        List<DataItem> currentList = new ArrayList<>(getCurrentList());
        currentList.remove(position);
        submitList(currentList);
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private final ListFoodsBinding binding;

        public ViewHolder(ListFoodsBinding binding){
            super(binding.getRoot());
            this.binding = binding;
        }
        public void bind(DataItem item) {

            ApiCore apiCore = new ApiCore();

            binding.nama.setText(item.getNama());
            binding.harga.setText(item.getHarga());
            Glide.with(itemView.getContext()).load(item.getImage()).into(binding.imageView);

            binding.edit.setOnClickListener(v -> {
                Intent intent = new Intent(itemView.getContext(), EditFoodActivity.class);
                intent.putExtra("id", item.getId());
                itemView.getContext().startActivity(intent);
            });

            binding.delete.setOnClickListener(v -> {
                Call<Void> call = apiCore.getApiService().deleteFood(item.getId());
                call.enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(@NonNull Call<Void> call, @NonNull Response<Void> response) {
                        if (response.isSuccessful()){
                            int position = getAdapterPosition();
                            if (position != RecyclerView.NO_POSITION){
                                removeItem(position);
                            }
                            Toast.makeText(itemView.getContext(), "Delete Food berhasil", Toast.LENGTH_SHORT).show();
                        } else {
                            Log.d("delete", response.message());
                            Toast.makeText(itemView.getContext(), "Delete Food Gagal", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<Void> call, @NonNull Throwable t) {
                        Log.d("delete", t.getMessage());
                    }
                });
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
