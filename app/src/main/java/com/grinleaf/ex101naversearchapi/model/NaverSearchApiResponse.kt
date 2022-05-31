package com.grinleaf.ex101naversearchapi.model

data class NaverSearchApiResponse(
    val total:Int,
    val display:Int,
    var items:MutableList<SearchItem>
) //데이터 클래스는 생성자와 멤버변수를 쓰지 않으면 에러남!
//데이터를 받을 변수명이 JSON 객체의 해당 식별자와 동일해야함! ★

data class SearchItem(
    val title:String,
    val link:String,
    var image:String,
    var lprice:String,
    var brand:String
    )   //Json 객체 중 필요한 값만 받아 사용할 것