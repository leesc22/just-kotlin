package com.example.android.justkotlin

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.CheckBox
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.example.android.justkotlin.R.id.quantity_text_view
import kotlinx.android.synthetic.main.activity_main.quantity_text_view
import android.content.Intent
import android.net.Uri


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
     * This method is called when the plus button is clicked.
     */
    fun increment(view: View) {
        if (quantity == 100) {
            // Show an error message as a toast
            Toast.makeText(this, "You cannot have more than 100 coffees", Toast.LENGTH_SHORT).show()
            // Exit this method early because there's nothing left to do
            return
        }

        quantity += 1
        displayQuantity(quantity)
    }

    /*
     * This method is called when the minus button is clicked.
     */
    fun decrement(view: View) {
        if (quantity == 1) {
            // Show an error message as a toast
            Toast.makeText(this, "You cannot have less than 1 coffee", Toast.LENGTH_SHORT).show()
            // Exit this method early because there's nothing left to do
            return
        }

        quantity -= 1
        displayQuantity(quantity)
    }

    /*
     * This method is called when the order button is clicked.
     */
    fun submitOrder(view: View) {
        // Find the user's name
        val nameField = findViewById<EditText>(R.id.name_field) as EditText
        val name:String = nameField.text.toString()

        // Figure out if the user wants whipped cream topping
        val whippedCreamCheckbox = findViewById<CheckBox>(R.id.whipped_cream_checkbox) as CheckBox
        val hasWhippedCream:Boolean = whippedCreamCheckbox.isChecked

        // Figure out if the user wants chocolate topping
        val chocolateCheckBox = findViewById<CheckBox>(R.id.chocolate_checkbox) as CheckBox
        val hasChocolate:Boolean = chocolateCheckBox.isChecked

        // Calculate the price
        val price:Int = calculatePrice(hasWhippedCream, hasChocolate)

        // Display the order summary on the screen
        val priceMessage:String = createOrderSummary(name, price, hasWhippedCream, hasChocolate)

        // Use an intent to launch an email app.
        // Send the order summary in the email body
        val intent = Intent(Intent.ACTION_SENDTO)
        intent.data = Uri.parse("mailto:") // only email apps should handle this
        intent.putExtra(Intent.EXTRA_SUBJECT, "Just Kotlin order for $name")
        intent.putExtra(Intent.EXTRA_TEXT, priceMessage)
        if (intent.resolveActivity(packageManager) != null) {
            startActivity(intent)
        }
    }

    /**
     * Calculates the price of the order.
     *
     * @param addWhippedCream is whether or not we should include whipped cream topping in the price
     * @param addChocolate    is whether or not we should include chocolate topping in the price
     * @return total price
     */
    private fun calculatePrice(addWhippedCream: Boolean, addChocolate: Boolean):Int {
        // First calculate the price of one cup of coffee
        var basePrice:Int = 5

        // If the user wants whipped cream, add $1 per cup
        if (addWhippedCream) {
            basePrice += 1
        }

        // If the user wants chocolate, add $2 per cup
        if (addChocolate) {
            basePrice += 2
        }

        // Calculate the total order price by multiplying by the quantity
        return quantity * basePrice
    }

    /**
     * Create summary of the order
     *
     * @param name of the customer
     * @param price of the order
     * @param addWhippedCream is whether or not the user wants whipped cream topping
     * @param addChocolate is whether or not the user wants chocolate topping
     * @return text summary
     */
    private fun createOrderSummary(name:String, price: Int, addWhippedCream: Boolean, addChocolate: Boolean):String {
        var priceMessage = "Name: $name"
        priceMessage += "\nAdd whipped cream? $addWhippedCream"
        priceMessage += "\nAdd chocolate? $addChocolate"
        priceMessage += "\nQuantity: $quantity"
        priceMessage += "\nTotal: $$price"
        priceMessage += "\nThank you!"
        return priceMessage
    }

    /*
     * This method displays the given quantity value on the screen.
     */
    private fun displayQuantity(number: Int) {
        quantity_text_view.text = number.toString()
    }
}
