package com.example.mad_assignment

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DBHelper(context: Context?) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    // below is the method for creating a database by a sqlite query
    override fun onCreate(db: SQLiteDatabase) {
        // below is a sqlite query, where column names
        // along with their data types is given
        val query = ("CREATE TABLE " + TABLE_NAME + " (" +
                TYPE_COL + " TEXT," +
                SPEND_COL + " TEXT" + ")")

        // we are calling sqlite
        // method for executing our query
        db.execSQL(query)
    }

    override fun onUpgrade(db: SQLiteDatabase, p1: Int, p2: Int) {
        // this method is to check if table already exists
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME)
        onCreate(db)
    }

    fun log(text: String){
        val values = ContentValues()
        values.put(SPEND_COL, String())
        writableDatabase.insert(TABLE_NAME,null,values);

    }

    fun getLogs(): Cursor{
        return readableDatabase.query(TABLE_NAME, arrayOf(TYPE_COL, SPEND_COL),null,null,null,null,"DESC")
    }

    // This method is for adding data in our database
    fun addName(type : String, spend : Int ){

        // below we are creating
        // a content values variable
        val values = ContentValues()

        // we are inserting our values
        // in the form of key-value pair
        values.put(TYPE_COL, type)
        values.put(SPEND_COL, spend)

        // here we are creating a
        // writable variable of
        // our database as we want to
        // insert value in our database
        val db = this.writableDatabase

        // all values are inserted into database
        db.insert(TABLE_NAME, null, values)

        // at last we are
        // closing our database
        db.close()
    }

    // below method is to get
    // all data from our database
    fun getName(): Cursor? {

        // here we are creating a readable
        // variable of our database
        // as we want to read value from it
        val db = this.readableDatabase

        // below code returns a cursor to
        // read data from the database
        return db.rawQuery("SELECT * FROM " + TABLE_NAME, null)

    }

    companion object{
        // here we have defined variables for our database

        // below is variable for database name
        private val DATABASE_NAME = "Assigiment"

        // below is the variable for database version
        private val DATABASE_VERSION = 1

        // below is the variable for table name
        val TABLE_NAME = "SPENDING"

        // below is the variable for id column
        val ID_COL = "id"

        // below is the variable for name column
        val TYPE_COL = "type"

        // below is the variable for age column
        val SPEND_COL = "spend"
    }
}

//From https://www.geeksforgeeks.org/android-sqlite-database-in-kotlin/