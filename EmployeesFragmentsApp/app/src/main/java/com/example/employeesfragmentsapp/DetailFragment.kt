package com.example.employeesfragmentsapp

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import kotlinx.android.synthetic.main.employee_item.view.*


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
        if (EmployeesAdapter.position != -1) {
            val employee = MainActivity.employees.getJSONObject(EmployeesAdapter.position)
            // show data in UI
            employee?.let {
                rootView.nameTextView.text = it.getString("lastName") + " " + it.getString("firstName")
                rootView.titleTextView.text = it.getString("title")
                rootView.emailTextView.text = it.getString("email")
                rootView.phoneTextView.text = it.getString("phone")
                rootView.departmentTextView.text = it.getString("department")
                val requestOptions = RequestOptions()
                requestOptions.override(500, 500)
                Glide.with(this).load(it.getString("image")).apply(requestOptions).into(rootView.imageView)
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
