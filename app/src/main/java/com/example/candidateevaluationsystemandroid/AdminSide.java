package com.example.candidateevaluationsystemandroid;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class AdminSide extends AppCompatActivity {

    Button btncandidate,btnrequirement;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_side);

        btnrequirement = (Button) findViewById(R.id.buttonrequirement);
        btncandidate = (Button) findViewById(R.id.buttoncandidate);

        btnrequirement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intreq = new Intent(AdminSide.this,Requirement.class);
                startActivity(intreq);
            }
        });

        btncandidate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intcandidate = new Intent(AdminSide.this,Candidatelist.class);
                startActivity(intcandidate);
            }
        });

    }
}
