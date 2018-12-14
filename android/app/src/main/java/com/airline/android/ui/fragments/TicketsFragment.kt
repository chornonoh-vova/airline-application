package com.airline.android.ui.fragments

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
import com.airline.android.R
import com.airline.android.ui.MainActivity
import com.airline.android.ui.adapter.TicketsAdapter
import com.airline.android.vm.TicketsViewModel

class TicketsFragment : Fragment() {
    private lateinit var ticketsList: RecyclerView
    private lateinit var ticketsAdapter: TicketsAdapter
    private lateinit var viewModel: TicketsViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.tickets_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        ticketsList = view.findViewById(R.id.tickets_list)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(TicketsViewModel::class.java)
        viewModel.errorCallback = errorCallback

        ticketsAdapter = TicketsAdapter(emptyList())

        ticketsList.apply {
            layoutManager = LinearLayoutManager(activity)
            itemAnimator = DefaultItemAnimator()
            adapter = ticketsAdapter
        }

        viewModel.getAllTickets().observe(this, Observer {
            ticketsAdapter.dataset = it
        })
    }

    private val errorCallback: (message: String?) -> Unit = {
        val mainActivity = activity as MainActivity
        mainActivity.showSnackbar(it.toString())
    }
}
