package com.example.shashank.login;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.InputType;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    Button LogInButton, RegisterButton;
    EditText Email, Password;
    String EmailHolder, PasswordHolder;
    Boolean EditTextEmptyHolder;
    SQLiteDatabase sqLiteDatabaseObj;
    SQLiteHelper sqLiteHelper;
    Cursor cursor;
    String TempPassword = "찾을 수 없습니다.";

    EditText emailEditText;
    public static final String UserEmail = "";

    @SuppressLint("CutPasteId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sqLiteHelper = new SQLiteHelper(this);

        emailEditText = findViewById(R.id.editEmail);

        LogInButton = (Button) findViewById(R.id.buttonLogin);

        RegisterButton = (Button) findViewById(R.id.buttonRegister);

        Email = (EditText) findViewById(R.id.editEmail);
        Password = (EditText) findViewById(R.id.editPassword);

        sqLiteHelper = new SQLiteHelper(this);
        final ImageView togglePassword = findViewById(R.id.togglePassword);
        final EditText editPassword = findViewById(R.id.editPassword);
        final boolean[] isPasswordVisible = {false}; // 초기 상태는 비밀번호가 숨겨진 상태로 설정합니다.

        togglePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isPasswordVisible[0]) {
                    // Hide password
                    editPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    togglePassword.setImageResource(R.drawable.toggle_off);
                } else {
                    // Show password
                    editPassword.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                    togglePassword.setImageResource(R.drawable.toggle_on);
                }
                // Move cursor to the end of the text
                editPassword.setSelection(editPassword.getText().length());

                isPasswordVisible[0] = !isPasswordVisible[0]; // 토글 상태를 변경합니다.
            }
        });
        //Adding click listener to log in button.
        LogInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // Calling EditText is empty or no method.
                CheckEditTextStatus();

                // Calling login method.
                LoginFunction();

            }
        });

        // Adding click listener to register button.
        RegisterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // Opening new user registration activity using intent on button click.
                Intent intent = new Intent(MainActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });

    }

    // Login function starts from here.
    @SuppressLint("Range")
    public void LoginFunction() {
        TempPassword = "찾을 수 없습니다."; // Reset the temporary password

        if (EditTextEmptyHolder) {
            // Opening SQLite database read permission.
            sqLiteDatabaseObj = sqLiteHelper.getReadableDatabase();

            // Adding search email query to cursor.
            cursor = sqLiteDatabaseObj.query(
                    SQLiteHelper.TABLE_NAME,
                    null,
                    " " + SQLiteHelper.Table_Column_2_Email + "=?",
                    new String[]{EmailHolder},
                    null,
                    null,
                    null
            );

            if (cursor.moveToFirst()) {
                // Retrieve the password associated with the entered email.
                TempPassword = cursor.getString(cursor.getColumnIndex(SQLiteHelper.Table_Column_3_Password));
            }

            // Closing cursor.
            cursor.close();

            // Calling method to check final result.
            CheckFinalResult();
        } else {
            // If any of the login EditText fields is empty, display a message.
            Toast.makeText(MainActivity.this, "이메일 또는 비밀번호를 입력해주세요.", Toast.LENGTH_LONG).show();
        }
    }

    // Checking EditText is empty or not.
    public void CheckEditTextStatus() {

        // Getting value from All EditText and storing into String Variables.
        EmailHolder = Email.getText().toString();
        PasswordHolder = Password.getText().toString();

        // Checking EditText is empty or no using TextUtils.
        if (TextUtils.isEmpty(EmailHolder) || TextUtils.isEmpty(PasswordHolder)) {

            EditTextEmptyHolder = false;

        } else {

            EditTextEmptyHolder = true;
        }
    }

    // Checking entered password from SQLite database email associated password.
    // Checking entered password from SQLite database email associated password.
    public void CheckFinalResult() {
        if (TempPassword.equalsIgnoreCase(PasswordHolder)) {
            // Opening SQLite database read permission.
            sqLiteDatabaseObj = sqLiteHelper.getReadableDatabase();

            // Adding search email query to cursor.
            cursor = sqLiteDatabaseObj.query(
                    SQLiteHelper.TABLE_NAME,
                    null,
                    " " + SQLiteHelper.Table_Column_2_Email + "=?",
                    new String[]{EmailHolder},
                    null,
                    null,
                    null
            );

            if (cursor.moveToFirst()) {
                // User found in the database.
                // Move to the desired activity based on user type.
                if (EmailHolder.equalsIgnoreCase("admin")) {
                    // Admin login
                    Toast.makeText(MainActivity.this, "관리자로 로그인되었습니다.", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(MainActivity.this, admin.class);
                    intent.putExtra(UserEmail, EmailHolder);
                    startActivity(intent);
                } else {
                    // Normal user login
                    Toast.makeText(MainActivity.this, "로그인 성공.", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(MainActivity.this, DashboardActivity.class);
                    intent.putExtra(UserEmail, EmailHolder);
                    intent.putExtra("email", EmailHolder);
                    startActivity(intent);
                }
            } else {
                // User not found in the database.
                Toast.makeText(MainActivity.this, "회원 가입되지 않은 이메일입니다.", Toast.LENGTH_LONG).show();
            }

            // Closing cursor.
            cursor.close();
        } else {
            // Password mismatch.
            Toast.makeText(MainActivity.this, "이메일 또는 비밀번호를 다시 입력해주세요.", Toast.LENGTH_LONG).show();
        }

        // Reset the temporary password.
        TempPassword = "찾을 수 없습니다.";
    }

}