package com.airline.android.ui.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Spinner
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.airline.android.R
import com.airline.android.ui.MainActivity
import com.airline.android.ui.adapter.FlightsAdapter
import com.airline.android.vm.FlightsViewModel

class FlightsFragment: Fragment() {
    private lateinit var additionalFields: ConstraintLayout
    private lateinit var expandCollapse: ImageButton

    private lateinit var arrivalField: EditText
    private lateinit var departureField: EditText

    private lateinit var sortBySpinner: Spinner
    private lateinit var orderSpinner: Spinner

    private lateinit var search: ImageButton

    private lateinit var flightsList: RecyclerView
    private lateinit var flightsAdapter: FlightsAdapter
    private lateinit var flightsViewModel: FlightsViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
        inflater.inflate(R.layout.flights_fragment_layout, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        additionalFields = view.findViewById(R.id.additional_fields)
        expandCollapse = view.findViewById(R.id.expand_collapse)
        expandCollapse.setOnClickListener {
            if (additionalFields.visibility == View.GONE) {
                additionalFields.visibility = View.VISIBLE
                expandCollapse.setImageResource(R.drawable.ic_arrow_up)
            } else {
                additionalFields.visibility = View.GONE
                expandCollapse.setImageResource(R.drawable.ic_arrow_down)
            }
        }
        orderSpinner = view.findViewById(R.id.order_spinner)
        sortBySpinner = view.findViewById(R.id.sort_by_spinner)
        arrivalField = view.findViewById(R.id.to_field)
        departureField = view.findViewById(R.id.from_field)
        search = view.findViewById(R.id.search)
        search.setOnClickListener(searchClickListener)
        flightsList = view.findViewById(R.id.routes_list)
        flightsViewModel = ViewModelProviders.of(this).get(FlightsViewModel::class.java)
        flightsViewModel.errorCallback = errorCallback
        flightsAdapter = FlightsAdapter(emptyList())
        flightsList.apply {
            layoutManager = LinearLayoutManager(activity)
            itemAnimator = DefaultItemAnimator()
            adapter = flightsAdapter
        }
        flightsViewModel.getAllFlights().observe(this, Observer {
            flightsAdapter.dataset = it
        })
    }
    private val searchClickListener = View.OnClickListener {
        hideKeyboard()
        val mainActivity = activity as MainActivity
        val arrival = arrivalField.text.toString().trim()
        if (arrival.isEmpty()) {
            mainActivity.showSnackbar("To field is empty")
            return@OnClickListener
        }
        var departure: String? = departureField.text.toString().trim()
        if (departure.isNullOrBlank()) departure = null
        val sort = sortBySpinner.selectedItem.toString().toLowerCase()
        val order = orderSpinner.selectedItem.toString().toLowerCase()
        flightsViewModel.searchFlights(departure, arrival, sort, order)
    }
    private val errorCallback: (message: String?) -> Unit = {
        val mainActivity = activity as MainActivity
        mainActivity.showSnackbar(it.toString())
    }
    private fun hideKeyboard() {
        val imm = activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(activity?.currentFocus?.windowToken, 0)
    }
}