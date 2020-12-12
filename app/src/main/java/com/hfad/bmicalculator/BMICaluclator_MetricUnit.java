package com.hfad.bmicalculator;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class BMICaluclator_MetricUnit extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bmi_caluclator__metric_unit);
    }

    public void BMIFormulaMetric(View view)
    {
        EditText cms = (EditText)findViewById(R.id.cm_value);
        Integer cms_int = Integer.parseInt(cms.getText().toString());
        EditText kgs = (EditText)findViewById(R.id.kgsValue);
        Integer kgs_int = Integer.parseInt(kgs.getText().toString());
        float BmiValue = kgs_int * 10000/(cms_int * cms_int);
        TextView BMIValueSet = (TextView)findViewById(R.id.BMIValue);
        BMIValueSet.setText(String.valueOf(BmiValue));
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
    }
}
