package com.puresoftware.bottomnavigationappbar.Home.category

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.puresoftware.bottomnavigationappbar.Home.adapter.SoonGroupbuyAdapter
import com.puresoftware.bottomnavigationappbar.Home.data.BodyList
import com.puresoftware.bottomnavigationappbar.Home.data.GroupBuyData
import com.puresoftware.bottomnavigationappbar.Home.manager.GroupManager
import com.puresoftware.bottomnavigationappbar.MainActivity
import com.puresoftware.bottomnavigationappbar.R
import com.puresoftware.bottomnavigationappbar.Server.MasterApplication
import com.puresoftware.bottomnavigationappbar.Weggler.Model.ProductList
import com.puresoftware.bottomnavigationappbar.databinding.FragmentAllBinding
import com.puresoftware.bottomnavigationappbar.databinding.FragmentFeedBinding
import com.puresoftware.bottomnavigationappbar.databinding.FragmentFoodBinding


class FoodFragment : Fragment() {

    private lateinit var binding: FragmentFoodBinding
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
        binding = FragmentFoodBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        groupManager = GroupManager(wegglerApp)

        groupManager.getCategoryProduct("food"){
            val foodList = ArrayList<BodyList>()
            for(i in 0 until it.size) {
                foodList.add(BodyList(it[i].subjectFiles,
                            it[i].name,
                            it[i].body.company,
                            it[i].body.charge,
                            it[i].body.discount,
                            it[i].body.original,
                            it[i].body.price,
                            it[i].body.duration,
                            it[i].body.benefit,
                            it[i].contentFiles,
                            it[i].productId))
            }
            binding.foodRecycler.adapter = context?.let {
                SoonGroupbuyAdapter(foodList, it)
            }
            binding.foodRecycler.layoutManager = GridLayoutManager(context,2)
        }
    }
}