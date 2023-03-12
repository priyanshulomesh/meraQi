package com.originals.meraqi;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ForgetPassword1 extends AppCompatActivity {
    private EditText email;
    private String mail;
   private FirebaseAuth auth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password1);
        getSupportActionBar().hide(); //hide the title bar
        email=findViewById(R.id.enteremail);

//        send.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                validate();
//            }
//        });
    }
    public void validate(View view){
        mail=email.getText().toString();
        if(mail.isEmpty()){
            email.setError("Please enter email");
        }
        else{
            forg();
        }
    }
    public void forg(){
        auth=FirebaseAuth.getInstance();
        auth.sendPasswordResetEmail(mail).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(ForgetPassword1.this, "Mail has been sent", Toast.LENGTH_LONG).show();
                    Intent i = new Intent(ForgetPassword1.this, loginPage.class);
                    startActivity(i);
                }
            }
        });
    }
}