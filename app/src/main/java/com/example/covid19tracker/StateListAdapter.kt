package com.example.covid19tracker

import android.content.res.Resources
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import androidx.core.content.ContextCompat
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
        view.tvConfirmedNo.text = SpannableDelta(
                "${currentItem.confirmed}\n ↑${currentItem.deltaconfirmed ?: "0"}",
                currentItem.confirmed?.length ?: 0)

        view.tvActiveNo.text = SpannableDelta(
                "${currentItem.active}\n ↑${currentItem.deltaactive ?: "0"}",
                currentItem.active?.length ?: 0)

        view.tvRecoveredNo.text = SpannableDelta(
                "${currentItem.recovered}\n ↑${currentItem.deltarecovered ?: "0"}",
                currentItem.recovered?.length ?: 0)

        view.tvDeathNo.text = SpannableDelta(
                "${currentItem.deaths}\n ↑${currentItem.deltadeaths ?: "0"}",
                currentItem.deaths?.length ?: 0)

        view.tvStateName.text = currentItem.state

        return view
    }
}