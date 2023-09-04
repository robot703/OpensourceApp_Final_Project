package com.example.shashank.login;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class SQLiteHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "UserDataBase.db";
    public static final String TABLE_NAME = "UserTable";
    public static final String Table_Column_ID = "id";
    public static final String Table_Column_1_Name = "name";
    public static final String Table_Column_2_Email = "email";
    public static final String Table_Column_3_Password = "password";
    public static final String Table_Column_4_Birthday = "birthday";
    public static final String Table_Column_5_Phone = "phone";

    public SQLiteHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    public Cursor queryUserByEmail(String email) {
        SQLiteDatabase db = this.getReadableDatabase();

        String[] columns = {
                Table_Column_1_Name,
                Table_Column_2_Email,
                Table_Column_4_Birthday,
                Table_Column_5_Phone
        };

        String selection = Table_Column_2_Email + " = ?";
        String[] selectionArgs = { email };

        return db.query(TABLE_NAME, columns, selection, selectionArgs, null, null, null);
    }

    @Override
    public void onCreate(SQLiteDatabase database) {
        String CREATE_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " ("
                + Table_Column_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + Table_Column_1_Name + " TEXT, "
                + Table_Column_2_Email + " TEXT, "
                + Table_Column_3_Password + " TEXT, "
                + Table_Column_4_Birthday + " DATE, "
                + Table_Column_5_Phone + " INTEGER);";
        database.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, int oldVersion, int newVersion) {
        String DROP_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME;
        database.execSQL(DROP_TABLE);
        onCreate(database);
    }

}