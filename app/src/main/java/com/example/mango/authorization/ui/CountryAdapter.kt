package com.example.mango.authorization.ui

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.example.mango.R
import com.example.mango.authorization.entities.Country


class CountryAdapter(
    context: Context,
    private val resLocal: Int,
    private val resTextVIew: Int,
    private val countries: List<Country>,
): ArrayAdapter<Country>(context,resLocal,resTextVIew, countries) {


    private val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater


    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
        return getCustomView(position, convertView, parent)
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        return getSelectedView(position, convertView, parent)
    }

    private fun getCustomView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val row: View = inflater.inflate(resLocal, parent, false)
        val currentCountry = countries[position]

        val countryName = row.findViewById(resTextVIew) as TextView
        countryName.text = currentCountry.name

        val code = row.findViewById(R.id.code) as TextView
        code.text = currentCountry.code

        val dialCode = row.findViewById(R.id.dial_code) as TextView
        dialCode.text = currentCountry.dial_code
        return row
    }

    @SuppressLint("MissingInflatedId")
    private fun getSelectedView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val row: View = inflater.inflate(R.layout.country_selected_item, parent, false)
        val currentCountry = countries[position]

        val countryName = row.findViewById(R.id.selected_country_name) as TextView
        countryName.text = currentCountry.name


        return row
    }
}