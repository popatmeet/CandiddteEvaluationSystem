package com.example.candidateevaluationsystemandroid;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class Skills_Specialities extends AppCompatActivity {

    final String[] select_skills_CE  = {"AI","Machine Learning","Android","Java","Computer Networking","Computer Security","CyberSecurity","Cryptography","React Native","BlockChain","Augmented Reality","Cloud Computing","Angular & React Database","DevOps","IOT","Big Data","RPA"};
    final String[] select_skills_ME  = {"Refrigeration & Air Conditioning","Heat Transfer","Fluid Mechanics","Thermodynamics"};
    final String[] select_skills_PE  = {"Industrial Sociology","Industroial Chemistry","Elective","Measurement & Metrology"};
    final String[] Hobbies  = {"Hobbies","Reading & Writing","Swimming","Playing Games","Cycling"};

    ArrayList<spinner_item> list = new ArrayList<>();
    AlertDialog.Builder builder;
    String text;
    Button submit;
    CheckBox checkBox1;
    CheckBox checkBox2;
    CheckBox checkBox3;
    CheckBox checkBox4;
    CheckBox checkBox5;
    CheckBox checkBox6;
    CheckBox checkBox7;
    CheckBox checkBox8;
    CheckBox checkBox9;
    CheckBox checkBox10;
    CheckBox checkBox11;
    CheckBox checkBox12;
    CheckBox checkBox13;
    CheckBox checkBox14;
    FirebaseUser user;
    FirebaseFirestore db;
    ArrayList<String> skills;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_skills__specialities);
        findViewsById();
        Intent intent = getIntent();
        text = intent.getStringExtra("str");

        if(text.equals("Computer Engineering")) {
            checkBox1.setText(select_skills_CE[0]);
            checkBox2.setText(select_skills_CE[1]);
            checkBox3.setText(select_skills_CE[2]);
            checkBox4.setText(select_skills_CE[3]);
            checkBox5.setText(select_skills_CE[4]);
            checkBox6.setText(select_skills_CE[5]);
            checkBox7.setText(select_skills_CE[6]);
            checkBox8.setText(select_skills_CE[7]);
            checkBox9.setText(select_skills_CE[8]);
            checkBox10.setText(select_skills_CE[9]);
            checkBox11.setText(select_skills_CE[10]);
            checkBox12.setText(select_skills_CE[11]);
            checkBox13.setText(select_skills_CE[12]);
            checkBox14.setText(select_skills_CE[13]);
        }
        else if(text.equals("Mechanical Engineering")) {
            checkBox1.setText(select_skills_ME[0]);
            checkBox2.setText(select_skills_ME[1]);
            checkBox3.setText(select_skills_ME[2]);
            checkBox4.setText(select_skills_ME[3]);
            checkBox5.setText(select_skills_ME[4]);
            checkBox6.setText(select_skills_ME[5]);
            checkBox7.setText(select_skills_ME[6]);
            checkBox8.setText(select_skills_ME[7]);
            checkBox9.setText(select_skills_ME[8]);
            checkBox10.setText(select_skills_ME[9]);
            checkBox11.setText(select_skills_ME[10]);
            checkBox12.setText(select_skills_ME[11]);
            checkBox13.setText(select_skills_ME[12]);
            checkBox14.setText(select_skills_ME[13]);
        }
        else if(text.equals("Plastic Engineering")) {
            checkBox1.setText(select_skills_PE[0]);
            checkBox2.setText(select_skills_PE[1]);
            checkBox3.setText(select_skills_PE[2]);
            checkBox4.setText(select_skills_PE[3]);
            checkBox5.setText(select_skills_PE[4]);
            checkBox6.setText(select_skills_PE[5]);
            checkBox7.setText(select_skills_PE[6]);
            checkBox8.setText(select_skills_PE[7]);
            checkBox9.setText(select_skills_PE[8]);
            checkBox10.setText(select_skills_PE[9]);
            checkBox11.setText(select_skills_PE[10]);
            checkBox12.setText(select_skills_PE[11]);
            checkBox13.setText(select_skills_PE[12]);
            checkBox14.setText(select_skills_PE[13]);
        }

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getScore();
                builder = new AlertDialog.Builder(Skills_Specialities.this);
                builder.setMessage("Warning!");
                builder.setMessage("AFTER PRESSING OK YOU WON'T BE ABLE TO EDIT DETAILS").setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Map<String, Object> skill= new HashMap<>();
                        skill.put("Skills",skills);
                        Random random = new Random();
                        String id = String.format("%04d", random.nextInt(10000));
                        Map<String, Object> otp = new HashMap<>();
                        otp.put("Code",id);
                        db.collection("Resumes").document(user.getPhoneNumber()).update("Score",""+educational_details.score);
                       // db.collection("Resumes").document(user.getPhoneNumber()).update("Contact",user.getPhoneNumber());
                        db.collection("Resumes").document(user.getPhoneNumber()).update("State",true);
                        db.collection("Resumes").document(user.getPhoneNumber()).set(otp, SetOptions.merge());
                        db.collection("Resumes").document(user.getPhoneNumber()).collection("SkillDetails").document("1").set(skill);

                        FirebaseAuth.getInstance().signOut();
                        Intent intent1 = new Intent(Skills_Specialities.this,Home.class);
                        intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent1);
                    }
                }).setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                AlertDialog alert = builder.create();
                alert.setIcon(R.drawable.alert);
                alert.show();
            }
        });

        for (int i = 0; i < Hobbies.length; i++) {
            spinner_item spinner_item = new spinner_item();
            spinner_item.setTitle(Hobbies[i]);
            spinner_item.setSelected(false);
            list.add(spinner_item);
        }
    }
    public void findViewsById(){
        checkBox1 = (CheckBox) findViewById(R.id.checkBox1);
        checkBox2 = (CheckBox) findViewById(R.id.checkBox2);
        checkBox3 = (CheckBox) findViewById(R.id.checkBox3);
        checkBox4 = (CheckBox) findViewById(R.id.checkBox4);
        checkBox5 = (CheckBox) findViewById(R.id.checkBox5);
        checkBox6 = (CheckBox) findViewById(R.id.checkBox6);
        checkBox7 = (CheckBox) findViewById(R.id.checkBox7);
        checkBox8 = (CheckBox) findViewById(R.id.checkBox8);
        checkBox9 = (CheckBox) findViewById(R.id.checkBox9);
        checkBox10 = (CheckBox) findViewById(R.id.checkBox10);
        checkBox11 = (CheckBox) findViewById(R.id.checkBox11);
        checkBox12 = (CheckBox) findViewById(R.id.checkBox12);
        checkBox13 = (CheckBox) findViewById(R.id.checkBox13);
        checkBox14 = (CheckBox) findViewById(R.id.checkBox14);
        user = FirebaseAuth.getInstance().getCurrentUser();
        skills = new ArrayList<String>();
        db = FirebaseFirestore.getInstance();
        submit = findViewById(R.id.submit1);
    }
    public void getScore(){
        if(checkBox1.isChecked()){
            educational_details.score += 2;
            skills.add(checkBox1.getText().toString());
        }
        if(checkBox2.isChecked()){
            educational_details.score += 2;
            skills.add(checkBox2.getText().toString());
        }
        if(checkBox3.isChecked()){
            educational_details.score += 2;
            skills.add(checkBox3.getText().toString());
        }
        if(checkBox4.isChecked()){
            educational_details.score += 2;
            skills.add(checkBox4.getText().toString());
        }
        if(checkBox5.isChecked()){
            educational_details.score += 2;
            skills.add(checkBox5.getText().toString());
        }
        if(checkBox6.isChecked()){
            educational_details.score += 2;
            skills.add(checkBox6.getText().toString());
        }
        if(checkBox7.isChecked()){
            educational_details.score += 2;
            skills.add(checkBox7.getText().toString());
        }
        if(checkBox8.isChecked()){
            educational_details.score += 2;
            skills.add(checkBox8.getText().toString());
        }
        if(checkBox9.isChecked()){
            educational_details.score += 2;
            skills.add(checkBox9.getText().toString());
        }
        if(checkBox10.isChecked()){
            educational_details.score += 2;
            skills.add(checkBox10.getText().toString());
        }
        if(checkBox11.isChecked()){
            educational_details.score += 2;
            skills.add(checkBox11.getText().toString());
        }
        if(checkBox12.isChecked()){
            educational_details.score += 2;
            skills.add(checkBox12.getText().toString());
        }
        if(checkBox13.isChecked()){
            educational_details.score += 2;
            skills.add(checkBox13.getText().toString());
        }
        if(checkBox14.isChecked()){
            educational_details.score += 2;
            skills.add(checkBox14.getText().toString());
        }
    }
}

