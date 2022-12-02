package com.example.countrylist.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.countrylist.databinding.CountryItemBinding
import com.example.countrylist.models.Country

class CountryRecyclerViewAdapter(private val countries: List<Country>)
    : RecyclerView.Adapter<CountryRecyclerViewAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
       return ViewHolder(
           CountryItemBinding.inflate(
               LayoutInflater.from(parent.context),
               parent,
               false
           )
       )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val country = countries[position]

        val name = country.name ?: ""
        if (name.isEmpty()) {
             return
        }
        val capital = country.capital ?: ""
        val region = country.region ?: ""
        val code = country.code ?: ""

        holder.countryCode.text = code
        holder.countryCapital.text = capital
        holder.countryName.text = String.format("%s, %s", name, region)
    }

    override fun getItemCount(): Int {
        return countries.size
    }

    inner class ViewHolder(binding: CountryItemBinding)
        : RecyclerView.ViewHolder(binding.root) {

        val countryName: TextView = binding.countryName
        val countryCapital: TextView = binding.countryCapital
        val countryCode: TextView = binding.countryCode
    }
}