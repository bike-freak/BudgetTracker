package com.example.budgettracker

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import org.w3c.dom.Text

class LabelsAdapter(var mCtx: Context, var resource:Int, var items: List<Labels>):
    ArrayAdapter<Labels>(mCtx,resource,items){
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val layoutInflater: LayoutInflater = LayoutInflater.from(mCtx)
        val view:View = layoutInflater.inflate(resource,null)

        val labelText = view.findViewById<TextView>(R.id.labelItemText)
        val labelId = view.findViewById<TextView>(R.id.labelId)

        val mItem:Labels = items[position]

        labelText.text = mItem.labelName
        labelId.text = mItem.id.toString()

        return view
    }
}