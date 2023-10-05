package com.example.assignment1

import android.app.Activity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.TextView
import kotlin.math.pow

class ResultsActivity: Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result)

        //ToString turns it from String? type to String
        val amount = intent?.extras?.getInt("amount")!!
        val termLength = intent?.extras?.getInt("term")!!
        val interest = intent?.extras?.getDouble("interest")!! / 100
        val amortization = intent?.extras?.getInt("amortization")!!

        val monthly = calculateMonthly(amount, interest, amortization)
        val textResults = findViewById<TextView>(R.id.text_results)
        textResults.text = getString(R.string.results, monthly)

        val totalPaid = monthly * 12 * termLength
        val interestPaid = monthly * interest
        val principalPaid = totalPaid - interestPaid
    }

    private fun calculateMonthly(amount: Int, interest: Double, amortization: Int): Double {
        val numerator = amount * interest
        val denominator = 1 - ( 1 + interest).pow(amortization * -12)
        return (numerator/denominator)
    }

}