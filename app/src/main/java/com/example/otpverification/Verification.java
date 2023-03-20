package com.example.otpverification;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class  Verification extends AppCompatActivity {

    EditText input1,input2,input3,input4,input5,input6;
    TextView showMobile;
    Button buttonVerify;
    String getOtpBackend;
    ProgressBar submitOtpProgress;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verification);

        input1 = findViewById(R.id.input_1);
        input2 = findViewById(R.id.input_2);
        input3 = findViewById(R.id.input_3);
        input4 = findViewById(R.id.input_4);
        input5 = findViewById(R.id.input_5);
        input6 = findViewById(R.id.input_6);

        showMobile = findViewById(R.id.showmobilenumber);
        showMobile.setText(String.format("+91-%s",getIntent().getStringExtra("mobile")));
        getOtpBackend = getIntent().getStringExtra("otp");
        submitOtpProgress = findViewById(R.id.progressbar_submit_button);


        buttonVerify = findViewById(R.id.buttonVerify);
        buttonVerify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!input1.getText().toString().trim().isEmpty() &&!input2.getText().toString().trim().isEmpty() &&!input3.getText().toString().trim().isEmpty() &&!input4.getText().toString().trim().isEmpty() &&!input5.getText().toString().trim().isEmpty() &&!input6.getText().toString().trim().isEmpty())
                {
                    String enterCodeOtp = input1.getText().toString()+
                            input2.getText().toString()+
                            input3.getText().toString()+
                            input4.getText().toString()+
                            input5.getText().toString()+
                            input6.getText().toString();

                    if(getOtpBackend!=null){
                        submitOtpProgress.setVisibility(View.VISIBLE);
                        buttonVerify.setVisibility(View.INVISIBLE);

                        PhoneAuthCredential phoneAuthCredential = PhoneAuthProvider.getCredential(getOtpBackend,enterCodeOtp);
                        FirebaseAuth.getInstance().signInWithCredential(phoneAuthCredential).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                submitOtpProgress.setVisibility(View.GONE);
                                buttonVerify.setVisibility(View.VISIBLE);

                                if(task.isSuccessful())
                                {
                                    Intent intent = new Intent(getApplicationContext(),Dashboard.class);
                                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                    startActivity(intent);
                                }else{
                                    Toast.makeText(Verification.this, "Enter correct Otp", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });

                    }else{
                        Toast.makeText(Verification.this, "Please check internet connection", Toast.LENGTH_SHORT).show();
                    }



//                    Toast.makeText(Verification.this, "Otp Verify", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(Verification.this, "Please enter code", Toast.LENGTH_SHORT).show();
                }
            }
        });

        numberOtpMove();
        findViewById(R.id.resendotp).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PhoneAuthProvider.getInstance().verifyPhoneNumber(
                        "+91" + getIntent().getStringExtra("mobile"),
                        60, TimeUnit.SECONDS, Verification.this,
                        new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                            @Override
                            public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                                submitOtpProgress.setVisibility(View.GONE);
                                buttonVerify.setVisibility(View.VISIBLE);
                            }

                            @Override
                            public void onVerificationFailed(@NonNull FirebaseException e) {
                                submitOtpProgress.setVisibility(View.GONE);
                                buttonVerify.setVisibility(View.VISIBLE);
                                Toast.makeText(Verification.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onCodeSent(@NonNull String backendOtp, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                                submitOtpProgress.setVisibility(View.GONE);
                                buttonVerify.setVisibility(View.VISIBLE);
                                getOtpBackend = backendOtp;
                            }
                        }
                );
            }
        });

    }

    private void numberOtpMove() {

        input1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(!s.toString().trim().isEmpty())
                {
                    input2.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }

        });
        input2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(!s.toString().trim().isEmpty())
                {
                    input3.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }

        });
        input3.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(!s.toString().trim().isEmpty())
                {
                    input4.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }

        });
        input4.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(!s.toString().trim().isEmpty())
                {
                    input5.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }

        });
        input5.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(!s.toString().trim().isEmpty())
                {
                    input6.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }

        });
    }
}