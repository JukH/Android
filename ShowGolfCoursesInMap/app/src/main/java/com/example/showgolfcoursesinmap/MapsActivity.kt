package com.example.showgolfcoursesinmap

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.*
import org.json.JSONObject

class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

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
        val url = "http://ptm.fi/materials/golfcourses/golf_courses.json"
        // Create request and listeners
        val jsonObjectRequest = JsonObjectRequest(
            Request.Method.GET, url, null,
            Response.Listener { response ->
                // Get employees from JSON

                val paikat = response.getJSONArray("courses") // Looppaa "paikat" läpi, poimi tiedot


                val i = 0
                for (i in 0..(paikat.length() - 1)) {
                    val i = paikat.getJSONObject(i)
                    val lat = i.getString("lat").toDouble()
                    val lng = i.getString("lng").toDouble()
                    val nimi = i.getString("course").toString()
                    val osoite = i.getString("address").toString()
                    val puh = i.getString("phone").toString()
                    val web = i.getString("web").toString()
                    val email = i.getString("email").toString()
                    val kuvaus = i.getString("text").toString()
                    val kenttaTyyppi = i.getString("type").toString()
                    val sijainti = LatLng(lat, lng)





                    if (kenttaTyyppi == "Kulta") {
                        mMap.addMarker(
                            MarkerOptions().position(sijainti).title(nimi).snippet("$osoite\n $puh\n $email\n $web").icon(
                                BitmapDescriptorFactory.defaultMarker(
                                    BitmapDescriptorFactory.HUE_YELLOW
                                )
                            )
                        )
                    } else if (kenttaTyyppi == "?") {
                        mMap.addMarker(
                            MarkerOptions().position(sijainti).title(nimi).snippet("$osoite\n $puh\n $email\n $web").icon(
                                BitmapDescriptorFactory.defaultMarker(
                                    BitmapDescriptorFactory.HUE_ROSE
                                )
                            )
                        )
                    } else if(kenttaTyyppi == "Kulta/Etu"){
                        mMap.addMarker(
                            MarkerOptions().position(sijainti).title(nimi).snippet("$osoite\n $puh\n $email\n $web").icon(
                                BitmapDescriptorFactory.defaultMarker(
                                    BitmapDescriptorFactory.HUE_GREEN
                                )
                            )
                        )
                    } else {
                        mMap.addMarker(
                            MarkerOptions().position(sijainti).title(nimi).snippet("$osoite\n $puh\n $email\n $web").icon(
                                BitmapDescriptorFactory.defaultMarker(
                                    BitmapDescriptorFactory.HUE_BLUE
                                )
                            )
                        )
                    }
                }
                /////////////////////////////////////////////////////////
                mMap.setInfoWindowAdapter(object : GoogleMap.InfoWindowAdapter {

                    override// Return null here, so that getInfoContents() is called next.
                    fun getInfoWindow(arg0: Marker): View? {
                        return null
                    }

                    override fun getInfoContents(marker: Marker): View {
                        // Inflate the layouts for the info window, title and snippet.
                        val infoWindow = layoutInflater.inflate(R.layout.custom_info_window, null)

                        val title = infoWindow.findViewById(R.id.title) as TextView
                        title.text = marker.title

                        val text = infoWindow.findViewById(R.id.text) as TextView
                        text.text = marker.snippet

                        return infoWindow
                    }
                })
                /////////////////////////////////////////////////////////
                //annetaan kartalle lähtöpiste
                val jyvaskyla = LatLng(62.24147, 25.72088)


                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(jyvaskyla, 6.0F))


                //lisätään zoom
                mMap.uiSettings.isZoomControlsEnabled = true
               // mMap.setOnMarkerClickListener(this)
            },
            Response.ErrorListener { error ->
                Log.d("JSON", error.toString())
            }
        )
        // Add the request to the RequestQueue.
        queue.add(jsonObjectRequest)
    }
}
