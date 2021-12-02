package com.example.retrofitauthentication;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;

public interface UserClient {

    @POST(Api.LOGIN_API)
    Call<AuthUser> login(@Body Login login);

    @POST(Api.REGISTER_API)
    Call<CreatedUserResponse> register(@Body Login login);

    @PUT(Api.UPDATE_USERAPI)
    Call<Update_Response> update(@Body Update updateObj);

    @DELETE(Api.DELETE_USERAPI)
    Call<ResponseBody> delete();


    @GET(Api.LIST_USERS)
    Call<ResponseUsers> getUsers();


    //@Header("Authorization") String authToken
    // we will pass authorization header token this way in get request.

}
