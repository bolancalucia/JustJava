package com.example.android.justjava;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private int quantity = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TextView quantity = findViewById(R.id.quantity_text_view);
        quantity.setText("1");
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        CheckBox cb1 = findViewById(R.id.checkbox1);
        CheckBox cb2 = findViewById(R.id.checkbox2);
        //EditText editText = findViewById(R.id.edittext);
        outState.putBoolean("checkbox1", cb1.isChecked());
        outState.putBoolean("checkbox2", cb2.isChecked());
        //outState.putInt("quantity", quantity);
        //outState.putString("price", createOrderSummary(cb1.isChecked(),cb2.isChecked(),editText.getText().toString())); //ovo i quantity NE RADEE za landscape
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
    }

    public void submitOrder(View view) {
        CheckBox checkBox1 = findViewById(R.id.checkbox1);
        CheckBox checkBox2 = findViewById(R.id.checkbox2);
        EditText editText = findViewById(R.id.edittext);
        boolean hasWhippedCream = checkBox1.isChecked();
        boolean hasChocolate = checkBox2.isChecked();
        String name = editText.getText().toString();
        String message = createOrderSummary(hasWhippedCream, hasChocolate, name);
        String subject = "JustJava order for " + name;
        String address = "luce0104@gmail.com";

        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:"));
        intent.putExtra(Intent.EXTRA_EMAIL, address);
        intent.putExtra(Intent.EXTRA_SUBJECT, subject);
        intent.putExtra(Intent.EXTRA_TEXT, message);

        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }

    private String createOrderSummary(boolean addWhippedCream, boolean addChocolate, String name) {
        String orderSummary = "Name: " + name;
        orderSummary += "\nQuantity: " + quantity;
        orderSummary += "\nAdd whipped cream? " + addWhippedCream;
        orderSummary += "\nAdd chocolate? " + addChocolate;
        orderSummary += "\nTotal: $" + calculatePrice(addWhippedCream, addChocolate);
        orderSummary += "\n" + getString(R.string.thank_you);
        return orderSummary;
    }

    private int calculatePrice(boolean addWhippedCream, boolean addChocolate) {
        int basePrice = 5;
        if (addWhippedCream)
            basePrice++;
        if (addChocolate)
            basePrice += 2;
        return basePrice * quantity;
    }

    public void increment(View view) {
        if (quantity >= 100) {
            Toast.makeText(this, "You can't have more than 100 cups of coffee!", Toast.LENGTH_SHORT).show();
            return;
        } else
            quantity++;
        displayQuantity(quantity);
    }

    public void decrement(View view) {
        if (quantity <= 1) {
            Toast.makeText(this, "You can't have less than 1 cup of coffee!", Toast.LENGTH_SHORT).show();
            return;
        } else
            quantity--;
        displayQuantity(quantity);
    }

    private void displayQuantity(int number) {
        TextView quantityTextView = findViewById(R.id.quantity_text_view);
        String quantityText = "" + number;
        quantityTextView.setText(quantityText);
    }
}