package com.airline.android.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.airline.android.R
import com.airline.android.model.Flight

class FlightsAdapter(dataset: List<Flight>) : RecyclerView.Adapter<FlightsAdapter.ViewHolder>() {
    var dataset = dataset
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(p: ViewGroup, viewType: Int) =
        ViewHolder(LayoutInflater.from(p.context).inflate(R.layout.flight_item, p, false))

    override fun getItemCount() = dataset.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.departure.text = dataset[position].departureTime
        holder.arrival.text = dataset[position].arrivalTime
        holder.status.text = dataset[position].cancellationStatus.toString()
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val departure: TextView = itemView.findViewById(R.id.flight_departure_text)
        val arrival: TextView = itemView.findViewById(R.id.flight_arrival_text)
        val status: TextView = itemView.findViewById(R.id.flight_cancellation)
    }
}