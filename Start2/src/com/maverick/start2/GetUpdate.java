/*
* Basic Traffic Density Mapping
*
* By Sree Harsha Mamilla and Arjun Palla
*
*/

package com.maverick.start2;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.speech.tts.TextToSpeech;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class GetUpdate extends Activity {
	
	
	double dlat1,dlong1,lat1,long1,lat2,long2,lat3,long3,lat4,long4;
	int den1, den2, den3, den4;
	Location location1;
	LocationManager locationmanager;
	LocationListener listener;
	TextView addressList1, addressList2, addressList3, addressList4;
	TextView latlong;
	Geocoder gc;
	List<Address> address1,address2,address3,address4;
	StringBuilder result;
	TextToSpeech tts;
	String speak1,speak2,speak3,speak4, density;
	Runnable runobj, runobj1;
	private Handler mHandler = new Handler();
	private Handler mHandler1 = new Handler();
	private MyPoster poster;
	private static String uri = "http://49.205.192.246/trial/get.php";
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.getupdates);
		
		Button get =(Button)findViewById(R.id.button1);
		addressList1 = (TextView)findViewById(R.id.textView2);
		addressList2 = (TextView)findViewById(R.id.TextView01);
		addressList3 = (TextView)findViewById(R.id.TextView02);
		addressList4 = (TextView)findViewById(R.id.TextView03);
		latlong = (TextView)findViewById(R.id.textView1);
		
		Toast.makeText(getApplicationContext(), "wait", Toast.LENGTH_LONG).show();
        
		
		locationmanager= (LocationManager)getSystemService(Context.LOCATION_SERVICE);
		listener = new MyLocationListener();
		locationmanager.requestLocationUpdates(LocationManager.GPS_PROVIDER,1000*60,10,listener);

			gc = new Geocoder(this, Locale.getDefault());
			
			//text to speech initialization
			tts = new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
				
				public void onInit(int status) {
				if(status != TextToSpeech.ERROR){
						tts.setLanguage(Locale.UK);
					}
				}
				
				
			}); // text to speech initialization
			 
			get.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					addressList1.setText("");
					addressList2.setText("");
					addressList3.setText("");
					addressList4.setText("");
					updateAddress();
					
				}
			});
			
			//Poster!
			runobj = new Runnable() {
				public void run()
				{
					if(dlat1 != 0 || dlong1 != 0)
					{
					poster = new MyPoster(uri);
					String latitude = Double.toString(dlat1);
					String longitude = Double.toString(dlong1);
					
					
					ArrayList<NameValuePair> data = new ArrayList<NameValuePair>();
					
					data.add(new BasicNameValuePair("latitude", latitude));
					data.add(new BasicNameValuePair("longitude", longitude));
					
					poster.Poster(data , new Handler(){
						
						public void handleMessage(Message msg) {
							
							switch(msg.what){
							case MyPoster.HTTP_POST_OK:
							
							
							try {
								JSONObject object = new JSONObject((String)msg.obj);
								

								lat1 = Double.parseDouble(object.getString("latitude1"));
								long1 =Double.parseDouble(object.getString("longitude1"));
								lat2 =Double.parseDouble(object.getString("latitude2"));
								long2 =Double.parseDouble(object.getString("longitude2"));
								lat3 = Double.parseDouble(object.getString("latitude3"));
								long3 =Double.parseDouble(object.getString("longitude3"));
								lat4 = Double.parseDouble(object.getString("latitude4"));
								long4 =Double.parseDouble(object.getString("longitude4"));
								
								den1 = Integer.parseInt(object.getString("density1"));
								den2 = Integer.parseInt(object.getString("density2"));
								den3 = Integer.parseInt(object.getString("density3"));
								den4 = Integer.parseInt(object.getString("density4"));
								
								 
								Toast.makeText(getApplicationContext(), "Completed" , Toast.LENGTH_SHORT).show();
								Toast.makeText(getApplicationContext(), "Press Get!" , Toast.LENGTH_SHORT).show();
								
							}catch(JSONException e){
							Toast.makeText(getApplicationContext(), "Unable to convert JSON", Toast.LENGTH_SHORT).show();
							}
							
							
								break;
							case MyPoster.HTTP_POST_ERROR:
							
								Toast.makeText(getApplicationContext(), "error", Toast.LENGTH_SHORT).show();

								break;
								}
						}
						
					}); 
					
					Toast.makeText(getApplicationContext(), "Sent", Toast.LENGTH_LONG).show();
					
					}
					else {
						Toast.makeText(getApplicationContext(), "Latitude and Longitude are Null", Toast.LENGTH_LONG).show();
					}
				}
				
				
				};
				mHandler.postDelayed(runobj, 20000);
				//Poster!
			
			
			
			//handler for 5 mins post delay refreshment of the activity
		    runobj1 = new Runnable() {
			public void run()
			{
				dostuff();

			}
			};
			mHandler1.postDelayed(runobj1, 100000);
	
	} //end of oncreate
	
	
	 protected void dostuff() {
	 Intent intent = getIntent();
     finish();
     startActivity(intent);
     Toast.makeText(getApplicationContext(), "refreshed", Toast.LENGTH_LONG).show();
 
	 }


	
	protected void onPause() {
		// TODO Auto-generated method stub
		 if(tts != null){
			 tts.stop();
			 tts.shutdown();
		}
		finish();
		super.onPause();
	}
	 
	public void onBackPressed() {
        super.onBackPressed();
        finish();
        super.onBackPressed();
        mHandler.removeCallbacks(runobj);
        mHandler1.removeCallbacks(runobj1);
	 	}
	
	


	@Override
	protected void onStop() {
		
		mHandler.removeCallbacks(runobj);
		mHandler1.removeCallbacks(runobj1);

		super.onStop();
	}

	//Geocoding
	protected void updateAddress() {
		
		 try
		 
		 {  
			 address1 = gc.getFromLocation(lat1, long1, 1);
			 address2 = gc.getFromLocation(lat2, long2, 1);
			 address3 = gc.getFromLocation(lat3, long3, 1);
			 address4 = gc.getFromLocation(lat4, long4, 1);
		} catch (IOException e) {
			
			System.out.println("Geocoder Problem!");
		}
			switch(den1){
			case 1 : density = "low";
					break;
			case 2 : density = "Moderate";
			break;
			case 3 : density = "High";
			break;
			}
		
		if(address1.size() > 0 ){
			
			for (Address addresses : address1) {
		       addressList1.append("\n"+ "Traffic density ahead at " + addresses.getAddressLine(0) +", \n" + addresses.getLocality()+ " is " +density+"                   ");
		    }
			
			try{
				speak1 = addressList1.getText().toString();
			 
			}
			catch(Exception e){
				System.out.println("address forming error" + e.toString());
			}
		}
		else{
			System.out.println("Null1");
		}
		
		switch(den2){
		case 1 : density = "low";
				break;
		case 2 : density = "Moderate";
		break;
		case 3 : density = "High";
		break;
		}
		
		if(address2.size() > 0 ){
			
			for (Address addresses : address2) {
		       addressList2.append("\n"+ "Traffic density behind at "+ addresses.getAddressLine(0) +", \n" + addresses.getLocality()+ " is " +density+"                   ");
		    }
			
			try{
				speak2 = addressList2.getText().toString();
			 
			}
			catch(Exception e){
				System.out.println("address forming error" + e.toString());
			}
		}
		else{
			System.out.println("Null2");
		}
		
		switch(den3){
		case 1 : density = "low";
				break;
		case 2 : density = "Moderate";
		break;
		case 3 : density = "High";
		break;
		}
		
		
		if(address3.size() > 0 ){
			
			for (Address addresses : address3) {
		       addressList3.append("\n"+ "Traffic density to your right at " + addresses.getAddressLine(0) +", \n" + addresses.getLocality()+ " is " +density+"                   ");
		    }
			
			try{
				speak3 = addressList3.getText().toString();
			 
			}
			catch(Exception e){
				System.out.println("address forming error" + e.toString());
			}
		}
		else{   
			System.out.println("Null3");
		}
		
		if(address4.size() > 0 ){
			
			for (Address addresses : address4) {
		       addressList4.append("\n"+ "Traffic density to your left at " + addresses.getAddressLine(0) +", \n" + addresses.getLocality()+ " is " +density+"                   ");
		    }
			
			try{
				speak4 = addressList4.getText().toString();
			 
			}
			catch(Exception e){
				System.out.println("address forming error" + e.toString());
			}
		}
		else{
			System.out.println("Null4");
		}
		
		try{
		speak1 = speak1.concat(speak2);
		speak1 = speak1.concat(speak3);
		speak1 = speak1.concat(speak4);
		
		tts.speak(speak1, TextToSpeech.QUEUE_FLUSH, null);
		}catch(Exception e){
			System.out.println("speak broke");
		}

		
	}
	//GeoCoding

	
	//Inner Class
	class  MyLocationListener implements LocationListener{
		
		@Override
		public void onLocationChanged(Location location) {
			
			location1=location;
			dlat1 = location1.getLatitude();
			dlong1 = location1.getLongitude();
			latlong.setText("Latitude "+dlat1 + ", Longitude " + dlong1);
			
		}

		@Override
		public void onProviderDisabled(String provider) {
			// TODO Auto-generated method stub
			
		}@Override
		public void onProviderEnabled(String provider) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onStatusChanged(String provider, int status,Bundle extras) {
			// TODO Auto-generated method stub
			
		}
		
	} //end of myLocationListener class 
 //Inner class

	 
}
