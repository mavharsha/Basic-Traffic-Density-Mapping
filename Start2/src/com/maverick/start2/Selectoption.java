/*
* Basic Traffic Density Mapping
*
* By Sree Harsha Mamilla and Arjun Palla
*
*/
package com.maverick.start2;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;
import android.app.Activity;
import android.content.Intent;

public class Selectoption extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.startup);
        
       
        
        Button SendUpdate =(Button)findViewById(R.id.button1);
        Button GetUpdate =(Button)findViewById(R.id.button2);

        SendUpdate.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent it= new Intent(Selectoption.this, UpdateTaffic.class);
		           startActivity(it);
				
			}
		});

        
        GetUpdate.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
			Toast.makeText(getApplicationContext(), "Get", Toast.LENGTH_LONG).show();
			Intent it1 = new Intent(Selectoption.this, GetUpdate.class);
				startActivity(it1);
			}
		});

}
}