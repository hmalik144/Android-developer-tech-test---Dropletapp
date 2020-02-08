package com.example.h_mal.firebase_mobile_signin_app.ui.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.example.h_mal.firebase_mobile_signin_app.ui.main.MainActivity.Companion.viewModel

import com.example.h_mal.firebase_mobile_signin_app.R
import com.example.h_mal.firebase_mobile_signin_app.databinding.FragmentSignUpBinding
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.kodein
import org.kodein.di.generic.instance


class SignUpFragment : Fragment(){

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding: FragmentSignUpBinding = DataBindingUtil.inflate(inflater,R.layout.fragment_sign_up, container, false)
        binding.viewmodel = viewModel

        return binding.root
    }

}
