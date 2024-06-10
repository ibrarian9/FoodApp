package com.app.foodapp.api;

import com.app.foodapp.models.RequestLogin;
import com.app.foodapp.models.RequestOrder;
import com.app.foodapp.models.RequestRegister;
import com.app.foodapp.models.ResponseAddFoods;
import com.app.foodapp.models.ResponseData;
import com.app.foodapp.models.ResponseFoods;
import com.app.foodapp.models.ResponseListOrder;
import com.app.foodapp.models.ResponseOrder;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;

public interface ApiService {

    @POST("api/register")
    Call<ResponseData> postRegister(@Body RequestRegister requestRegister);

    @POST("api/login")
    Call<ResponseData> postLogin(@Body RequestLogin requestLogin);

    @GET("api/foods")
    Call<ResponseFoods> getFoods();

    @Multipart
    @POST("api/foods")
    Call<ResponseAddFoods> postFood(
            @Part("nama") RequestBody nama,
            @Part("harga") RequestBody harga,
            @Part MultipartBody.Part image
    );

    @Multipart
    @POST("api/foods/{id}")
    Call<ResponseAddFoods> editFood(
            @Path("id") int id,
            @Part("nama") RequestBody nama,
            @Part("harga") RequestBody harga,
            @Part MultipartBody.Part image,
            @Part("_method") RequestBody method
    );

    @Multipart
    @POST("api/foods/{id}")
    Call<ResponseAddFoods> editFoodNoImage(
            @Path("id") int id,
            @Part("nama") RequestBody nama,
            @Part("harga") RequestBody harga,
            @Part("_method") RequestBody method
    );

    @GET("api/foods/{id}")
    Call<ResponseAddFoods> detailFood(@Path("id") int id);

    @DELETE("api/foods/{id}")
    Call<Void> deleteFood(@Path("id") int id);

    @GET("api/orders")
    Call<ResponseListOrder> getOrder();

    @POST("api/order")
    Call<ResponseOrder> postOrder(@Body RequestOrder requestOrder);

}
