package com.airline.android.ui.fragments

import android.content.Context
import android.content.Intent
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
import com.airline.android.Const
import com.airline.android.R
import com.airline.android.RouteItemClick
import com.airline.android.model.Route
import com.airline.android.ui.MainActivity
import com.airline.android.ui.RouteDetailsActivity
import com.airline.android.ui.adapter.RoutesAdapter
import com.airline.android.vm.RoutesViewModel

class RoutesFragment : Fragment() {
    private lateinit var additionalFields: ConstraintLayout
    private lateinit var expandCollapse: ImageButton

    private lateinit var toField: EditText
    private lateinit var fromField: EditText

    private lateinit var sortBySpinner: Spinner
    private lateinit var orderSpinner: Spinner

    private lateinit var search: ImageButton

    private lateinit var routesList: RecyclerView
    private lateinit var routesAdapter: RoutesAdapter
    private lateinit var routesViewModel: RoutesViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
        inflater.inflate(R.layout.routes_fragment_layout, container, false)

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
        toField = view.findViewById(R.id.to_field)
        fromField = view.findViewById(R.id.from_field)

        search = view.findViewById(R.id.search)

        search.setOnClickListener(searchClickListener)

        routesList = view.findViewById(R.id.routes_list)

        routesViewModel = ViewModelProviders.of(this).get(RoutesViewModel::class.java)
        routesViewModel.errorCallback = errorCallback

        routesAdapter = RoutesAdapter(emptyList(), routeDetails)
        routesList.apply {
            layoutManager = LinearLayoutManager(activity)
            itemAnimator = DefaultItemAnimator()
            adapter = routesAdapter
        }

        routesViewModel.getAllRoutes().observe(this, Observer {
            routesAdapter.dataset = it
        })
    }

    private val searchClickListener = View.OnClickListener {
        hideKeyboard()

        val mainActivity = activity as MainActivity

        val toAirport = toField.text.toString().trim()

        if (toAirport.isEmpty()) {
            mainActivity.showSnackbar("To field is empty")
            return@OnClickListener
        }

        var fromAirport: String? = fromField.text.toString().trim()
        if (fromAirport.isNullOrBlank()) fromAirport = null

        val sort = sortBySpinner.selectedItem.toString().toLowerCase()
        val order = orderSpinner.selectedItem.toString().toLowerCase()

        routesViewModel.searchRoutes(fromAirport, toAirport, sort, order)
    }

    private val routeDetails: RouteItemClick = {
        val intent = Intent(activity, RouteDetailsActivity::class.java)
        intent.putExtra(Const.ROUTE_ID_KEY, it.routeId)
        activity?.startActivity(intent)
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
