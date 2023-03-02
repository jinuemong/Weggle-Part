package com.puresoftware.bottomnavigationappbar.MyAccount

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.fragment.app.Fragment
import com.kakao.sdk.common.util.Utility.getKeyHash
import com.puresoftware.bottomnavigationappbar.R
import com.puresoftware.bottomnavigationappbar.databinding.MyAccountFragmentBinding
import java.util.zip.Inflater

class MyAccountFragment : Fragment() {
    lateinit var binding : MyAccountFragmentBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = MyAccountFragmentBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.myAccountWriteButton.setOnClickListener{
           val popupMenu = PopupMenu(context, it)

            activity?.menuInflater?.inflate(R.menu.my_account_popup,popupMenu.menu)
            popupMenu.show()
            popupMenu.setOnMenuItemClickListener {
                when(it.itemId){
                    R.id.review_write -> {
                        val intent = Intent(activity, ReviewActivity::class.java)
                                startActivity(intent)
                        return@setOnMenuItemClickListener true
                    }
                    R.id.challenge -> {
                        val intent = Intent(activity, ChallengeActivity::class.java)
                        startActivity(intent)
                        return@setOnMenuItemClickListener true
                    }
                    else -> {
                        return@setOnMenuItemClickListener false
                    }
                }
            }
        }
    }
}