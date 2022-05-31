package com.grinleaf.ex101naversearchapi.activities

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.grinleaf.ex101naversearchapi.R
import com.grinleaf.ex101naversearchapi.adapters.MyAdapter
import com.grinleaf.ex101naversearchapi.databinding.ActivityMainBinding
import com.grinleaf.ex101naversearchapi.model.NaverSearchApiResponse
import com.grinleaf.ex101naversearchapi.network.NaverApiRetrofitService
import com.grinleaf.ex101naversearchapi.network.RetrofitHelper
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit

//R.java 문서는 무조건 기본 패키지의 클래스. 다른 패키지에 있는 클래스는 import 하지 않으면 사용할 수 없음!
//우린 viewBinding 사용하니까 그렇게 귀찮게 여겨지진 않는댕

class MainActivity : AppCompatActivity() {
    val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        binding.btnSearch.setOnClickListener { searchData() }
        binding.et.setOnEditorActionListener { textView, i, keyEvent ->
            //두번째 파라미터 i == actionId
            when(i){
                EditorInfo.IME_ACTION_SEARCH -> searchData()
            }
            true    //람다 표현식에서는 return 키워드까지 생략해야만 한다.
        }
    }
    
    //★ Naver 검색 API JSON 파싱작업을 수행하는 기능 메소드 ★
    private fun searchData(){

        //소프트 키패드 안 보이도록 설정 : inputMethodManager == imm (변수 선언 시 자료형 지정 해줘야 자동완성 뜬다 ^_^)
        val imm:InputMethodManager= getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(currentFocus?.windowToken,0) //포커스를 가진 애가 토큰을 가지고 있음. 갸한테 달라구 하기~, 즉시

        //Retrofit 객체 생성
        val retrofit:Retrofit= RetrofitHelper.getRetrofitInstance()

        //Retrofit이 해야할 작업의 규격을 정하는 인터페이스 설계
        val retrofitService:NaverApiRetrofitService= retrofit.create(NaverApiRetrofitService::class.java)
        val call= retrofitService.searchData(binding.et.text.toString(),50)
        call.enqueue(object :Callback<NaverSearchApiResponse>{
            override fun onResponse(
                call: Call<NaverSearchApiResponse>,
                response: Response<NaverSearchApiResponse>
            ) {
                val searchApiResponse:NaverSearchApiResponse?= response.body()
                //확인용 다이얼로그
//                AlertDialog.Builder(this@MainActivity).setMessage(searchApiResponse?.total.toString()+"\n"+ searchApiResponse?.items?.size.toString()).create().show()
                searchApiResponse?.items?.let {
                    //얘는 null 이면 실행을 안하는 스코프함수!
                    binding.recycler.adapter= MyAdapter(this@MainActivity, it)

                }
            }

            override fun onFailure(call: Call<NaverSearchApiResponse>, t: Throwable) {
                Toast.makeText(this@MainActivity, ""+t.message, Toast.LENGTH_SHORT).show()
            }

        })

        //네이버 애플리케이션 생성시 발급해 주는 Client ID, Client Secret 코드, 입력받은 텍스트(검색어), 검색결과 출력 개수
//        val call:Call<String> = retrofitService.searchDataByString("CFFPYGaQ3tO4GojaFSnS","2JPfK3Liw4", binding.et.text.toString(), 50)
//        call.enqueue(object : Callback<String>{
//            override fun onResponse(call: Call<String>, response: Response<String>) {
//                AlertDialog.Builder(this@MainActivity).setMessage(response.body()).create().show()
//            }
//            override fun onFailure(call: Call<String>, t: Throwable) {
//                AlertDialog.Builder(this@MainActivity).setMessage(t.message).create().show()
//            }
//        })

    }
}