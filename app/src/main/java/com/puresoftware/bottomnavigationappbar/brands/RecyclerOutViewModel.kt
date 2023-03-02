package com.puresoftware.bottomnavigationappbar.brands

data class RecyclerOutViewModel(
    var company: String,
    var main: String,
    var innerList: MutableList<RecyclerInViewModel>
) {
}