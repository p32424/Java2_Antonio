/*
 * project SpaceImageViewer
 * 
 * packager com.arianaantonio.spaceimageviewer
 * 
 * @author Ariana Antonio
 * 
 * date Jul 10, 2014
 * 
 */
package com.arianaantonio.spaceimageviewer;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONException;

import JSON.FileManager;
import JSON.ServiceClass;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Messenger;
import android.util.Log;
import android.view.Menu;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.arianaantonio.networkconnection.NetworkConnect;
//import com.arianaantonio.networkjar.networkConnection;


public class MainActivity extends Activity {
	
	//global variables
	
	Context mContext;

	public static ArrayList<HashMap> arrayForGridView = new ArrayList<HashMap>();
	private static FileManager fileManager = FileManager.getInstance();
	final MyHandler handler = new MyHandler(this);
	
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		ListView listView = (ListView) findViewById(R.id.listView1);
		
		mContext = this;
		//final buildJSON imageJSON = new buildJSON();
		setContentView(R.layout.activity_main);
		
		//checking network connection from JAR
		NetworkConnect networkConnection = new NetworkConnect();
		Boolean networkConn = networkConnection.connectionStatus(mContext);
		if (networkConn) {
			Toast.makeText(mContext, "You are connected", Toast.LENGTH_LONG).show(); 
		} else {
			Toast.makeText(mContext, "You are NOT connected", Toast.LENGTH_LONG).show();
		}
		
		//empty listview so it doesn't append data
		//arrayForGridView.removeAll(arrayForGridView);
			
		//final MyHandler handler = new MyHandler(this);
		
		//getData();
		
		//ArrayAdapter<String> listAdapter = new ArrayAdapter<String>(mContext, android.R.layout.simple_list_item_1, arrayForGridView);
		
				
				//try {
					/*
					//creating listview
					ArrayAdapter<String> listAdapter = new ArrayAdapter<String>(mContext, android.R.layout.simple_list_item_1, arrayForGridView);
					ListView listView = (ListView) findViewById(R.id.listView1);
					listView.setAdapter(listAdapter);
					
					//check for network when trying to pull data
					if (networkConn) {
						//grab data from type selected and set to listview array
						
					
						final ArrayList<String> arrayForImageURL = new ArrayList<String>();
						/*
						for (int i = 0; i < imageTypeArray.length(); i++) {
							
							//Log.v(typeSelected, "Image array test:" + imageTypeArray.getJSONObject(i).getString("url_thumb"));
							String urlString = imageTypeArray.getJSONObject(i).getString("url_regular");
							String titleString = imageTypeArray.getJSONObject(i).getString("title");
							arrayForImageURL.add(urlString);
							arrayForGridView.add(titleString);
							
						}
							//onclick for listview
							listView.setOnItemClickListener(new OnItemClickListener() {

								@Override
								public void onItemClick(AdapterView<?> parent, View view,
										int position, long id) {
									
									//grab item selected
									String imageSelected = arrayForGridView.get(position);
									
									//pull corresponding URl
									String pullImage = arrayForImageURL.get(position);
									Log.v(pullImage, "Image pulled: " + pullImage);
									
									//set smartimageview to URL
									final SmartImageView imageView = (SmartImageView) findViewById(R.id.my_image);
									imageView.setImageUrl(pullImage);
									Log.v(imageSelected, "Image Selected:" + imageSelected);
								
									
								}

							});
					
					} else {
						//if no network connection
						arrayForGridView.removeAll(arrayForGridView);
						listAdapter.notifyDataSetChanged();
						Toast.makeText(mContext, "You are NOT connected", Toast.LENGTH_LONG).show();
					}
				} catch (JSONException e) {
					e.printStackTrace();
				
				}*/
				
		
			} 
	
	private static class MyHandler extends Handler {
		
		private final WeakReference<MainActivity> myActivity;
		
		public MyHandler(MainActivity activity) {
			myActivity = new WeakReference<MainActivity>(activity);
		}
		
		public void messageHandler(Message message) {
			MainActivity activity = myActivity.get();
			if (activity !=null) {
				
				Object objectReturned = message.obj;
				String filename = objectReturned.toString();
				if (message.arg1 == RESULT_OK && objectReturned !=null) {
					Log.i("Main Activity", "Message handler");
					String fileContent = fileManager.readStringFile(activity, filename);
					JSONArray jsonArray;
					try {
						jsonArray = new JSONArray(fileContent);
						activity.displayData(jsonArray);
					} catch (JSONException e) {

						e.printStackTrace();
					}
					
				}
			}
		}
	}
	
	public void getData() {
		Messenger messenger = new Messenger(handler);
		
		Intent getIntent = new Intent(this, ServiceClass.class);
		getIntent.putExtra("messenger", messenger);
		startService(getIntent);
	}
	
	public void displayData(JSONArray jsonArray) {
		
		for (int i = 0; i < jsonArray.length(); i++) {
			HashMap<String, String> displayText = new HashMap<String, String>();
			
			try {
				displayText.put("title", jsonArray.getString(1));
				displayText.put("user", jsonArray.getString(2));
				displayText.put("imaging_cameras", jsonArray.getString(3));
			} catch (JSONException e) {
				Log.e("Error displaying data in listview", e.getMessage().toString());
				e.printStackTrace();
			}
			arrayForGridView.add(displayText);
			
			//SimpleAdapter adapter = new SimpleAdapter(this, arrayForGridView, R.layout.image_list,
					//new String[] {"title", "user", "imaging_cameras"}, new int[] {R.id.title, R.id.user, R.id.camera});
			
			//listView.setAdapter(adapter);
			
		}
	}
			 

			
			 
	
		
	


	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
