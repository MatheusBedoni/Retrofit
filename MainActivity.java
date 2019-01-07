package com.app.redes.retrofitex1;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.converter.gson.GsonConverterFactory;

// retrofit.GsonConverterFactory;

import retrofit2.Retrofit;


public class MainActivity extends AppCompatActivity {
    private static final String TAG = "log";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(UdacityService.BASE_URL)
               .addConverterFactory(GsonConverterFactory.create())
                .build();

        UdacityService service = retrofit.create((UdacityService.class));
        Call<UdacityCatalog> requestCatalog = service.listCatalog();

        requestCatalog.enqueue(new Callback<UdacityCatalog>() {
            @Override
            public void onResponse(Call<UdacityCatalog> call, Response<UdacityCatalog> response) {
                if(!response.isSuccessful()){
                    Log.i("TAG","Erro: "+response.code());
                }else{
                    UdacityCatalog catalog = response.body();
                    for(Course c : catalog.courses){
                        Log.i(TAG,String.format("%s : %s",c.title,c.subTitle));
                        for(Instructor i : c.instructors){
                            Log.i("TAG",i.name);
                        }
                        Log.i(TAG,"------");
                    }
                }
            }

            @Override
            public void onFailure(Call<UdacityCatalog> call, Throwable t) {
               // Log.e()
            }
        });


    }
}
