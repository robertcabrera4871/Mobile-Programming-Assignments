package com.example.feb26lecture

import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    inner class myClass: AsyncTask<String, Void, String>(){
        override fun doInBackground(vararg params: String?): String {

            return  params[0].toString()
        }

        override fun onPostExecute(result: String?) {
            super.onPostExecute(result)
            Toast.makeText(this@MainActivity, result, Toast.LENGTH_LONG).show()
        }

    }

    fun buttonClicked(view: View) {
        myClass().execute("hello","goodbye", "Hey Again")
    }
}
