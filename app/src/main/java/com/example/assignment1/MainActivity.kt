package com.example.assignment1

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import kotlinx.coroutines.selects.select

class MainActivity : Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val spinner: Spinner = findViewById(R.id.spinner_term_length)
        // Create an ArrayAdapter using the string array and a default spinner layout.
        ArrayAdapter.createFromResource(
            this,
            R.array.term_lengths,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            // Specify the layout to use when the list of choices appears.
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            // Apply the adapter to the spinner.
            spinner.adapter = adapter
        }
    }

    /*Checks to see if all the user inputs are entered appropriately.
    If it is not entered correctly then it displays an error
    If it is then it goes to the next screen
    */
    fun handleCalculate(v: View){
        //Gets all the user input views
        val editTextAmount = findViewById<EditText>(R.id.edit_number_principal_amount)
        val spinner = findViewById<Spinner>(R.id.spinner_term_length)
        val editTextRate = findViewById<EditText>(R.id.edit_number_interest_rate)
        val editTextAmortization = findViewById<EditText>(R.id.edit_number_amortization)

        //Makes sure the amount is not empty for converting it to int to avoid crashes
        val amountString = editTextAmount.text.toString()
        if (amountString == ""){
            showError(getString(R.string.error_principal_amount))
            return
        }
        val amount = amountString.toInt()

        val selection = spinner.selectedItemPosition
        //Sets the term length to the selection value
        var termLength = selection
        //Sets the term length to 10 if the 8th positioned item (10 years) is selected
        if (termLength == 8)
            termLength = 10

        //Makes sure the interest rate is not empty for converting it to int to avoid crashes
        val interestString = editTextRate.text.toString()
        if (interestString == ""){
            showError(getString(R.string.error_interest_rate))
            return
        }
        val interest = interestString.toDouble()

        //Makes sure the interest rate is not empty for converting it to int to avoid crashes
        val amortizationString = editTextAmortization.text.toString()
        if (amortizationString == ""){
            showError(getString(R.string.error_amortization))
            return
        }
        val amortization = interestString.toInt()

        println("Amount $amount, selection $selection, interest $interest, amortization $amortization")

        if (amount < 20000 || amount > 9000000){
            showError(getString(R.string.error_principal_amount))
            return
        }
        if(termLength == 0){
            showError(getString(R.string.error_term))
            return
        }
        if(interest < 0 || interest > 30){
            showError(getString(R.string.error_interest_rate))
            return
        }
        if(amortization < 0 || amortization > 25){
            showError(getString(R.string.error_amortization))
            return
        }

        //Input is successful go to next screen
        val intent = Intent(this, ResultsActivity::class.java)
        println("Amount is $amount")
        intent.putExtra("amount", amount)
        intent.putExtra("term", termLength)
        intent.putExtra("interest", interest)
        intent.putExtra("amortization", amortization)

        startActivity(intent)
    }

    private fun showError(error: String){
        val textError = findViewById<TextView>(R.id.text_error)
        textError.text = error
    }
}