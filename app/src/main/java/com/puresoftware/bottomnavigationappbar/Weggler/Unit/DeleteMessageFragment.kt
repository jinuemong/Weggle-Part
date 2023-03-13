package com.puresoftware.bottomnavigationappbar.Weggler.Unit

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.puresoftware.bottomnavigationappbar.R
import com.puresoftware.bottomnavigationappbar.databinding.FragmentDeleteMessaegBinding

class DeleteMessageFragment : DialogFragment() {
    private var _binding  : FragmentDeleteMessaegBinding? = null
    private val binding get() = _binding!!

    private var onItemClickListener : OnItemClickListener? = null
    private var isOk : Boolean = false
    interface OnItemClickListener{
        fun onItemClick()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        isCancelable = true //취소 가능
    }

    fun setItemClickListener(listener: OnItemClickListener){
        this.onItemClickListener = listener
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View{
        _binding = FragmentDeleteMessaegBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.delUserType.addTextChangedListener(object: TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun afterTextChanged(p0: Editable?) {
                setButtonColor()
            }

        })

        //닫기 버튼
        binding.closeButton.setOnClickListener {
            dismissNow()
        }


        binding.commitButton.setOnClickListener{
            if (onItemClickListener!=null && isOk){
                onItemClickListener?.onItemClick()
            }
        }


    }


    fun setButtonColor(){
        if (binding.delUserType.text.equals("회원탈퇴")){
            binding.commitButton.setBackgroundResource(R.drawable.round_border_selected)
            isOk = true
        }else{
            binding.commitButton.setBackgroundResource(R.drawable.round_border_unselected)
            isOk = false
        }
    }



}
