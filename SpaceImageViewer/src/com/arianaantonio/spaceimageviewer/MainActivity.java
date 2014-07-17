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

import json.FileManager;
import json.ServiceClass;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Messenger;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.arianaantonio.networkconnection.NetworkConnect;
//import com.arianaantonio.networkjar.networkConnection;


public class MainActivity extends Activity {
	
	//global variables
	
	Context mContext;
	FileManager mFile;
	String mFileName = "ImageFile.txt";
	ListView listView;

	//ArrayList<HashMap> arrayForGridView = new ArrayList<HashMap>();
	private static FileManager fileManager = FileManager.getInstance();
	final MyHandler handler = new MyHandler(this);
	ArrayList<HashMap<String, String>> myData = new ArrayList<HashMap<String, String>>();
	
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		mContext = this;
		mFile = FileManager.getInstance();
		
		setContentView(R.layout.activity_main);
		
		//checking network connection from JAR
		NetworkConnect networkConnection = new NetworkConnect();
		Boolean networkConn = networkConnection.connectionStatus(mContext);
		if (networkConn) {
			Toast.makeText(mContext, "You are connected", Toast.LENGTH_LONG).show(); 
		} else {
			Toast.makeText(mContext, "You are NOT connected", Toast.LENGTH_LONG).show();
		}
			
		final MyHandler handler = new MyHandler(this);
		
		getData(handler);
	
	} 
	
	private static class MyHandler extends Handler {
		
		private final WeakReference<MainActivity> myActivity;
		
		public MyHandler(MainActivity activity) {
			myActivity = new WeakReference<MainActivity>(activity);
		}
		@Override
		public void handleMessage(Message message) {
			MainActivity activity = myActivity.get();
			if (activity !=null) {
				
				Object objectReturned = message.obj;
				String filename = objectReturned.toString();
				Log.i("Filename", filename);
				
				if (message.arg1 == RESULT_OK && objectReturned !=null) {
					
					Log.i("Main Activity", "Message handler");
					String fileContent = fileManager.readStringFile(activity, filename);
					Log.i("Main Activity", "File content: " +fileContent);
					
					
					Log.i("Main Activity", "working");
					try {
						//Log.i("Main Activity", "Handler working here");
						JSONObject json = new JSONObject(fileContent);
						Log.i("Main Activity", "Handler working here");
						JSONArray imagesArray = json.getJSONArray("objects");
						activity.displayData(imagesArray);
					} catch (JSONException e) {
						Log.e("JSON Parser", "Error parsing data [" + e.getMessage()+"] "+fileContent);
						e.printStackTrace();
					}
					
				}
			}
		}
	}
	//start intent service to get the API data
	public void getData(Handler handler) {
		Messenger messenger = new Messenger(handler);
		
		Intent getIntent = new Intent(this, ServiceClass.class);
		getIntent.putExtra("messenger", messenger);
		startService(getIntent);
	}
	/**grab data from txt file, add each object value to the display Hashmap
	* set the values to the Simple Adapter to display on the listview
	*/
	public void displayData(JSONArray jsonArray) {
		
		Log.i("Main Activity", "working1");
		for (int i = 0; i < jsonArray.length(); i++) {
			HashMap<String, String> displayText = new HashMap<String, String>();
			Log.i("Main Activity", "working2");
			try {
				String urlString = jsonArray.getJSONObject(i).getString("url_regular");
				String title = jsonArray.getJSONObject(i).getString("title");
				String user = jsonArray.getJSONObject(i).getString("user");
				String camera = jsonArray.getJSONObject(i).getString("imaging_cameras");
				String hd = jsonArray.getJSONObject(i).getString("url_hd");
				Log.i("Returned objects", title+ " " +user+" " +camera+ " " +urlString);
				displayText.put("title", title);
				displayText.put("user", user);
				displayText.put("imaging_cameras", camera);
				displayText.put("url", urlString);
				displayText.put("hdImage", hd);
				Log.i("Main Activity", "working3");
			} catch (JSONException e) {
				Log.e("Error displaying data in listview", e.getMessage().toString());
				e.printStackTrace();
			}
			myData.add(displayText);
			Log.i("Main Activity", "working4");
			SimpleAdapter adapter = new SimpleAdapter(this, myData, R.layout.advance_listview,
					new String[] {"title", "user", "imaging_cameras"}, new int[] {R.id.title, R.id.user, R.id.camera});
			Log.i("Main Activity", "working5");
			listView = (ListView) findViewById(R.id.listView1);
			listView.setAdapter(adapter);
			
			listView.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> arg0, View arg1,
						int position, long arg3) {
					HashMap<String, String> selectedListItem = myData.get(position);
					String usernameString = (String)selectedListItem.get("user");
					String cameraString = (String)selectedListItem.get("imaging_cameras");
					String titleString = (String)selectedListItem.get("title");
					String urlString = (String)selectedListItem.get("url");
					String hdString = (String)selectedListItem.get("hdImage");
					Log.i("Item Selected", "name: " +usernameString);
					
					
					Intent detailActivity = new Intent(getBaseContext(), DetailsActivity.class);
					detailActivity.putExtra("user", usernameString);
					detailActivity.putExtra("title", titleString); 
					detailActivity.putExtra("url", urlString);
					detailActivity.putExtra("camera", cameraString);
					detailActivity.putExtra("hdImage", hdString);
					Log.i("Main Activity", "working6");
					startActivityForResult(detailActivity, 0);
					Log.i("Main Activity", "working7");
					
					
					
				}
				
			});
			
		}
	}
		
	protected void onActivityResult(int requestCode, int resultCode, Intent dataPassing) {
		Log.i("Main Activity", "Pulling passed data");
		
		if (resultCode == RESULT_OK && requestCode == 0) {
			if (dataPassing.hasExtra("rating") && dataPassing.hasExtra("title")) {
				Float rating = dataPassing.getExtras().getFloat("rating");
				String title = dataPassing.getExtras().getString("title");
				
				AlertDialog.Builder ratingDialog = new AlertDialog.Builder(this);
				
				if (rating.toString() == "0.0") {
					ratingDialog.setTitle(title).setMessage("You did not rate this as a favorite")
					.setNegativeButton("Ok", new DialogInterface.OnClickListener() {
					
						@Override
						public void onClick(DialogInterface dialog, int which) {
							dialog.dismiss();
						
						}
					});
				} else {
					ratingDialog.setTitle(title).setMessage("You rated this as a favorite")
					.setNegativeButton("Ok", new DialogInterface.OnClickListener() {
					
						@Override
						public void onClick(DialogInterface dialog, int which) {
							dialog.dismiss();
						
						}
					});
				}
				ratingDialog.create();
				ratingDialog.show();
				
				
			}
		}
	}

			
			 
	
		
	


	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
