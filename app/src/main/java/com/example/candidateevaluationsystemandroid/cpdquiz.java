package com.example.candidateevaluationsystemandroid;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.candidateevaluationsystemandroid.Model.Question;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Random;

public class cpdquiz extends AppCompatActivity {

    Button b1,b2,b3,b4;
    TextView timertxt,t1_question;
    CountDownTimer cdt;
    int total = 0;
    int questionnum;
    int correct = 0;
    DatabaseReference reference;

    int wrong = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);
        Toast.makeText(this, "Personality", Toast.LENGTH_SHORT).show();
        b1 = (Button) findViewById(R.id.button1);
        b2 = (Button) findViewById(R.id.button2);
        b3 = (Button) findViewById(R.id.button3);
        b4 =(Button) findViewById(R.id.button4);

        timertxt  = (TextView) findViewById(R.id.timerTxt);
        t1_question = (TextView) findViewById(R.id.questiontxt);

        reverseTimer(10,timertxt);
        updateQuetion();


    }

    private void updateQuetion() {
        Random random = new Random();
        questionnum = random.nextInt(128 - 101) + 101;
        total++;
        if (total >= 10)
        {
            Toast.makeText(this, ""+correct, Toast.LENGTH_SHORT).show();
            Intent result = new Intent(cpdquiz.this,Result.class);
            startActivity(result);
        }
        else {
            reference = FirebaseDatabase.getInstance().getReference().child("Questions").child(String.valueOf(questionnum));
            reference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                    final Question question = dataSnapshot.getValue(Question.class);
                    t1_question.setText(question.getQuestion());
                    b1.setText(question.getOption1());
                    b2.setText(question.getOption2());
                    b3.setText(question.getOption3());
                    b4.setText(question.getOption4());

                    b1.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {


                            if (b1.getText().toString().equals(question.getAnswer()))
                            {

                                b1.setBackgroundColor(Color.GREEN);
                                Handler handler =new Handler();
                                handler.postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        correct++;
                                        b1.setBackgroundColor(Color.parseColor("#03A9f4"));
                                        updateQuetion();
                                        cdt.cancel();
                                        reverseTimer(10,timertxt);
                                    }
                                },1500);
                            }
                            else {

                                wrong++;
                                b1.setBackgroundColor(Color.RED);
                                cdt.cancel();
                                reverseTimer(10,timertxt);
                                if(b2.getText().toString().equals(question.getAnswer()))
                                {
                                    b2.setBackgroundColor(Color.GREEN);

                                }
                                else if (b3.getText().toString().equals(question.getAnswer()))
                                {
                                    b3.setBackgroundColor(Color.GREEN);
                                }
                                else if(b4.getText().toString().equals(question.getAnswer()))
                                {
                                    b4.setBackgroundColor(Color.GREEN);
                                }


                                Handler handler =new Handler();
                                handler.postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        b1.setBackgroundColor(Color.parseColor("#03a9f4"));
                                        b2.setBackgroundColor(Color.parseColor("#03a9f4"));
                                        b3.setBackgroundColor(Color.parseColor("#03a9f4"));
                                        b4.setBackgroundColor(Color.parseColor("#03a9f4"));

                                        updateQuetion();
                                        cdt.cancel();
                                        reverseTimer(30,timertxt);
                                    }
                                },1500);

                            }
                        }
                    });

                    b2.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            if (b2.getText().toString().equals(question.getAnswer()))
                            {
                                b2.setBackgroundColor(Color.GREEN);
                                Handler handler =new Handler();
                                handler.postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        correct++;
                                        b2.setBackgroundColor(Color.parseColor("#03A9f4"));
                                        updateQuetion();
                                        cdt.cancel();
                                        reverseTimer(10,timertxt);
                                    }
                                },1500);
                            }
                            else {

                                wrong++;
                                b2.setBackgroundColor(Color.RED);
                                cdt.cancel();
                                reverseTimer(10,timertxt);
                                if(b1.getText().toString().equals(question.getAnswer()))
                                {
                                    b1.setBackgroundColor(Color.GREEN);

                                }
                                else if (b3.getText().toString().equals(question.getAnswer()))
                                {
                                    b3.setBackgroundColor(Color.GREEN);
                                }
                                else if(b4.getText().toString().equals(question.getAnswer()))
                                {
                                    b4.setBackgroundColor(Color.GREEN);
                                }


                                Handler handler =new Handler();
                                handler.postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        b1.setBackgroundColor(Color.parseColor("#03a9f4"));
                                        b2.setBackgroundColor(Color.parseColor("#03a9f4"));
                                        b3.setBackgroundColor(Color.parseColor("#03a9f4"));
                                        b4.setBackgroundColor(Color.parseColor("#03a9f4"));


                                        updateQuetion();
                                        cdt.cancel();
                                        reverseTimer(10,timertxt);
                                    }
                                },1500);

                            }
                        }
                    });
                    b3.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            if (b3.getText().toString().equals(question.getAnswer()))
                            {
                                b3.setBackgroundColor(Color.GREEN);
                                Handler handler =new Handler();
                                handler.postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        correct++;
                                        b3.setBackgroundColor(Color.parseColor("#03A9f4"));
                                        updateQuetion();
                                        cdt.cancel();
                                        reverseTimer(10,timertxt);
                                    }
                                },1500);
                            }
                            else {

                                wrong++;
                                b3.setBackgroundColor(Color.RED);
                                if(b1.getText().toString().equals(question.getAnswer()))
                                {
                                    b1.setBackgroundColor(Color.GREEN);

                                }
                                else if (b2.getText().toString().equals(question.getAnswer()))
                                {
                                    b2.setBackgroundColor(Color.GREEN);
                                }
                                else if(b4.getText().toString().equals(question.getAnswer()))
                                {
                                    b4.setBackgroundColor(Color.GREEN);
                                }

                                Handler handler =new Handler();
                                handler.postDelayed(new Runnable() {
                                    @Override
                                    public void run() {

                                        b1.setBackgroundColor(Color.parseColor("#03a9f4"));
                                        b2.setBackgroundColor(Color.parseColor("#03a9f4"));
                                        b3.setBackgroundColor(Color.parseColor("#03a9f4"));
                                        b4.setBackgroundColor(Color.parseColor("#03a9f4"));

                                        updateQuetion();
                                        cdt.cancel();
                                        reverseTimer(10,timertxt);
                                    }
                                },1500);

                            }
                        }
                    });

                    b4.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            if (b4.getText().toString().equals(question.getAnswer()))
                            {
                                b4.setBackgroundColor(Color.GREEN);
                                Handler handler =new Handler();
                                handler.postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        correct++;
                                        b4.setBackgroundColor(Color.parseColor("#03A9f4"));
                                        updateQuetion();
                                        cdt.cancel();
                                        reverseTimer(10,timertxt);

                                    }
                                },1500);
                            }
                            else {

                                wrong++;
                                b4.setBackgroundColor(Color.RED);
                                if(b1.getText().toString().equals(question.getAnswer()))
                                {
                                    b1.setBackgroundColor(Color.GREEN);

                                }
                                else if (b2.getText().toString().equals(question.getAnswer()))
                                {
                                    b2.setBackgroundColor(Color.GREEN);
                                }
                                else if(b3.getText().toString().equals(question.getAnswer()))
                                {
                                    b3.setBackgroundColor(Color.GREEN);
                                }

                                Handler handler =new Handler();
                                handler.postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        b1.setBackgroundColor(Color.parseColor("#03a9f4"));
                                        b2.setBackgroundColor(Color.parseColor("#03a9f4"));
                                        b3.setBackgroundColor(Color.parseColor("#03a9f4"));
                                        b4.setBackgroundColor(Color.parseColor("#03a9f4"));

                                        updateQuetion();
                                        cdt.cancel();
                                        reverseTimer(10,timertxt);
                                    }
                                },1500);

                            }
                        }
                    });
                }


                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }
    }
    public void reverseTimer(int seconds,final TextView tv)
    {
        cdt =new  CountDownTimer(seconds * 1000 +1000,1000){

            @Override
            public void onTick(long l) {
                int seconds = (int)(l / 1000);
                int minutes = seconds / 60;
                seconds = seconds % 60;
                tv.setText(String.format("%02d",minutes)
                        + ":" + String.format("%02d",seconds));

            }

            @Override
            public void onFinish() {

                updateQuetion();
                reverseTimer(10,timertxt);

            }
        }.start();
    }
}
