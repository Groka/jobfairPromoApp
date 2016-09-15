package com.jf.jobfairpromo;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * Created by Tarik on 9/15/2016.
 */

public interface ApiInteface {
    @POST("inputs")
    Call<User> createUser(@Body User user);
}
