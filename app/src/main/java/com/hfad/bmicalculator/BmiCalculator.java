package com.hfad.bmicalculator;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;

public class BmiCalculator extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bmi_calculator);
    }

    public void BmiFormula(View view)
    {
        Log.d("MainActivity2", "BmiFormula() is called");
        EditText feet = (EditText)findViewById(R.id.editTextNumber2);
        Integer feet_converted_inches = Integer.parseInt(feet.getText().toString()) * 12;
        EditText inches = (EditText)findViewById(R.id.cm_value);
        Integer TotalInches = Integer.parseInt(inches.getText().toString()) + feet_converted_inches;
        EditText lbsView = (EditText)findViewById(R.id.kgsValue);
        Integer lbs = Integer.parseInt(lbsView.getText().toString());
        float bmiValue = lbs * 703/(TotalInches * TotalInches);
        TextView set_bmiValue = (TextView) findViewById(R.id.BMIValue);
        set_bmiValue.setText(String.valueOf(bmiValue));
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

    public void metricview(View view)
    {
       boolean on = ((Switch)view).isChecked();
        if(on)
        {
            Intent intent = new Intent(this, BMICaluclator_MetricUnit.class);
            startActivity(intent);
        }
    }
}