package com.puresoftware.bottomnavigationappbar.SideMenu

import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.puresoftware.bottomnavigationappbar.MyAccount.Manager.UserManager
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import com.puresoftware.bottomnavigationappbar.LoginActivityTemporary
import com.puresoftware.bottomnavigationappbar.MainActivity
import com.puresoftware.bottomnavigationappbar.Weggler.Unit.DeleteMessageFragment
import com.puresoftware.bottomnavigationappbar.Weggler.Unit.MessageFragment
import com.puresoftware.bottomnavigationappbar.databinding.FragmentSettingBinding

class SettingFragment : Fragment() {
    private lateinit var mainActivity : MainActivity
    private var _binding : FragmentSettingBinding? = null
    private val binding get() = _binding!!
    private lateinit var  callback : OnBackPressedCallback

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mainActivity = context as MainActivity
        callback =object : OnBackPressedCallback(true){
            override fun handleOnBackPressed() {
                mainActivity.setSubFragment()
            }

        }
        requireActivity().onBackPressedDispatcher.addCallback(this,callback)
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSettingBinding.inflate(inflater,container,false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpListener()
    }


    private fun setUpListener(){
        binding.backButton.setOnClickListener {
            mainActivity.setSubFragment()
        }
        binding.logout.setOnClickListener {
            // 로그아웃
            UserManager(mainActivity.masterApp)
                .userLogout(paramFun = { success,error->
                    if (success!=null){
                        //토큰 데이터 삭제 후 이동
                        val sp  =mainActivity.getSharedPreferences("login_sp",Context.MODE_PRIVATE)
                        val editor = sp.edit()
                        editor.remove("refreshToken")
                        editor.remove("accessToken")
                        editor.clear().apply()

                        val intent = Intent(mainActivity, LoginActivityTemporary::class.java)
                        intent.putExtra("logout",true)
                        startActivity(intent)
                    }else{
                        getLogoutMessage(error)
                    }
                })
        }
        binding.delUser.setOnClickListener {
            getDelUser()
        }
    }

    private fun getDelUser(){
        val messageBox = DeleteMessageFragment()
        messageBox.show(mainActivity.fragmentManager!!,null)
        messageBox.apply {
            setItemClickListener(object : DeleteMessageFragment.OnItemClickListener{
                override fun onItemClick() {
                    val userManager =UserManager(mainActivity.masterApp)
                    userManager.getUser(paramFun = { user1,message1->
                        if (user1!=null && user1.body.name!=null){
                            userManager.userDelete(user1.body.name!!, paramFun = { user2,message2->
                                if (user2!=null){
                                    messageBox.dismissNow()
                                    mainActivity.finishAffinity()
                                }else{
                                    messageBox.dismissNow()
                                    getDelUserMessage(message2)
                                }
                            })
                        }else{
                            messageBox.dismissNow()
                            getDelUserMessage(message1)
                        }
                    })
                }

            })
        }
    }


    private fun getLogoutMessage(message:String?){
        val messageBox = MessageFragment.newInstance("로그아웃 할 수 없습니다. 앱을 종료 하시겠습니까? ${message.toString()}")
        messageBox.show(mainActivity.fragmentManager!!, null)
        messageBox.apply {
            setItemClickListener(object : MessageFragment.OnItemClickListener{
                override fun onItemClick() {
                    messageBox.dismissNow()
                    mainActivity.finishAffinity()
                }
            })
        }
    }

    private fun getDelUserMessage(message: String?){
        val messageBox = MessageFragment.newInstance("삭제 실패 ${message.toString()}")
        messageBox.show(mainActivity.fragmentManager!!, null)
        messageBox.apply {
            setItemClickListener(object : MessageFragment.OnItemClickListener{
                override fun onItemClick() {
                    messageBox.dismissNow()
                }
            })
        }
    }


}