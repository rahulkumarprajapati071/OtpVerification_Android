package com.example.otpverification;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.renderscript.ScriptGroup;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.firebase.FirebaseException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class EnterMobileNumber extends AppCompatActivity {

    EditText editText;
    Button sendButton;
    ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entermobilenumber);

        editText = findViewById(R.id.mobile_number);
        sendButton = findViewById(R.id.sendButton);
        progressBar = findViewById(R.id.progressbar_send_button);

        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!editText.getText().toString().trim().isEmpty()){
                    if(editText.getText().toString().trim().length() == 10){

                        progressBar.setVisibility(View.VISIBLE);
                        sendButton.setVisibility(View.INVISIBLE);

                        allProcessOfOTPVerification();

                    }else{
                        Toast.makeText(EnterMobileNumber.this, "Please enter correct number", Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(EnterMobileNumber.this, "Please enter mobile number", Toast.LENGTH_SHORT).show();
                }
            }
        });



    }

    private void allProcessOfOTPVerification() {
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                "+91" + editText.getText().toString(),
                60, TimeUnit.SECONDS, EnterMobileNumber.this,
                new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                    @Override
                    public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                        progressBar.setVisibility(View.GONE);
                        sendButton.setVisibility(View.VISIBLE);
                    }

                    @Override
                    public void onVerificationFailed(@NonNull FirebaseException e) {
                        progressBar.setVisibility(View.GONE);
                        sendButton.setVisibility(View.VISIBLE);
                        Toast.makeText(EnterMobileNumber.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onCodeSent(@NonNull String backendOtp, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                        progressBar.setVisibility(View.GONE);
                        sendButton.setVisibility(View.VISIBLE);
                        Intent intent = new Intent(getApplicationContext(),Verification.class);
                        intent.putExtra("mobile",editText.getText().toString());
                        intent.putExtra("otp",backendOtp);
                        startActivity(intent);
                    }
                }
        );
    }
}