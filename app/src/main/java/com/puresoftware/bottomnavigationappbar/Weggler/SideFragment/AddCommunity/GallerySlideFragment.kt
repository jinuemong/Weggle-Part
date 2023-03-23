package com.puresoftware.bottomnavigationappbar.Weggler.SideFragment.AddCommunity

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.bumptech.glide.Glide
import com.gun0912.tedpermission.PermissionListener
import com.gun0912.tedpermission.TedPermission
import com.puresoftware.bottomnavigationappbar.MainActivity
import com.puresoftware.bottomnavigationappbar.Weggler.Adapter.SelectPicAdapter
import com.puresoftware.bottomnavigationappbar.Weggler.Unit.isVideo
import com.puresoftware.bottomnavigationappbar.databinding.FragmentGallerySlideBinding

class GallerySlideFragment : Fragment() {
    private var _binding : FragmentGallerySlideBinding? = null
    private val binding get() = _binding!!
    lateinit var mainActivity: MainActivity
    private lateinit var readGalleryListener : PermissionListener

    private var onItemClickListener : OnItemClickListener?= null
    fun setOnItemClickListener(listener: OnItemClickListener){
        this.onItemClickListener = listener
    }
    interface OnItemClickListener{
        fun onItemClick(imageUri:Uri?){}
    }

    var currentUri :Uri? = null
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

        setImageView()

        //권한 설정 리스너 선언
        readGalleryListener = object :PermissionListener{

            //권한 성공
            override fun onPermissionGranted() {
                val uriList = openFileList()
                val adapter = SelectPicAdapter(mainActivity,uriList)

                //어댑터 연결 후 아이템 클릭 리스너 적용
                binding.imageRecycler.adapter = adapter.apply {
                    setOnItemClickListener(object : SelectPicAdapter.OnItemClickListener{
                        override fun onItemClick(imageUri: Uri?) {
                            currentUri = imageUri
                            if(currentUri==null){
                                binding.selectImage.setImageResource(0)
                            }else {
                                if (currentUri.toString().contains("video")){
                                    setVideoView()
                                    binding.selectVideo.apply {
                                        layoutParams = binding.videoContainer.layoutParams
                                        setVideoURI(currentUri)
                                        setMediaController(null)
                                        setOnClickListener {
                                            if (this.isPlaying){
                                                binding.playButton.visibility = View.VISIBLE
                                                pause()
                                            }else {
                                                binding.playButton.visibility = View.GONE
                                                start()
                                            }
                                        }
                                        setOnCompletionListener {
                                            binding.playButton.visibility = View.VISIBLE
                                        }
                                    }
                                }else {
                                    setImageView()
                                    Glide.with(mainActivity)
                                        .load(currentUri)
                                        .into(binding.selectImage)
                                }
                            }
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

    private fun openFileList(): List<Uri> {
        val image = getAllShownImagesPath()
        val video = getAllShownVideosPath()
        val totalData = image.plus(video)
        totalData.sortedBy { it }
        return totalData
    }
    @SuppressLint("Recycle")
    private fun getAllShownImagesPath(): ArrayList<Uri>{
        val uriList = ArrayList<Uri>()
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
                imageId = cursor.getLong(columnIndexId)
                val uriImage = Uri.withAppendedPath(uriExternal, "" + imageId)
                uriList.add(uriImage)
            }
            cursor.close()
        }
        return uriList
    }

    @SuppressLint("Recycle")
    private fun getAllShownVideosPath(): ArrayList<Uri>{
        val uriList = ArrayList<Uri>()
        //현재 얻은 uri
        val uriExternal : Uri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI

        var columnIndexId : Int
        var videoId : Long
        val cursor = mainActivity.contentResolver.query(
            MediaStore.Video.Media.EXTERNAL_CONTENT_URI,
            null,null,null,
            MediaStore.Video.VideoColumns.DATE_TAKEN + " DESC"
        )
        //사진 경로 하나씩 가져오기
        if (cursor!=null){
            while (cursor.moveToNext()){
                columnIndexId = cursor.getColumnIndexOrThrow(MediaStore.Video.Media._ID)
                videoId = cursor.getLong(columnIndexId)
                val uriImage = Uri.withAppendedPath(uriExternal, "" + videoId)
                uriList.add(uriImage)
            }
            cursor.close()
        }
        return uriList
    }


    @SuppressLint("ResourceType")
    private fun setUpListener(){
        //뒤로가기
        binding.cancelButton.setOnClickListener {
            onItemClickListener?.onItemClick(currentUri)
        }

        binding.uploadButton.setOnClickListener {
            onItemClickListener?.onItemClick(currentUri)
        }
    }

    private fun setImageView(){
        binding.selectImage.visibility = View.VISIBLE
        binding.videoContainer.visibility = View.GONE
    }

    private fun setVideoView(){
        binding.videoContainer.visibility = View.VISIBLE
        binding.selectImage.visibility = View.GONE
    }
}