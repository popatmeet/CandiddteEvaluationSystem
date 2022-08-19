package com.example.candidateevaluationsystemandroid;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.util.Patterns;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class Details extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {
    private EditText dob,mobilenum,name,email,address;
    private  EditText common;
    private Calendar mcalendar;
    private DatePickerDialog datePickerDialog;
    private int day,month,year;
    private static final int REQUEST_CODE = 100;
    FirebaseUser user;
    Button next;
    private FirebaseFirestore db;
    static String mobilenumtext,nameText,dobText,emailText,addressText;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        findViewsById();
        mobilenum.setText(user.getPhoneNumber());
        address.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                common = address;
                final int DRAWABLE_LEFT = 0;
                final int DRAWABLE_TOP = 1;
                final int DRAWABLE_RIGHT = 2;
                final int DRAWABLE_BOTTOM = 3;

                if (event.getAction() == MotionEvent.ACTION_UP) {
                    if (event.getRawX() >= (address.getRight() - address.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {
                        // your action here
                        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
                        try {
                            startActivityForResult(intent, REQUEST_CODE);
                        } catch (ActivityNotFoundException a) {}
                        return true;
                    }
                }
                return false;
            }
        });
        name.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                common = name;
                final int DRAWABLE_LEFT = 0;
                final int DRAWABLE_TOP = 1;
                final int DRAWABLE_RIGHT = 2;
                final int DRAWABLE_BOTTOM = 3;

                if (event.getAction() == MotionEvent.ACTION_UP) {
                    if (event.getRawX() >= (name.getRight() - name.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {
                        // your action here
                        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
                        try {
                            startActivityForResult(intent, REQUEST_CODE);
                        } catch (ActivityNotFoundException a) {}
                        return true;
                    }
                }
                return false;
            }
        });
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(validate()) {
                    Map<String, Object> personalDetails = new HashMap<>();
                    personalDetails.put("Name",nameText);
                    personalDetails.put("Email",emailText);
                    personalDetails.put("DOB",dobText);
                    personalDetails.put("Address",addressText);
                    personalDetails.put("Mobile",mobilenumtext);
                    personalDetails.put("State",true);
                    db.collection("Resumes").document(user.getPhoneNumber()).collection("PersonalDetails").document("1").set(personalDetails);
                    Intent intent = new Intent(Details.this, educational_details.class);
                    startActivity(intent);
                }
                else {
                    Toast.makeText(getApplicationContext(),"Enter Fields Properly",Toast.LENGTH_LONG).show();
                }
            }
        });
        dob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                day=mcalendar.get(Calendar.DAY_OF_MONTH);
                year=mcalendar.get(Calendar.YEAR);
                month=mcalendar.get(Calendar.MONTH);
                datePickerDialog.show();
            }
        });


    }

    @Override
    public void onDateSet(DatePicker view, int myear, int mmonth, int mdayOfMonth) {
        mmonth = mmonth + 1;
        dob.setText(mdayOfMonth + " / "+ mmonth + " / "+myear);
    }

    public void findViewsById(){
        db =FirebaseFirestore.getInstance();
        name = findViewById(R.id.input_name);
        email = findViewById(R.id.input_email);
        address = findViewById(R.id.input_address);
        mcalendar = Calendar.getInstance();
        user = FirebaseAuth.getInstance().getCurrentUser();
        dob = (EditText) findViewById(R.id.input_birthdate);
        next = (Button) findViewById(R.id.next1);
        datePickerDialog = new DatePickerDialog(Details.this, this, day, month, year);
        mobilenum = findViewById(R.id.input_mobilenumber);
    }

    public boolean validate(){
        nameText = name.getText().toString().trim();
        emailText = email.getText().toString().trim();
        dobText = dob.getText().toString().trim();
        addressText = address.getText().toString().trim();
        mobilenumtext = mobilenum.getText().toString().trim();
        if(nameText.matches("^[a-zA-Z\\s]+$")) {
            if (!(nameText.isEmpty())) {

            }
            else{
                name.setError("Name Is Required");
                name.requestFocus();
                return false;
            }
        }
        else{
            return false;
        }
        if(Patterns.EMAIL_ADDRESS.matcher(emailText).matches()){

        }
        else {
            email.setError("Enter Valid Email");
            email.requestFocus();
            return false;
        }
        return true;
    }
    @Override

//Handle the results//

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case REQUEST_CODE: {
                if (resultCode == RESULT_OK && null != data) {
                    ArrayList<String> result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    common.setText(result.get(0));
                }
                break;
            }
        }
    }
}
