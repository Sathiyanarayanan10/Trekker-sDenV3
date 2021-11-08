package com.example.trekkersdenv3;

import static android.content.Intent.ACTION_INSERT;
import static android.content.Intent.ACTION_VIEW;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Pop extends Activity{


    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.popupwindow);
        DisplayMetrics dm=new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int width=dm.widthPixels;
        int height=dm.heightPixels;
        getWindow().setLayout((int) (width*0.8),(int)(height*0.4));


        Button navi=(Button)findViewById(R.id.navi);
        Button sch=(Button)findViewById(R.id.sch);

        Intent intent = getIntent();
        String name=intent.getStringExtra("msg_key2");
        String location=intent.getStringExtra("msg_key1");

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String email = user.getEmail();
       navi.setOnClickListener(new View.OnClickListener(){

           @Override
           public void onClick(View v) {
               Intent intent=new Intent(ACTION_VIEW, Uri.parse(location));
               intent.setPackage("com.google.android.apps.maps");
               startActivity(intent);
           }
       });

       sch.setOnClickListener(new View.OnClickListener(){

           @Override
           public void onClick(View v) {
              Intent intent=new Intent(ACTION_INSERT);
              intent.setData(CalendarContract.Events.CONTENT_URI);
              intent.putExtra(CalendarContract.Events.TITLE,name+" Trek");
              intent.putExtra(CalendarContract.Events.DESCRIPTION,"\"It’s not the mountain we conquer, but ourselves.\" -Sir Edmund Hillary");
              intent.putExtra(CalendarContract.Events.EVENT_LOCATION,location);
              startActivity(intent);
           }
       });



    }
}
