package com.puresoftware.bottomnavigationappbar.Weggler.Model

import java.io.Serializable

//프로덕트 리스트를 불러옴
// 공구해요, 프리토크 두개의 탭 가져옴

class ProductList (
    val content : ArrayList<Product>,
    val pageable : Page,
    val totalPages : Int,
    val totalElements : Int,
    val number : Int,
    val sort : SortData,
    val size : Int,
    val numberOfElements : Int,
    val first : Boolean,
    val last : Boolean,
    val empty : Boolean
): Serializable

class Page(
    val sort : SortData,
    val offset : Int,
    val pageNumber : Int,
    val pageSize : Int,
    val paged : Boolean,
    val unpaged : Boolean
)

class SortData(
    val empty : Boolean,
    val sorted : Boolean,
    val unsorted : Boolean
)