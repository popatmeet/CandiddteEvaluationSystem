package com.example.candidateevaluationsystemandroid;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class educational_details extends AppCompatActivity {
    Spinner institute,course,newSpiner;
    Button next2;
    TextInputLayout instituteInputLayout, collegeInputLayout, offieldInputLayout;
    String courseString;
    EditText startyr,passyr,cpi,experience,common;
    FirebaseUser user;
    String instituteText,newspinnerText,startyrText,passyrText,cpiText,experienceText;
    FirebaseFirestore db;
    int expScore[] = {1,2,4,6,8,10,12,14,16,18,20};
    static int score;
    private static final int REQUEST_CODE = 100;
    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_educational_details);
        findViewsById();
        cpi.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                common = cpi;
                final int DRAWABLE_LEFT = 0;
                final int DRAWABLE_TOP = 1;
                final int DRAWABLE_RIGHT = 2;
                final int DRAWABLE_BOTTOM = 3;

                if (event.getAction() == MotionEvent.ACTION_UP) {
                    if (event.getRawX() >= (cpi.getRight() - cpi.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {
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
        startyr.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                common = startyr;
                final int DRAWABLE_LEFT = 0;
                final int DRAWABLE_TOP = 1;
                final int DRAWABLE_RIGHT = 2;
                final int DRAWABLE_BOTTOM = 3;

                if (event.getAction() == MotionEvent.ACTION_UP) {
                    if (event.getRawX() >= (startyr.getRight() - startyr.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {
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
        passyr.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                common = passyr;
                final int DRAWABLE_LEFT = 0;
                final int DRAWABLE_TOP = 1;
                final int DRAWABLE_RIGHT = 2;
                final int DRAWABLE_BOTTOM = 3;

                if (event.getAction() == MotionEvent.ACTION_UP) {
                    if (event.getRawX() >= (passyr.getRight() - passyr.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {
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
        experience.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                common = experience;
                final int DRAWABLE_LEFT = 0;
                final int DRAWABLE_TOP = 1;
                final int DRAWABLE_RIGHT = 2;
                final int DRAWABLE_BOTTOM = 3;

                if (event.getAction() == MotionEvent.ACTION_UP) {
                    if (event.getRawX() >= (experience.getRight() - experience.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {
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
        next2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(validate()) {
                    Map<String, Object> edudetails= new HashMap<>();
                    edudetails.put("Institute",instituteText);
                    edudetails.put("Branch",courseString);
                    edudetails.put("College",newspinnerText);
                    edudetails.put("CPI",cpiText);
                    edudetails.put("Start_Year",startyrText);
                    edudetails.put("Pass_Year",passyrText);
                    edudetails.put("Experience",experienceText);
                    getScore();
                    db.collection("Resumes").document(user.getPhoneNumber()).collection("EducationalDetails").document("1").set(edudetails).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                           if(task.isSuccessful()) {
                               Intent intent = new Intent(educational_details.this, Skills_Specialities.class);
                               intent.putExtra("str", courseString);
                               db.collection("Resumes").document(user.getPhoneNumber()).update("Score",""+score);
                               startActivity(intent);
                           }
                           else{
                               Toast.makeText(educational_details.this, "Error In Server", Toast.LENGTH_SHORT).show();
                           }
                        }
                    });
                }
                else{
                    Toast.makeText(educational_details.this, "Enter Required Fields", Toast.LENGTH_SHORT).show();
                }
            }
        });
        course.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                courseString = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        List<String> institutes = new ArrayList<String>();
        institutes.add("Gujarat Technological University");
        ArrayAdapter arrayAdapter = new ArrayAdapter<>(this,R.layout.support_simple_spinner_dropdown_item,institutes);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        institute.setAdapter(arrayAdapter);

        List<String> offield = new ArrayList<String>();
        offield.add("Computer Engineering");
        offield.add("Mechanical Engineering");
        offield.add("Automobile Engineering");
        ArrayAdapter offieldAdapter = new ArrayAdapter<>(this,R.layout.support_simple_spinner_dropdown_item,offield);
        offieldAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        course.setAdapter(offieldAdapter);


        List<String> college = new ArrayList<String>();
        college.add("Nirma");
        college.add("Indus");
        college.add("LD");
        college.add("VGEC");
        college.add("PDPU");
        college.add("GIT");
        ArrayAdapter collegeAdapter = new ArrayAdapter<>(this,R.layout.support_simple_spinner_dropdown_item,college);
        collegeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        newSpiner.setAdapter(collegeAdapter);
    }

    public void findViewsById(){
        newSpiner = findViewById(R.id.newSpinner);
        institute = (Spinner) findViewById(R.id.input_institute);
        course = (Spinner) findViewById(R.id.input_studyoffield);
        next2 = (Button) findViewById(R.id.next2);
        startyr = findViewById(R.id.startingyear);
        passyr = findViewById(R.id.passingyear);
        cpi = findViewById(R.id.input_cpi);
        experience = findViewById(R.id.yearexperience);
        user = FirebaseAuth.getInstance().getCurrentUser();
        db = FirebaseFirestore.getInstance();
    }
    public boolean validate(){
        instituteText = institute.getSelectedItem().toString();
        courseString = course.getSelectedItem().toString();
        startyrText = startyr.getText().toString();
        passyrText = passyr.getText().toString();
        cpiText = cpi.getText().toString().trim();
        newspinnerText = newSpiner.getSelectedItem().toString();
        experienceText = experience.getText().toString();
       /* if(startyrText.isEmpty()){
            startyr.setError("Required");
            return false;
        }
        if(passyrText.isEmpty()){
            passyr.setError("Required");
            return false;
        }
        if(cpiText.isEmpty()){
            cpi.setError("Required");
            return false;
        }
        if(experienceText.isEmpty()){
            experience.setError("Required");
            return false;
        }*/
        return true;
    }
    public void getScore(){
        score = 0;
        //College Score
        score += 5;
        switch(newspinnerText){
            case "Nirma":
                score += 5;
                break;

            case "LD":
                score += 5;
                break;

            case "VGEC":
                score += 5;
                break;

            case "PDPU":
                score += 5;
                break;
        }
        //CPI Score
        if(Double.parseDouble(cpiText)<=10 && Double.parseDouble(cpiText)>=9){
            score += 20;
        }
        else if(Double.parseDouble(cpiText)<=9 && Double.parseDouble(cpiText)>=8){
            score += 16;
        }
        else if(Double.parseDouble(cpiText)<8 && Double.parseDouble(cpiText)>=7){
            score += 12;
        }
        else if(Double.parseDouble(cpiText)<7 && Double.parseDouble(cpiText)>=6){
            score += 8;
        }
        else if(Double.parseDouble(cpiText)<6 && Double.parseDouble(cpiText)>=5){
            score += 4;
        }
        else if(Integer.parseInt(cpiText)<5){
            score += 0;
        }
        else{
            score += 0;
        }
        //Experience Score
        if(Integer.parseInt(experienceText) >= 11){
            score += 20;
        }
        else if(Integer.parseInt(experienceText)<0){

        }
        else{
            score += expScore[Integer.parseInt(experienceText)];
        }
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
