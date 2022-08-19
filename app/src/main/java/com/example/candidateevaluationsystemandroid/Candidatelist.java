package com.example.candidateevaluationsystemandroid;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.telephony.SmsManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class Candidatelist extends AppCompatActivity {


    GridView gridView;
    String[] name = {"Basic c","C++","Php","Android","ACp","FSD","Basic c","C++","Php","Android","ACp"};
    String[] email =  new String[30]; //{"CE","ME","civil","CE","CE","IT"};
    String[] address =  new String[30]; //{"CE","ME","civil","CE","CE","IT"};
    String[] contact =   new String[30]; //{"1","3","5","6","3","5"};
    String[] scoredata = new String[30];

   public ArrayList<String> mArray = new ArrayList<String>();
    public ArrayList<String> mArrayscore = new ArrayList<String>();
    public ArrayList<String> mArrayEmail = new ArrayList<String>();
    public ArrayList<String> mArrayAddress = new ArrayList<String>();
    public ArrayList<String> mArrayname = new ArrayList<String>();
    public ArrayList<String> mArraycode = new ArrayList<String>();




    String mString;

    public String namedata,emaildata,addressdata,contactdata,scoredataaa;
    String exadata;

    private RecyclerView recyclerView;
    private FirebaseFirestore firebaseFirestore;

     recycleradapter myadapter;
    FirebaseFirestore db;
    DocumentReference docRef;
    //public static String doc,Score,Name,Email,Contact,Address;
    Adapter gridadapter;

    public String Score,Name,Email,Contact,Address,doc,code,no;
    private List<DocumentSnapshot> list;

    public int i=1;
  //  private List<DocumentSnapshot> list;
    TextView tname , temail, taddress, tcontact, tscore;
    ArrayList<ModelData> models;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_candidatelist);
        findViewsById();



        db.collection("Resumes").orderBy("Score", Query.Direction.DESCENDING).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                QuerySnapshot queryDocumentSnapshot = task.getResult();
                list = queryDocumentSnapshot.getDocuments();

                  // for (i = 1 ; !list.isEmpty() ; i++ ) {
                  while(!list.isEmpty()){

                    doc = list.get(0).getId();
                    mArray.add(doc);
                    Score = list.get(0).getString("Score");
                    mArrayscore.add(Score);

                    code = list.get(0).getString("Code");
                    mArraycode.add(code);
                   // mArray.add(doc);
                    Toast.makeText(Candidatelist.this, "no" +doc, Toast.LENGTH_SHORT).show();
                    docRef = db.collection("Resumes").document(doc);

                    docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                            if(task.isSuccessful()){
                                DocumentSnapshot documentSnapshot = task.getResult();

                              // code = documentSnapshot.getString("Code");
                              //  code = list.get(1).getString("Code");
                               // mArraycode.add(code);
                               // Score = documentSnapshot.getString("Score");

                                //Toast.makeText(Candidatelist.this, Score, Toast.LENGTH_SHORT).show();
                               // mArrayscore.add(Score);

                              //  no = documentSnapshot.getString("Contact");
                                //mArray.add(no);
                                docRef.collection("PersonalDetails").document("1").get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                        if(task.isSuccessful()) {
                                            DocumentSnapshot documentSnapshot = task.getResult();
                                            Name = documentSnapshot.getString("Name");
                                            Email = documentSnapshot.getString("Email");
                                            Address = documentSnapshot.getString("Address");
                                            Contact = documentSnapshot.getString("Mobile");

                                            mArrayname.add(Name);
                                            mArrayEmail.add(Email);
                                            mArrayAddress.add(Address);
                                            namedata = Name;
                                            emaildata = Email;
                                            addressdata = Address;
                                            contactdata = Contact;

                                            if(list.isEmpty())
                                            {
                                                myadapter = new recycleradapter(Candidatelist.this,getMyList());
                                                recyclerView.setAdapter(myadapter);
                                              //  gridadapter = new Adapter(Candidatelist.this,mArraycode,mArray,mArrayscore);
                                               // gridView.setAdapter(gridadapter);
                                               Toast.makeText(Candidatelist.this, " Contact =" + i,Toast.LENGTH_LONG).show();

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


            firebaseFirestore = FirebaseFirestore.getInstance();
      //  gridView = (GridView) findViewById(R.id.gridview);
        //Adapter gridadapter = new Adapter(Candidatelist.this,name,email,address,contact,scoredata);
      // gridView.setAdapter(gridadapter);

         recyclerView = findViewById(R.id.rec_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
      //  myadapter = new recycleradapter(this,getMyList());
       // recyclerView.setAdapter(myadapter);


    }

   private ArrayList<ModelData> getMyList(){
        ArrayList<ModelData> models = new ArrayList<>();
        //int len = doc.length();
       // Toast.makeText(getApplicationContext(),"print" + mArraycode.get(5),Toast.LENGTH_LONG).show();

        for (int i=0;i < mArray.size();i++)
        {
       //  Toast.makeText(getApplicationContext(),"Mo no :"+Name,Toast.LENGTH_SHORT).show();
        ModelData m = new ModelData();
      //  m.setMname("kjf");
       // m.setMemail("jkfksa");
        m.setMadderess(mArraycode.get(i));
        m.setMcontact(mArray.get(i));
        m.setMscore(mArrayscore.get(i));
        models.add(m);
        }

        return models;
    }
    public void findViewsById(){
        db = FirebaseFirestore.getInstance();

     //   tname = (TextView) findViewById(R.id.txtnameans);
      //  temail = (TextView) findViewById(R.id.txtemailans);
        taddress = (TextView) findViewById(R.id.txtaddressans);
        tcontact = (TextView) findViewById(R.id.txtcontactnoans);
        tscore = (TextView) findViewById(R.id.txtscoreans);

    }
}





class recycleradapter extends RecyclerView.Adapter<recycleradapter.MyHolder> {

    Context context;
    ArrayList<ModelData> models;

    public recycleradapter(Context context, ArrayList<ModelData> models) {
        this.context = context;
        this.models = models;

    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_design,null);
        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, final int position) {

        // holder.mhname.setText(models.get(position).getMname());
        // holder.mhemail.setText(models.get(position).getMemail());
        holder.mhaddress.setText(models.get(position).getMadderess());
        holder.mhcontact.setText(models.get(position).getMcontact());
        holder.mhscore.setText(models.get(position).getMscore());

        holder.btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(context,Candidatelist.class);
                PendingIntent pi=PendingIntent.getActivity(context, 8, intent,PendingIntent.FLAG_ONE_SHOT);

                String no = models.get(position).getMcontact();
                String msg = models.get(position).getMadderess();
                //Get the SmsManager instance and call the sendTextMessage method to send message
                SmsManager sms=SmsManager.getDefault();
                sms.sendTextMessage(no, null, msg, pi,null);

                Toast.makeText(context,"Code to send = " +  models.get(position).getMadderess(),Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return models.size();
    }



    public class MyHolder extends RecyclerView.ViewHolder {

        TextView mhname , mhemail , mhaddress , mhcontact , mhscore;
        Button btn;

        public MyHolder(@NonNull View itemView) {
            super(itemView);

            //   this.mhname = itemView.findViewById(R.id.txtnameans);
            //  this.mhemail = itemView.findViewById(R.id.txtemailans);
            this.mhaddress = itemView.findViewById(R.id.txtaddressans);
            this.mhcontact = itemView.findViewById(R.id.txtcontactnoans);
            this.mhscore = itemView.findViewById(R.id.txtscoreans);
            this.btn = itemView.findViewById(R.id.btnsend);
          /*  itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(context,"posittion" + getAdapterPosition(),Toast.LENGTH_LONG).show();

                }
            }); */

        }
    }
}





