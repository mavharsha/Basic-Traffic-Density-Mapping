/*
* Basic Traffic Density Mapping
*
* By Sree Harsha Mamilla and Arjun Palla
*
*/



package com.maverick.start2;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Toast;

public class Main extends Activity {
	 private Handler mHandler = new Handler();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        

        mHandler.postDelayed(new Runnable() {
            @Override
			public void run() {
            	 doStuff();
            }
        }, 5000);
    }

    
       void doStuff(){
       	Toast.makeText(getApplicationContext(), "yippee", Toast.LENGTH_LONG).show();

    	   Intent it= new Intent(Main.this, Selectoption.class);
           startActivity(it);
       }

    
}
