package com.puresoftware.bottomnavigationappbar


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.activity.viewModels
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.puresoftware.bottomnavigationappbar.CenterWeggle.CenterWeggleFragment
import com.puresoftware.bottomnavigationappbar.Home.HomeFragment
import com.puresoftware.bottomnavigationappbar.MyAccount.MyAccountFragment
import com.puresoftware.bottomnavigationappbar.Server.MasterApplication
import com.puresoftware.bottomnavigationappbar.Weggler.ViewModel.CommunityViewModel
import com.puresoftware.bottomnavigationappbar.MyAccount.ViewModel.MyAccountViewModel
import com.puresoftware.bottomnavigationappbar.SideMenu.MainNavigationFragment
import com.puresoftware.bottomnavigationappbar.Weggler.WegglerFragment
import com.puresoftware.bottomnavigationappbar.brands.BrandsFragment
import com.puresoftware.bottomnavigationappbar.databinding.ActivityMainBinding
import com.sothree.slidinguppanel.SlidingUpPanelLayout

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private var backPressTime:Long = 0
    private lateinit var callback :OnBackPressedCallback
    lateinit var drawerLayout: DrawerLayout
    // fragment
    // https://aries574.tistory.com/382
    var homeFragment: HomeFragment? = null // 홈
    var brandsFragment: BrandsFragment? = null // 브랜드관
    var centerWeggleFragment: CenterWeggleFragment? = null // 메인
    var wegglerFragment: WegglerFragment? = null // 위글러
    var myAccountFragment: MyAccountFragment? = null // 내 정보
    var fragmentManager: FragmentManager? = null // Fragment manager
    var transaction: FragmentTransaction? = null // Fragment transaction


    //MaterApplication : 로그인 + API 연동////
    val masterApp = MasterApplication()
    ///////////////////////////////////////

    //viewModel////////////////////
    val communityViewModel: CommunityViewModel by viewModels()
    val myAccountViewModel : MyAccountViewModel by viewModels()
    /////////////////////////////

    private val TAG: String = MainActivity::class.java.simpleName // 태그

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        drawerLayout = binding.drawLayout
        //masterApp init //////////////
        masterApp.createRetrofit(this@MainActivity)
        //////////////////////


        // toolbar control ///////////////
        var toolbar = binding.toolbar
        setSupportActionBar(toolbar)
        supportActionBar?.setTitle("")
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        //왼쪽 버튼 아이콘 변경
        supportActionBar!!.setHomeAsUpIndicator(R.drawable.ic_round_menu_24)
        supportActionBar!!.hide()

        // null 여부를 감지하여 불필요한 자원 소모 방지하여 최적화
        if (centerWeggleFragment == null) { // null이면 fragment 초기 data 만들기

            // fragment 생성
            homeFragment = HomeFragment()
            brandsFragment = BrandsFragment()
            centerWeggleFragment = CenterWeggleFragment()
            wegglerFragment = WegglerFragment()
            myAccountFragment = MyAccountFragment()

            // System 생성
            fragmentManager = supportFragmentManager // manager 호출
            transaction = fragmentManager!!.beginTransaction() // 화면 전환 호출
            transaction!!.add(R.id.main_frame, centerWeggleFragment!!).commit() // 최초로 첫 화면 갱신
            binding.bottomNavi.menu.getItem(2)
                .setChecked(true)
            Log.i(TAG, "Fragment Data 최초로 생성함")
        }

        // BottomNavigation 선택
        binding.bottomNavi.setOnItemSelectedListener {

            transaction =
                fragmentManager!!.beginTransaction() // 화면 전환 호출
            supportActionBar!!.show()

            when (it.itemId) {
                R.id.frag1 -> {
                    transaction?.replace(R.id.main_frame, homeFragment!!)?.commit()
                    Log.i(TAG, "home 선택됨")
                    true
                }
                R.id.frag2 -> {
                    transaction?.replace(R.id.main_frame, brandsFragment!!)?.commit()
                    Log.i(TAG, "brands 선택됨")
                    true
                }
                R.id.frag3 -> {
                    transaction?.replace(R.id.main_frame, centerWeggleFragment!!)?.commit()
                    supportActionBar!!.hide()
                    Log.i(TAG, "centerWeggle 선택됨")
                    true
                }
                R.id.frag4 -> {
                    transaction?.replace(R.id.main_frame, wegglerFragment!!)?.commit()
                    Log.i(TAG, "weggler 선택됨")
                    true
                }
                R.id.frag5 -> {
                    transaction?.replace(R.id.main_frame, myAccountFragment!!)?.commit()
                    Log.i(TAG, "myAccount 선택됨")
//                    UserApiClient.instance.accessTokenInfo { tokenInfo, error ->
//                        if (error != null) {
//                            val intent = Intent(this, LoginActivity::class.java)
//                            startActivity(intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
//                            finish()
//                        } else if (tokenInfo != null) {
//                            Log.d("하하하1","하하하하")
//                            Toast.makeText(this, "토큰 정보 보기 성공", Toast.LENGTH_SHORT).show()
//                            transaction?.replace(R.id.main_frame, myAccountFragment!!)?.commit()
//                            Log.i(TAG, "myAccount 선택됨")
//                        }
//                    }
//                    Log.d("하하하7","하하하하")
                    true
                }
                else -> {
                    false
                }
            }
        }

        //뒤로가기 버튼 조작 (2초 이내 연속 클릭 시 앱 종료 )
        callback = object : OnBackPressedCallback(true){
            override fun handleOnBackPressed() {
                if(System.currentTimeMillis()>backPressTime+2000){
                    backPressTime = System.currentTimeMillis()
                    Toast.makeText(applicationContext,"'뒤로' 버틀을 한번 더 누르시면 앱이 종료됩니다."
                        ,Toast.LENGTH_SHORT).show()
                }else{
                    finishAffinity()
                }
            }
        }
        this.onBackPressedDispatcher.addCallback(this,callback)

        // ImageView의 Weggle Button 동작
        binding.btnCenterWeggle.setOnClickListener {
            transaction = fragmentManager!!.beginTransaction()
            transaction?.replace(R.id.main_frame, centerWeggleFragment!!)?.commit()
            binding.bottomNavi.menu.getItem(2).isChecked = true
            supportActionBar!!.hide()
            Log.i(TAG, "weggler btn 선택됨")
        }

        // 좌측 뷰 나타내기
        fragmentManager!!.beginTransaction()
            .replace(R.id.main_navi,MainNavigationFragment())
            .commit()


    }

    // 메뉴 선택 옵션 (상단)
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                Log.d(TAG, "드로블메뉴")
                //드로어블 사이드
                binding.drawLayout.openDrawer(GravityCompat.START)
                return true
            }
            R.id.search -> {
                Log.d(TAG, "서치")
                return true
            }
            R.id.basket -> {
                Log.d(TAG, "바스켓")
                return true
            }

            else -> return super.onOptionsItemSelected(item)
        }
    }


    // ActionBar에 Item 넣기
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.tb_main, menu)
        return true
    }

    // fragment change
    fun changeFragment(goFragment: Fragment) {
        fragmentManager!!.beginTransaction().add(R.id.main_frame, goFragment)
            .addToBackStack(null)
            .commit()
    }

    // sub view change
    fun setSubFragmentView(goFragment: Fragment,stack:Int){
        fragmentManager!!.beginTransaction().replace(R.id.slide_layout, goFragment)
            .addToBackStack(null)
            .commit()
        if(stack==1) {
            setSubFragment()
        }
    }


    // side View
    fun setSubFragment(){
        val state = binding.frameLayout.panelState
        // 닫힌 상태일 경우 열기
        if (state == SlidingUpPanelLayout.PanelState.COLLAPSED) {
            binding.frameLayout.isTouchEnabled =false // 슬라이드 막기
            binding.frameLayout.panelState = SlidingUpPanelLayout.PanelState.ANCHORED
        }
        // 열린 상태일 경우 닫기
        else if (state == SlidingUpPanelLayout.PanelState.EXPANDED) {
            binding.frameLayout.isTouchEnabled =true //슬라이드 허용
            binding.frameLayout.panelState = SlidingUpPanelLayout.PanelState.COLLAPSED
        }
    }
    fun goBackFragment(fragment: Fragment) {
            fragmentManager!!.beginTransaction().remove(fragment).commit()
            fragmentManager!!.popBackStack()
    }

    // tab , bottom view 등 visibility
    fun setMainViewVisibility(isSet: Boolean) {
        if (isSet) {
            binding.bottomNavi.visibility = View.VISIBLE
            binding.toolbar.visibility = View.VISIBLE
            binding.btnCenterWeggle.visibility = View.VISIBLE
        } else {
            binding.bottomNavi.visibility = View.GONE
            binding.toolbar.visibility = View.GONE
            binding.btnCenterWeggle.visibility = View.GONE
        }
    }
    /////////////////////////////

}