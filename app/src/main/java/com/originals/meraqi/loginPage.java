package com.originals.meraqi;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;

public class loginPage extends AppCompatActivity {
    EditText email;
    EditText password;
    Button login;
    TextView error;
    FirebaseAuth auth;
    boolean flag;
    ArrayList<String> hrs;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        requestWindowFeature(Window.FEATURE_NO_TITLE);//will hide the title
        getSupportActionBar().hide(); //hide the title bar
        setContentView(R.layout.activity_login_page);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        login = findViewById(R.id.login);
        error = findViewById(R.id.error);
        auth = FirebaseAuth.getInstance();

        hrs=new ArrayList<>();

        DatabaseReference reference=FirebaseDatabase.getInstance().getReference().
                child("hr");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot snapshot1: snapshot.getChildren()){
                    hrs.add(snapshot1.getValue().toString());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void check(View view) {
        String em = email.getText().toString();
        String pw = password.getText().toString();
        if (em.length() == 0) {
            email.setError("Please Enter your email");
            return;
        }
        else if (pw.length() == 0) {
            password.setError("Please Enter your password");
            return;
        }
        else {
            checkLogin(em, pw);
        }
    }
    public void checkLogin(String em, String pw) {
        flag=false;
//        auth.signInWithEmailAndPassword(em, pw).addOnSuccessListener(loginPage.this, new OnSuccessListener<AuthResult>() {
//            @Override
//            public void onSuccess(AuthResult authResult) {
//                flag=true;
//                Toast.makeText(loginPage.this, "Logged in successfully", Toast.LENGTH_SHORT).show();
//                Intent intent = new Intent(loginPage.this, afterLogin.class);
//                startActivity(intent);
//            }
//        });
//        HashMap<String,Object> mp=new HashMap<String,Object>();
//        mp.put("hi","hello");
////        mp.put("sks_maths2001-gmail_com","hr");
////        mp.put("sidtrivedi100-gmail_com","hr");

//        FirebaseDatabase db=FirebaseDatabase.getInstance();
//        DatabaseReference root=db.getReference().child("hr");
//        root.child("hr1").setValue("sks_maths2001-gmail_com");
//        root.child("hr2").setValue("sks_comp2001-gmail_com");
//        root.child("hr3").setValue("sidtrivedi100-gmail_com");

        auth.signInWithEmailAndPassword(em,pw).addOnCompleteListener(loginPage.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    Toast.makeText(loginPage.this, "Logged in successfully", Toast.LENGTH_SHORT).show();
                    Intent intent=new Intent(loginPage.this,employee_login.class);
                    if(search(em)) {
                        intent = new Intent(loginPage.this, afterLogin.class);
                    }
                    intent.putExtra("emailid",em);
                    startActivity(intent);
                }
                else{
                    error.setText("Invalid email or password");
                }
            }
        });
//        if(flag==false)
//            error.setText("Invalid email or password");
    }

//    public void clickforgetpassword(View view){
//        Intent i=new Intent(loginPage.this,afterLogin.class);
//        startActivity(i);
//    }
    boolean search(String email){
        int i;
        for(i=0;i<hrs.size();++i){
            if(hrs.get(i).equals(email))
                return true;
        }
        return false;
    }
}