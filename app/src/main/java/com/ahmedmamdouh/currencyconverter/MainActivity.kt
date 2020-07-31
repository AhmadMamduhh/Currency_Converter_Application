package com.ahmedmamdouh.currencyconverter

import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import java.text.NumberFormat
import java.util.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        // Setting up the currencies menus
        setupSpinners()

        // Conversion operation
        convertButton.setOnClickListener {
            when (valueEditText.text.toString()) {
                "" -> {
                    Toast.makeText(this@MainActivity, R.string.empty_warning, Toast.LENGTH_LONG)
                        .show()
                    outputCardView.visibility = View.GONE
                }
                else -> convertCurrency(valueEditText.text.toString())
            }
        }

    }

    private fun convertCurrency(currency: String) {
        // This method is responsible for converting the currency and displaying the results
        if (currencySpinner1.selectedItem == currencySpinner2.selectedItem) {
            Toast.makeText(this@MainActivity, R.string.same_currency_warning, Toast.LENGTH_LONG)
                .show()
            outputCardView.visibility = View.GONE
        } else {

            if (outputCardView.visibility == View.GONE)
                outputCardView.visibility = View.VISIBLE

            var convertedValue: Double = currency.toDouble()
            when (currencySpinner2.selectedItem) {
                "EGP" -> convertedValue *= 15.9
                "USD" -> convertedValue /= 15.9
            }

            resultsText.text = numToCurrencyFormat(convertedValue)

        }

    }



    private fun numToCurrencyFormat(convertedValue: Double): String {
        val numFormat: NumberFormat = NumberFormat.getCurrencyInstance()
        numFormat.maximumFractionDigits = 2

        // American currency symbol setup
        if(currencySpinner2.selectedItem.toString() == "USD") {
            numFormat.currency = Currency.getInstance(Locale.US)
            return numFormat.format(convertedValue)
        }

        // Egyptian currency symbol setup
        val result = numFormat.format(convertedValue)
        return result.substring(1, result.length) + " LE"

    }

    private fun setupSpinners() {
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter.createFromResource(
            applicationContext,
            R.array.currencies_array,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            currencySpinner1.adapter = adapter
            currencySpinner2.adapter = adapter
        }
        currencySpinner1.setSelection(1)
    }
}