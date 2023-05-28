package com.puresoftware.bottomnavigationappbar.Weggler.AboutCommunity.AddCommunity

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.setFragmentResultListener
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.puresoftware.bottomnavigationappbar.MainActivity
import com.puresoftware.bottomnavigationappbar.MyAccount.Manager.ProductManager
import com.puresoftware.bottomnavigationappbar.R
import com.puresoftware.bottomnavigationappbar.Weggler.Manager.CommunityManagerWithReview
import com.puresoftware.bottomnavigationappbar.Weggler.Model.MultiCommunityDataBody
import com.puresoftware.bottomnavigationappbar.databinding.FragmentAddJointPurchaseBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.net.HttpURLConnection
import java.net.URL
import java.text.DecimalFormat

class AddJointPurchaseFragment : Fragment() {
    private var _binding : FragmentAddJointPurchaseBinding? = null
    private val binding get()=_binding!!
    private lateinit var mainActivity:MainActivity
    private val type = 1 //joint
    private var subject=""
    private var text = ""
    private var jointProductId = -1
    private var productImage : Bitmap? = null
    private var fileName : String= ""
    override fun onAttach(context: Context) {
        super.onAttach(context)
        mainActivity = context as MainActivity
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAddJointPurchaseBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        setUpListener()

        setFragmentResultListener("productId"){ _, bundle ->
            jointProductId = bundle.getInt("pId",-1)
            ProductManager(mainActivity.masterApp)
                .getProductFromProductId(jointProductId, paramFun = {item,_->
                    if (item!=null){
                        //view visible 세팅
                        binding.groupBuyProduct.root.visibility = View.VISIBLE
                        setButtonColor()

                        item.subjectFiles[0].let {
                            //코루틴 사용해서 변환 후 저장
                            lifecycleScope.launch(Dispatchers.IO){
                                productImage = convertBitmapFromUrl(it)
                                val startIndex = it.lastIndexOf("/")+1
                                val lastIndex = it.lastIndexOf(".")
                                //프로덕트의 파일 이름 그대로 사용
                                fileName = it.substring(startIndex,lastIndex)
                            }

                            Glide.with(mainActivity)
                                .load(it)
                                .into(binding.productImage)
                        }
                        binding.productCompany.text = item.body.company
                        binding.productName.text = item.name
                        binding.salePer.text = "${item.body.discount}%"
                        val decimal = DecimalFormat("#,###")
                        binding.salePrice.text = "${decimal.format(item.body.price)}원"
                    }
                })
        }
    }

    private fun initView(){
        binding.groupBuyProduct.root.visibility = View.INVISIBLE
        setButtonColor()
        binding.typSubject.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if(p0!=null) {
                    binding.subjectNum.text=p0.length.toString()
                }
            }
            override fun afterTextChanged(p0: Editable?) {
                if(p0!=null) {
                    subject = p0.toString()
                    setButtonColor()
                }
            }
        })

        binding.typContent.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if(p0!=null) {
                    binding.commentNum.text=p0.length.toString()
                }
            }
            override fun afterTextChanged(p0: Editable?) {
                if(p0!=null) {
                    text = p0.toString()
                    setButtonColor()
                }
            }
        })
    }
    private fun setUpListener(){
        binding.delSubject.setOnClickListener {
            binding.typSubject.text = null
            binding.subjectNum.text = "0"
            subject = ""
        }

        // 게시물 작성 가능 : Post

        binding.uploadButton.setOnClickListener {
            if (subject != "" && text.length >= 10 && jointProductId != -1) {
                if (mainActivity.communityViewModel.communityProduct!=null){
                    val multiCommunityData = MultiCommunityDataBody(
                        type,subject,text,"",jointProductId
                    )
                    CommunityManagerWithReview(mainActivity.masterApp)
                        .addCommunityReviewTypeGroup(
                            mainActivity.communityViewModel.communityProduct!!.productId,multiCommunityData
                            ,productImage,fileName,
                            mainActivity, paramFunc = {data,message->
                                if(data!=null) {
                                    //view model에 데이터 추가 후 메인으로 이동
                                    mainActivity.communityViewModel.addCommunityData(data)
                                    mainActivity.communityViewModel.addMyPostingData(data)
                                    mainActivity.communityViewModel.addPopularPostingData(data)
                                    mainActivity.goBackFragment(this@AddJointPurchaseFragment)
                                    mainActivity.setMainViewVisibility(true)
                                }else{
                                    Toast.makeText(mainActivity, message, Toast.LENGTH_SHORT)
                                        .show()
                                    Log.d("message",message!!)
                                }
                            }
                        )

                }
            }
        }

        binding.selectProduct.setOnClickListener {
            mainActivity.changeFragment(SearchGroupBuyFragment())
        }

    }
    @SuppressLint("ResourceAsColor")
    private fun setButtonColor(){
        if (subject!="" && text.length>=10 && jointProductId!=-1){
            binding.uploadButton.setBackgroundResource(R.drawable.round_border_selected)
        }else{
            binding.uploadButton.setBackgroundResource(R.drawable.round_border_unselected)
        }
    }

    //url to bitmap
    private fun convertBitmapFromUrl(url:String):Bitmap?{
        try{
            val connection = URL(url).openConnection() as HttpURLConnection
            connection.doInput = true
            connection.connect()
            val input = connection.inputStream
            return BitmapFactory.decodeStream(input)
        }catch (_: java.lang.Exception){ }
        return null
    }
}