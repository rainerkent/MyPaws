package com.google.tbuilding;
import android.app.*;
import android.os.*;
import android.widget.*;

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
            mPetImage.setImageResource(extras.getInt("PET_IMAGE"));
        } 
	}
}
