package com.example.contactfetch

import android.app.Application
import android.content.ContentProviderClient
import android.content.ContentResolver
import android.content.Context
import android.database.Cursor
import android.os.Build
import android.provider.ContactsContract
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch


class ViewModel(application: Application) : AndroidViewModel(application){

    val mApplication = application
    private val _contactsLiveData = MutableLiveData<ArrayList<Contacts>>()
    val contactsLiveData:LiveData<ArrayList<Contacts>> = _contactsLiveData
    var list = ArrayList<Contacts>()

    fun fetchContacts(){
        viewModelScope.launch {
            val contactListAsync = async { getPhoneContacts() }
            val list = contactListAsync.await()
            _contactsLiveData.postValue(list)
        }
    }


    private fun getPhoneContacts(): ArrayList<Contacts> {
        var arrayList = ArrayList<Contacts>()
        arrayList.clear()
        try {
            val cursor = mApplication.contentResolver?.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null,
                    null, null,
                    ContactsContract.Contacts.DISPLAY_NAME + " ASC")

            if (cursor != null && cursor.count > 0){
                while (cursor.moveToNext()){
                    var name = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME))
                    var number = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER))
                    val re_hiphen = Regex("-")
                    val re_space = Regex(" ")
                    number = re_hiphen.replace(number, "")
                    number = re_space.replace(number, "")
                    var contacts = Contacts(number, name)
                    if(name != null && number.length > 8 && !arrayList.contains(contacts)) {
                        arrayList.add(contacts)
                        Log.i("DATADISPLAY", "Name: $name    Phone: $number")
                    }
                }
            }

            cursor?.close()
        }
        catch (e : Exception ){
            Log.i("ERROR", "Some exception occured:  $e");
        }

        return arrayList
    }

}