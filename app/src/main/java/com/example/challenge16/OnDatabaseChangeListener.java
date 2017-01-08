package com.example.challenge16;

import android.database.Cursor;

import java.util.ArrayList;

/**
 * Created by 지민 on 2017-01-08.
 */

public interface OnDatabaseChangeListener
{
    public void onDataBaseChanged(Cursor cursor);
}
