package com.example.golfapplication

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions

import kotlinx.android.synthetic.main.course_item.view.*
import kotlinx.android.synthetic.main.item_detail.*
import android.support.v7.app.AppCompatActivity
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.MobileAds
import kotlinx.android.synthetic.main.activity_detail.*
import kotlinx.android.synthetic.main.activity_detail.view.*


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [DetailFragment.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [DetailFragment.newInstance] factory method to
 * create an instance of this fragment.
 *
 */
class DetailFragment : Fragment() {




    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private var listener: OnFragmentInteractionListener? = null
    lateinit var mAdView : AdView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }






    }





    // create view
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // get root view
        val rootView = inflater.inflate(R.layout.item_detail, container, false)

        // show employee if there is a selection made in recycler view's adapter
        if (CourseAdapter.position != -1) {
            val employee = MainActivity.employees.getJSONObject(CourseAdapter.position)
            // show data in UI
            employee?.let {
                //rootView.nameTextView.text = it.getString("course")
                rootView.titleTextView.text = it.getString("address")
                rootView.emailTextView.text = it.getString("email")
                rootView.phoneTextView.text = it.getString("phone")
                rootView.kuvausTextView.text = it.getString("text")





                val requestOptions = RequestOptions()
                requestOptions.override(500, 500)
                Glide.with(this).load("http://ptm.fi/materials/golfcourses/"+it.getString("image")).apply(requestOptions).into(rootView.imageView)
                val lat = it.getString("lat").toString()
                val lng = it.getString("lng").toString()
                val paikka = it.getString("course").toString()
                val nettiSivu = it.getString("web").toString()

                var linkkiTeksti = rootView.findViewById<TextView>(R.id.linkki)
                linkkiTeksti.text = nettiSivu




                var otsikko = rootView.findViewById<TextView>(R.id.toolbar_titteli)
                otsikko.text = "SGKY: " + it.getString("course")
                //JATKA TÄTÄ




                val linkki = rootView.findViewById<TextView>(R.id.linkki)
                linkki.setOnClickListener(object : View.OnClickListener{
                    override fun onClick(v: View?) {
                        startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(nettiSivu)))
                    }
                })

                val kartan_avaus = rootView.findViewById<TextView>(R.id.kartan_avaus)
                kartan_avaus.setOnClickListener(object : View.OnClickListener{
                    override fun onClick(v: View?) {
                        val intent = Intent(context, MapsActivity::class.java)
                        intent.putExtra("lng", lng)
                        intent.putExtra("lat", lat)
                        intent.putExtra("paikka", paikka)
                        startActivity(intent)
                    }
                })

                //Tuodaan mainokset
                MobileAds.initialize(context, "ca-app-pub-3940256099942544~3347511713")
                mAdView = rootView.findViewById(R.id.adView)
                val adRequest = AdRequest.Builder().build()
                mAdView.loadAd(adRequest)

            }









        }
        // return view
        return rootView
    }

    // TODO: Rename method, update argument and hook method into UI event
    fun onButtonPressed(uri: Uri) {
        listener?.onFragmentInteraction(uri)
    }




    override fun onDetach() {
        super.onDetach()
        listener = null
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     *
     *
     * See the Android Training lesson [Communicating with Other Fragments]
     * (http://developer.android.com/training/basics/fragments/communicating.html)
     * for more information.
     */
    interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        fun onFragmentInteraction(uri: Uri)
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment DetailFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            DetailFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}
