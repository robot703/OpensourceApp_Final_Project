package com.example.shashank.login;

/**
 * Created by Shank on 14-Feb-18.
 */

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.InputStream;

public class RegisterActivity extends AppCompatActivity {

    EditText Email, Password, Name , Birthday, Phone ;
    Button Register;
    String NameHolder, EmailHolder, PasswordHolder, BirthdayHolder, PhoneHolder;
    Boolean EditTextEmptyHolder;
    SQLiteDatabase sqLiteDatabaseObj;
    String SQLiteDataBaseQueryHolder ;
    SQLiteHelper sqLiteHelper;
    Cursor cursor;
    String F_Result = "찾을 수 없습니다.";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        Register = (Button) findViewById(R.id.buttonRegister1);


        Email = (EditText)findViewById(R.id.editEmail);
        Password = (EditText)findViewById(R.id.editPassword);
        Name = (EditText)findViewById(R.id.editName);
        Birthday = (EditText)findViewById(R.id.editBirthday);
        Phone = (EditText)findViewById(R.id.editPhone);

        sqLiteHelper = new SQLiteHelper(this);

        // Adding click listener to register button.
        Register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // Creating SQLite database if dose n't exists
                SQLiteDataBaseBuild();

                // Creating SQLite table if dose n't exists.
                SQLiteTableBuild();

                // Checking EditText is empty or Not.
                CheckEditTextStatus();

                // Method to check Email is already exists or not.
                CheckingEmailAlreadyExistsOrNot();

                InsertDataIntoSQLiteDatabase();
                F_Result = "Not_Found" ;
                // Empty EditText After done inserting process.
                EmptyEditTextAfterDataInsert();



            }
        });

    }
    // SQLite database build method.
    public void SQLiteDataBaseBuild(){

        sqLiteDatabaseObj = openOrCreateDatabase(SQLiteHelper.DATABASE_NAME, Context.MODE_PRIVATE, null);

    }

    // SQLite table build method.
    // SQLite table build method.
    // SQLite table build method.
    public void SQLiteTableBuild() {
        sqLiteDatabaseObj.execSQL("CREATE TABLE IF NOT EXISTS " + SQLiteHelper.TABLE_NAME + " ("
                + SQLiteHelper.Table_Column_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + SQLiteHelper.Table_Column_1_Name + " TEXT, "
                + SQLiteHelper.Table_Column_2_Email + " TEXT, "
                + SQLiteHelper.Table_Column_3_Password + " TEXT, "
                + SQLiteHelper.Table_Column_4_Birthday + " DATA, "
                + SQLiteHelper.Table_Column_5_Phone + " INTEGER);");
    }

    // Insert data into SQLite database method.
    public void InsertDataIntoSQLiteDatabase(){

        // If editText is not empty then this block will executed.
        if(EditTextEmptyHolder)
        {

            // SQLite query to insert data into table.
            SQLiteDataBaseQueryHolder = "INSERT INTO "+SQLiteHelper.TABLE_NAME+" (name,email,password,birthday,phone) VALUES('"+NameHolder+"', '"+EmailHolder+"', '"+PasswordHolder+"','"+BirthdayHolder+"','"+PhoneHolder+"');";

            // Executing query.
            sqLiteDatabaseObj.execSQL(SQLiteDataBaseQueryHolder);

            // Closing SQLite database object.
            sqLiteDatabaseObj.close();

            Intent intent = new Intent(RegisterActivity.this, DashboardActivity.class);
            intent.putExtra("name", NameHolder);
            intent.putExtra("email", EmailHolder);
            intent.putExtra("birthday", BirthdayHolder);
            intent.putExtra("phone", PhoneHolder);

            startActivity(intent);
            // Printing toast message after done inserting.
            Toast.makeText(RegisterActivity.this,"회원가입이 완료되었습니다.", Toast.LENGTH_LONG).show();
            // Start MainActivity
            startActivity(new Intent(RegisterActivity.this, MainActivity.class));
            // Finish RegisterActivity to prevent navigating back to it
            finish();
        }
        // This block will execute if any of the registration EditText is empty.
        else {

            // Printing toast message if any of EditText is empty.
            Toast.makeText(RegisterActivity.this,"정보를 입력해주시기 바랍니다.", Toast.LENGTH_LONG).show();

        }

    }

    // Empty edittext after done inserting process method.
    public void EmptyEditTextAfterDataInsert(){

        Name.getText().clear();

        Email.getText().clear();

        Password.getText().clear();

        Birthday.getText().clear();

        Phone.getText().clear();

    }

    // Method to check EditText is empty or Not.
    public void CheckEditTextStatus(){

        // Getting value from All EditText and storing into String Variables.
        NameHolder = Name.getText().toString() ;
        EmailHolder = Email.getText().toString();
        PasswordHolder = Password.getText().toString();
        BirthdayHolder = Birthday.getText().toString();
        PhoneHolder = Phone.getText().toString();

        EditTextEmptyHolder = !TextUtils.isEmpty(NameHolder) && !TextUtils.isEmpty(EmailHolder) && !TextUtils.isEmpty(PasswordHolder) && !TextUtils.isEmpty(BirthdayHolder) && !TextUtils.isEmpty(PhoneHolder);
    }

    // Checking Email is already exists or not.
    public void CheckingEmailAlreadyExistsOrNot(){

        // Opening SQLite database write permission.
        sqLiteDatabaseObj = sqLiteHelper.getWritableDatabase();

        // Adding search email query to cursor.
        cursor = sqLiteDatabaseObj.query(SQLiteHelper.TABLE_NAME, null, " " + SQLiteHelper.Table_Column_2_Email + "=?", new String[]{EmailHolder}, null, null, null);

        while (cursor.moveToNext()) {

            if (cursor.isFirst()) {

                cursor.moveToFirst();

                // If Email is already exists then Result variable value set as Email Found.
                F_Result = "Email Found";

                // Closing cursor.
                cursor.close();
            }
        }

        // Calling method to check final result and insert data into SQLite database.
        CheckFinalResult();

    }


    // Checking result
    public void CheckFinalResult(){

        // Checking whether email is already exists or not.
        if(F_Result.equalsIgnoreCase("Email Found"))
        {

            // If email is exists then toast msg will display.
            Toast.makeText(RegisterActivity.this,"Email Already Exists",Toast.LENGTH_LONG).show();

        }
        else {

            // If email already dose n't exists then user registration details will entered to SQLite database.
            InsertDataIntoSQLiteDatabase();

        }

        F_Result = "Not_Found" ;

    }

}