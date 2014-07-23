package com.arianaantonio.spaceimageviewer;

import java.util.ArrayList;
import java.util.HashMap;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
//import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.SimpleAdapter;

public class MainFragment extends Fragment implements OnItemClickListener{

	private ArrayList<HashMap<String, String>> myData = new ArrayList<HashMap<String, String>>();
	private ListView listView;
	private Context context; 
	private ParentListener listener;
	public interface ParentListener {
		//void displayDetailedData(Intent intent, int position);
		void startActivityForResult(Intent intent, int position);
	
	}
	public MainFragment() {
		context = getActivity();
	}
	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		try {
			listener = (ParentListener) activity;
		} catch (ClassCastException e) {
			throw new ClassCastException(activity.toString() + "class does not implement fragment interface");
		}
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		//View view = inflater.inflate(R.layout.activity_main, container);
		
		//listView = (ListView) view.findViewById(R.id.listView1);
		/*
		if (savedInstanceState != null) {
			Log.i("Main Activity", "working in first saved");
			myData = (ArrayList<HashMap<String, String>>) savedInstanceState.getSerializable("saved"); 
			
			if (myData != null) {
				Log.i("Main Activity", "work in mydata not null");
				Log.i("Main Activity", "Saved Instance");
				SimpleAdapter adapter = new SimpleAdapter(context, myData, R.layout.advance_listview,
						new String[] {"title", "user", "imaging_cameras"}, new int[] {R.id.title, R.id.user, R.id.camera});
				listView = (ListView) view.findViewById(R.id.listView1);
				listView.setAdapter(adapter);
				Log.i("Main Activity", "working after my data not null"); 
				
			} else {
				Log.i("Main Activity", "Did not save");
			}
		} else {
			Log.i("Main Activity", "No saved instance");
		}*/
		View view = inflater.inflate(R.layout.activity_main, container);
		//Log.i("Main Fragment", "working before call");
		//MainFragment newFragment = new MainFragment();
	
		//newFragment.getArguments().toString(); 
		//String user = this.getArguments().getString("user"); 
		//Log.i("Main Fragment", "working after call");
		//Log.i("In fragment", "user: " + user);
		//View view = inflater.inflate(R.layout.activity_main, container);
		//Log.i("Main Fragment", "working after call2");
		listView = (ListView) view.findViewById(R.id.listView1);
		return view;
	 
	
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
		// TODO Auto-generated method stub
		HashMap<String, String> selectedListItem = myData.get(position);
		String usernameString = (String)selectedListItem.get("user");
		String cameraString = (String)selectedListItem.get("imaging_cameras");
		String titleString = (String)selectedListItem.get("title");
		String urlString = (String)selectedListItem.get("url");
		String hdString = (String)selectedListItem.get("hdImage");
		Log.i("Item Selected", "name: " +usernameString);
	} 
	public void displayData(ArrayList<HashMap<String, String>> data) {
		//(String user, String camera, String title, String url, String hd)
		/*
		Log.i("Display Data", user);
		HashMap<String, String> displayText = new HashMap<String, String>();
		
		displayText.put("title", title);
		displayText.put("user", user);
		displayText.put("imaging_cameras", camera);
		displayText.put("url", url);
		displayText.put("hdImage", hd);
		Log.i("Main Fragment", "working3");
		
		myData.add(displayText);*/ 
		//context = getActivity();
		myData = data; 
		Log.i("My Data", myData.toString());   
		Log.i("Main Fragment", "working4");
		SimpleAdapter adapter = new SimpleAdapter(context, myData, R.layout.advance_listview, 
				new String[] {"title", "user", "imaging_cameras"}, new int[] {R.id.title, R.id.user, R.id.camera});
		Log.i("Main Fragment", "working5");
		//listView = (ListView) view.findViewById(R.id.listView1);
		listView.setAdapter(adapter);
	}
	public void startMainFragment(String urlString,String title,String user,String camera, String hd) {
		/*
		HashMap<String, String> displayText = new HashMap<String, String>();
		
		displayText.put("title", title);
		displayText.put("user", user);
		displayText.put("imaging_cameras", camera);
		displayText.put("url", urlString);
		displayText.put("hdImage", hd);
		Log.i("Main Fragment", "working3");
		
		myData.add(displayText);
		Log.i("Main Fragment", "working4");
		SimpleAdapter adapter = new SimpleAdapter(context, myData, R.layout.advance_listview,
				new String[] {"title", "user", "imaging_cameras"}, new int[] {R.id.title, R.id.user, R.id.camera});
		Log.i("Main Fragment", "working5");
		//listView = (ListView) view.findViewById(R.id.listView1);
		listView.setAdapter(adapter);*/
		/*
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
			}
		}*/
	}
	/*
	//saving instance state of listView
		@Override
		protected void onSaveInstanceState(Bundle outState) {
			super.onSaveInstanceState(outState);
			
			if (myData != null && !myData.isEmpty()) {
				outState.putSerializable("saved", (Serializable) myData);
				Log.i("Main Activity", "Saving instance state");
			}
		}*/
}
