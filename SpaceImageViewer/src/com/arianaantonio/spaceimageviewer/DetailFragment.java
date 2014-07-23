package com.arianaantonio.spaceimageviewer;

import java.util.HashMap;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;

import com.loopj.android.image.SmartImageView;

public class DetailFragment extends Fragment {
	private RatingBar ratingBar;
	float rating;
	private Button hdButton;
	private TextView titleView;
	private TextView userView;
	private TextView cameraView;
	private String hdUrl;
	private String title;
	private SmartImageView imageView;
	View view;
	
	@SuppressWarnings("unused")
	private Context context; 
	private ParentListener listener;
	public interface ParentListener {
		 //void passRatingInfo(Intent dataPassing);
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
		Log.i("Detail Activity", "working5");
		view = inflater.inflate(R.layout.details_activity, container);
	
		titleView = (TextView) view.findViewById(R.id.title);
		//titleView.setText("Test title");
		userView = (TextView) view.findViewById(R.id.userName);
		cameraView = (TextView) view.findViewById(R.id.cameraType);
		hdButton = (Button) view.findViewById(R.id.button1);
		imageView = (SmartImageView) view.findViewById(R.id.my_image);
		ratingBar = (RatingBar) view.findViewById(R.id.ratingBar);
		ratingBar.setRating(0);
		/*
		ratingBar.setOnTouchListener(new OnTouchListener() {
			
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				
				rating = ratingBar.getRating();
				String ratingValue = String.valueOf(rating);
				Log.i("Favorite", ratingValue);
				Intent dataPassing = new Intent(); 
				dataPassing.putExtra("title", title);
				dataPassing.putExtra("rating", rating);
				listener.passRatingInfo(dataPassing);
				
				return false;
			}
		});*/
		//passRating();
		
		Bundle data = getActivity().getIntent().getExtras();
		Log.i("Detail Activity", "Create bundle: " +data);
		  if(data != null){
		   Log.i("Detail Activity", "working5");
		  
		   //displayDetails(userString, titleString, urlString, cameraString,hdString);
		   displayDetails(data);
		 }
		return view;
}
	/*
	public void passRating() { 
		Intent dataPassing = new Intent();
		dataPassing.putExtra("title", title);
		dataPassing.putExtra("rating", rating);
		listener.passRatingInfo(dataPassing);
		
	}*/

	public void displayDetails(Bundle bundle) {
		 Log.i("Detail Fragment", "Bundle: " + bundle);
		 //final SmartImageView imageView = (SmartImageView)
		//view.findViewById(R.id.my_image);
		  //imageView.setImageUrl(url);
		  //Log.i("Detail Fragment", "Bundle: " + bundle);
		  //bundle = this.getIntent().getExtras();
		//  HashMap<String, String> data = (HashMap<String, String>) result;
		//  Log.i("Detail Fragment", "HashMap" + data);
		//  String username = data.get("user");
		//  String url = data.get("url");
		 
		  @SuppressWarnings("unchecked")
		 HashMap<String, String> data = (HashMap<String, String>)bundle.getSerializable("details");
		  Log.i("Detail Fragment", "data" +data);
		 // titleView = (TextView) view.findViewById(R.id.title);
		  Log.i("Detail Fragment", "titleView" +titleView);
		  if (titleView == null) {
			  Log.i("Detail Fragment", "titleView is null");
		  } else {
			  titleView.setText(data.get("title")); 
			  userView.setText(data.get("user"));
			  cameraView.setText(data.get("imaging_cameras")); 
			  imageView.setImageUrl(data.get("url")); 
			  hdUrl = data.get("hdImage");
			  //hdUrl.setText(data.get("hdUrl"));
			  hdButton.setOnClickListener(new OnClickListener() {
					
			 @Override
			 public void onClick(View v) {
						Uri website = Uri.parse(hdUrl);
						Intent websiteIntent = new Intent(Intent.ACTION_VIEW, website);
						startActivity(websiteIntent);
						
					}
				});
		  }
		  //titleView.setText(data.get("title"));
		  //titleView.setText("Title two");
		  Log.i("Detail Fragment", "working 7");
		 /*
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
		titleView.setText(title); 
		userView.setText(username);
		cameraView.setText(camera); 
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
