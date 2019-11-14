package com.example.golfapplication

import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.example.golfapplication.R
import kotlinx.android.synthetic.main.course_item.view.*
import org.json.JSONObject

class CourseAdapter(
    private val parentActivity: MainActivity,
    private val twoPane: Boolean
    //var kentanNimi: String = ""
) : RecyclerView.Adapter<CourseAdapter.ViewHolder>() {
    private val onClickListener: View.OnClickListener

    // continue here...


    init {
        // initialize click listener code
        onClickListener = View.OnClickListener { v ->
            // get the item position - look also onBindViewHolder code below
            position = v.tag as Int
            // if landscape -> show detail fragment
            if (twoPane) {
                parentActivity.supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.item_detail_container, DetailFragment())
                    .commit()
                // if portrait -> use explicit intent to show employee
            } else {
                val intent = Intent(v.context, DetailActivity::class.java) // LISÄÄ TÄNNE KENTÄN NIMI
                //intent.putExtra(kentanNimi, position)
                v.context.startActivity(intent)
            }
        }
        // show list and detail side by side (on startup)
        if (twoPane) {
            parentActivity.supportFragmentManager
                .beginTransaction()
                .replace(R.id.item_detail_container, DetailFragment())
                .commit()
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CourseAdapter.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(R.layout.course_item, parent, false)
        return ViewHolder(view)
    }

    // return item count in employees
    override fun getItemCount(): Int = MainActivity.employees.length()

    // bind data to UI View Holder
    override fun onBindViewHolder(holder: CourseAdapter.ViewHolder, position: Int) {
        // employee to bind UI
        val employee: JSONObject = MainActivity.employees.getJSONObject(position)
        // name
        holder.nameTextView.text = employee["course"].toString()
        // osoite
        holder.titleTextView.text = employee["address"].toString()
        // email
        holder.emailTextView.text = employee["email"].toString()
        // phone
        holder.phoneTextView.text = employee["phone"].toString()

        var kentanNimi = employee["course"].toString()



        // image
        //val radius = R.dimen.corner_radius
        val requestOptions = RequestOptions()
        requestOptions.override(500,300)
        //requestOptions.centerCrop()
        //requestOptions.transform(RoundedCorners(radius))
        Glide.with(holder.imageView.context).load("http://ptm.fi/materials/golfcourses/"+employee["image"].toString()).apply(requestOptions).into(holder.imageView)
        // add position tag and click listener to holder view
        with(holder.itemView) {
            tag = position
            setOnClickListener(onClickListener)
        }
    }

    // View Holder class to hold UI views
    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val nameTextView: TextView = view.nameTextView
        val titleTextView: TextView = view.titleTextView
        val emailTextView: TextView = view.emailTextView
        val phoneTextView: TextView = view.phoneTextView

        val imageView: ImageView = view.imageView
    }
    companion object {
        var position: Int = 0
    }
}