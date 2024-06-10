package com.app.foodapp;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.PickVisualMediaRequest;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.app.foodapp.api.ApiCore;
import com.app.foodapp.databinding.ActivityEditFoodBinding;
import com.app.foodapp.models.DataItem;
import com.app.foodapp.models.ResponseAddFoods;
import com.app.foodapp.utility.FileUtils;

import java.io.File;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditFoodActivity extends AppCompatActivity {

    private Uri imageUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityEditFoodBinding binding = ActivityEditFoodBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        ApiCore apiCore = new ApiCore();
        int id = getIntent().getIntExtra("id", 1);

        ActivityResultLauncher<PickVisualMediaRequest> pickVisualMediaLauncher =
                registerForActivityResult(new ActivityResultContracts.PickVisualMedia(), uri -> {
                    if (uri != null) {
                        imageUri = uri;
                    }
                });

        binding.selectFoto.setOnClickListener( v -> pickVisualMediaLauncher.launch(new PickVisualMediaRequest.Builder()
                .setMediaType(ActivityResultContracts.PickVisualMedia.ImageOnly.INSTANCE)
                .build()
        ));

        Call<ResponseAddFoods> foodsCall = apiCore.getApiService().detailFood(id);
        foodsCall.enqueue(new Callback<ResponseAddFoods>() {
            @Override
            public void onResponse(@NonNull Call<ResponseAddFoods> call, @NonNull Response<ResponseAddFoods> response) {
                if (response.isSuccessful() && response.body() != null && response.body().getSuccess()){
                    DataItem item = response.body().getData();
                    binding.nama.setText(item.getNama());
                    binding.harga.setText(item.getHarga());
                } else {
                    pesanToast("Data tidak termuat");
                }
            }

            @Override
            public void onFailure(@NonNull Call<ResponseAddFoods> call, @NonNull Throwable t) {
                pesanToast("Failed ; " + t.getMessage());
            }
        });

        binding.simpan.setOnClickListener( v -> {
            String nama = binding.nama.getText().toString();
            String harga = binding.harga.getText().toString();

            if (nama.isEmpty()){
                pesanToast("Nama belum diisi");
            } else if (harga.isEmpty()) {
                pesanToast("harga belum diisi");
            } else {
                RequestBody bodyNama = RequestBody.create(MediaType.parse("text/plain"), nama);
                RequestBody bodyHarga = RequestBody.create(MediaType.parse("text/plain"), harga);
                RequestBody put = RequestBody.create(MediaType.parse("text/plain"), "PUT");

                if (imageUri != null){
                    File file = new File(FileUtils.getPath(this, imageUri));
                    RequestBody requestFile = RequestBody.create(MediaType.parse(getContentResolver().getType(imageUri)), file);
                    MultipartBody.Part body = MultipartBody.Part.createFormData("image", file.getName(), requestFile);
                    Call<ResponseAddFoods> call = apiCore.getApiService().editFood(id, bodyNama, bodyHarga, body, put);
                    call.enqueue(new Callback<ResponseAddFoods>() {
                        @Override
                        public void onResponse(@NonNull Call<ResponseAddFoods> call, @NonNull Response<ResponseAddFoods> response) {
                            if (response.isSuccessful() && response.body() != null && response.body().getSuccess()){
                                pesanToast(response.body().getMessage());
                                Intent intent = new Intent(EditFoodActivity.this, ListFoodActivity.class);
                                startActivity(intent);
                                finish();
                            } else {
                                pesanToast("failed : " + response.message());
                            }
                        }

                        @Override
                        public void onFailure(@NonNull Call<ResponseAddFoods> call, @NonNull Throwable t) {
                            pesanToast("failed : " + t.getMessage());
                        }
                    });
                } else {
                    Call<ResponseAddFoods> call = apiCore.getApiService().editFoodNoImage(id, bodyNama, bodyHarga, put);
                    call.enqueue(new Callback<ResponseAddFoods>() {
                        @Override
                        public void onResponse(@NonNull Call<ResponseAddFoods> call, @NonNull Response<ResponseAddFoods> response) {
                            if (response.isSuccessful() && response.body() != null && response.body().getSuccess()){
                                pesanToast(response.body().getMessage());
                                Intent intent = new Intent(EditFoodActivity.this, ListFoodActivity.class);
                                startActivity(intent);
                                finish();
                            } else {
                                pesanToast("failed : " + response.message());
                            }
                        }

                        @Override
                        public void onFailure(@NonNull Call<ResponseAddFoods> call, @NonNull Throwable t) {
                            pesanToast("failed : " + t.getMessage());
                        }
                    });
                }
            }
        });
    }

    private void pesanToast(String pesan) {
        Toast.makeText(EditFoodActivity.this, pesan, Toast.LENGTH_SHORT).show();
    }
}