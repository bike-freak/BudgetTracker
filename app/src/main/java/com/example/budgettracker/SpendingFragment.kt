package com.example.budgettracker

import android.app.DatePickerDialog
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.ListView
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.core.view.isInvisible
import androidx.core.view.isVisible
import androidx.room.Room
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.time.YearMonth
import java.util.*


private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class SpendingFragment : Fragment() {
    private var param1: String? = null
    private var param2: String? = null

    var day: Int = 0
    var month: Int = 0
    var year: Int =0
    lateinit var yearMonth: YearMonth

    var dateString: String = "DD-MM-YYYY"

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
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_spending, container, false)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val addExpense = view.findViewById<Button>(R.id.addExpense)
        val listView = view.findViewById<ListView>(R.id.expenseList)
        val backButton = view.findViewById<Button>(R.id.backButton)
        var listLabels: List<Labels> = listOf()
        val databaseE = Room.databaseBuilder(view.context,
        ExpensesDatabase::class.java,
        "ExpenseDB").build()

        val databaseL = Room.databaseBuilder(view.context,
        LabelsDatabase::class.java,
        "LabelDB").build()

        backButton.visibility = View.INVISIBLE

        populateYearMonth(listView, databaseE, view)


        GlobalScope.launch {
            listLabels = databaseL.LabelsDAO().getLabels()
        }
        addExpense.setOnClickListener {
            val builder = AlertDialog.Builder(view.context)
            val dialogLayout = layoutInflater.inflate(R.layout.expense_adder,null)
            val dateLabel = dialogLayout.findViewById<TextView>(R.id.dateDisplay)
            val calendarButton = dialogLayout.findViewById<ImageButton>(R.id.calendarBtn)
            val expenseText = dialogLayout.findViewById<EditText>(R.id.expenseAmount)
            var labelSpinner = dialogLayout.findViewById<Spinner>(R.id.labelSpinner)
            builder.setTitle("Expense")
            builder.setMessage("Enter al thee details")
            builder.setView(dialogLayout)

            val cal = Calendar.getInstance()
            day = cal.get(Calendar.DAY_OF_MONTH)
            month = cal.get(Calendar.MONTH)
            year = cal.get(Calendar.YEAR)
            yearMonth = YearMonth.of(year,(month+1))
            dateLabel.text = "$day-${month+1}-$year"

            var spinnerAdapter = SpinnerLabels(view.context, listLabels)
            labelSpinner.adapter = spinnerAdapter

            calendarButton.setOnClickListener {
                val datePickerDialog = DatePickerDialog(view.context,
                        { view, mYear, mMonth, mDay ->
                            dateString = String.format("%d %d, %d",mDay, mMonth, mYear)
                            dateLabel.text = String.format("%d-%d-%d", mDay, mMonth+1, mYear)
                            year=mYear
                            month=mMonth
                            day=mDay
                            yearMonth = YearMonth.of(year,(month+1))
                        }, year, month, day)
                datePickerDialog.show()
            }

            builder.setPositiveButton("Add"){dialogInterface, with->
                if(expenseText.text.isBlank()) {
                    Toast.makeText(view.context,"Task is empty",Toast.LENGTH_LONG).show()
                } else {
                    val label = labelSpinner.selectedView.findViewById<TextView>(R.id.labelItemText).text.toString()
                    val expense = Expenses(null,expenseText.text.toString().toInt(),label,yearMonth.toString(),day,month+1,year)
                    GlobalScope.launch {
                        databaseE.ExpensesDAO().insertExpense(expense)
                    }
                }
            }

            builder.setNeutralButton("Cancel"){dialogLayout, with ->
                Toast.makeText(view.context,"Expense not created",Toast.LENGTH_LONG).show()
            }

            builder.show()
        }

        listView.setOnItemClickListener { parent, view, position, id ->
            val yearMont = view.findViewById<TextView>(R.id.yearMonthView)
            var yearMonth = yearMont.text.toString()
            populateData(listView,databaseE,view,yearMonth)
            backButton.visibility = View.VISIBLE
            Toast.makeText(view.context,"20202020",Toast.LENGTH_LONG).show()
        }

        backButton.setOnClickListener {
            populateYearMonth(listView,databaseE,view)
            backButton.visibility = View.INVISIBLE
        }

    }
    fun populateYearMonth(listView: ListView, databaseE: ExpensesDatabase, view: View){
        databaseE.ExpensesDAO().getYearMonth().observe(viewLifecycleOwner){
            val adapter = ExpensesAdapterYM(view.context,R.layout.expense_ym_view,it)
            listView.adapter = adapter
        }
    }
    fun populateData(listView: ListView,databaseE: ExpensesDatabase,view: View,yearMonth: String){
        databaseE.ExpensesDAO().getExpenses(yearMonth).observe(viewLifecycleOwner){
            val adapter = ExpensesAdapterAll(view.context,R.layout.expense_data,it)
            listView.adapter = adapter
        }
    }
}