package com.example.devicerotation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ListView

/**
 * While rotating a screen, an activity's onPause and onStop call
 * and then onSaveInstanceState
 * calls and after rotation completed
 * it's onCreate calls and then onRestoreInstanceState calls and then onResume.
 * So to get beck lost state we have to save
 * state onSaveInstanceState method
 * and have to get back that state on onRestoreInstanceState method call.
 * **/

class MainActivity : AppCompatActivity() {

    private var lvNames: ListView? = null
    private var namesList:ArrayList<String>? = null
    private var arrayAdapter: ArrayAdapter<String>? = null
    private lateinit var edtName: EditText

    companion object{
        private val TAG = MainActivity::class.java.simpleName
        private val STATE_NAMES_LIST = "STATE_NAMES_LIST"
    }

    fun print(message: String){
        Log.d(TAG,message)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        print("onCreate starts")
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        lvNames = findViewById(R.id.lv_names)
        edtName = findViewById(R.id.edt_name)
        if(savedInstanceState==null){
            namesList = ArrayList()
            arrayAdapter = ArrayAdapter(this, android.R.layout.simple_list_item_1,namesList!!)
            lvNames?.adapter = arrayAdapter
        }
        findViewById<Button>(R.id.btn_add).setOnClickListener {
            val name = edtName.text.toString()
            print("findViewById<EditText>(R.id.btn_add).setOnClickListener: ${name}")
            if(!name.isNullOrBlank()){
               namesList?.add(name)
               arrayAdapter?.notifyDataSetChanged()
               edtName.setText("")
            }
        }
        print("onCreate ends")
    }

    override fun onPause() {
        super.onPause()
        print("onPause: called")
    }

    override fun onStop() {
        super.onStop()
        print("onStop: called")
    }

    override fun onRestart() {
        super.onRestart()
        print("onRestart: called")
    }

    override fun onResume() {
        super.onResume()
        print("onResume: called")
    }

    override fun onSaveInstanceState(outState: Bundle) {
        print("onSaveInstanceState: starts")
        super.onSaveInstanceState(outState)
        outState.putStringArrayList(STATE_NAMES_LIST,namesList)
        print("onSaveInstanceState: ends")
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        print("onRestoreInstanceState: starts")
        namesList = savedInstanceState.getStringArrayList(STATE_NAMES_LIST)
        arrayAdapter = ArrayAdapter(this, android.R.layout.simple_list_item_1,namesList!!)
        lvNames?.adapter = arrayAdapter
        arrayAdapter?.notifyDataSetChanged()
        super.onRestoreInstanceState(savedInstanceState)
        print("onRestoreInstanceState: ends")
    }
}