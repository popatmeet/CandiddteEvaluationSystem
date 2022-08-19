package com.example.candidateevaluationsystemandroid;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskExecutors;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.mukesh.OtpView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class Home extends AppCompatActivity {
   // private static final String PHONE_NUMBER = "^(?:(?:\\+|0{0,2})91(\\s*[\\ -]\\s*)?|[0]?)?[789]\\d{9}|(\\d[ -]?){10}\\d$";
   private static final String PHONE_NUMBER = "^[6-9][0-9]{9}";
    private Pattern pattern;
    private Matcher matcher;
    Button login,apply,next,verify;
    OtpView otpView;
    static EditText phoneno;
    BottomSheetDialog bottomSheetDialog,bottomSheetDialogLogin;
    private FirebaseUser user;
    static String phonenumber = null;
    private ProgressBar progress;
    private FirebaseAuth mAuth;
    private String verificationId;
    private DocumentReference docRef;
    private EditText loginusername,loginpassword;
    private FirebaseFirestore db;

    public String code;
    private List<DocumentSnapshot> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        findViewsById();
        login.setOnClickListener(mlogin);
        apply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                user = FirebaseAuth.getInstance().getCurrentUser();
                if(user != null){
                    Intent intent = new Intent(Home.this,Details.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                }
                else {
                    bottomSheetDialog = new BottomSheetDialog(Home.this, R.style.BottomSheetDialogTheme);
                    bottomSheetDialog.setContentView(R.layout.bottomsheet);
                    phoneno = (EditText) bottomSheetDialog.findViewById(R.id.phoneno);
                    verify = (Button) bottomSheetDialog.findViewById(R.id.verify);
                    next = (Button) bottomSheetDialog.findViewById(R.id.submit);
                    otpView = (OtpView) bottomSheetDialog.findViewById(R.id.otp_view);
                    progress = (ProgressBar) bottomSheetDialog.findViewById(R.id.progressBar);
                    bottomSheetDialog.setCanceledOnTouchOutside(false);
                    bottomSheetDialog.show();
                    phoneno.addTextChangedListener(new TextWatcher() {
                        @Override
                        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                        }

                        @Override
                        public void onTextChanged(CharSequence s, int start, int before, int count) {
                            next.setVisibility(View.VISIBLE);
                            otpView.setVisibility(View.INVISIBLE);
                            verify.setVisibility(View.INVISIBLE);
                        }

                        @Override
                        public void afterTextChanged(Editable s) {

                        }
                    });
                    next.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            pattern = Pattern.compile(PHONE_NUMBER);
                            matcher = pattern.matcher(phoneno.getText().toString());
                            phonenumber = "+91" + phoneno.getText().toString().trim();
                            if (matcher.find()) {
                                docRef = db.collection("Resumes").document(phonenumber);
                                docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                        if(task.isSuccessful()){
                                            DocumentSnapshot documentSnapshot = task.getResult();
                                            Boolean state = documentSnapshot.getBoolean("State");
                                            if(state == null){
                                                next.setVisibility(View.INVISIBLE);
                                                otpView.setVisibility(View.VISIBLE);
                                                verify.setVisibility(View.VISIBLE);
                                                sendVerificationCode(phonenumber);
                                                otpView.requestFocus();
                                                verify.setOnClickListener(new View.OnClickListener() {
                                                    @Override
                                                    public void onClick(View v) {
                                                        String code = otpView.getText().toString().trim();
                                                        if(code.isEmpty() || code.length()<6){
                                                            Toast.makeText(getApplicationContext(),"Invalid OTP",Toast.LENGTH_SHORT).show();
                                                            return;
                                                        }
                                                        else{
                                                            progress.setVisibility(View.VISIBLE);
                                                            verifyCode(code);
                                                        }
                                                    }
                                                });
                                            }
                                            else if(!state){
                                                next.setVisibility(View.INVISIBLE);
                                                otpView.setVisibility(View.VISIBLE);
                                                verify.setVisibility(View.VISIBLE);
                                                sendVerificationCode(phonenumber);
                                                otpView.requestFocus();
                                                verify.setOnClickListener(new View.OnClickListener() {
                                                    @Override
                                                    public void onClick(View v) {
                                                        String code = otpView.getText().toString().trim();
                                                        if(code.isEmpty() || code.length()<6){
                                                            Toast.makeText(getApplicationContext(),"Invalid OTP",Toast.LENGTH_SHORT).show();
                                                            return;
                                                        }
                                                        else{
                                                            progress.setVisibility(View.VISIBLE);
                                                            verifyCode(code);
                                                        }
                                                    }
                                                });
                                            }
                                            else{
                                                Toast.makeText(getApplicationContext(),"You have Already applied for job",Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    }
                                });
                            }
                            else {
                                Toast.makeText(getApplicationContext(), "Enter Valid Phone Number", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });
    }
    private void verifyCode(String code){
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationId,code);
        signInWithCredential(credential);
    }

    private void signInWithCredential(PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    bottomSheetDialog.dismiss();
                    Map<String, Object> doc = new HashMap<>();
                    doc.put("Score","0");
                    doc.put("State",false);
                    Map<String, Object> commondoc = new HashMap<>();
                    commondoc.put("State",false);
                    Intent intent = new Intent(Home.this,Details.class);
                    db.collection("Resumes").document(phonenumber).set(doc);
                    db.collection("Resumes").document(phonenumber).collection("PersonalDetails").document("1").set(commondoc);
                    db.collection("Resumes").document(phonenumber).collection("EducationalDetails").document("1").set(commondoc);
                    db.collection("Resumes").document(phonenumber).collection("SkillDetails").document("1").set(commondoc);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                }
                else{
                    Toast.makeText(getApplicationContext(),task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void sendVerificationCode(String number){
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                number,
                60,
                TimeUnit.SECONDS,
                TaskExecutors.MAIN_THREAD,
                mCallBack
        );
    }
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallBack = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
        @Override
        public void onCodeSent(String s, PhoneAuthProvider.ForceResendingToken forceResendingToken) {
            super.onCodeSent(s, forceResendingToken);
            verificationId = s;
        }

        @Override
        public void onCodeAutoRetrievalTimeOut(String s) {
            super.onCodeAutoRetrievalTimeOut(s);
        }

        @Override
        public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {
            String code = phoneAuthCredential.getSmsCode();
            if(code != null){
                verify.setEnabled(false);
                progress.setVisibility(View.VISIBLE);
                otpView.setText(code);
                verifyCode(code);
            }
        }

        @Override
        public void onVerificationFailed(FirebaseException e) {
            Toast.makeText(Home.this,e.getMessage(),Toast.LENGTH_SHORT).show();
        }
    };

    private void findViewsById(){
        login = (Button) findViewById(R.id.login);
        apply = (Button) findViewById(R.id.apply);
        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
    }

    View.OnClickListener mlogin = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            bottomSheetDialogLogin = new BottomSheetDialog(Home.this, R.style.BottomSheetDialogTheme);
            bottomSheetDialogLogin.setContentView(R.layout.bottomsheetlogin);
            bottomSheetDialogLogin.setCanceledOnTouchOutside(false);
            loginusername = bottomSheetDialogLogin.findViewById(R.id.username);
            loginpassword = bottomSheetDialogLogin.findViewById(R.id.password);
            Button loginbtn = bottomSheetDialogLogin.findViewById(R.id.loginbtn);
            loginbtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(loginusername.getText().toString().trim().equals("Admin") && loginpassword.getText().toString().trim().equals("Admin")) {
                        Intent intent = new Intent(Home.this, AdminSide.class );
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        bottomSheetDialogLogin.dismiss();
                        startActivity(intent);
                    }
                    else
                    {
                       // pattern = Pattern.compile(PHONE_NUMBER);
                       // matcher = pattern.matcher(phoneno.getText().toString());
                     phonenumber = "+91" + loginusername.getText().toString().trim();


                     // if (matcher.find()) {
                            docRef = db.collection("Resumes").document(phonenumber);
                            docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                    if(task.isSuccessful()){
                                        DocumentSnapshot documentSnapshot = task.getResult();
                                        Boolean state = documentSnapshot.getBoolean("State");

                                       // code = list.get(0).getString("Code");
                                        code = documentSnapshot.getString("Code");
                                        if(loginusername.getText().toString().trim().equals(loginusername.getText().toString().trim()) && loginpassword.getText().toString().trim().equals(code)){


                                            Intent intquiz = new Intent(Home.this, QuizActivity.class);
                                            intquiz.putExtra("mono",phonenumber);
                                            intquiz.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                            bottomSheetDialogLogin.dismiss();
                                            startActivity(intquiz);
                                        }
                                        else if (loginusername.getText().toString().isEmpty() && loginpassword.getText().toString().isEmpty())
                                        {
                                            Toast.makeText(getApplicationContext(),"Enter MobileNo And Code",Toast.LENGTH_LONG).show();
                                        }
                                        else {
                                            Toast.makeText(getApplicationContext(),"Invalid Code",Toast.LENGTH_LONG).show();
                                        }
                                    }
                                }
                            });
                       //





                      /*  if(loginusername.getText().toString().trim().equals("8320224014") && loginpassword.getText().toString().trim().equals("0944")){


                            Intent intquiz = new Intent(Home.this, QuizActivity.class);
                            intquiz.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            bottomSheetDialogLogin.dismiss();
                            startActivity(intquiz);
                        } */
                    }
                }
            });
            bottomSheetDialogLogin.show();
        }
    };
}
