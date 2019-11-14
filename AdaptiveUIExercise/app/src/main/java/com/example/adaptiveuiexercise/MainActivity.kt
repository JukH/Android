package com.example.adaptiveuiexercise

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun krosas (view: View){
        var id = R.drawable.krosas
        imageView.setImageResource(id)
    }

    fun lily (@Suppress("UNUSED_PARAMETER")view: View){
        imageView.setImageResource(R.drawable.lily)
    }
}
