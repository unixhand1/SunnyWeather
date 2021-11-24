package com.sunnyweather.android.logic.model

import com.google.gson.annotations.SerializedName

data class RealtimeResponse(val status:String,val result: Result){
    data class Result(val realtime:Realtime)
    data    class Realtime (val temperature:Float,val skycon:String,@SerializedName("air_quality")val air_quality:airQuality)
    data class  airQuality(val aqi:AQI)
    data class AQI(val chn:Float)
}