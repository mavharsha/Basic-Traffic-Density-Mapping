/*
* Basic Traffic Density Mapping
*
* By Sree Harsha Mamilla and Arjun Palla
*
*/

package com.maverick.start2;

import java.util.ArrayList;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import android.app.Activity;
import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

public class UpdateTaffic extends Activity{

	
	String s;
	double dlat,dlong;
	Location location;
	LocationManager lm;
	LocationListener listener;
	private MyPoster poster;
	private static String uri = "http://49.205.192.246/trial/add.php";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.updatetraffic);
		

		 lm = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
		 listener = new MyLocationListener();

		lm.requestLocationUpdates(LocationManager.GPS_PROVIDER,1000*60,10,listener);

        Button low =(Button)findViewById(R.id.Get);
        Button med =(Button)findViewById(R.id.button2);
        Button high =(Button)findViewById(R.id.button3);

 low.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Toast.makeText(getApplicationContext(),"clicked" , Toast.LENGTH_LONG).show();
				int s= 1;
			
				updateWithLocation(s);

				
			}
		});

 med.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Toast.makeText(getApplicationContext(),"clicked" , Toast.LENGTH_LONG).show();
				int s= 2;
				updateWithLocation(s);
				}

			
		});
 

 high.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				
				Toast.makeText(getApplicationContext(),"clicked" , Toast.LENGTH_LONG).show();
				int s= 3;
				updateWithLocation(s);

				}

			
		});
	}
		
	
	
	class  MyLocationListener implements LocationListener{
	
		@Override
		public void onLocationChanged(Location location) {
			// TODO Auto-generated method stub
							
			  dlat = location.getLatitude();
			 dlong = location.getLongitude();
			Toast.makeText(getApplicationContext(), "changed", Toast.LENGTH_LONG).show();

		}

		@Override
		public void onProviderDisabled(String provider) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onProviderEnabled(String provider) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onStatusChanged(String provider, int status,
				Bundle extras) {
			// TODO Auto-generated method stub
			
		}
		
	}
	
	
	
	public void updateWithLocation(int s) {
		// TODO Auto-generated method stub
		poster = new MyPoster(uri);
		
		
		String latitude = Double.toString(dlat);
		String longitude = Double.toString(dlong);
		String s1 = Integer.toString(s);
		
		ArrayList<NameValuePair> data = new ArrayList<NameValuePair>();
		
		data.add(new BasicNameValuePair("latitude", latitude));
		data.add(new BasicNameValuePair("longitude", longitude));
		data.add(new BasicNameValuePair("density", s1));
		
		poster.Poster(data , new Handler(){
			
			@Override
			public void handleMessage(Message msg) {
				
				switch(msg.what){
				case MyPoster.HTTP_POST_OK:
					
					Toast.makeText(getApplicationContext(), "Completed", Toast.LENGTH_SHORT).show();
				
					break;
				case MyPoster.HTTP_POST_ERROR:
					Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_SHORT).show();

					break;
					}
			}
			
		});
		
		Toast.makeText(getApplicationContext(),"type "+s+ " lat " +dlat+ " long " +dlong, Toast.LENGTH_LONG).show();
		
	}
		
		

}
