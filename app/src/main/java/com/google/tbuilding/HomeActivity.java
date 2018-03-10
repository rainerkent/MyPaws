package com.google.tbuilding;
import android.os.*;
import android.app.*;
import android.widget.*;
import android.view.View.*;
import android.view.*;
import android.content.*;

public class HomeActivity extends Activity
{
	SharedPreferences sp;
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home);

		sp = getSharedPreferences(getPackageName(), Activity.MODE_PRIVATE);
		String userName = sp.getString("USER_NAME", null);
		TextView name = findViewById(R.id.user_name);
		name.setText(userName);

		TextView logout = findViewById(R.id.log_out);
		logout.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {
				SharedPreferences.Editor editor = sp.edit();
				editor.clear();
				editor.apply();
				startActivity(new Intent(getApplicationContext(), SignInActivity.class));
				finish();
			}
		});


		Button newClaimButton = findViewById(R.id.newclaim);
		newClaimButton.setOnClickListener(new OnClickListener(){
			public void onClick(View v){
				startActivity(new Intent(getApplicationContext(), NewClaimActivity.class));
			}
		});

		Button scoopButton = findViewById(R.id.scoop);
		scoopButton.setOnClickListener(new OnClickListener(){
			public void onClick(View v){
				startActivity(new Intent(getApplicationContext(), ScoopActivity.class));
			}
		});

		Button referButton = findViewById(R.id.refer);
		scoopButton.setOnClickListener(new OnClickListener(){
			public void onClick(View v){
				startActivity(new Intent(getApplicationContext(), AddPetActivity.class));
			}
		});
	}
}
