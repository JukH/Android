package com.example.golfapplication

import android.app.PendingIntent.getActivity
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.Toolbar
import android.util.Log
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.MobileAds
import kotlinx.android.synthetic.main.activity_detail.*

import kotlinx.android.synthetic.main.item_list.*
import org.json.JSONArray

class MainActivity : AppCompatActivity() {

    // true if device is in landscape mode
    private var twoPane: Boolean = false

    // employees static object - can be used as MainActivity.employees
    companion object {
        var employees: JSONArray = JSONArray()
    }
    lateinit var mAdView : AdView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //Tuodaan mainokset
        MobileAds.initialize(this, "ca-app-pub-3940256099942544~3347511713")
        mAdView = findViewById(R.id.adView)
        val adRequest = AdRequest.Builder().build()
        mAdView.loadAd(adRequest)




        // is device landscape mode
        if (item_detail_container != null) {
            // The detail container view will be present only in the landscape
            // If this view is present, then the activity should be in two-pane mode.
            twoPane = true
        }

        // Load employees if not loaded, if loaded setup recycler view
        if (employees.length() == 0) loadJsonData()
        else setupRecyclerView(employees)

    }
    // Add layout manager and adapter to recycler view
    private fun setupRecyclerView(employees: JSONArray) {
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = CourseAdapter(this, twoPane)
    }

    // Load JSON from the net
    private fun loadJsonData() {
        // Instantiate the RequestQueue
        val queue = Volley.newRequestQueue(this)
        // URL to JSON data - remember use your own data here or "http://ptm.fi/data/android_employees.json"
        val url = "http://ptm.fi/materials/golfcourses/golf_courses.json"

        // Create request and listeners
        val jsonObjectRequest = JsonObjectRequest(
            Request.Method.GET, url, null,
            Response.Listener { response ->
                // store loaded json to static employees
                employees = response.getJSONArray("courses")
                // setup recycler view
                setupRecyclerView(employees)
            },
            Response.ErrorListener { error ->
                Log.d("JSON",error.toString())
            }
        )
        // Add the request to the RequestQueue.
        queue.add(jsonObjectRequest)
    }
}

