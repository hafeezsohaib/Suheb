package com.jio.wheatherapp.model.repository





import com.jio.wheatherapp.common.Constants.Companion.BASE_URL
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.Serializable
import java.util.concurrent.TimeUnit


class RetrofitSingelton//  .cache(MyApplication.cache)



   private constructor() : Serializable {

    init {

        val client = OkHttpClient.Builder()
             .addInterceptor(QueryParameterAddInterceptor())
            //  .cache(MyApplication.cache)
            .readTimeout(60, TimeUnit.SECONDS).writeTimeout(60, TimeUnit.SECONDS)
            .build()
        mRetrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
    }

   companion object{

       private var retrofitSingelton: RetrofitSingelton? = null
       private var mRetrofit: Retrofit? = null

       fun getInstance(): ApiInterface? {

           if (retrofitSingelton == null) {
               retrofitSingelton = RetrofitSingelton()
               synchronized(RetrofitSingelton::class.java)
               {
                   if (retrofitSingelton== null)
                       retrofitSingelton = RetrofitSingelton()
               }

           }
           return mRetrofit?.create(ApiInterface::class.java)
       }

   }

}