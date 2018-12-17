package com.airline.android.ui.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.airline.android.Const
import com.airline.android.FlightItemClick
import com.airline.android.R
import com.airline.android.ui.CallbackActivity
import com.airline.android.ui.FlightDetailsActivity
import com.airline.android.ui.adapter.FlightsAdapter
import com.airline.android.vm.FlightsViewModel

class FlightsFragment : Fragment() {
    private lateinit var flightsList: RecyclerView
    private lateinit var flightsAdapter: FlightsAdapter
    private lateinit var flightsViewModel: FlightsViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
        inflater.inflate(R.layout.flights_fragment_layout, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        flightsList = view.findViewById(R.id.flights_list)
        flightsViewModel = ViewModelProviders.of(activity!!).get(FlightsViewModel::class.java)
        flightsViewModel.errorCallback = errorCallback
        flightsAdapter = FlightsAdapter(emptyList(), flightDetails)
        flightsList.apply {
            layoutManager = LinearLayoutManager(activity)
            itemAnimator = DefaultItemAnimator()
            isNestedScrollingEnabled = false
            adapter = flightsAdapter
        }
        flightsViewModel.routeId.observe(this, Observer { _ ->
            flightsViewModel.getFlightsForRoute().observe(this, Observer {
                flightsAdapter.dataset = it
            })
        })
    }

    private val flightDetails: FlightItemClick = {
        val intent = Intent(activity, FlightDetailsActivity::class.java)
        intent.putExtra(Const.FLIGHT_ID_KEY, it.flightId)
        activity?.startActivity(intent)
    }

    private val errorCallback: (message: String?) -> Unit = {
        val mActivity = activity as CallbackActivity
        mActivity.showSnackbar(it.toString())
    }
}