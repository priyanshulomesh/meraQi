package com.originals.meraqi;

import static android.view.KeyEvent.KEYCODE_ENTER;
import static android.view.KeyEvent.KEYCODE_S;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
//import android.support.annotation.NonNull;
//import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class afterLogin extends AppCompatActivity {
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;
    ActionBarDrawerToggle actionBarDrawerToggle;
    Button button;
    RecyclerView recyclerView;
    List<complaintDetails> list;
    List<employeeDetails> listEmployee;
    List<personalDetails> listPersonal;
    List<scheduleDetails> listSchedule;
    String current_search;
    EditText editText;
    ImageView button_search;
    FirebaseFirestore db;
    complaintRecyclerAdapter c;
    employeeDetailsAdapter e;
    personalnfoAdapter p;
    scheduleRecyclerAdapter sa;
    TextView title;
    ConstraintLayout title_layout,update_layout;
    String current_email_id;
    int whereamI=0;
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if(actionBarDrawerToggle.onOptionsItemSelected(item))
            return true;
        return super.onOptionsItemSelected(item);
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_after_login);
        current_search="";
        editText=findViewById(R.id.searchBar);
        current_email_id = getIntent().getExtras().getString("emailid");
       // spinnerInit();
        title=findViewById(R.id.titleText);
        button_search=findViewById(R.id.search_button);
        title_layout=findViewById(R.id.titleLayoutAfterLogin);
        update_layout=findViewById(R.id.updateLayoutAfterLogin);
        title_layout.setVisibility(View.VISIBLE);
        update_layout.setVisibility(View.GONE);
        recyclerView=findViewById(R.id.recView);
        list=new ArrayList<>();
        listEmployee=new ArrayList<>();
        listPersonal=new ArrayList<>();
        listSchedule=new ArrayList<>();
        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.NavView);
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.menu_Open, R.string.close_menu);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_menu_camera);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@androidx.annotation.NonNull MenuItem menuItem) {
                int id = menuItem.getItemId();
                switch (id) {
                    case R.id.complaints:
                       // Toast.makeText(afterLogin.this, String.valueOf(menuItem.getItemId()), Toast.LENGTH_SHORT).show();
                        //onOptionsItemSelected(menuItem);
                        title_layout.setVisibility(View.VISIBLE);
                        update_layout.setVisibility(View.GONE);
                        title.setText("Complaints");
                        editText.setHint("search(leave,abuse,apprecition,etc.)");
                        current_search="";
                        whereamI=0;
                        displayComplaints();
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;
                    case R.id.employee_db:
                        //Toast.makeText(afterLogin.this, "Employee information clicked", Toast.LENGTH_SHORT).show();
                        title_layout.setVisibility(View.VISIBLE);
                        update_layout.setVisibility(View.GONE);
                        title.setText("Employee Information");
                        editText.setHint("Search an employee(by name,role,email etc)");
                        current_search="";
                        whereamI=1;
                        displayEmployee();
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;
                    case R.id.personal_info:
                        //Toast.makeText(afterLogin.this, "Your details clicked", Toast.LENGTH_SHORT).show();
                        whereamI=2;
                        title_layout.setVisibility(View.GONE);
                        update_layout.setVisibility(View.VISIBLE);
                        current_search="";
                        displayPersonal();
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;
                    case R.id.schedule:
                        //Toast.makeText(afterLogin.this, "Schedules clicked", Toast.LENGTH_SHORT).show();
                        title_layout.setVisibility(View.VISIBLE);
                        update_layout.setVisibility(View.GONE);
                        current_search="";
                        title.setText("Your upcoming schedules");
                        editText.setHint("Search(location,date,etc");
                        whereamI=3;
                        displaySchedule();
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;
                }
                return true;
            }
        });

        displayComplaints();
        button_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                search_it(editText.getText().toString());
            }
        });
        editText.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction()!=KeyEvent.ACTION_DOWN)
                    return false;
                if(keyCode==KEYCODE_ENTER)
                {
                    //your necessary codes...
                    search_it(editText.getText().toString());
                    return true;
                }
                return false;

            }
        });
    }
    void initAdapter(List<complaintDetails> list,String x){
        Log.d("adapterSet","Done");
        current_search=x;
        c=new complaintRecyclerAdapter(list);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(c);
        DividerItemDecoration dividerItemDecoration=new DividerItemDecoration(this,DividerItemDecoration.VERTICAL);
        recyclerView.addItemDecoration(dividerItemDecoration);
    }
    void initEmployeeAdapter(List<employeeDetails> list,String x){
        Log.d("adapterSet","Done");
        current_search=x;
        e=new employeeDetailsAdapter(list);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(e);
        DividerItemDecoration dividerItemDecoration=new DividerItemDecoration(this,DividerItemDecoration.VERTICAL);
        recyclerView.addItemDecoration(dividerItemDecoration);
    }
    void initPersonalAdapter(List<personalDetails> list,String x){
        Log.d("adapterSet","Done");
        current_search=x;
        p=new personalnfoAdapter(list);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(p);
        DividerItemDecoration dividerItemDecoration=new DividerItemDecoration(this,DividerItemDecoration.VERTICAL);
        recyclerView.addItemDecoration(dividerItemDecoration);
    }
    void initScheduleAdapter(List<scheduleDetails> list,String x){
        Log.d("adapterSet","Done");
        current_search=x;
        sa=new scheduleRecyclerAdapter(list);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(sa);
        DividerItemDecoration dividerItemDecoration=new DividerItemDecoration(this,DividerItemDecoration.VERTICAL);
        recyclerView.addItemDecoration(dividerItemDecoration);
    }
    int a=0;
    void displayComplaints(){
        list.clear();
//        list.add(new complaintDetails("Shashwat","Leave","Due to World Cup match"));
//        list.add(new complaintDetails("Siddharth","Leave","For Breakup"));
//        list.add(new complaintDetails("Rahil","Leave","For Gym"));
//        list.add(new complaintDetails("Shashwat","Team Change","mvjsdkncj ksd"));
//        list.add(new complaintDetails("Siddharth","Leave","For Breakup"));
//        list.add(new complaintDetails("Rahil","Leave","For Gym"));
//        list.add(new complaintDetails("Shashwat","Leave","Due to World Cup match"));
//        list.add(new complaintDetails("Siddharth","Leave","For Breakup"));
//        list.add(new complaintDetails("Rahil","Leave","For Gym"));
//        Toast.makeText(this, "Loading...", Toast.LENGTH_SHORT).show();
       // initAdapter(list,"");
        initAdapter(list, "");
        db=FirebaseFirestore.getInstance();
        db.collection("complaints").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                List<DocumentSnapshot> lds=queryDocumentSnapshots.getDocuments();
                for(DocumentSnapshot x:lds){
                    list.add(new complaintDetails(x.getString("name"),x.getString("category"),x.getString("description")));
                    c.notifyDataSetChanged();
                }

            }
        });
    }
    void displayEmployee(){
        listEmployee.clear();
        initEmployeeAdapter(listEmployee, "");
        db=FirebaseFirestore.getInstance();
        db.collection("employee").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                List<DocumentSnapshot> lds=queryDocumentSnapshots.getDocuments();
                for(DocumentSnapshot x:lds){
                    listEmployee.add(new employeeDetails(x.getString("name"),x.getString("role"),x.getString("email"),
                            x.getString("phone"),x.getString("salary"),x.getString("ml"),x.getString("cl"),x.getString("doj")));
                    e.notifyDataSetChanged();
                }

            }
        });
    }
    void displayPersonal(){
        listPersonal.clear();
//        listPersonal.add(new personalDetails("Shashwat","abc@hotmail.com","4588965412","40000","14","17","12/10/19"));
//        listPersonal.add(new personalDetails("Shashwat","abc@hotmail.com","4588965412","40000","14","17","12/10/19"));
//        listPersonal.add(new personalDetails("Shashwat","abc@hotmail.com","4588965412","40000","14","17","12/10/19"));
//        listPersonal.add(new personalDetails("Shashwat","abc@hotmail.com","4588965412","40000","14","17","12/10/19"));
//        listPersonal.add(new personalDetails("Shashwat","abc@hotmail.com","4588965412","40000","14","17","12/10/19"));
//        listPersonal.add(new personalDetails("Shashwat","abc@hotmail.com","4588965412","40000","14","17","12/10/19"));
//        listPersonal.add(new personalDetails("Shashwat","abc@hotmail.com","4588965412","40000","14","17","12/10/19"));
//        listPersonal.add(new personalDetails("Shashwat","abc@hotmail.com","4588965412","40000","14","17","12/10/19"));
//        listPersonal.add(new personalDetails("Shashwat","abc@hotmail.com","4588965412","40000","14","17","12/10/19"));
//        listPersonal.add(new personalDetails("Shashwat","abc@hotmail.com","4588965412","40000","14","17","12/10/19"));
//        listPersonal.add(new personalDetails("Shashwat","abc@hotmail.com","4588965412","40000","14","17","12/10/19"));
//        listPersonal.add(new personalDetails("Kaustubh","abc@hotmail.com","4588965412","40000","14","17","12/10/19"));
        initPersonalAdapter(listPersonal,"");
        db=FirebaseFirestore.getInstance();
        db.collection("personalInfo").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                List<DocumentSnapshot> lds=queryDocumentSnapshots.getDocuments();
                for(DocumentSnapshot x:lds){
                    if(x.getString("id").equals(current_email_id)) {
                        listPersonal.add(new personalDetails(x.getString("name"), x.getString("email"), x.getString("phone"), x.getString("salary"),
                                x.getString("ml"), x.getString("cl"), x.getString("doj")));
                        break;
                    }
                }
                p.notifyDataSetChanged();
            }
        });
    }
    void displaySchedule(){
//        listSchedule.add(new scheduleDetails(12,2022,2,3,15,"Hazratganj Lucknow","Deal with Shree Ram Tower Owner",
//                "2 hours","Tie Up with Shree Ram Tower"));
//        listSchedule.add(new scheduleDetails(11,2022,2,3,15,"Hazratganj Lucknow","Deal with Shree Ram Tower Owner",
//                "2 hours","Tie Up with Shree Ram Tower"));
//        listSchedule.add(new scheduleDetails(11,2022,2,3,15,"Hazratganj Lucknow","Deal with Shree Ram Tower Owner",
//                "2 hours","Tie Up with Shree Ram Tower"));
//        listSchedule.add(new scheduleDetails(11,2023,2,4,15,"Hazratganj Lucknow","Deal with Shree Ram Tower Owner",
//                "2 hours","Tie Up with Shree Ram Tower"));
//        listSchedule.add(new scheduleDetails(11,2022,2,3,16,"Hazratganj Lucknow","Deal with Shree Ram Tower Owner",
//                "2 hours","Tie Up with Shree Ram Tower"));
        initScheduleAdapter(listSchedule,"");
        db=FirebaseFirestore.getInstance();
        db.collection("schedule").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                List<DocumentSnapshot> lds=queryDocumentSnapshots.getDocuments();
                for(DocumentSnapshot x:lds){

                        listSchedule.add(new scheduleDetails(Integer.parseInt(x.getString("month")),Integer.parseInt(x.getString("year")), Integer.parseInt(x.getString("date")), Integer.parseInt(x.getString("hour")),
                                Integer.parseInt(x.getString("minute")), x.getString("location"), x.getString("title"),x.getString("duration"),x.getString("description")));

                }
               sa.notifyDataSetChanged();
            }
        });
        Collections.sort(listSchedule,new SortByDate());

    }
    void search_it(String y){
       // String y=editText.getText().toString();
        if(y.isEmpty()||y.equals(current_search))
            return;
        int position;
       // String currentlyViewing=editText.getText().toString();
        if(whereamI==0) {
            List<complaintDetails> l1 = new ArrayList<>();
            for (position = 0; position < list.size(); ++position) {
                String x = "";
                x = list.get(position).getName() + " " + list.get(position).getCategory() + " " + list.get(position).getDescription();
                x = x.toUpperCase();
                y = y.toUpperCase();
                if (x.contains(y))
                    l1.add(list.get(position));
            }
            initAdapter(l1, y);
        }
        else if(whereamI==1) {

            List<employeeDetails> l1 = new ArrayList<>();
            for (position = 0; position < listEmployee.size(); ++position) {
                String x = "";
                x = listEmployee.get(position).getName() + " " + listEmployee.get(position).getRole() + " " + listEmployee.get(position).getEmail() + " " +listEmployee.get(position).getPhone() + " " +
                        listEmployee.get(position).getSalary() + " " +listEmployee.get(position).getMl() + " " +listEmployee.get(position).getCl() + " " +listEmployee.get(position).getDoj();
                        x = x.toUpperCase();
                y = y.toUpperCase();
                if (x.contains(y))
                    l1.add(listEmployee.get(position));
            }
            initEmployeeAdapter(l1, y);
        }

        else if(whereamI==2) {

            List<personalDetails> l1 = new ArrayList<>();
            for (position = 0; position < listPersonal.size(); ++position) {
                String x = "";
                x = listPersonal.get(position).getName() + " " + listPersonal.get(position).getEmail() + " " +listPersonal.get(position).getPhone() + " " + listPersonal.get(position).getSalary() + " " +
                        listPersonal.get(position).getMl() + " " +listPersonal.get(position).getCl() + " " +listPersonal.get(position).getDoj();
                x = x.toUpperCase();
                y = y.toUpperCase();
                if (x.contains(y))
                    l1.add(listPersonal.get(position));
            }
            initPersonalAdapter(l1, y);
        }
        else{
            List<scheduleDetails> l1=new ArrayList<>();
            for (position = 0; position < listPersonal.size(); ++position) {
                String x = "";
                x=listSchedule.get(position).getDescription()+" "+listSchedule.get(position).getTitle()+" "+listSchedule.get(position).getDuration()+" "+
                        listSchedule.get(position).getLocation()+" "+ listSchedule.get(position).getYear() +" "+ listSchedule.get(position).getMonth()+" "+
                        listSchedule.get(position).getDate()+" "+listSchedule.get(position).getHour()+" "+listSchedule.get(position).getMinute();
                x = x.toUpperCase();
                y = y.toUpperCase();
                if (x.contains(y))
                    l1.add(listSchedule.get(position));
            }
            initScheduleAdapter(l1, y);
        }
        //Toast.makeText(this, "aagya", Toast.LENGTH_SHORT).show();
    }


    @Override
    public void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        actionBarDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        actionBarDrawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

}




class SortByDate implements Comparator<scheduleDetails> {

    // Method
    // Sorting in ascending order of roll number
    public int compare(scheduleDetails a, scheduleDetails b)
    {
    if(a.getYear()!=b.getYear())
        return a.getYear()-b.getYear();
    else if(a.getMonth()!=b.getMonth())
        return a.getMonth()-b.getMonth();
    else if(a.getDate()!= b.getDate())
        return a.getDate()-b.getDate();
    else if(a.getHour()!=b.getHour())
        return a.getHour()-b.getHour();
    return a.getMinute()-b.getMinute();
    }
}