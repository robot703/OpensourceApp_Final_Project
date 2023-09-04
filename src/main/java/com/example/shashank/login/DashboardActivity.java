package com.example.shashank.login;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.io.InputStream;

public class DashboardActivity extends AppCompatActivity {
    String NameHolder, EmailHolder, BirthdayHolder, PhoneHolder;

    TextView nameTextView, emailTextView, birthdayTextView, phoneTextView;
    ImageView ProfileImage;
    SQLiteHelper dbHelper;

    private BottomNavigationView bottomNavigationView;
    private user_graph user_graph;

    @SuppressLint({"SetTextI18n", "Range"})
    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_manual);

        dbHelper = new SQLiteHelper(this);

        bottomNavigationView = findViewById(R.id.bottomNavi);

        ProfileImage = findViewById(R.id.profileImage1);
        ProfileImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent, 1);
            }
        });
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {

            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {

                    case R.id.create:
                        setFrag(2);
                        break;
                }
                return true;
            }
        });

        user_graph = new user_graph();

        ImageButton logout = findViewById(R.id.button2);

        nameTextView = findViewById(R.id.textName);
        emailTextView = findViewById(R.id.textEmail);
        birthdayTextView = findViewById(R.id.textBirthday);
        phoneTextView = findViewById(R.id.textPhone);

        Intent intent = getIntent();
        if (intent.hasExtra("email")) {
            String email = intent.getStringExtra("email");

            Cursor cursor = dbHelper.queryUserByEmail(email);

            if (cursor != null && cursor.moveToFirst()) {
                NameHolder = cursor.getString(cursor.getColumnIndex(SQLiteHelper.Table_Column_1_Name));
                EmailHolder = cursor.getString(cursor.getColumnIndex(SQLiteHelper.Table_Column_2_Email));
                BirthdayHolder = cursor.getString(cursor.getColumnIndex(SQLiteHelper.Table_Column_4_Birthday));
                PhoneHolder = cursor.getString(cursor.getColumnIndex(SQLiteHelper.Table_Column_5_Phone));

                nameTextView.setText("Name: " + NameHolder);
                emailTextView.setText("Email: " + EmailHolder);
                birthdayTextView.setText("Birthday: " + BirthdayHolder);
                phoneTextView.setText("Phone: " + PhoneHolder);
            }
        }

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DashboardActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
                Toast.makeText(DashboardActivity.this, "로그아웃", Toast.LENGTH_LONG).show();
            }
        });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {
                try {
                    InputStream in = getContentResolver().openInputStream(data.getData());

                    Bitmap img = BitmapFactory.decodeStream(in);
                    in.close();

                    ProfileImage.setImageBitmap(img);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else if (resultCode == RESULT_CANCELED) {
                Toast.makeText(this, "사진 선택 취소", Toast.LENGTH_LONG).show();
            }
        }
    }
    private void setFrag(int n) {
        switch (n) {
            case 1:
                break;
            case 2:
                Intent intent = new Intent(DashboardActivity.this, user_graph.class);
                startActivity(intent);
                break;
            case 3:
                // Add code to handle other menu items if needed
                break;
        }
    }


}