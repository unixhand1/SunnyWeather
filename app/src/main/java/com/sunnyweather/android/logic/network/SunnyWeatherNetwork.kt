package com.sunnyweather.android.logic.network

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.await
import retrofit2.http.Query
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

object SunnyWeatherNetwork{
    //创建了PlaceService 的动态代理
    private  val  placeService = ServiceCreator.create(PlaceService::class.java)
    //创建weatherService 的动态代理
    private  val weatherService =ServiceCreator.create(WeatherService::class.java)


    suspend fun getDailyWeather(lng: String, lat: String) = weatherService.getDailyWeather(lng, lat).await()
    suspend fun getRealtimeWeather(lng: String, lat: String) = weatherService.getRealtimeWeather(lng, lat).await()

    //suspend 协程关键字
//    作用是:[提醒]
//    函数的创建者对函数的调用者的提醒
//    “我”是一个耗时的函数，我被我的创建者用挂起的方式放到了后台运行，所以要在协程里调用“我”。
//    它让我们的主线程不卡。通过挂起函数这种形式把耗时task切线程的这个工作交给了函数的创建者而不是调用者。
//    调用者只会收到一个提醒：你只需要把我放在一个协程里调用。
//    挂起操作靠的是挂起函数里的实际代码，而不是关键字
    suspend fun  searchPlaces(query: String) = placeService.searchPlaces(query).await()
    private suspend fun <T> Call<T>.await(): T {
        return suspendCoroutine { continuation ->
            enqueue(object : Callback<T> {
                override fun onResponse(call: Call<T>, response: Response<T>) {
                    val body = response.body()
                    if (body != null) continuation.resume(body)
                    else continuation.resumeWithException(RuntimeException("response body is null"))
                }

                override fun onFailure(call: Call<T>, t: Throwable) {
                    continuation.resumeWithException(t)
                }
            })
        }
    }



}