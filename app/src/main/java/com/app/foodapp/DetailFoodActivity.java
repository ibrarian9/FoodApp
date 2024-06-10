package com.app.foodapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.app.foodapp.api.ApiCore;
import com.app.foodapp.databinding.ActivityDetailFoodBinding;
import com.app.foodapp.models.DataItem;
import com.app.foodapp.models.RequestOrder;
import com.app.foodapp.models.ResponseAddFoods;
import com.app.foodapp.models.ResponseOrder;
import com.bumptech.glide.Glide;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailFoodActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityDetailFoodBinding binding = ActivityDetailFoodBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        ApiCore apiCore = new ApiCore();
        int id = getIntent().getIntExtra("id", 1);

        binding.jumlah.setText("1");

        Call<ResponseAddFoods> foodsCall = apiCore.getApiService().detailFood(id);
        foodsCall.enqueue(new Callback<ResponseAddFoods>() {
            @Override
            public void onResponse(@NonNull Call<ResponseAddFoods> call, @NonNull Response<ResponseAddFoods> response) {
                if (response.isSuccessful() && response.body() != null && response.body().getSuccess()){
                    DataItem item = response.body().getData();
                    binding.nama.setText(item.getNama());
                    binding.harga.setText(item.getHarga());
                    Glide.with(DetailFoodActivity.this).load(item.getImage()).into(binding.image);
                } else {
                    pesanToast("Data tidak termuat");
                }
            }

            @Override
            public void onFailure(@NonNull Call<ResponseAddFoods> call, @NonNull Throwable t) {
                pesanToast("Failed ; " + t.getMessage());
            }
        });

        binding.bayar.setOnClickListener( v -> {
            String nama = binding.nama.getText().toString();
            String harga = binding.harga.getText().toString();
            String jumlah = binding.jumlah.getText().toString();

            if (nama.isEmpty()){
                pesanToast("Makanan tidak ada");
            } else if (harga.isEmpty()) {
                pesanToast("Harga tidak ada");
            } else if (jumlah.isEmpty()) {
                pesanToast("jumlah tidak boleh kosong");
            } else {

                int dataHarga = Integer.parseInt(harga);
                int dataJumlah = Integer.parseInt(jumlah);
                int dataTotal = dataHarga * dataJumlah;
                Log.d("nama", nama);
                Log.d("harga", String.valueOf(dataHarga));
                Log.d("jumlah", String.valueOf(dataJumlah));
                Log.d("total", String.valueOf(dataTotal));

                RequestOrder requestOrder = new RequestOrder(nama, dataHarga, dataJumlah, dataTotal);
                Call<ResponseOrder> call = apiCore.getApiService().postOrder(requestOrder);
                call.enqueue(new Callback<ResponseOrder>() {
                    @Override
                    public void onResponse(@NonNull Call<ResponseOrder> call, @NonNull Response<ResponseOrder> response) {
                        if (response.isSuccessful() && response.body() != null && response.body().getSuccess()){
                            pesanToast("Pembayaran Berhasil");
                            Intent intent = new Intent(DetailFoodActivity.this, NotaActivity.class);
                            intent.putExtra("nama", nama);
                            intent.putExtra("harga", dataHarga);
                            intent.putExtra("jumlah", dataJumlah);
                            intent.putExtra("total", dataTotal);
                            startActivity(intent);
                            finish();
                        } else {
                            Log.d("error bayar",response.message());
                            pesanToast("Pembayaran Gagal : " + response.message());
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<ResponseOrder> call, @NonNull Throwable t) {
                        pesanToast("Pembayaran Gagal : " + t.getMessage());
                        Log.d("Pembayaran Gagal : ", t.getMessage());
                    }
                });

            }

        });
    }

    private void pesanToast(String pesan) {
        Toast.makeText(DetailFoodActivity.this, pesan, Toast.LENGTH_SHORT).show();
    }
}