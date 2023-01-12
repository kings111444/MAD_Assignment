package com.example.mad_assignment

import android.app.Application
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.mad_assignment.DBHelper
import kotlin.properties.Delegates

class MainActivity : Application() {

    var dbHelper: DBHelper by Delegates.notNull()

    override fun onCreate() {
        //intantiate the db-helper when the app is created
        dbHelper = DBHelper(this)
    }

}

