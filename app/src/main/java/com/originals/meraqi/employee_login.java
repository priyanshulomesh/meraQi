package com.originals.meraqi;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class employee_login extends AppCompatActivity {
    FirebaseFirestore db;
    String email="";
    String name="";
    TextView greet;
    EditText cat,des,acc;
    String category="",accused_email="",description="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_employee_login);
        Bundle extras = getIntent().getExtras();
        email=extras.getString("emailid");
        greet=findViewById(R.id.greet);
        db=FirebaseFirestore.getInstance();
        db.collection("employee").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                List<DocumentSnapshot> lds=queryDocumentSnapshots.getDocuments();
                for(DocumentSnapshot x:lds){
                    if(x.getString("email").equals(email)){
                        name=x.getString("name");
                        String abc="Welcome "+name+"!";
                        greet.setText(abc);
                        break;
                    }
                }
            }
        });
        cat=findViewById(R.id.category);
        des=findViewById(R.id.description);
        acc=findViewById(R.id.accused);
    }
    public void complaint(View view){
        category=cat.getText().toString();
        if(category.isEmpty()){
            Toast.makeText(this, "Category is mandatory", Toast.LENGTH_SHORT).show();
            return;
        }
        description=des.getText().toString();
        if(description.isEmpty()){
            Toast.makeText(this, "Description is mandatory", Toast.LENGTH_SHORT).show();
            return;
        }
        accused_email=acc.getText().toString();
        Map<String,String> mp=new HashMap<>();
        mp.put("accused_email",accused_email);
        mp.put("category",category);
        mp.put("description",description);
        mp.put("name",name);
        mp.put("sender_email",email);
        FirebaseFirestore.getInstance().collection("complaints").document().
                set(mp).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Toast.makeText(employee_login.this, "Complaint Raised", Toast.LENGTH_SHORT).show();
                    }
                });
    }
}