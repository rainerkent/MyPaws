package com.google.tbuilding;
import android.os.*;
import android.app.*;
import android.widget.*;
import android.view.View.*;
import android.view.*;
import android.content.*;

public class HomeActivity extends Activity
{
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home);
		
		Button newClaimButton = (Button) findViewById(R.id.newclaim);
		newClaimButton.setOnClickListener(new OnClickListener(){
			public void onClick(View v){
				startActivity(new Intent(v.getContext(), NewClaimActivity.class));
			}
		});
		
		Button scoopButton = (Button) findViewById(R.id.scoop);
		scoopButton.setOnClickListener(new OnClickListener(){
				public void onClick(View v){
					startActivity(new Intent(v.getContext(), ScoopActivity.class));
				}
			});
	}
}
