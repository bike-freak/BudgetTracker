package com.example.budgettracker

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView

class ExpensesAdapterYM(var mCtx: Context, var resource: Int, var items: List<String>):
    ArrayAdapter<String>(mCtx,resource,items){
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val layoutInflater: LayoutInflater = LayoutInflater.from(mCtx)
        val view: View = layoutInflater.inflate(resource,null)

        val yearMonthView = view.findViewById<TextView>(R.id.yearMonthView)

        val mItem:String = items[position]

        yearMonthView.text = mItem

        return view
    }
}