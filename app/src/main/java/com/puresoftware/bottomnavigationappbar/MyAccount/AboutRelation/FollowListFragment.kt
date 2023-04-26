package com.puresoftware.bottomnavigationappbar.MyAccount.AboutRelation

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView.OnItemClickListener
import com.puresoftware.bottomnavigationappbar.MainActivity
import com.puresoftware.bottomnavigationappbar.MyAccount.Adapter.ItemFollowAdapter
import com.puresoftware.bottomnavigationappbar.MyAccount.Manager.RelationManager
import com.puresoftware.bottomnavigationappbar.MyAccount.Model.FollowData
import com.puresoftware.bottomnavigationappbar.MyAccount.Model.UserInfo
import com.puresoftware.bottomnavigationappbar.R
import com.puresoftware.bottomnavigationappbar.databinding.FragmentFollowListBinding


class FollowListFragment : Fragment() {

    private var _binding : FragmentFollowListBinding? = null
    private val binding get() = _binding!!
    private var type = 0
    private var userName = ""
    private lateinit var mainActivity: MainActivity
    override fun onAttach(context: Context) {
        super.onAttach(context)
        mainActivity = context as MainActivity
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            type = it.getInt("type")
            userName = it.getString("user").toString()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFollowListBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (type==0){
            RelationManager(mainActivity.masterApp)
                .getTargetUserFollowers(userName, paramFunc = {data,_->
                    if (data!=null) {
                        setRecycler(data)
                    }
                })
        }else{
            RelationManager(mainActivity.masterApp)
                .getTargetUserFollowings(userName, paramFunc = {data,_->
                    if (data!=null) {
                        setRecycler(data)
                    }
                })
        }

    }

    private fun setRecycler(dataList : ArrayList<FollowData>){
        binding.followUserList.adapter = ItemFollowAdapter(mainActivity,dataList)
            .apply {
                setOnItemClickListener(object : ItemFollowAdapter.OnItemClickListener{
                    override fun clickUser(userInfo: UserInfo) {
                        mainActivity.changeFragment(UserProfileFragment.newInstance(userInfo.id,"sub"))
                    }

                })
            }
    }
    companion object {

        @JvmStatic
        fun newInstance(userName:String,type: Int) =
            FollowListFragment().apply {
                arguments = Bundle().apply {
                    putString("user",userName)
                    putInt("type", type)
                }
            }
    }
}