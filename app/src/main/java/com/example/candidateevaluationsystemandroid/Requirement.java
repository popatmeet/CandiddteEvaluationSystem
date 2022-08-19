package com.example.candidateevaluationsystemandroid;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;


import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Requirement extends AppCompatActivity{

    private RecyclerView recyclerView;
    private FirebaseFirestore firebaseFirestore;

    recycleradapterresult myadapter;
    FirebaseFirestore db;
    DocumentReference docRef;
    private List<DocumentSnapshot> list;
    public int i=1;
    public String result,doc;
    public ArrayList<String> mArrayresult = new ArrayList<String>();
    public ArrayList<String> mArraycont = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_requirement);

        db = FirebaseFirestore.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();

        db.collection("Resumes").orderBy("Result", Query.Direction.DESCENDING).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                QuerySnapshot queryDocumentSnapshot = task.getResult();
                list = queryDocumentSnapshot.getDocuments();

                // for (i = 1 ; !list.isEmpty() ; i++ ) {
                while(!list.isEmpty()){

                    doc = list.get(0).getId();
                    mArraycont.add(doc);
                    result = list.get(0).getString("Result");
                    mArrayresult.add(result);

                    // mArray.add(doc);

                    docRef = db.collection("Resumes").document(doc);

                    docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                            if(task.isSuccessful()){
                                DocumentSnapshot documentSnapshot = task.getResult();


                                docRef.collection("PersonalDetails").document("1").get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                        if(task.isSuccessful()) {
                                            DocumentSnapshot documentSnapshot = task.getResult();


                                            if(list.isEmpty())
                                            {
                                                myadapter = new recycleradapterresult(Requirement.this,getMyListdata());
                                                recyclerView.setAdapter(myadapter);
                                                //  gridadapter = new Adapter(Candidatelist.this,mArraycode,mArray,mArrayscore);
                                                // gridView.setAdapter(gridadapter);


                                            }
                                            i++;
                                        }
                                    }
                                });
                            }
                        }
                    });
                    list.remove(0);
                }
            }
        });






        recyclerView = findViewById(R.id.rec_result);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }


    private ArrayList<ModelResult> getMyListdata(){
        ArrayList<ModelResult> models = new ArrayList<>();
        //int len = doc.length();
        // Toast.makeText(getApplicationContext(),"print" + mArraycode.get(5),Toast.LENGTH_LONG).show();

        for (int i=0;i < mArraycont.size();i++)
        {
            //  Toast.makeText(getApplicationContext(),"Mo no :"+Name,Toast.LENGTH_SHORT).show();
            ModelResult m = new ModelResult();
            //  m.setMname("kjf");
            // m.setMemail("jkfksa");

            m.setMcont(mArraycont.get(i));
            m.setMresult(mArrayresult.get(i));
            models.add(m);
        }

        return models;
    }

}

 class recycleradapterresult extends RecyclerView.Adapter<recycleradapterresult.MyHolderResult> {

     Context context;
     ArrayList<ModelResult> models;

     public recycleradapterresult(Context context, ArrayList<ModelResult> models) {
         this.context = context;
         this.models = models;

     }

     @NonNull
     @Override
     public MyHolderResult onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

         View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_result, null);
         return new MyHolderResult(view);
     }

     @Override
     public void onBindViewHolder(@NonNull MyHolderResult holder, int position) {

         // holder.mhname.setText(models.get(position).getMname());
         // holder.mhemail.setText(models.get(position).getMemail());

         holder.mhcontact.setText(models.get(position).getMcont());
         holder.mhresult.setText(models.get(position).getMresult());

     }

     @Override
     public int getItemCount() {
         return models.size();
     }

     public class MyHolderResult extends RecyclerView.ViewHolder {

         TextView mhcontact, mhresult;

         public MyHolderResult(@NonNull View itemView) {

             super(itemView);

             //   this.mhname = itemView.findViewById(R.id.txtnameans);
             //  this.mhemail = itemView.findViewById(R.id.txtemailans);

             this.mhcontact = itemView.findViewById(R.id.txtmoans);
             this.mhresult = itemView.findViewById(R.id.txtResultans);

         }

     }
 }

