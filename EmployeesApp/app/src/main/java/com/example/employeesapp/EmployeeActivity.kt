package com.example.employeesapp

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import kotlinx.android.synthetic.main.activity_employee.*
import org.json.JSONObject

class EmployeeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_employee)

        // get data from intent
        val bundle: Bundle? = intent.extras
        if (bundle != null) {
            val employeeString = bundle.getString("employee")
            val employee = JSONObject(employeeString)
            val name = employee["lastName"].toString() + " " + employee["firstName"].toString()
            val title = employee["title"].toString()
            val email = employee["email"].toString()
            val phone = employee["phone"].toString()
            val department = employee["department"].toString()

            val url = employee["image"].toString()

           Glide.with(employeeImageView.context).load(url).into(employeeImageView)

            //Toast.makeText(this, "Name is $name",Toast.LENGTH_LONG).show()
            nameTextView2.text = name
            emailTextView2.text = email
            phoneTextView2.text = phone
            departmentTextView2.text = department
            titleTextView2.text = title
        }


    }
}
