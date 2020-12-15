package com.hfad.bmicalculator;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class BMICalculatorDatabaseHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "bmicalculator";
    private static final int DB_VERSION =  1;
    public BMICalculatorDatabaseHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        updateMyDataBase(db,0,DB_VERSION);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            updateMyDataBase(db,oldVersion,DB_VERSION);
    }

    public void updateMyDataBase(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        if ( oldVersion < 1)
        {
            db.execSQL("CREATE TABLE BMI (_ID INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + "USERNAME TEXT, "
                    + "BMIVALUE INTEGER, "
                    + "DATETIME INTEGER);" );
        }

    }

    public static void insertRows( SQLiteDatabase db, String userName, int bmiValue, int dateTime)
    {
        ContentValues contentValues = new ContentValues();
        contentValues.put("USERNAME", userName);
        contentValues.put("BMIVALUE", bmiValue);
        contentValues.put("DATETIME", dateTime);
        db.insert("BMI",null, contentValues);
    }
}
