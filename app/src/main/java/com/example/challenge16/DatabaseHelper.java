package com.example.challenge16;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.Nullable;
import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by 지민 on 2017-01-07.
 */

public class DatabaseHelper extends SQLiteOpenHelper
{
    private static final String TAG = "DatabaseHelper";

    private static String DATABASE_NAME;
    private static int DATABASE_VERSION;

    private static String TABLE_NAME;

    private OnDatabaseChangeListener onDatabaseChangeListener;

    public DatabaseHelper(Context context, String databaseName, int databaseVersion, String tableName)
    {
        super(context, databaseName, null, databaseVersion);

        DATABASE_NAME = databaseName;
        DATABASE_VERSION = databaseVersion;

        TABLE_NAME = tableName;
    }

    public void setOnDatabaseChangeListener(OnDatabaseChangeListener listener)
    {
        onDatabaseChangeListener = listener;
    }

    @Override
    public void onCreate(SQLiteDatabase db)
    {
        println("creating table [" + TABLE_NAME +"].");

        try
        {
            String DROP_SQL = "drop table if exists " + TABLE_NAME;
            db.execSQL(DROP_SQL);
        }
        catch(Exception ex)
        {
            Log.e(TAG, "Exception in DROP_SQL", ex);
        }

        String CREATE_SQL = "create table " + TABLE_NAME + "("
                + " key text PRIMARY KEY, "
                + " hour text, "
                + " min text, "
                + " message text, "
                + " weather text )";
        try
        {
            db.execSQL(CREATE_SQL);
        }
        catch (Exception ex)
        {
            Log.e(TAG, "Exception in CREATE_SQL", ex);
        }
    }

    @Override
    public void onOpen(SQLiteDatabase db)
    {
        super.onOpen(db);

        println("opened database [" + DATABASE_NAME + "].");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion)
    {
        Log.w(TAG, "Upgrading database from version " + oldVersion + " to " + newVersion + ".");
    }

    public boolean createRecord(SQLiteDatabase db, ArrayList<DayData> dayDatas)
    {
        println("inserting records.");

        try
        {
            DayData dayData;
            ArrayList<String> selectArray  = new ArrayList<String>();
            selectArray.add("key");
            for(int i = 0; i < dayDatas.size(); i++)
            {
                dayData = dayDatas.get(i);

                Cursor c1 = selectSQL( db, selectArray, dayData.getDay() );
                if( c1.getCount() <= 0 )
                {
                    db.execSQL( "insert into " + TABLE_NAME + "(key, hour, min, message, weather) values ('" + dayData.getDay() + "', '" + dayData.getHour() + "', '" + dayData.getMin() + "', '" +  dayData.getMessage() + "', '" + dayData.getWeather() + "');");
                }
                else
                {
                    db.execSQL( "update " + TABLE_NAME + " set " + "hour='" + dayData.getHour() + "', " + "min='" + dayData.getMin() + "', " + "message='" + dayData.getMessage() + "', " + "weather='" + dayData.getWeather() + "'" + " where key='" + dayData.getDay() + "'");
                }
            }

            selectArray.add("hour");
            selectArray.add("min");
            selectArray.add("message");
            selectArray.add("weather");

            onDatabaseChangeListener.onDataBaseChanged( selectSQL(db, selectArray, "") );

            return true;
        }
        catch(Exception ex)
        {
            Log.e(TAG, "Exception in insert SQL", ex);

            return false;
        }
    }

    public Cursor selectSQL(SQLiteDatabase db, ArrayList<String> selectArray, String where)
    {
        String selectQuery = "";

        selectQuery = selectQuery.concat( "select" );

        for(int i = 0; i < selectArray.size(); i++)
        {
            selectQuery = selectQuery.concat( " " + selectArray.get(i) );

            if( i != selectArray.size()-1)
            {
                selectQuery = selectQuery.concat( "," );
            }
        }

        selectQuery = selectQuery.concat( " from " + TABLE_NAME );

        if( where != null && !where.equals("") )
        {
            selectQuery = selectQuery.concat(" where key = ?");
        }

        return executeRawQueryParam( db, selectQuery, where );
    }

    @Nullable
    private Cursor executeRawQueryParam(SQLiteDatabase db, String query, String where)
    {
        if( db == null )
            return null;

        println("\nexecuteRawQueryParam called.\n");

        String[] args = { where };
        String[] notArgs = {};
        Cursor c1;
        if( where != null && !where.equals("") )
        {
            c1  = db.rawQuery( query, args );
        }
        else
        {
            c1 = db.rawQuery( query, notArgs );
        }

        return c1;
    }

    private void println(String message)
    {
        System.out.println( message );
    }
}
