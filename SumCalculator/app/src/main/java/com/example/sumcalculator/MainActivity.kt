package com.example.sumcalculator

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {






    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }



    fun laske(@Suppress("UNUSED_PARAMETER")view: View?){

        var ekaLuku: Int = editText.text.toString().toInt()
        var tokaLuku: Int = editText2.text.toString().toInt()

        var summa : Int = ekaLuku + tokaLuku

        var tulos = summa.toString()

        textView.setText(tulos)
    }






}
