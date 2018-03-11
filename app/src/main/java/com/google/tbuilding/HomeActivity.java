package com.google.tbuilding;

import android.os.*;
import android.app.*;
import android.support.annotation.NonNull;
import android.widget.*;
import android.view.View.*;
import android.view.*;
import android.content.*;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class HomeActivity extends Activity
{
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
	SharedPreferences sp;
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home);

		mAuth = FirebaseAuth.getInstance();
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if (firebaseAuth.getCurrentUser() == null) {
                    Intent signInIntent = new Intent(HomeActivity.this, SignInActivity.class);
                    signInIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(signInIntent);
                }
            }
        };

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
				// startActivity(new Intent(getApplicationContext(), SignInActivity.class));
				// finish();
				mAuth.signOut();
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
		referButton.setOnClickListener(new OnClickListener(){
			public void onClick(View v){
				startActivity(new Intent(getApplicationContext(), AddPetActivity.class));
			}
		});
	}

	@Override
	protected void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }
}
