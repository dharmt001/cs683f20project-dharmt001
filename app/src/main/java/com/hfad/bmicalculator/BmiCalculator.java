package com.hfad.bmicalculator;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class BmiCalculator extends AppCompatActivity {

    private static DecimalFormat df = new DecimalFormat("0.0");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bmi_calculator);
    }

    public void BmiFormula(View view)
    {
        Log.d("MainActivity2", "BmiFormula() is called");
        //validating feet
        EditText feet = (EditText)findViewById(R.id.editTextNumber2);
        if (feet.getText().toString().isEmpty())
        {
            Toast toast = Toast.makeText(this,
                    "feet can not be null ",
                    Toast.LENGTH_LONG);
            toast.show();
            return;
        }
        Integer feet_converted_inches = Integer.parseInt(feet.getText().toString()) * 12;
        if(feet_converted_inches > 84)
        {
            Toast toast = Toast.makeText(this,"Greater than 7 feet not allowed ",
                    Toast.LENGTH_LONG);
            toast.show();
            return;
        }
        //validating Inches
        EditText inches = (EditText)findViewById(R.id.cm_value);
        if (inches.getText().toString().isEmpty())
        {
            Toast toast = Toast.makeText(this,
                    "inches can not be null ",
                    Toast.LENGTH_LONG);
            toast.show();
            return;
        }
        Integer inches_int = Integer.parseInt(inches.getText().toString());
        if (inches_int >11)
        {
            Toast toast = Toast.makeText(this,
                    "Greater than 11 inches not allowed, use feet and inches ",
                    Toast.LENGTH_LONG);
            toast.show();
            return;
        }
        //validating total height
        Integer TotalInches = inches_int  + feet_converted_inches;
        if (TotalInches < 12)
        {
            Toast toast = Toast.makeText(this,"Height less than 12 inches not allowed ",
                    Toast.LENGTH_LONG);
            toast.show();
            return;
        }
        //validating pounds
        EditText lbsView = (EditText)findViewById(R.id.kgsValue);
        if (lbsView.getText().toString().isEmpty())
        {
            Toast toast = Toast.makeText(this,
                    "lbs can not be null ",
                    Toast.LENGTH_LONG);
            toast.show();
            return;
        }
        Float lbs = Float.parseFloat(lbsView.getText().toString());
        if (lbs > 400 || lbs < 10)
        {
            {
                Toast toast = Toast.makeText(this,
                        " Lbs should be less than 400 pounds or greater than 10 pounds",
                        Toast.LENGTH_LONG);
                toast.show();
            }
        }
        //calculating BMI
        float bmiValue = lbs * 703/(TotalInches * TotalInches);
        String bmiValue_s = df.format(bmiValue);
        TextView set_bmiValue = (TextView) findViewById(R.id.BMIValue);
        set_bmiValue.setText(bmiValue_s);
        //setting BMI Category
        TextView set_category = (TextView)findViewById(R.id.CategoryValue);
        if (bmiValue < 18.5)
        {
            set_category.setText("Under Weight");
        }
        else if (bmiValue >= 18.5 && bmiValue <25)
        {
            set_category.setText("Normal");
        }
        else if (bmiValue >= 25 && bmiValue <30)
        {
            set_category.setText("Over Weight");
        }
        else
        {
            set_category.setText("Obese");
        }

    }
    
    //Trigger metric view
    public void metricview(View view)
    {
       boolean on = ((Switch)view).isChecked();
        if(on)
        {
            Intent intent = new Intent(this, BMICaluclator_MetricUnit.class);
            startActivity(intent);
        }
    }

    // save data in SQLite
    public void saveinDB0(View view) {
        EditText userName = (EditText)findViewById(R.id.editUserName0);
        String name = userName.getText().toString();
        TextView BMIValueSet = (TextView)findViewById(R.id.BMIValue);
        String BMIValue_s = BMIValueSet.getText().toString();
        Integer dateTime = (int)(new Date().getTime()/1000);
        if(BMIValue_s.isEmpty())
        {
            Toast.makeText(this, "For Saving BMI Value is required",
                    Toast.LENGTH_LONG).show();
        }
        else if (name.isEmpty())
        {
            Toast toast = Toast.makeText(this, "For Saving User Name is required", Toast.LENGTH_SHORT);
            toast.show();
        }
        else {
            Integer BMIValue = (int) Float.parseFloat(BMIValue_s);
            SQLiteOpenHelper bmiCalculatorDatabaseHelper =
                    new BMICalculatorDatabaseHelper(this);
            try{
                SQLiteDatabase db = bmiCalculatorDatabaseHelper.getWritableDatabase();
                BMICalculatorDatabaseHelper.insertRows(db, name, BMIValue, dateTime) ;
                Log.d("DBActivity", "Successfully inserted a row");
                db.close();
                Toast toast = Toast.makeText(this, name + " "+"your data got saved ",
                        Toast.LENGTH_LONG);
                toast.show();
            } catch (SQLException e) {
                Toast toast = Toast.makeText(this, "Database is Unavailable",
                        Toast.LENGTH_SHORT);
                toast.show();
            }

        }
    }
     // sharing BMI to friends
    public void shareBMI (View view)
    {
        TextView BMIValueSet = (TextView)findViewById(R.id.BMIValue);
        String BMIValue_s = BMIValueSet.getText().toString();
        if(BMIValue_s.isEmpty())
        {
            Toast.makeText(this, "For Sharing BMI Value is required",
                    Toast.LENGTH_LONG).show();
            return;
        }
        TextView categoryValue = (TextView)findViewById(R.id.CategoryValue);
        String categoryValue_s = categoryValue.getText().toString();
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        String messageText = "Hey check my BMI Details measured today at " +
                dtf.format(now).toString() + "." + " BMI Value is " + BMIValue_s + " and i am in " +
                categoryValue_s + " category.";
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_TEXT, messageText);
        startActivity(intent);
    }
}