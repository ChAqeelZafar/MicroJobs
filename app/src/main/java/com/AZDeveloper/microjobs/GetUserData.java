package com.AZDeveloper.microjobs;

import android.content.Intent;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.AZDeveloper.microjobs.models.User;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

public class GetUserData extends AppCompatActivity {
    TextInputLayout nameText;
    EditText mailEdit;
    Button signupBtn;

    FirebaseFirestore db ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_user_data);

        nameText = findViewById(R.id.getuserdata_textInputLayout_name);
        mailEdit = findViewById(R.id.getuserdata_editText_email);
        signupBtn = findViewById(R.id.getuserdata_btn_verifynow);

        signupBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isEmpty()){
                    Toast.makeText(GetUserData.this,"Some fields are empty", Toast.LENGTH_LONG).show();
                }
                else{
                    updateDatatoTheFirestore(nameText.getEditText().getText().toString(), mailEdit.getText().toString());
                }
            }
        });

    }

    boolean isEmpty(){
        if(nameText.getEditText().toString().isEmpty() || mailEdit.getText().equals("")){
            return true;
        }
        return false;
    }

    //Function to update User Data to the firestore
    void updateDatatoTheFirestore(String Uname, String Uemail){
        db = FirebaseFirestore.getInstance();
        String UId = FirebaseAuth.getInstance().getUid();
        db.collection("usersID").document(UId).set(new User(UId, Uname, Uemail )).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Intent intent = new Intent(GetUserData.this, FragmentContainer.class);
                startActivity(intent);
            }
        });
    }
}
