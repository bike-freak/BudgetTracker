package com.example.budgettracker

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import org.w3c.dom.Text

class ExpensesAdapterAll(var mCtx: Context, var resources: Int, var items: List<Expenses>):
    ArrayAdapter<Expenses>(mCtx,resources,items){
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view: View = LayoutInflater.from(mCtx).inflate(resources,null)

        val dateTextView = view.findViewById<TextView>(R.id.dateTextView)
        val expenseTextView = view.findViewById<TextView>(R.id.expenseTextView)
        val expenseLabelTextView = view.findViewById<TextView>(R.id.expenseLabelTextView)

        val item:Expenses = items[position]

        dateTextView.text = "${item.date}-${item.month}-${item.year}"
        expenseTextView.text = item.expense.toString()
        expenseLabelTextView.text = item.label

        return view
    }

    override fun isEnabled(position: Int): Boolean {
        return false
    }
}