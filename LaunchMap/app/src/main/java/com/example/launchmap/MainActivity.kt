package com.example.launchmap

import android.content.Intent
import android.content.pm.ResolveInfo
import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)



    }

    fun showMap(@Suppress("UNUSED_PARAMETER")view: View) {

        val latitude = latEditText.text.toString().toDouble()
        val longitude = lngEditText.text.toString().toDouble()

        val location = Uri.parse("geo:$latitude,$longitude")
        val mapIntent = Intent(Intent.ACTION_VIEW, location)

        val activities: List<ResolveInfo> = packageManager.queryIntentActivities(mapIntent, 0)
        val isIntentSafe: Boolean = activities.isNotEmpty()

        if (isIntentSafe) {
            startActivity(mapIntent)
        } else {
            Toast.makeText(this, "No available activity for intent!", Toast.LENGTH_LONG).show();
        }
    }
}
