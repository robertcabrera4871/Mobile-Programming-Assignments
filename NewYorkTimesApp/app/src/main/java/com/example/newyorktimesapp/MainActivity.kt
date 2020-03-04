package com.example.newyorktimesapp

import android.net.Uri
import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import kotlinx.android.synthetic.main.activity_main.*
import org.json.JSONObject
import java.net.URL
import java.util.*
import android.text.method.ScrollingMovementMethod
import androidx.core.app.ComponentActivity
import androidx.core.app.ComponentActivity.ExtraData
import androidx.core.content.ContextCompat.getSystemService
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import android.util.Log
import kotlin.collections.ArrayList


class MainActivity : AppCompatActivity() {

    var headlineStringToAPI: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        progressBar1.visibility = View.GONE
        textViewMe.movementMethod = ScrollingMovementMethod()
    }
    inner class nyTimesThread: AsyncTask<Void, Void, String>()
    {
        lateinit var nyTimesUriString: String
        var isBlank: Boolean = false

        override fun onPreExecute() {
            super.onPreExecute()
            if (headlineSearch.text.isNotBlank()) {
                isBlank = false
                nyTimesUriString = buildCustomString(headlineSearch.text.toString())
                progressBar1.visibility = View.VISIBLE
            } else{
                isBlank = true
            }
        }
        override fun doInBackground(vararg params: Void?): String {
            if(!isBlank) {
                return URL(nyTimesUriString).readText()
            }
            return "Your input is blank!"
        }
        override fun onPostExecute(result: String?) {
            super.onPostExecute(result)
            if(!isBlank) {
                setTextView(JSONObject(result))
            }
            progressBar1.visibility = View.GONE
        }
        private fun buildCustomString(headlineText: String): String {
            val buildUri = Uri.parse("https://api.nytimes.com/svc/search/v2/articlesearch.json").buildUpon()
                .appendQueryParameter("api-key", "RolcAwjNyPZB8vAb7AdlurYadhd4lkcf")
                .appendQueryParameter("q", headlineText)
                .build()
            return buildUri.toString()
        }
        private fun setTextView(myJson: JSONObject) {
            try {
                headlineStringToAPI = ""
                var x = 0
                val docs = myJson.getJSONObject("response")
                    .getJSONArray("docs")
                while(x < 10) {
                    var headline = docs
                        .getJSONObject(x++)
                        .getJSONObject("headline")
                        .getString("main")
                //    textViewMe.text = textViewMe.text.toString().plus(headline).plus("\n\n")
                    headlineStringToAPI = headlineStringToAPI.plus("Article $x: $headline \n\n")
                }
                textViewMe.text = headlineStringToAPI
                myClass().execute()
            } catch (e: Exception) {
                textViewMe.text = e.message
                e.printStackTrace()
            }
        }

    }



    inner class myClass : AsyncTask<Void, Void, String>() {
        lateinit var uriString: String

        override fun onPreExecute() {
            super.onPreExecute()
            progressBar1.visibility = View.VISIBLE

        }
        override fun doInBackground(vararg params: Void?): String {
            return URL(buildCustomString()).readText()
        }

        override fun onPostExecute(result: String?) {
            super.onPostExecute(result)
            setTextView(JSONObject(result))
            progressBar1.visibility = View.GONE

        }
        private fun setTextView(myJson: JSONObject) {
            try {
              //  Log.e("FUCK", myJson.toString())
                val irony = myJson.getString("irony")
                val scoreTag = myJson.getString("score_tag")
                val confidence = myJson.getString("confidence")
                textViewMe.text = textViewMe.text.toString().plus("\nIrony: $irony\nScore Tag: $scoreTag\nConfidence: $confidence\n")
            } catch (e: Exception) {
                textViewMe.text = textViewMe.text.toString().plus("\n$e.message")
                e.printStackTrace()
            }
        }

        private fun buildCustomString(): String {
            val buildUri = Uri.parse("https://api.meaningcloud.com/sentiment-2.1").buildUpon()
                .appendQueryParameter("key", "8fc450298af34b9a7df6ff81aa6c162d")
                .appendQueryParameter("lang", "en")
                .appendQueryParameter("txt", headlineStringToAPI)
                .build()
            return buildUri.toString()
        }
    }


    fun doRequest(view: View) {
        nyTimesThread().execute()
    }
}


