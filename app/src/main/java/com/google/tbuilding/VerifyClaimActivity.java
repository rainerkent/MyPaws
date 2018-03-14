package com.google.tbuilding;

import android.app.*;
import android.content.*;
import android.os.*;
import android.view.*;
import android.widget.*;

import com.squareup.picasso.Picasso;

public class VerifyClaimActivity extends Activity
{
	private ImageView mPetImage;
	private TextView mPetName;
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_verify_claim);

		mPetImage = (ImageView) findViewById(R.id.pet_image);
        mPetName = (TextView) findViewById(R.id.pet_claim);

		if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            mPetName.setText("Claim for " + extras.getString("PET_NAME"));
            Picasso.with(getApplicationContext()).load(extras.getString("PET_IMAGE")).into(mPetImage);
            // mPetImage.setImageURI(extras.getString("PET_IMAGE"));
        }

        Button agreeButton = findViewById(R.id.agree);
        Button disagreeButton = findViewById(R.id.disagree);

        agreeButton.setOnClickListener( new View.OnClickListener() {
        	@Override
        	public void onClick(View v) {
        		Intent i = new Intent(getApplicationContext(), HomeActivity.class);
        		i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        		startActivity(i);
        		finish();
        	}
        });

        disagreeButton.setOnClickListener( new View.OnClickListener() {
        	@Override
        	public void onClick(View v) {
        		finish();
        	}
        });
	}
}
