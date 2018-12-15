package com.airline.android.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.airline.android.R
import com.airline.android.RouteItemClick
import com.airline.android.model.Route

class RoutesAdapter(
    dataset: List<Route>,
    private val listener: RouteItemClick
) : RecyclerView.Adapter<RoutesAdapter.ViewHolder>() {
    var dataset = dataset
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(p: ViewGroup, viewType: Int) =
        ViewHolder(LayoutInflater.from(p.context).inflate(R.layout.route_item, p, false))

    override fun getItemCount() = dataset.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.to.text = dataset[position].destinationAirport
        holder.from.text = dataset[position].departureAirport
        holder.duration.text = dataset[position].duration
        holder.price.text = dataset[position].price.toString()
        holder.bind(dataset[position], listener)
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val to: TextView = itemView.findViewById(R.id.route_to_text)
        val from: TextView = itemView.findViewById(R.id.route_from_text)
        val duration: TextView = itemView.findViewById(R.id.route_duration)
        val price: TextView = itemView.findViewById(R.id.route_price)

        fun bind(route: Route, listener: RouteItemClick) {
            itemView.setOnClickListener {
                listener(route)
            }
        }
    }
}
