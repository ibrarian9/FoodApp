package com.app.foodapp;

import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.app.foodapp.adapter.OrderAdapter;
import com.app.foodapp.api.ApiCore;
import com.app.foodapp.databinding.ActivityOrderBinding;
import com.app.foodapp.models.ResponseListOrder;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OrderActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityOrderBinding binding = ActivityOrderBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        ApiCore apiCore = new ApiCore();

        Call<ResponseListOrder> call = apiCore.getApiService().getOrder();
        call.enqueue(new Callback<ResponseListOrder>() {
            @Override
            public void onResponse(@NonNull Call<ResponseListOrder> call, @NonNull Response<ResponseListOrder> response) {
                if (response.isSuccessful() && response.body() != null && response.body().getSuccess()){
                    OrderAdapter adapter = new OrderAdapter();
                    adapter.submitList(response.body().getData());
                    binding.rv.setLayoutManager(new LinearLayoutManager(OrderActivity.this));
                    binding.rv.setAdapter(adapter);
                } else {
                    pesanToast("fetching failed : " + response.message());
                }
            }

            @Override
            public void onFailure(@NonNull Call<ResponseListOrder> call, @NonNull Throwable t) {
                pesanToast("fetching failed : " + t.getMessage());
            }
        });
    }

    private void pesanToast(String pesan) {
        Toast.makeText(OrderActivity.this, pesan, Toast.LENGTH_SHORT).show();
    }
}