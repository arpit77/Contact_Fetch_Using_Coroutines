package com.example.contactfetch

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.util.EnumSet.of

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: ViewModel
    private lateinit var button: Button
    private lateinit var recyclerView: RecyclerView
    private var arrayList = ArrayList<Contacts>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        viewModel = ViewModelProvider(this).get(ViewModel::class.java)
        viewModel.contactsLiveData.observe(this, Observer {
            Log.i("hcbsdghcv", "shcsdhgv  ${it.size}")
            arrayList = it
        })
        recyclerView = findViewById(R.id.recyclerView)
        button = findViewById(R.id.button_fetch)
        recyclerView.layoutManager = LinearLayoutManager(this)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(
                    arrayOf(Manifest.permission.READ_CONTACTS)
                    , 1);
            }

            else{
                viewModel.fetchContacts()
            }
        }
        button.setOnClickListener {
            Log.i("sdjcdsg", "jhcvsd  ${arrayList.size}")
            recyclerView.adapter = RecylerAdapter(this, arrayList)
        }




    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            viewModel.fetchContacts()

        }
    }
}