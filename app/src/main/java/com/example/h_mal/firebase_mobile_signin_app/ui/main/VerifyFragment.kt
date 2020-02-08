package com.example.h_mal.firebase_mobile_signin_app.ui.main


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.example.h_mal.firebase_mobile_signin_app.ui.main.MainActivity.Companion.viewModel

import com.example.h_mal.firebase_mobile_signin_app.R
import com.example.h_mal.firebase_mobile_signin_app.databinding.MainFragmentBinding
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.kodein
import org.kodein.di.generic.instance

class VerifyFragment : Fragment(), KodeinAware{

    override val kodein by kodein()
    private val factory: MainViewModelFactory by instance()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val binding: MainFragmentBinding = DataBindingUtil.inflate(inflater,R.layout.main_fragment, container, false)
//        val viewModel = ViewModelProvider(this, factory).get(MainViewModel::class.java)
        binding.viewmodel = viewModel

        return binding.root
    }

}
