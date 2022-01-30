package com.example.tokotlin.lesson9

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.provider.ContactsContract
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.example.tokotlin.databinding.FragmentContentProviderBinding


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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        checkPermission()
    }


    private fun checkPermission(){
        context?.let {
            when{
                ContextCompat.checkSelfPermission(it,Manifest.permission.READ_CONTACTS)
                        == PackageManager.PERMISSION_GRANTED -> {
                            getContacts()
                        }
                shouldShowRequestPermissionRationale(Manifest.permission.READ_CONTACTS) -> {
                    showDialog()
                }
                else ->{
                    myRequestPermissions()
                }
            }
        }
    }


    private fun getContacts(){
        context?.let {
            val contentResolver = it.contentResolver
            val cursor = contentResolver.query(
                ContactsContract.Contacts.CONTENT_URI,
                null,
                null,
                null,
                ContactsContract.Contacts.DISPLAY_NAME + " ASC"
            )
            cursor?.let { cursor->
                for(i in 0 until cursor.count){
                    cursor.moveToPosition(i)
                    val name = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME))
                    addView(name)
                }
            }
            cursor?.close()
        }
    }

    private fun addView(name:String){
        binding.containerForContacts.addView(TextView(requireContext()).apply {
            text = name
            textSize = 30f
        })
    }


    private val REQUEST_CODE = 999

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        //super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if(requestCode == REQUEST_CODE){

            when{
                (grantResults[0] == PackageManager.PERMISSION_GRANTED) ->{
                    getContacts()
                }
                shouldShowRequestPermissionRationale(Manifest.permission.READ_CONTACTS) -> {
                    showDialog()
                }
                else -> {

                }
            }
        }
    }



    private fun showDialog(){
        AlertDialog.Builder(requireContext())
            .setTitle("Доступ к контактам").setMessage("Объяснение")
            .setPositiveButton("Предоставить доступ"){_,_ ->
                myRequestPermissions()
            }
            .setNegativeButton("Не надо") {dialog, _ -> dialog.dismiss()}
            .create()
            .show()
    }

    private fun myRequestPermissions(){
        requestPermissions(arrayOf(Manifest.permission.READ_CONTACTS), REQUEST_CODE )
    }

    companion object {
        @JvmStatic
        fun newInstance() = ContentProviderFragment()
    }
}