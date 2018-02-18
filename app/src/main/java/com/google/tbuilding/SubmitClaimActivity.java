package com.google.tbuilding;

import android.app.*;
import android.content.*;
import android.graphics.*;
import android.os.*;
import android.provider.*;
import android.widget.*;
import android.view.View.*;
import android.view.*;
import android.widget.LinearLayout.*;

public class SubmitClaimActivity extends Activity
{
	private ImageView mPetImage;
    private TextView mPetName;
	private GridLayout mGridLayout;
	
	private int mImageResource;
	private String petName;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_submit_claim);

        mPetImage = (ImageView) findViewById(R.id.pet_image);
        mPetName = (TextView) findViewById(R.id.pet_claim);
		mGridLayout = (GridLayout) findViewById(R.id.grid_layout);

        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            mPetName.setText("Claim for " + extras.getString("PET_NAME"));
            mPetImage.setImageResource(extras.getInt("PET_IMAGE"));
			mImageResource = extras.getInt("PET_IMAGE");
			petName = extras.getString("PET_NAME");
        } else {
            // newString= (String) savedInstanceState.getSerializable("STRING_I_NEED");
        }
		
		Button newClaimButton = (Button) findViewById(R.id.btn_new_claim);
		newClaimButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				pickImage();
			}
		});
		Button submitButton = (Button) findViewById(R.id.button_submit_claim);
		submitButton.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v){
				Intent i =  new Intent(getApplicationContext(), VerifyClaimActivity.class);
				i.putExtra("PET_NAME", petName);
				i.putExtra("PET_IMAGE", mImageResource);
				startActivity(i);
			}
		});
	}

    public void pickImage() {
        Intent intent = new Intent(Intent.ACTION_PICK,
                MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        intent.setType("image/*");
        intent.putExtra("crop", "true");
        intent.putExtra("scale", true);
        intent.putExtra("outputX", 256);
        intent.putExtra("outputY", 256);
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        intent.putExtra("return-data", true);
        startActivityForResult(intent, 1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != RESULT_OK) {
            return;
        }
        if (requestCode == 1) {
            final Bundle extras = data.getExtras();
            if (extras != null) {
                Bitmap newProfilePic = extras.getParcelable("data");
			    ImageView claimImage = new ImageView(this);
				claimImage.setImageBitmap(newProfilePic);
				GridLayout.LayoutParams params = new GridLayout.LayoutParams();
				params.height = 180;
				params.width = 180;
				params.leftMargin = 2;
				params.rightMargin = 2;
				params.topMargin = 5;
				params.setGravity(Gravity.CENTER);
				claimImage.setLayoutParams(params);
				mGridLayout.addView(claimImage);
            }
        }
    }
}
