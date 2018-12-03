package com.airline.android.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.airline.android.R
import com.airline.android.ui.MainActivity

class HomeFragment: Fragment() {
    private lateinit var routesLink: TextView
    private lateinit var flightsLink: TextView
    private lateinit var ticketsLink: TextView
    private lateinit var profileLink: TextView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View =
        inflater.inflate(R.layout.home_page_layout, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        routesLink = view.findViewById(R.id.routes_link)
        flightsLink = view.findViewById(R.id.flights_link)
        ticketsLink = view.findViewById(R.id.tickets_link)
        profileLink = view.findViewById(R.id.profile_link)

        val mainActivity = activity as MainActivity

        routesLink.setOnClickListener {
            // TODO: actually switch to routes fragment
            mainActivity.showSnackbar("Switching to routes fragment")
        }

        flightsLink.setOnClickListener {
            // TODO: actually switch to flights fragment
            mainActivity.showSnackbar("Switching to flights fragment")
        }

        ticketsLink.setOnClickListener {
            // TODO: actually switch to tickets fragment
            mainActivity.showSnackbar("Switching to tickets fragment")
        }

        profileLink.setOnClickListener {
            // TODO: actually switch to profile fragment
            mainActivity.showSnackbar("Switching to profile fragment")
        }
    }
}
