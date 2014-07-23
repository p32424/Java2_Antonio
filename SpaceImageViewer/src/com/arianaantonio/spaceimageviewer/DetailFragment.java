package com.arianaantonio.spaceimageviewer;

import java.io.Serializable;
import java.util.HashMap;


import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;

import com.loopj.android.image.SmartImageView;

public class DetailFragment extends Fragment {
	RatingBar ratingBar;
	float rating;
	Button hdButton;
	TextView titleView;
	TextView userView;
	TextView cameraView;
	String hdUrl;
	String title;
	SmartImageView imageView;
	View view;
	private Context context; 
	private ParentListener listener;
	public interface ParentListener {
		
	}
	@Override
	public void onAttach(Activity activity) {
		// TODO Auto-generated method stub
		super.onAttach(activity);
		try {
			listener = (ParentListener) activity;
		} catch (ClassCastException e) {
			throw new ClassCastException(activity.toString() + "class does not implement fragment interface");
		}
	}
	@Override 
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		context = getActivity();
	} 
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		view = inflater.inflate(R.layout.details_activity, container);
	
		titleView = (TextView) view.findViewById(R.id.title);
		userView = (TextView) view.findViewById(R.id.userName);
		cameraView = (TextView) view.findViewById(R.id.cameraType);
		hdButton = (Button) view.findViewById(R.id.button1);
		ratingBar = (RatingBar) view.findViewById(R.id.ratingBar);
		ratingBar.setOnTouchListener(new OnTouchListener() {
			
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				rating = ratingBar.getRating();
				//listener.setRating(rating);
				String ratingValue = String.valueOf(rating);
				Log.i("Favorite", ratingValue);
				
				return false;
			}
		});
		
		return view;
}
	public void displayDetails(Bundle bundle) {
		//final SmartImageView imageView = (SmartImageView) view.findViewById(R.id.my_image);
		//imageView.setImageUrl(url);
		Log.i("Detail Fragment", "Bundle: " + bundle);
		//bundle = this.getIntent().getExtras();
		Serializable result = bundle.getSerializable("clicked data");
		HashMap<String, String> data = (HashMap<String, String>) result;
		Log.i("Detail Fragment", "HashMap" + data);
		String username = data.get("user");
		String url = data.get("url");
		title = data.get("title");
		hdUrl = data.get("hdImage");
		String camera = data.get("imaging_cameras"); 
		Log.i("Detail Fragment", "User: " + username);
		//Log.i("Detail Activity", "Brought over: " +username+ " " +url+ " " +title+ " " +camera);
	
		/*
		titleView = (TextView) view.findViewById(R.id.title);
		titleView.setText(title); 
		userView.setText(username);
		cameraView.setText(camera); 
		imageView = (SmartImageView) view.findViewById(R.id.my_image);
		imageView.setImageUrl(url);
		
		hdButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Uri website = Uri.parse(hdUrl);
				Intent websiteIntent = new Intent(Intent.ACTION_VIEW, website);
				startActivity(websiteIntent);
				
			}
		});*/
	}
}
