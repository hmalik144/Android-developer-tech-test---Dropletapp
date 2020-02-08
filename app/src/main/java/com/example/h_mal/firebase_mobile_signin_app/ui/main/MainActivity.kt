package com.example.h_mal.firebase_mobile_signin_app.ui.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.example.h_mal.firebase_mobile_signin_app.R
import com.example.h_mal.firebase_mobile_signin_app.ui.CompletionListener
import kotlinx.android.synthetic.main.main_activity.*
import org.kodein.di.KodeinAware
import org.kodein.di.android.kodein
import org.kodein.di.generic.instance

class MainActivity : AppCompatActivity(),
    CompletionListener, KodeinAware {

    override val kodein by kodein()
    private val factory: MainViewModelFactory by instance()

    companion object{
        lateinit var viewModel: MainViewModel
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, VerifyFragment())
                .commit()
        }

        viewModel = ViewModelProvider(this, factory).get(MainViewModel::class.java)
        viewModel.completionListener = this
    }

    override fun onBackPressed() {
        if (supportFragmentManager.backStackEntryCount > 1 ){
            supportFragmentManager.popBackStack()
            return
        }
        super.onBackPressed()
    }

    override fun onStarted() {
        progress_bar.visibility = View.VISIBLE
    }

    override fun onChange() {
        supportFragmentManager.beginTransaction()
            .addToBackStack("firstFragment")
            .replace(R.id.container, SignUpFragment())
            .commit()
    }

    override fun onSuccess() {
        progress_bar.visibility = View.GONE
    }

    override fun onFailure(message: String) {
        progress_bar.visibility = View.GONE
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }

}
