package com.airline.android.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.airline.android.R
import com.airline.android.model.Ticket

class TicketsAdapter(dataset: List<Ticket>) : RecyclerView.Adapter<TicketsAdapter.ViewHolder>() {
    var dataset = dataset
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.ticket_item, parent, false))

    override fun getItemCount(): Int = dataset.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val context = holder.view.context
        holder.flightNumber.text = context.getString(R.string.flight_number_text, dataset[position].flightId)
        holder.ticketSeatNumber.text = context.getString(R.string.ticket_seat_number_text, dataset[position].seat)
    }

    class ViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        val flightNumber: TextView = view.findViewById(R.id.flight_number)
        val ticketSeatNumber: TextView = view.findViewById(R.id.ticket_seat_number)
    }
}