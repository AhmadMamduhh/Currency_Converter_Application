package com.ahmedmamdouh.currencyconverter

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private val MAX_CURRENCY_VALUE: Double = 999999.0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Setting up the currencies menus
        setupSpinners()

        // Conversion operation
        convertButton.setOnClickListener {
            when (valueEditText.text.toString()) {
                "" -> Toast.makeText(this@MainActivity, R.string.empty_warning, Toast.LENGTH_LONG)
                    .show()
                else -> convertCurrency(valueEditText.text.toString())
            }
        }

    }

    private fun convertCurrency(currency: String) {
        // This method is responsible for converting the currency and displaying the results
        if (currencySpinner1.selectedItem == currencySpinner2.selectedItem)
            Toast.makeText(this@MainActivity, R.string.same_currency_warning, Toast.LENGTH_LONG)
                .show()
        else {
            outputCardView.visibility = View.VISIBLE
            var convertedValue: Double = currency.toDouble()
            when (currencySpinner2.selectedItem) {
                "EGP" -> convertedValue /= 15.9
                "USD" -> convertedValue *= 15.9
            }
            resultsText.text = "${convertedValue.toFloat()} ${currencySpinner2.selectedItem}"


        }


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