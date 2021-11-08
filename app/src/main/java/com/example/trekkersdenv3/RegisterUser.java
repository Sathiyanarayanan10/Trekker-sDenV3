package com.example.trekkersdenv3;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.util.regex.Pattern;

public class RegisterUser extends AppCompatActivity implements View.OnClickListener {

    private FirebaseAuth mAuth;
    private Button registerUser;
    private EditText editFullName,editEmail,editPassword;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_user);

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();
        registerUser=(Button)findViewById(R.id.registerUser);
        registerUser.setOnClickListener(this);

        editFullName=(EditText)findViewById(R.id.fullName);
        editEmail=(EditText)findViewById(R.id.email);
        editPassword=(EditText)findViewById(R.id.password);

        progressBar= (ProgressBar) findViewById(R.id.progressBar);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){

            case R.id.registerUser:
                registerUser();
                break;

        }

    }

    private void registerUser() {

        String email=editEmail.getText().toString().trim();
        String password=editPassword.getText().toString().trim();
        String fullName=editFullName.getText().toString().trim();

        if(fullName.isEmpty()){
            editFullName.setError("Full name is required");
            editFullName.requestFocus();
            return;
        }
        if(email.isEmpty()){
            editEmail.setError("Email is required");
            editEmail.requestFocus();
            return;
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            editEmail.setError("Pls provide valid email");
            editEmail.requestFocus();
            return;
        }
        if(password.isEmpty()){
            editPassword.setError("Pls provide password");
            editPassword.requestFocus();
            return;
        }
        if(password.length() < 6){
            editPassword.setError("Minimum length is 6");
            editPassword.requestFocus();
            return;
        }

        progressBar.setVisibility(View.VISIBLE);
        mAuth.createUserWithEmailAndPassword(email,password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>(){

                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                       if(task.isSuccessful()){
                           User user=new User(fullName,email);
                           FirebaseDatabase.getInstance().getReference("Users")
                                   .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                   .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>(){

                               @Override
                               public void onComplete(@NonNull Task<Void> task) {

                                   if (task.isSuccessful()){
                                       Toast.makeText(RegisterUser.this,"User has been registered",Toast.LENGTH_LONG).show();
                                       progressBar.setVisibility(View.GONE);
                                       startActivity(new Intent(RegisterUser.this,activity_homescreen.class));

                                       //redirect to login layout
                                   }else{
                                       Toast.makeText(RegisterUser.this,"Failed to register",Toast.LENGTH_LONG).show();
                                       progressBar.setVisibility(View.GONE);
                                   }
                               }
                           });
                       }else{
                           Toast.makeText(RegisterUser.this,"Failed to register",Toast.LENGTH_LONG).show();
                           progressBar.setVisibility(View.GONE);
                       }
                    }
                });
    }
}