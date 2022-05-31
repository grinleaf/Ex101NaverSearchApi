package com.grinleaf.ex101naversearchapi.network

import com.grinleaf.ex101naversearchapi.model.NaverSearchApiResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.Query

interface NaverApiRetrofitService {

    //JSON 객체 형태 확인용
    @GET("v1/search/shop.json")    //@요청방식("어떤 경로로 보낼지(baseUrl을 뺀 나머지 경로 URL부분!)")
    fun searchDataByString(
        @Header("X-Naver-Client-Id") clientId:String,
        @Header("X-Naver-Client-Secret") clientSecret:String,
        @Query("query") query:String,    //@Query("함수 호출 시 받을 요청변수명") 데이터 저장할 query 변수
        @Query("display") display:Int    //@Query("함수 호출 시 받을 요청변수명") 데이터 저장할 display 변수
    ): Call<String>

    //** JSON 결과를 객체로 파싱하여 받아주는 기능 규격
    //헤더값이 자주 변경되는 것이 아니라면 @Header 파라미터로 전달받지 않고, @Headers()를 이용하여 정적 헤더값 지정을 한 번에 하는 것이 더 편함!
    @GET("v1/search/shop.json")
    @Headers("X-Naver-Client-Id: CFFPYGaQ3tO4GojaFSnS","X-Naver-Client-Secret: 2JPfK3Liw4")
    fun searchData(
        @Query("query") query:String,
        @Query("display") display:Int
    ): Call<NaverSearchApiResponse>
}
