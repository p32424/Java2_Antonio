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
import android.view.View.OnClickListener;
import android.widget.Button;
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
		//String storeString = "Test String";
		//mFile.writeStringFile(mContext, mFileName, storeString);
		
		
		listView = (ListView) findViewById(R.id.listView1);
		
		
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
			
		final MyHandler handler = new MyHandler(this);
		
		getData(handler);
		
		Button button = (Button) findViewById(R.id.button1);
		button.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent detailActivity = new Intent(getBaseContext(), DetailsActivity.class);
				startActivityForResult(detailActivity, 0);
				
			}
		});
		
	
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
						//JSONObject json = new JSONObject(fileContent.substring(fileContent.indexOf("{"), fileContent.lastIndexOf("}") + 1));
						JSONObject json = new JSONObject(fileContent);
						Log.i("Main Activity", "working");
						//JSONArray jsonArray = new JSONArray(fileContent);
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
		
		for (int i = 0; i < jsonArray.length(); i++) {
			HashMap<String, String> displayText = new HashMap<String, String>();
			
			try {
				String urlString = jsonArray.getJSONObject(i).getString("url_regular");
				String title = jsonArray.getJSONObject(i).getString("title");
				String user = jsonArray.getJSONObject(i).getString("user");
				String camera = jsonArray.getJSONObject(i).getString("imaging_cameras");
				
				displayText.put("title", title);
				displayText.put("user", user);
				displayText.put("imaging_cameras", camera);
				displayText.put("url", urlString);
				
			} catch (JSONException e) {
				Log.e("Error displaying data in listview", e.getMessage().toString());
				e.printStackTrace();
			}
			myData.add(displayText);
			
			SimpleAdapter adapter = new SimpleAdapter(this, myData, R.layout.image_list,
					new String[] {"title", "user", "imaging_cameras"}, new int[] {R.id.title, R.id.user, R.id.camera});
			
			listView.setAdapter(adapter);
			
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
