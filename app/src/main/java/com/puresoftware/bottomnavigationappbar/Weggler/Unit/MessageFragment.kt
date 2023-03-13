package com.puresoftware.bottomnavigationappbar.Weggler.Unit

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.puresoftware.bottomnavigationappbar.Weggler.Adapter.ItemPopularPostingTabAdapter
import com.puresoftware.bottomnavigationappbar.databinding.MessaegBoxBinding

class MessageFragment : DialogFragment() {
    private var message: String? = null
    private lateinit var binding: MessaegBoxBinding

    private var onItemClickListener: OnItemClickListener? = null

    interface OnItemClickListener {
        fun onItemClick()
    }

    fun setItemClickListener(listener: OnItemClickListener) {
        this.onItemClickListener = listener
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        isCancelable = true
        message = arguments?.getString("message")
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = MessaegBoxBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (message != null) {
            binding.text.text = message
        }

        binding.closeButton.setOnClickListener {
            dismissNow()
        }

        binding.commitButton.setOnClickListener {
            if (onItemClickListener != null) {
                onItemClickListener?.onItemClick()
            }
        }

    }

    companion object {
        fun newInstance(message: String) =

            MessageFragment().apply {
                arguments = Bundle().apply {
                    putString("message", message)
                }
            }
    }
}