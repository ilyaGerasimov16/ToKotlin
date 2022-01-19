package com.example.tokotlin.lesson6

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.tokotlin.databinding.FragmentThreadsBinding

class ThreadsFragment : Fragment(){

    private var _binding: FragmentThreadsBinding? = null
    private val binding:FragmentThreadsBinding
    get(){
        return _binding!!
    }

    val myThread = MyThread()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        myThread.start()

        binding.button.setOnClickListener{
            myThread.handler?.post{
                val result = startCalculations(2)
                activity?.let{ activity->
                    Handler(Looper.getMainLooper()).post{
                        binding.mainContainer.addView(TextView(activity).apply {
                            text = result
                        })
                    }
                }

            }
        }
    }

    class MyThread:Thread(){
        var handler:Handler? = null
        override fun run() {
            Looper.prepare()
            handler = Handler(Looper.myLooper()!!)
            Looper.loop()
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
        myThread.handler?.removeCallbacksAndMessages(null)
        super.onDestroy()

        _binding = null
    }

}