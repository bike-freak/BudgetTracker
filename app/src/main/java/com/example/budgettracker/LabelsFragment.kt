package com.example.budgettracker

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ListView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.view.get
import androidx.room.Room
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class LabelsFragment : Fragment() {
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
        return inflater.inflate(R.layout.fragment_labels, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val labelFAB = view.findViewById<FloatingActionButton>(R.id.addLabelFAB)
        val listView = view.findViewById<ListView>(R.id.labelListView)
        val database = Room.databaseBuilder(view.context,
            LabelsDatabase::class.java,
            "LabelDB").build()

        labelFAB.setOnClickListener {
            val builder = AlertDialog.Builder(view.context)
            val dialogLayout = layoutInflater.inflate(R.layout.label_alert_dialog,null)
            val editText = dialogLayout.findViewById<EditText>(R.id.labelAlertEditText)
            builder.setTitle("Add Label")
            builder.setMessage("Enter a new label for your expenses")
            builder.setView(dialogLayout)

            builder.setPositiveButton("Add"){dialogInterface, with ->
                GlobalScope.launch {
                    database.LabelsDAO().insertLabel(Labels(null,editText.text.toString()))
                }
                Toast.makeText(view.context,"New Label: " + editText.text.toString() + " Created",Toast.LENGTH_LONG).show()
            }
            builder.setNeutralButton("Cancel"){dialogInterface,with ->
                Toast.makeText(view.context,"No Label Added",Toast.LENGTH_LONG).show()
            }

            builder.show()
        }

        database.LabelsDAO().getLabel().observe(viewLifecycleOwner){
            val adapter = LabelsAdapter(view.context,R.layout.label_item,it)
            listView.adapter = adapter
        }

        listView.setOnItemLongClickListener { parent, view, position, id ->

            //val view = parent[position]
            //val label = view.findViewById<TextView>(R.id.labelListView)
            val idNo = view.findViewById<TextView>(R.id.labelId)
            Log.d("Label",position.toString()+idNo.text.toString())
            Toast.makeText(view.context, "long pressed", Toast.LENGTH_SHORT).show()
            GlobalScope.launch {
                database.LabelsDAO().deleteLabel(idNo!!.text.toString().toLong())
            }
            return@setOnItemLongClickListener true
        }

    }
}