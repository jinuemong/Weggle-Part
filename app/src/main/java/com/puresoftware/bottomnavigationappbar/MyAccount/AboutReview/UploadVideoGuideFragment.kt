package com.puresoftware.bottomnavigationappbar.MyAccount.AboutReview

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.puresoftware.bottomnavigationappbar.R
import com.puresoftware.bottomnavigationappbar.databinding.FragmentUploadVideoGuideBinding

//video 등록 용 dialog
class UploadVideoGuideFragment : DialogFragment() {
    private var _binding: FragmentUploadVideoGuideBinding? = null
    private val binding get() = _binding!!

    private var onItemClickListener: OnItemClickListener? = null

    interface OnItemClickListener {
        fun itemClick()
    }

    fun setOnClickListener(listener: OnItemClickListener) {
        this.onItemClickListener = listener
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        isCancelable = true //취소 가능
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentUploadVideoGuideBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.cancelButton.setOnClickListener {
            dismissNow()
        }

        binding.commitButton.setOnClickListener {
            if (onItemClickListener != null) {
                onItemClickListener?.itemClick()
            }
        }
    }


}