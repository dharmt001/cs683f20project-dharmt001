package com.hfad.bmicalculator;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class BMICaluclator_MetricUnit extends AppCompatActivity {

    private static DecimalFormat df = new DecimalFormat("0.0");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        setContentView(R.layout.activity_bmi_caluclator__metric_unit);

    }

    public void BMIFormulaMetric(View view)
    {
        Log.d("MainActivity2", "BmiFormulaMetric() is called");
        //validating CMs
        EditText cms = (EditText)findViewById(R.id.cm_value);
        if (cms.getText().toString().isEmpty())
        {
            Toast toast = Toast.makeText(this,
                    "CM can not be null ",
                    Toast.LENGTH_LONG);
            toast.show();
            return;
        }
        Integer cms_int = Integer.parseInt(cms.getText().toString());
        if(cms_int > 242 || cms_int < 31)
        {
            Toast toast = Toast.makeText(this,
                    "Greater than 242 CMs and less than 31 CMs not allowed ",
                    Toast.LENGTH_LONG);
            toast.show();
            return;
        }

        // Validating Kgs
        EditText kgs = (EditText)findViewById(R.id.kgsValue);
        if (kgs.getText().toString().isEmpty())
        {
            Toast toast = Toast.makeText(this,
                    "Kgs can not be null ",
                    Toast.LENGTH_LONG);
            toast.show();
            return;
        }
        Float kgs_int = Float.parseFloat(kgs.getText().toString());
        if(kgs_int > 182 || kgs_int < 5)
        {
            Toast toast = Toast.makeText(this,
                    "Greater than 182 Kgs and less than 5 Kgs not allowed ",
                    Toast.LENGTH_LONG);
            toast.show();
            return;
        }
        //calculating BMI
        float BmiValue = kgs_int * 10000/(cms_int * cms_int);
        String bmiValue_s = df.format(BmiValue);
        TextView BMIValueSet = (TextView)findViewById(R.id.BMIValue);
        BMIValueSet.setText(bmiValue_s);
        // setting BMI Category
        TextView CategorySet= (TextView)findViewById(R.id.CategoryValue);
        if (BmiValue < 18.5)
        {
            CategorySet.setText("Under Weight");
        }
        else if (BmiValue >= 18.5 && BmiValue <25)
        {
            CategorySet.setText("Normal");
        }
        else if (BmiValue >= 25 && BmiValue <30)
        {
            CategorySet.setText("Over Weight");
        }
        else
        {
            CategorySet.setText("Obese");
        }

        //ebaling sharing
    }
    //saving in SQLite
    public void saveinDB(View view) {
        EditText userName = (EditText)findViewById(R.id.userNameEdit);
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
    public void shareBMI_m (View view)
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
        String messageText = "Hey check my latest BMI details measured today at " +
                dtf.format(now).toString() + "." + " BMI Value is " + BMIValue_s + " and i am in " +
                categoryValue_s + " category.";
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_TEXT, messageText);
        startActivity(intent);
    }

}
