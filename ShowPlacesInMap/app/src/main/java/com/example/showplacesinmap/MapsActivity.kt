package com.example.showplacesinmap

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.GoogleMap.OnMarkerClickListener
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import org.json.JSONArray
import org.json.JSONObject

class MapsActivity : AppCompatActivity(), OnMapReadyCallback, OnMarkerClickListener{
    override fun onMarkerClick(marker: Marker?): Boolean {
        if(marker?.title == "Jumbo"){
            Toast.makeText(this, "Kauppakeskus Jumbo, kelpo paikka shoppailla.", Toast.LENGTH_LONG).show()
        }
        else if(marker?.title == "Laurea Tikkurila"){
            Toast.makeText(this, "Laurea Tikkurila, opinahjoni.", Toast.LENGTH_LONG).show()
        }
        else if(marker?.title == "Järvenpään jäähalli"){
            Toast.makeText(this, "Järvenpään jäähalli. Täällä pelailen usein jääkiekkoa.", Toast.LENGTH_LONG).show()
        }
    return true
    }


    private lateinit var mMap: GoogleMap




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */





    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap



        ////////////////////////////////////////////////////////////////////////////////////////////////////////
        val queue = Volley.newRequestQueue(this)
        // URL to JSON data - remember use your own data here
        val url = "https://api.myjson.com/bins/7tmxq.json"
        // Create request and listeners
        val jsonObjectRequest = JsonObjectRequest(Request.Method.GET, url, null,
            Response.Listener { response ->
                // Get employees from JSON

                val paikat = response.getJSONArray("paikat")
                // 0 = jumbo, 1 = Järvenpää, 2 = laurea tiksi

                //Kerätään data yksittäin
                val jumboJson: JSONObject = paikat.getJSONObject(0)
                val jakeJson: JSONObject = paikat.getJSONObject(1)
                val laureaJson: JSONObject = paikat.getJSONObject(2)
                val laureaLat = laureaJson.getString("latitude").toDouble()
                val laureaLong = laureaJson.getString("longitude").toDouble()
                val jumboLat = jumboJson.getString("latitude").toDouble()
                val jumboLong = jumboJson.getString("longitude").toDouble()
                val jakeLong = jakeJson.getString("longitude").toDouble()
                val jakeLat = jakeJson.getString("latitude").toDouble()

                //Muokataan tuotu data LatLng-muotoon
                val jumbo = LatLng(jumboLat,jumboLong)
                val jarvenpaanJaahalli = LatLng(jakeLat, jakeLong)
                val laurea = LatLng(laureaLat, laureaLong)
                mMap.addMarker(MarkerOptions().position(jumbo).title("Jumbo"))
                mMap.addMarker(MarkerOptions().position(laurea).title("Laurea Tikkurila"))
                mMap.addMarker(MarkerOptions().position(jarvenpaanJaahalli).title("Järvenpään jäähalli"))
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(jumbo, 9.0F))


                //lisätään zoom
                mMap.uiSettings.isZoomControlsEnabled = true
                mMap.setOnMarkerClickListener(this)
            },
            Response.ErrorListener { error ->
                Log.d("JSON", error.toString())
            }
        )
        // Add the request to the RequestQueue.
        queue.add(jsonObjectRequest)
        ////////////////////////////////////////////////////////////////////////////////////////////////////////

    }



}



