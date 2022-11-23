package com.example.budgettracker

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.fragment.app.findFragment
import androidx.room.Room
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"


class AccountFragment : Fragment() {

    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_account, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val editButton = view.findViewById<Button>(R.id.budgetEdit)
        val confirmButton = view.findViewById<Button>(R.id.confirmBudget)
        val newBudget = view.findViewById<EditText>(R.id.budgetEditText)
        val database = Room.databaseBuilder(view.context,AccountDatabase::class.java,"AccDB").build()
        val spendingText = view.findViewById<TextView>(R.id.spendingAmount)
        editButton.setOnClickListener {
            newBudget.visibility = View.VISIBLE
            confirmButton.visibility = View.VISIBLE
            confirmButton.setOnClickListener {
                if(!(newBudget.text.isEmpty())){
                    GlobalScope.launch {
                        database.AccountDAO()
                            .updateAcc(Account(1, newBudget.text.toString().toLong()))
                    }
                    spendingText.text = newBudget.text.toString()
                } else {
                    newBudget.text.clear()
                }
                newBudget.text.clear()
                newBudget.visibility = View.INVISIBLE
                confirmButton.visibility = View.INVISIBLE
            }
        }
    }

}