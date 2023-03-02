package com.puresoftware.bottomnavigationappbar.MyAccount

import android.app.AlertDialog
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import com.puresoftware.bottomnavigationappbar.MyAccount.retrofit.ProductDataClass
import com.puresoftware.bottomnavigationappbar.MyAccount.retrofit.RetrofitClient
import com.puresoftware.bottomnavigationappbar.MyAccount.retrofit.WegglerRetrofitService
import com.puresoftware.bottomnavigationappbar.databinding.ActivityChallengeBinding
import retrofit2.Call

class ChallengeActivity : AppCompatActivity() {
    lateinit var binding:ActivityChallengeBinding
    var galleryUri: Uri? = null
    var launcher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { it ->
        it.data?.data?.let { uri ->
            galleryUri = uri
        }
    }
    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChallengeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.challengeVideoChooseButton.setOnClickListener{
            when{
                ContextCompat.checkSelfPermission(this,
                    android.Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED -> {
                    val intentImage = Intent(Intent.ACTION_OPEN_DOCUMENT)
                    intentImage.type = "image/*"
                    launcher.launch(intentImage)
                    }
                shouldShowRequestPermissionRationale(android.Manifest.permission.READ_EXTERNAL_STORAGE) ->{
                    showPermissionContextPopup()
                }
                else -> {
                    requestPermissions(arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE),1000)
                }
            }
        }

    }
    @RequiresApi(Build.VERSION_CODES.M)
    fun showPermissionContextPopup() {
        AlertDialog.Builder(this)
            .setTitle("권한이 필요합니다.")
            .setMessage("사진을 불러오기 위해서 권한이 필요합니다.")
            .setPositiveButton("동의") { _, _ ->
                requestPermissions(arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE), 1000)
                intent = Intent(Intent.ACTION_OPEN_DOCUMENT)
                intent.type = "image/*"
                launcher.launch(intent)}
            .setNegativeButton("취소") { _, _ -> }
            .create()
            .show()
    }
    @RequiresApi(Build.VERSION_CODES.M)
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            1000 -> {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    val intentImage = Intent(Intent.ACTION_OPEN_DOCUMENT)
                    intentImage.type = "image/*"
                    launcher.launch(intentImage)
                } else {
                    // 거부 클릭시
                    Toast.makeText(this, "권한을 거부했습니다.", Toast.LENGTH_SHORT).show()
                    showPermissionContextPopup()
                }
            }
            else -> {
                Log.d("권한", "종료")
                showPermissionContextPopup()
            }
        }
    }

}