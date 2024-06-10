package com.app.foodapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.app.foodapp.adapter.MainAdapter;
import com.app.foodapp.api.ApiCore;
import com.app.foodapp.databinding.ActivityMainBinding;
import com.app.foodapp.models.ResponseFoods;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityMainBinding binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        ApiCore apiCore = new ApiCore();

        binding.login.setOnClickListener( v -> startActivity(new Intent(MainActivity.this, LoginActivity.class)));

        Call<ResponseFoods> call = apiCore.getApiService().getFoods();
        call.enqueue(new Callback<ResponseFoods>() {
            @Override
            public void onResponse(@NonNull Call<ResponseFoods> call, @NonNull Response<ResponseFoods> response) {
                if (response.isSuccessful() && response.body() != null && response.body().isSuccess()){
                    MainAdapter adapter = new MainAdapter();
                    adapter.submitList(response.body().getData());
                    binding.rv.setLayoutManager(new LinearLayoutManager(MainActivity.this));
                    binding.rv.setAdapter(adapter);
                } else {
                    pesanToast("fetching failed : " + response.message());
                }
            }

            @Override
            public void onFailure(@NonNull Call<ResponseFoods> call, @NonNull Throwable t) {
                pesanToast("fetching failed : " + t.getMessage());
            }
        });
    }

    private void pesanToast(String pesan) {
        Toast.makeText(MainActivity.this, pesan, Toast.LENGTH_SHORT).show();
    }
}