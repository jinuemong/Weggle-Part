package com.puresoftware.bottomnavigationappbar.MyAccount.MainView

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.puresoftware.bottomnavigationappbar.databinding.ActivityReviewBinding

class ReviewActivity : AppCompatActivity() {
    private lateinit var binding: ActivityReviewBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityReviewBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}