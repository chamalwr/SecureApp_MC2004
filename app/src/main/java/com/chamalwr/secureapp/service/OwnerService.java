package com.chamalwr.secureapp.service;


import com.chamalwr.secureapp.model.Owner;
import com.chamalwr.secureapp.model.OwnersResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface OwnerService {
    @GET("owner/{id}")
    Call<Owner> getOwnerById(@Path("id") String ownerId);

    @GET("owners")
    Call<OwnersResponse> getOwners(@Query("page") Integer page, @Query("limit") Integer limit);
}
