package com.example.aks.echo.Databases

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.aks.echo.Songs

/**
 * Created by aks on 16/12/17.
 */

/*
    SQliteHelper class provides utilities to simplify the task of creating and initializing
    the database and converting the contents and schemas of database
 */

class EchoDatabase : SQLiteOpenHelper{

    var _songList = ArrayList<Songs>()




    object Staticated{
        val DB_VERSION = 1
        val DB_NAME = "FavoriteDatabase"
        val TABLE_NAME = "FavoriteTable"
        val COLUMN_ID = "SongID"
        val COLUMN_SONG_TITLE = "SongTitle"
        val COLUMN_SONG_ARTIST = "SongArtist"
        val COLUMN_SONG_PATH = "SongPath"
    }

    // when we create the table and name the columns
    override fun onCreate(sqliteDatabase: SQLiteDatabase?) {
        sqliteDatabase?.execSQL("CREATE TABLE " + Staticated.TABLE_NAME + " ( " + Staticated.COLUMN_ID + " INTEGER, " + Staticated.COLUMN_SONG_ARTIST + " STRING, "
                + Staticated.COLUMN_SONG_TITLE + " STRING, " + Staticated.COLUMN_SONG_PATH + " STRING);")

    }

    // When we upgrade our app or add new columns to our table
    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    constructor(context: Context?, name: String?, factory: SQLiteDatabase.CursorFactory?, version: Int) : super(context, name, factory, version)
    constructor(context: Context?) : super(context, Staticated.DB_NAME, null, Staticated.DB_VERSION)


    // Function to insert values in the database
    fun storeAsFavorite(id: Int?, artist: String?, songTitle: String?, path: String?){
        val db = this.writableDatabase
        var contentValues = ContentValues()
        contentValues.put(Staticated.COLUMN_ID, id)
        contentValues.put(Staticated.COLUMN_SONG_ARTIST, artist)
        contentValues.put(Staticated.COLUMN_SONG_TITLE, songTitle)
        contentValues.put(Staticated.COLUMN_SONG_PATH, path)
        db.insert(Staticated.TABLE_NAME, null, contentValues)
        db.close()
    }

    fun queryDBList() : ArrayList<Songs>? {
        try {
            val db = this.readableDatabase
            val query_params = "select * from " + Staticated.TABLE_NAME
            val cSor = db.rawQuery(query_params, null)      // Cursor object

            if(cSor.moveToFirst()){
                do{
                    var _id = cSor.getInt(cSor.getColumnIndexOrThrow(Staticated.COLUMN_ID))
                    var _artist = cSor.getString(cSor.getColumnIndexOrThrow(Staticated.COLUMN_SONG_ARTIST))
                    var _title = cSor.getString(cSor.getColumnIndexOrThrow(Staticated.COLUMN_SONG_TITLE))
                    var _songPath = cSor.getString(cSor.getColumnIndexOrThrow(Staticated.COLUMN_SONG_PATH))

                    _songList.add(Songs(_id.toLong(), _title, _artist, _songPath, 0))

                }while (cSor.moveToNext())
            }else {
                return null
            }

        } catch (e: Exception) {
            e.printStackTrace()
        }
        return _songList
    }

    fun checkifIdExists(_id: Int): Boolean{
        var storeId = -1090
        val db = this.readableDatabase
        val query_params = "SELECT * FROM " + Staticated.TABLE_NAME + " WHERE SongId = '$_id'"
        val cSor = db.rawQuery(query_params, null)
        if(cSor.moveToFirst()){
            do{
                storeId = cSor.getInt(cSor.getColumnIndexOrThrow(Staticated.COLUMN_ID))
            }while (cSor.moveToNext())
        }else {
            return false
        }
        return storeId != -1090
    }

    fun deleteFavorite(_id: Int) {
        val db = this.writableDatabase
        db.delete(Staticated.TABLE_NAME, Staticated.COLUMN_ID + " = " + _id, null)
        db.close()
    }

    fun checkSize(): Int{
        var counter = 0
        val db = this.readableDatabase
        val query_params = "SELECT * FROM " + Staticated.TABLE_NAME
        val cSor = db.rawQuery(query_params, null)
        if(cSor.moveToFirst()){
            do{
                counter++
            }while (cSor.moveToNext())
        }else {
            return 0
        }
        return counter
    }
}