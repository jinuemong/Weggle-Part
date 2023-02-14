package com.puresoftware.bottomnavigationappbar.Weggler.SideFragment.AddCommunity

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity.RESULT_OK
import android.content.ContentResolver
import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.nfc.NdefRecord.createUri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat.checkSelfPermission
import com.bumptech.glide.Glide
import com.puresoftware.bottomnavigationappbar.MainActivity
import com.puresoftware.bottomnavigationappbar.R
import com.puresoftware.bottomnavigationappbar.databinding.FragmentGallerySlideBinding
import java.text.SimpleDateFormat

const val PERMISSION_CAMERA = 1
const val REQUEST_CAMERA = 2
class GallerySlideFragment() : Fragment() {
    private var _binding : FragmentGallerySlideBinding? = null
    private val binding get() = _binding!!
    lateinit var mainActivity: MainActivity
    var imageUri : Uri?= null
    override fun onAttach(context: Context) {
        super.onAttach(context)
        mainActivity = context as MainActivity
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentGallerySlideBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        requirePermissions(arrayOf(Manifest.permission.CAMERA), PERMISSION_CAMERA)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
    fun requirePermissions(permission:Array<String>,requestCode:Int){
        //권한 리스트, 권한 주체 구분을 위한 Code
        //SDK 버전 확인
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M){
            permissionGranted(requestCode)
        }else{
            // 권한이 모두 승인 되었는지 여부 저장
            // all 메서드로 배열 속의 모든 값 체크
            val isAllPermissionsGranted = permission.all {
                checkSelfPermission(mainActivity,it) == PackageManager.PERMISSION_GRANTED
            }
            if (isAllPermissionsGranted){
                permissionGranted(requestCode)
            }else{
                //사용자에 권한 승인 요청
                ActivityCompat.requestPermissions(mainActivity,permission,requestCode)
            }
        }


    }

    //승인 시 처리
    private fun permissionGranted(requestCode: Int){
        when(requestCode){
            PERMISSION_CAMERA-> openCamera()
        }
    }
    //거절 시 처리
    private fun permissionDenied(requestCode: Int){
        when(requestCode){
            PERMISSION_CAMERA-> Toast.makeText(
                mainActivity,
                "카메라 권한을 승인해주세요",
                Toast.LENGTH_LONG
            ).show()
        }
    }

    //카메라 열기
    private fun openCamera(){
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)

        createImageUri(newFileName(), "image/jpg")?.let{uri->
            imageUri = uri
            intent.putExtra(MediaStore.EXTRA_OUTPUT,imageUri)
            //StartActivityForResult가 departed 되어 아래 방법 사용
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
                if (it.resultCode == RESULT_OK) {
                    //응답 처리
                    imageUri?.let { uri ->
                        Log.d("카메라 uri 출력 ", uri.toString())
                        Glide.with(mainActivity)
                            .load(uri)
                            .into(binding.imageView)
                    }

                }else{
                    permissionDenied(it.resultCode)
                }
            }.launch(intent) //실행
        }

    }

    //새 파일 이름 설정
    @SuppressLint("SimpleDateFormat")
    private fun newFileName() : String{
        // 현재 날짜 데이터로 이름 설정
        val sdf = SimpleDateFormat("yyyyMMdd_HHmmss")
        return "${sdf.format(System.currentTimeMillis())}.jpg"
    }

    //이미지 파일 생성 함수
    private fun createImageUri(filename:String, type:String): Uri?{
        val values = ContentValues() //빈 값 생성
        values.put(MediaStore.Images.Media.DISPLAY_NAME,filename)
        values.put(MediaStore.Images.Media.MIME_TYPE,type)

        return mainActivity.contentResolver
            .insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,values)
    }
}