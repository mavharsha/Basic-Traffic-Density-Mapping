/*
* Basic Traffic Density Mapping
*
* By Sree Harsha Mamilla and Arjun Palla
*
*/


package com.maverick.start2;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import android.os.Handler;
import android.os.Message;

public class MyPoster {
	public static final int HTTP_POST_OK = 1;
	public static final int HTTP_POST_ERROR = 2;

	private HttpClient client;
	private HttpPost post;
	private HttpResponse response;
	
	private Thread thread;
	
	//constructor
	public MyPoster(String link){
		
		client = new DefaultHttpClient();
		System.out.println("client created");
		post = new HttpPost(link);
	}
	
	public void Poster(final ArrayList<NameValuePair> data, final Handler handler){
		
		thread = new Thread(){
		
		public void run(){
			Message message = new Message();
		try {
			//DataPreparation
			post.setEntity(new UrlEncodedFormEntity(data, HTTP.UTF_8));
			System.out.println("data  prepared");
			//execute
			String s = data.toString();
			System.out.println("datavalue"+s);
			
			response = client.execute(post); 		// doubt this line!
			System.out.println("data excuted");
			
			message.what = HTTP_POST_OK;
			
			message.obj = EntityUtils.toString(response.getEntity(), HTTP.UTF_8);
			System.out.println("message write done");
			
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			message.what = HTTP_POST_ERROR;

		} catch (ClientProtocolException e) {
			e.printStackTrace();
			message.what = HTTP_POST_ERROR;

		} catch (IOException e) {
			e.printStackTrace();
			message.what = HTTP_POST_ERROR;

		}
		finally{
			handler.sendMessage(message);	
			}
		
		}
		};
		thread.start();
	}
}
