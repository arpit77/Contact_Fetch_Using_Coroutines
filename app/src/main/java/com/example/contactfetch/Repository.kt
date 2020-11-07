package com.example.contactfetch

import android.app.Application
import androidx.lifecycle.LiveData
import java.util.ArrayList

class Repository(application: Application) {

    private lateinit var contactsList: LiveData<ArrayList<Contacts>>
}