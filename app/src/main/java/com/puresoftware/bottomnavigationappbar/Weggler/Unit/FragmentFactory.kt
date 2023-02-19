package com.puresoftware.bottomnavigationappbar.Weggler.Unit

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentFactory
import com.puresoftware.bottomnavigationappbar.Weggler.SideFragment.AddCommunity.GallerySlideFragment
import com.sothree.slidinguppanel.SlidingUpPanelLayout


// 빈 쌩성자를 작성하는 코드
class galleryFragmentFactory(private val mainFrame:SlidingUpPanelLayout): FragmentFactory() {
    // 빈 생성자
    override fun instantiate(classLoader: ClassLoader, className: String): Fragment {
        return when (className){
            GallerySlideFragment::class.java.name -> GallerySlideFragment(mainFrame)
            else -> super.instantiate(classLoader, className)
        }
    }
}