package com.example.h_mal.firebase_mobile_signin_app.ui.details

import android.Manifest.permission.READ_EXTERNAL_STORAGE
import android.Manifest.permission.WRITE_EXTERNAL_STORAGE
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Build
import android.os.Bundle
import android.util.Base64
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.h_mal.firebase_mobile_signin_app.R
import com.example.h_mal.firebase_mobile_signin_app.databinding.ActivityDetailsUpdateBinding
import com.example.h_mal.firebase_mobile_signin_app.ui.CompletionListener
import com.example.h_mal.firebase_mobile_signin_app.ui.main.MainActivity
import kotlinx.android.synthetic.main.activity_details_update.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.kodein.di.KodeinAware
import org.kodein.di.android.kodein
import org.kodein.di.generic.instance


class DetailsUpdateActivity : AppCompatActivity(),CompletionListener, KodeinAware {

    private val IMAGE_PICK_CODE: Int = 1000
    private val PERMISSION_CODE_WRITE: Int = 1002
    private val PERMISSION_CODE_READ: Int = 1001

    override val kodein by kodein()
    private val factory: DetailsViewModelFactory by instance()

    lateinit var viewModel: DetailsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = ViewModelProvider(this, factory).get(DetailsViewModel::class.java)
        val binding: ActivityDetailsUpdateBinding = DataBindingUtil.setContentView(this, R.layout.activity_details_update)
        binding.viewmodel = viewModel

        viewModel.completionListener = this

        viewModel.initialise()

        viewModel.currentUser.observe(this, Observer {
            email.setText(it.email)
            first_name.setText(it.firstName)
            last_name.setText(it.lastName)

            convertB64ToImage(it.avatar)
        })

        image_button.setOnClickListener {
            checkPermissionForImage()
        }
    }

    //create a menu to navigate to other activities
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId){
            R.id.sign_out ->{
                viewModel.logOutUser()
                Intent(this, MainActivity::class.java).also {
                    it.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    startActivity(it)
                }
            }
        }

        return super.onOptionsItemSelected(item)
    }

    private fun checkPermissionForImage() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M &&
            (checkSelfPermission(READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED)
            && (checkSelfPermission(WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED)
        ) {

            val permission = arrayOf(READ_EXTERNAL_STORAGE)
            val permissionCoarse = arrayOf(WRITE_EXTERNAL_STORAGE)

            requestPermissions(permission, PERMISSION_CODE_READ)
            requestPermissions(permissionCoarse, PERMISSION_CODE_WRITE)

        } else {
            pickImageFromGallery()
        }


    }

    private fun pickImageFromGallery() {
        Intent(Intent.ACTION_PICK).apply {
            this.type = "image/*"
            startActivityForResult(this, IMAGE_PICK_CODE)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK && requestCode == IMAGE_PICK_CODE) {
            data?.data?.let {uri ->
                viewModel.turnUriToString(this, uri)
                image_button.setImageURI(uri)
                return
            }
            Toast.makeText(this,"No image selected", Toast.LENGTH_LONG).show()
        }
    }

    private fun convertB64ToImage(b64String: String?){
        if (b64String.isNullOrEmpty()){
            return
        }

        CoroutineScope(Dispatchers.Default).launch {
            val decodedString: ByteArray =
                Base64.decode(b64String, Base64.DEFAULT)
            val bitmap =  BitmapFactory.decodeByteArray(decodedString, 0, decodedString.size)
            withContext(Dispatchers.Main){
                image_button.setImageBitmap(bitmap)
            }
        }
    }

    override fun onStarted() {
        progress_bar.visibility = View.VISIBLE
    }

    override fun onSuccess() {
        progress_bar.visibility = View.GONE
    }

    override fun onFailure(message: String) {
        progress_bar.visibility = View.GONE
        Toast.makeText(this,message, Toast.LENGTH_LONG).show()
    }

    override fun onChange() {
        viewModel.imageB64String?.let {
            convertB64ToImage(it)
        }
        progress_bar.visibility = View.GONE
    }
}
