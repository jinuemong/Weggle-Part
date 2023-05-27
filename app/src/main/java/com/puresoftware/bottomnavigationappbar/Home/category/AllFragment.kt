package com.puresoftware.bottomnavigationappbar.Home.category

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.puresoftware.bottomnavigationappbar.Home.manager.GroupManager
import com.puresoftware.bottomnavigationappbar.MainActivity
import com.puresoftware.bottomnavigationappbar.R
import com.puresoftware.bottomnavigationappbar.Server.MasterApplication
import com.puresoftware.bottomnavigationappbar.databinding.ActivityCategoryMainBinding
import com.puresoftware.bottomnavigationappbar.databinding.FragmentAllBinding
import com.puresoftware.bottomnavigationappbar.databinding.HomeFragmentBinding

class AllFragment : Fragment() {

    private lateinit var binding: FragmentAllBinding
    private lateinit var mainActivity: MainActivity
    private lateinit var groupManager: GroupManager
    private lateinit var wegglerApp : MasterApplication

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mainActivity = context as MainActivity
        wegglerApp = mainActivity.masterApp
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = FragmentAllBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        groupManager = GroupManager(wegglerApp)

//        groupManager.getCategoryProduct()
    }


}