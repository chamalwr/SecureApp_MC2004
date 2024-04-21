package com.chamalwr.secureapp.controller;

import android.util.Log;

import androidx.annotation.NonNull;

import com.chamalwr.secureapp.model.Owner;
import com.chamalwr.secureapp.model.OwnersResponse;
import com.chamalwr.secureapp.service.OwnerService;
import com.chamalwr.secureapp.service.RetrofitService;

import java.util.Objects;
import java.util.concurrent.CompletableFuture;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class OwnerController {

    private final OwnerService ownerService;
    private final String LOG_TAG = "OWNER_SERVICE";
    public OwnerController() {
        Retrofit retrofitService = RetrofitService.getClient();
        this.ownerService = retrofitService.create(OwnerService.class);
    }

    public CompletableFuture<Owner> getOwnerById(String ownerId) {
        CompletableFuture<Owner> futureResponse = new CompletableFuture<>();
        ownerService.getOwnerById(ownerId).enqueue(new Callback<Owner>() {
            @Override
            public void onResponse(@NonNull Call<Owner> call, @NonNull Response<Owner> response) {
                if (response.isSuccessful() && response.body() != null) {
                    futureResponse.complete(response.body());
                } else {
                    futureResponse.completeExceptionally(new RuntimeException("No Owner found on given ID, Response is null"));
                }
            }

            @Override
            public void onFailure(@NonNull Call<Owner> call, @NonNull Throwable throwable) {
                Log.v(LOG_TAG, throwable.toString());
                Log.v(LOG_TAG, Objects.requireNonNull(throwable.getMessage()));
                futureResponse.completeExceptionally(throwable);
            }
        });
        return futureResponse;
    }

    public CompletableFuture<OwnersResponse> getOwners(Integer page, Integer limit) {
        CompletableFuture<OwnersResponse> futureResponse = new CompletableFuture<>();

        if (page <= 0 || limit <= 0) {
            page = 1;
            limit = 60;
        }

        ownerService.getOwners(page, limit).enqueue(new Callback<OwnersResponse>() {
            @Override
            public void onResponse(@NonNull Call<OwnersResponse> call, @NonNull Response<OwnersResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    futureResponse.complete(response.body());
                } else {
                    futureResponse.completeExceptionally(new RuntimeException("No Owners Found, Response is null"));
                }
            }

            @Override
            public void onFailure(@NonNull Call<OwnersResponse> call, @NonNull Throwable throwable) {
                Log.v(LOG_TAG, throwable.toString());
                Log.v(LOG_TAG, Objects.requireNonNull(throwable.getMessage()));
                futureResponse.completeExceptionally(throwable);
            }
        });
        return futureResponse;
    }
}
