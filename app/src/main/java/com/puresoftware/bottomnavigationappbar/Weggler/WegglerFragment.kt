package com.puresoftware.bottomnavigationappbar.Weggler

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.puresoftware.bottomnavigationappbar.R

class WegglerFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view: View = inflater.inflate(R.layout.weggler_fragment, container, false)
        return view
    }

}