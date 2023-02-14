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
import com.gun0912.tedpermission.PermissionListener
import com.gun0912.tedpermission.TedPermission
import com.puresoftware.bottomnavigationappbar.MainActivity
import com.puresoftware.bottomnavigationappbar.R
import com.puresoftware.bottomnavigationappbar.Weggler.Adapter.SelectPicAdapter
import com.puresoftware.bottomnavigationappbar.databinding.FragmentGallerySlideBinding
import java.text.SimpleDateFormat

class GallerySlideFragment() : Fragment() {
    private var _binding : FragmentGallerySlideBinding? = null
    private val binding get() = _binding!!
    lateinit var mainActivity: MainActivity
    private lateinit var readGalleryListener : PermissionListener
    var currentUri :String= ""
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

        //권한 설정 리스너 선언
        readGalleryListener = object :PermissionListener{

            //권한 성공
            override fun onPermissionGranted() {
                val uriList = getAllShownImagesPath()
                val adapter = SelectPicAdapter(mainActivity,uriList)

                //어댑터 연결 후 아이템 클릭 리스너 적용
                binding.imageRecycler.adapter = adapter.apply {
                    setOnItemClickListener(object : SelectPicAdapter.OnItemClickListener{
                        override fun onItemClick(imageUri: String) {
                            super.onItemClick(imageUri)
                            currentUri = imageUri
                        }
                    })
                }
            }

            //권한 거절
            override fun onPermissionDenied(deniedPermissions: MutableList<String>?) {
                Toast.makeText(mainActivity,"권한 필요", Toast.LENGTH_SHORT)
                    .show()
            }

        }

        //갤러리 권한 요청
        TedPermission.with(mainActivity.applicationContext)
            .setPermissionListener(readGalleryListener)
            .setPermissions(
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            ).check()

        setUpListener()
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


    @SuppressLint("Recycle")
    private fun getAllShownImagesPath(): ArrayList<String>{
        val uriList = ArrayList<String>()
        //현재 얻은 uri
        val uriExternal : Uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI
        var columnIndexId : Int
        var imageId : Long
        val cursor = mainActivity.contentResolver.query(
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
            null,null,null,
            MediaStore.Images.ImageColumns.DATE_TAKEN + " DESC"
        )
        //사진 경로 하나씩 가져오기
        if (cursor!=null){
            while (cursor.moveToNext()){
                columnIndexId = cursor.getColumnIndexOrThrow(MediaStore.Images.Media._ID)
                while(cursor.moveToNext()){
                    imageId = cursor.getLong(columnIndexId)
                    val uriImage = Uri.withAppendedPath(uriExternal,""+imageId)
                    uriList.add(uriImage.toString())
                }
            }
            cursor.close()
        }
        return uriList
    }
    private fun setUpListener(){

    }
}