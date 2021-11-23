package com.sunnyweather.android.logic.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ServiceCreator{
    private  const val BAST_URL = "https://api.caiyunapp.com"
    private val retrofit = Retrofit.Builder()
        .baseUrl(BAST_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
    //外部可见的接口，接受class参数，调用这个就等于调用retrofit的create方法，建立相应service 的动态代理：
    //使用 val appService = ServiceCreator.create(Appservice::class.java)
    fun <T> create(serviceClass:Class<T>):T = retrofit.create(serviceClass)

    //优化写法：泛型实例化写法
    //val appService = ServiceCreator.create<Appservice>()
    inline  fun <reified  T> create():T = create(T::class.java)
}