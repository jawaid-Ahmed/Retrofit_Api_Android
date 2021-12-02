package com.example.retrofitauthentication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.provider.SyncStateContract;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LoginActivity extends AppCompatActivity {

    private static String ACCESS_TOKEN ;
    EditText emailField,passField;
    Button loginBtn,getDataBtn;
    TextView textView;
    String email="eve.holt@reqres.in"; String pass="cityslicka";
    Retrofit retrofit;
    UserClient userClient;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        emailField=findViewById(R.id.login_email);
        passField=findViewById(R.id.login_pass);
        loginBtn=findViewById(R.id.login_btn);
        getDataBtn=findViewById(R.id.login_getDatabtn);
        textView=findViewById(R.id.login_textview);

        emailField.setText(email);
        passField.setText(pass);


        Retrofit.Builder builder=new Retrofit.Builder()
                .baseUrl(Api.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create());


        retrofit=builder.build();
        userClient=retrofit.create(UserClient.class);

        Toast.makeText(LoginActivity.this, retrofit.baseUrl().toString(), Toast.LENGTH_SHORT).show();

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
            }
        });

        getDataBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getData();
            }
        });




    }

    private void getData() {
        Call<ResponseUsers> bodyCall=userClient.getUsers();
        bodyCall.enqueue(new Callback<ResponseUsers>() {
            @Override
            public void onResponse(Call<ResponseUsers> call, Response<ResponseUsers> response) {
                if (response.isSuccessful()) {

                    ResponseUsers user=response.body();
                    List<User> userList=user.data;
                    Toast.makeText(LoginActivity.this,userList.get(1).email , Toast.LENGTH_SHORT).show();

                } else {
                    Toast.makeText(LoginActivity.this, "Response Failed", Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onFailure(Call<ResponseUsers> call, Throwable t) {
                Toast.makeText(LoginActivity.this, "Something Wrong", Toast.LENGTH_SHORT).show();

            }

        });
    }
    private void updateUser() {
        Update update=new Update("morpheus","zion resident");
        Call<Update_Response> bodyCall=userClient.update(update);

        bodyCall.enqueue(new Callback<Update_Response>() {
            @Override
            public void onResponse(Call<Update_Response> call, Response<Update_Response> response) {

                if (response.isSuccessful()){
                    ACCESS_TOKEN=response.body().updatedAt;
                    Toast.makeText(LoginActivity.this, response.body().updatedAt, Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onFailure(Call<Update_Response> call, Throwable t) {
                Toast.makeText(LoginActivity.this, "on Failure Error", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void deleteUser(){
        Call<ResponseBody> deleteCall=userClient.delete();
        deleteCall.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()){
                    Toast.makeText(LoginActivity.this, response.message()+" M", Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(LoginActivity.this, "on Failure Error", Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void regiser() {
        Login login=new Login(emailField.getText().toString(),passField.getText().toString());

        Call<CreatedUserResponse> userCall=userClient.register(login);

        userCall.enqueue(new Callback<CreatedUserResponse>() {
            @Override
            public void onResponse(Call<CreatedUserResponse> call, Response<CreatedUserResponse> response) {

                if (response.isSuccessful()){
                    ACCESS_TOKEN=response.body().getToken();
                    Toast.makeText(LoginActivity.this, response.body().getId()+" Id", Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onFailure(Call<CreatedUserResponse> call, Throwable t) {
                Toast.makeText(LoginActivity.this, "on Failure Error", Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void login() {
        Login login=new Login(emailField.getText().toString(),passField.getText().toString());

        Call<AuthUser> userCall=userClient.login(login);

        userCall.enqueue(new Callback<AuthUser>() {
            @Override
            public void onResponse(Call<AuthUser> call, Response<AuthUser> response) {

                if (response.isSuccessful()){
                    ACCESS_TOKEN=response.body().getToken();
                    Toast.makeText(LoginActivity.this, response.body().getToken(), Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onFailure(Call<AuthUser> call, Throwable t) {
                Toast.makeText(LoginActivity.this, "on Failure Error", Toast.LENGTH_SHORT).show();
            }
        });
    }
}