package com.puresoftware.bottomnavigationappbar.MyAccount.ViewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.puresoftware.bottomnavigationappbar.Weggler.Model.Product

class AddReviewViewModel : ViewModel(){

    var reviewProduct : Product?=null
    var searchText  = "changeText"
    var searchData  = MutableLiveData<ArrayList<Product>>()
    var selectProductData = MutableLiveData<ArrayList<Product>>()
    init {
        searchData.value = ArrayList()
        selectProductData.value = ArrayList()
    }

    fun setSearchData(data: ArrayList<Product>){
        searchData.value = data
    }

    fun resetSearchData(){
        searchData.value?.clear()
    }

    //데이터 추가 시 유무 확인
    fun findIsData(data: Product): Product? {
        return selectProductData.value!!.find { it.productId == data.productId }
    }
    fun addSelectData(data:Product){
        //같은 값이 없을 경우 추가
        if (findIsData(data)==null) {
            selectProductData.value?.add(data)
        }
    }

    fun delSelectData(data:Product) : Int{
        //값이 있을 경우 삭제
        val pr = findIsData(data)
        if (pr !=null) {
            val index = selectProductData.value!!.indexOf(pr)
            selectProductData.value?.remove(pr)
            return index
        }
        return -1
    }

    fun getSelectNum():Int{
        return selectProductData.value!!.size
    }
    fun resetSelectData(){
        selectProductData.value?.clear()
    }


}