package com.example.android.justkotlin

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.android.justkotlin.R.id.price_text_view
import com.example.android.justkotlin.R.id.quantity_text_view
import kotlinx.android.synthetic.main.activity_main.*
import java.text.NumberFormat
import java.util.*

/*
 * This app display an order form to order coffee.
 */
class MainActivity : AppCompatActivity() {
    private var quantity:Int = 2

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    /*
     * This method is called when the order button is clicked.
     */
    fun submitOrder(view: View) {
        // displayPrice(quantity*5)
        var price:Int = quantity * 5
        var priceMessage:String = "Total: $$price"
        priceMessage = priceMessage + "\nThank you!"
        displayMessage(priceMessage)
    }

    /*
     * This method is called when the plus button is clicked.
     */
    fun increment(view: View) {
        quantity = quantity + 1
        display(quantity)
    }

    /*
     * This method is called when the minus button is clicked.
     */
    fun decrement(view: View) {
        quantity = quantity - 1
        display(quantity)
    }

    /*
     * This method displays the given quantity value on the screen.
     */
    private fun display(number: Int) {
        quantity_text_view.text = number.toString()
    }

    /*
     * This method displays the given price on the screen.
     */
    private fun displayPrice(number: Int) {
        price_text_view.text = NumberFormat.getCurrencyInstance().format(number)
    }

    /*
     * This method displays the given text on the screen.
     */
    private fun displayMessage(message: String) {
        price_text_view.text = message
    }
}
