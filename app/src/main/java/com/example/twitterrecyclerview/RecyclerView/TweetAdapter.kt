package com.example.twitterrecyclerview.RecyclerView

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.twitterrecyclerview.R
import com.example.twitterrecyclerview.model.Sms


class SmsMessageAdapter(private val smsList: List<Sms>) : RecyclerView.Adapter<SmsMessageAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.single_sms, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val sms = smsList[position]
        holder.address.text = sms.address
        holder.date.text = sms.date
        holder.body.text = sms.body
    }

    override fun getItemCount() = smsList.size

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val address: TextView = itemView.findViewById(R.id.addressTextView)
        val date: TextView = itemView.findViewById(R.id.dateTextView)
        val body: TextView = itemView.findViewById(R.id.bodyTextView)
    }
}


