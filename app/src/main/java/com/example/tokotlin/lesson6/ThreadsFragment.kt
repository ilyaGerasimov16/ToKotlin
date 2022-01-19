package com.example.tokotlin.lesson6

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.tokotlin.databinding.FragmentThreadsBinding

class ThreadsFragment : Fragment(){

    private var _binding: FragmentThreadsBinding? = null
    private val binding:FragmentThreadsBinding
    get(){
        return _binding!!
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.button.setOnClickListener{
            Thread{
                val result = startCalculations(2)
                requireActivity().runOnUiThread{
                    binding.textView.text = result
                }
            }.start()

        }
    }

    private fun startCalculations(seconds: Int): String {
        Thread.sleep(seconds*1000L)
        return "${seconds.toString()} ${Thread.currentThread().name}"
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentThreadsBinding.inflate(inflater,container,false)
        return binding.root
    }

    companion object {
        fun newInstance() = ThreadsFragment()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}