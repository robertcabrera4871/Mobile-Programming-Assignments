package com.example.shadedetector

import android.net.Uri
import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.activity_main.*
import org.json.JSONObject
import java.net.URL

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        progressBar1.visibility = View.GONE
    }

    inner class myClass : AsyncTask<Void, Void, String>() {
        lateinit var uriString: String
        var isBlank: Boolean = false

        override fun onPreExecute() {
            super.onPreExecute()
            if (dissInput.text.isNotBlank()) {
                isBlank = false
                uriString = buildCustomString(dissInput.text.toString())
                progressBar1.visibility = View.VISIBLE
            } else{
                isBlank = true
            }
        }
        override fun doInBackground(vararg params: Void?): String {

            if(!isBlank) {
                return URL(uriString).readText()
            }
            return "Your input is blank!"
        }

        override fun onPostExecute(result: String?) {
            super.onPostExecute(result)
            if(!isBlank) {
                setTextView(JSONObject(result))
            } else{
                textViewMe.text = result
            }
            progressBar1.visibility = View.GONE
        }
        private fun setTextView(myJson: JSONObject) {
            try {
                val irony = myJson.getString("irony")
                val scoreTag = myJson.getString("score_tag")
                val confidence = myJson.getString("confidence")
                textViewMe.text = "Irony: $irony\nScore Tag: $scoreTag\nConfidence: $confidence\n"
            } catch (e: Exception) {
                textViewMe.text = "API Failed"
            }
        }

        private fun buildCustomString(dissText: String): String {
            val buildUri = Uri.parse("https://api.meaningcloud.com/sentiment-2.1").buildUpon()
                .appendQueryParameter("key", "8fc450298af34b9a7df6ff81aa6c162d")
                .appendQueryParameter("lang", "auto")
                .appendQueryParameter("txt", dissText)
                .build()
            return buildUri.toString()
        }
    }

    fun doRequest(view: View) {
        myClass().execute()
    }
}


