package com.example.lecture11

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.PersistableBundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        myEditText.addTextChangedListener(object: TextWatcher{

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                textView2.text = s.toString()
            }
            override fun afterTextChanged(s: Editable?) {

            }


        })
        textView3.append(" On Create\n")
        textView4.text = savedInstanceState?.getCharSequence("textView4")
    }

    override fun onSaveInstanceState(myState: Bundle) {
        super.onSaveInstanceState(myState)
        myState?.putCharSequence("textView4", textView4.text)
    }
    override fun onStart(){
        super.onStart()
        textView3.append(" Start\n")
    }
    override fun onPause(){
        super.onPause()
        textView3.append(" Pause\n")
    }
    override fun onResume(){
        super.onResume()
        textView3.append(" Resume\n")
    }
    override fun onStop(){
        super.onStop()
        textView3.append(" Stop\n")
    }
    override fun onRestart(){
        super.onRestart()
        textView3.append(" Restart\n")
    }
    override fun onDestroy(){
        super.onDestroy()
        textView3.append(" Destroy\n")
    }

    fun changeText(view: View) {
        textView4.text = myEditText.text.toString()
    }
}
