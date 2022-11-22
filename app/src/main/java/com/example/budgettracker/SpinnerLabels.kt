package com.example.budgettracker

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView

class SpinnerLabels(val mCtx: Context, var data: List<Labels>):BaseAdapter() {
    override fun getCount(): Int {
        return data.size
    }

    override fun getItem(position: Int): Labels {
        return data[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view = LayoutInflater.from(mCtx).inflate(R.layout.label_item,parent,false)

        val labelText = view.findViewById<TextView>(R.id.labelItemText)
        val labelId = view.findViewById<TextView>(R.id.labelId)

        labelText.text = getItem(position).labelName
        labelId.text = getItem(position).id.toString()

        return view
    }

}