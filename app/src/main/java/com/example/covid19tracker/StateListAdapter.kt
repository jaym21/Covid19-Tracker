package com.example.covid19tracker

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import kotlinx.android.synthetic.main.list_item.view.*

class StateListAdapter(val list: List<StatewiseItem>?): BaseAdapter() {
    override fun getCount(): Int {
        return list!!.size
    }

    override fun getItem(position: Int): Any {
        return list!![position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        //using if else to check if view is already made then use it else make through LayoutInflater
        val view = convertView ?: LayoutInflater.from(parent?.context).inflate(R.layout.list_item, parent, false)
        val currentItem = list!![position]
        view.tvConfirmedNo.text = currentItem.confirmed
        view.tvActiveNo.text = currentItem.active
        view.tvRecoveredNo.text = currentItem.recovered
        view.tvDeathNo.text = currentItem.deaths
        view.tvStateName.text = currentItem.state

        return view
    }
}