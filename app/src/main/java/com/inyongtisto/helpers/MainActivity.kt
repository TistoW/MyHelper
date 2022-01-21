package com.inyongtisto.helpers

import android.Manifest
import android.app.Activity
import android.os.*
import androidx.appcompat.app.AppCompatActivity
import androidx.activity.result.contract.ActivityResultContracts
import com.inyongtisto.helpers.util.BaseActivity
import com.inyongtisto.myhelper.PullRefresh
import com.inyongtisto.myhelper.extension.*
import com.inyongtisto.myhelper.util.StoragePermissionsManager
import kotlinx.android.synthetic.main.activity_main.*
import java.io.File
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : BaseActivity() {

    private lateinit var permissions: StoragePermissionsManager
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        PullRefresh(swipeRefresh) {
            progress.show()
            Handler(Looper.myLooper()!!).postDelayed({
                progress.dismiss()
                swipeRefresh.isRefreshing = false
            }, 2000)
        }

        val outputDateFormat = SimpleDateFormat("dd.MM.yyyy", Locale.getDefault()).apply {
            timeZone = TimeZone.getTimeZone("UTC")
        }

        btn_simpan.setOnClickListener {

        }

        spn.setOnPositionSelectedListener(this, arrayListOf()) {

        }
//        permissions = StoragePermissionsManager(this, launcherResult, launcherPermissions)
//        permissions.askPermissions()
    }

    private val launcherResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
        if (permissions.isGranted(it)) {
            //Permission granted
        } else permissions.showDialogReq() //Permission not granted
    }

    private val launcherPermissions = registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { result ->
        permissions.isGranted(result)
        if (permissions.isGranted(result)) {
            //Permission granted
        } else this.permissions.showDialogReq()  //Permission not granted
    }

    var fileImage: File? = null
    private val profileLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
        if (it.resultCode == Activity.RESULT_OK) {
            val uri = it.data?.data!!
            fileImage = File(uri.path!!)
        }
    }
}