package com.AZDeveloper.microjobs;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskExecutors;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {

    String mVerificationId;
    PhoneAuthProvider.ForceResendingToken  mResendToken;
    EditText phoneEdit, codeEdit;
    Button sendCodeBtn, verifynowBtn;
    private FirebaseAuth mAuth;


    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAuth = FirebaseAuth.getInstance();
        phoneEdit = findViewById(R.id.main_edit_phoneno);
        codeEdit = findViewById(R.id.main_edit_code);
        sendCodeBtn = findViewById(R.id.main_btn_sendcode);
        verifynowBtn = findViewById(R.id.getuserdata_btn_verifynow);

        codeEdit.setVisibility(View.GONE);
        verifynowBtn.setVisibility(View.GONE);



        verifynowBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                verifyVerificationCode(codeEdit.getText().toString());

            }
        });
        phoneEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendCodeBtn.setClickable(true);
                codeEdit.setVisibility(View.GONE);
                verifynowBtn.setVisibility(View.GONE);
            }
        });
        sendCodeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                codeEdit.setVisibility(View.VISIBLE);
                verifynowBtn.setVisibility(View.VISIBLE);
                sendCodeBtn.setClickable(false);
                sendVerificationCode(phoneEdit.getText().toString());

            }
        });

        mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            @Override
            public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {

                signInWithPhoneAuthCredential(phoneAuthCredential);
            }

            @Override
            public void onVerificationFailed(FirebaseException e) {
                Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();

            }



            @Override
            public void onCodeSent(String s, PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                Toast.makeText(MainActivity.this,"Code sent successfully", Toast.LENGTH_LONG).show();
                super.onCodeSent(s, forceResendingToken);
                mVerificationId = s;
                mResendToken = forceResendingToken;
            }
        };

    }

    private void sendVerificationCode(String mobile) {
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                mobile,
                60,
                TimeUnit.SECONDS,
                TaskExecutors.MAIN_THREAD,
                mCallbacks);
    }


    private void verifyVerificationCode(String code) {
        //creating the credential
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(mVerificationId, code);

        //signing the user
        signInWithPhoneAuthCredential(credential);
    }

    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            //verification successful we will start the profile activity
                            checkIfDataAlreadyInFirestore();

                        } else {

                            //verification unsuccessful.. display an error message

                            String message = "Somthing is wrong, we will fix it soon...";

                            if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                                message = "Invalid code entered...";
                            }

                            Toast.makeText(MainActivity.this, message,Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }

    //Check if User is logged in First time. So I can get His Name & Mail also, Otherwise Simoly Login
    void checkIfDataAlreadyInFirestore(){
        final boolean isDataEntered = false;
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        DocumentReference docRef = db.collection("usersID").document(FirebaseAuth.getInstance().getUid());
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    Intent intent;
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {

                        //Mean User with current id is present in Firestore so go to the FragmentContainer Activity
                        intent = new Intent(MainActivity.this, FragmentContainer.class);

                    } else {

                        //User with current id is not present in firestore so now we have to get Other Daata from the user
                        intent = new Intent(MainActivity.this, GetUserData.class);

                    }
                    startActivity(intent);
                } else {
                    Toast.makeText(MainActivity.this, "get failed with ", Toast.LENGTH_LONG).show();
                }
            }
        });
    }


}
