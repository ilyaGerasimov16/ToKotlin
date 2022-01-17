package com.example.tokotlin.view

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.annotation.RequiresApi
import com.example.tokotlin.databinding.ActivityMainWebviewBinding
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.URL
import java.util.stream.Collectors
import javax.net.ssl.HttpsURLConnection


class MainActivityWebView : AppCompatActivity() {

    private lateinit var binding: ActivityMainWebviewBinding

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainWebviewBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.btnOk.setOnClickListener{
            request(binding.etUrl.text.toString())
        }
    }


    @RequiresApi(Build.VERSION_CODES.N)
    private fun request(urlString:String){
        lateinit var httpsURLConnection:HttpsURLConnection
        Thread{
            try {
                val url = URL(urlString)
                httpsURLConnection = (url.openConnection() as HttpsURLConnection).apply {
                    requestMethod = "GET"
                    readTimeout = 2000
                }
                val bufferedReader = BufferedReader(InputStreamReader(httpsURLConnection.inputStream))
                val result = convertBufferToResult(bufferedReader)
                runOnUiThread{
                    binding.webView.loadDataWithBaseURL(
                        null,
                        result,
                        "text/html; charset=utf-8",
                        "utf-8",
                        null)
                }
            }catch (e:Exception){
                Toast.makeText(applicationContext, "Error", Toast.LENGTH_SHORT).show()
           }finally {
                httpsURLConnection.disconnect()
            }
        }.start()
    }

    @RequiresApi(Build.VERSION_CODES.N)
    private fun convertBufferToResult(bufferedReader: BufferedReader):String {
        return bufferedReader.lines().collect(Collectors.joining("\n"))
    }
}




