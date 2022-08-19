package com.example.jobsfinder.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.jobsfinder.R
import com.example.jobsfinder.ui.model.Ad
import com.example.jobsfinder.ui.my_ads.MyAdsFragmentDirections


class CustomAdapter(private var list: ArrayList<Ad>): RecyclerView.Adapter<CustomAdapter.ViewHolder>() {

    class ViewHolder(view: View): RecyclerView.ViewHolder(view) {
        val titleTextView: TextView
        val cityTextView: TextView
        val salaryTextView: TextView

        init {
            titleTextView = view.findViewById(R.id.ad_title_textView)
            cityTextView = view.findViewById(R.id.ad_city_textView)
            salaryTextView = view.findViewById(R.id.ad_salary_textView)

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.ad_layout, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.titleTextView.text = list[position].title
        holder.cityTextView.text = list[position].city
        holder.salaryTextView.text = list[position].salary.toString()

        holder.itemView.setOnClickListener {
            it.findNavController().navigate(MyAdsFragmentDirections.actionMyAdsFragmentToMyAdsDialogFragment(holder.titleTextView.text.toString(),
            list[position].city, list[position].salary.toString(), list[position].description, list[position].id, list[position].owner))
        }

    }

    override fun getItemCount(): Int {
        return list.size
    }

    fun updateList(data : ArrayList<Ad>){
        list.clear()
        list = data
        notifyDataSetChanged()
    }
}