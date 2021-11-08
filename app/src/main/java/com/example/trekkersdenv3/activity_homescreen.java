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
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class activity_homescreen extends AppCompatActivity implements View.OnClickListener {

    private TextView register,forgotpassword;
    private EditText editEmail,editPassword;
    private Button signIn;

    private FirebaseAuth mAuth;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homescreen);

        register=(TextView)findViewById(R.id.register);
        register.setOnClickListener(this);

        signIn=(Button)findViewById(R.id.signIn);
        signIn.setOnClickListener(this);

        editEmail=(EditText)findViewById(R.id.email);
        editPassword=(EditText)findViewById(R.id.password);
        progressBar=(ProgressBar)findViewById(R.id.progressBar);

        mAuth=FirebaseAuth.getInstance();

        forgotpassword=(TextView)findViewById(R.id.forgotpassword);
        forgotpassword.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
      switch(v.getId()){
          case R.id.register:
              startActivity(new Intent(this,RegisterUser.class));
              break;

          case R.id.signIn:
              userLogin();
              break;
          case R.id.forgotpassword:
              startActivity(new Intent(this,Forgot_password.class));
              break;

      }
    }

    private void userLogin() {

        String email=editEmail.getText().toString().trim();
        String password=editPassword.getText().toString().trim();

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
        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>(){

            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if(task.isSuccessful()){
                    FirebaseUser user=FirebaseAuth.getInstance().getCurrentUser();
                    if(user.isEmailVerified()){
                        //redirect to content pg #ContentActivity
                        startActivity(new Intent(activity_homescreen.this,ContentActivity.class));
                    }else{
                        user.sendEmailVerification();
                        Toast.makeText(activity_homescreen.this,"Check your email to verify your account!",Toast.LENGTH_LONG).show();
                    }


                }else{
                    Toast.makeText(activity_homescreen.this,"Failed to login",Toast.LENGTH_LONG).show();
                }

            }
        });

    }

}