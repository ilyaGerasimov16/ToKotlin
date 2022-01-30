package com.example.tokotlin.lesson9

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.tokotlin.R
import com.example.tokotlin.databinding.FragmentContentProviderBinding
import com.example.tokotlin.databinding.FragmentMainBinding


class ContentProviderFragment : Fragment() {

    private var _binding: FragmentContentProviderBinding? = null
    private val binding: FragmentContentProviderBinding
        get(){
            return _binding!!
        }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentContentProviderBinding.inflate(inflater, container, false)
        return binding.root
    }

    fun getContacts(){

    }

    companion object {
        @JvmStatic
        fun newInstance() = ContentProviderFragment()
    }
}