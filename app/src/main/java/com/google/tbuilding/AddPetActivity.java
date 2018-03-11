package com.google.tbuilding;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class AddPetActivity extends AppCompatActivity {

	private static final int GALLERY_REQUEST = 1;

    private Uri mImageUri = null;
    private ImageButton mImgSelect;
    private Button btnAdd;
    private EditText petName;
    private EditText petBreed;
    private EditText petColor;

    private ProgressDialog progressDialog;

    private StorageReference storageReference;
    private DatabaseReference databaseReference;
    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_pet);

        storageReference = FirebaseStorage.getInstance().getReference();
        databaseReference = FirebaseDatabase.getInstance().getReference().child("pets");

        progressDialog = new ProgressDialog(this);

        mImgSelect = findViewById(R.id.img_select);
        btnAdd = findViewById(R.id.add_pet_button);
        petName = findViewById(R.id.pet_name);
        petBreed = findViewById(R.id.pet_breed);
        petColor = findViewById(R.id.pet_color);

        mImgSelect.setOnClickListener(new View.OnClickListener() {
        	@Override
        	public void onClick(View v) {
        		Intent galleryIntent = new Intent(Intent.ACTION_GET_CONTENT);
                galleryIntent.setType("image/*");
                startActivityForResult(galleryIntent, GALLERY_REQUEST);
        	}
        });

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startPosting();
            }
        });
    }

    private void startPosting() {

        final String name = petName.getText().toString().trim();
        final String breed = petBreed.getText().toString().trim();
        final String color = petColor.getText().toString().trim();

        if (!TextUtils.isEmpty(name) && !TextUtils.isEmpty(breed)
                && !TextUtils.isEmpty(color) && mImageUri != null) {

	        progressDialog.setMessage("Please wait!");
	        progressDialog.show();

            //uploading process
            StorageReference filePath = storageReference.child("pet_images").child(mImageUri.getLastPathSegment());
            filePath.putFile(mImageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                    Uri downloadUrl = taskSnapshot.getDownloadUrl();
                    Toast.makeText(AddPetActivity.this, "Succesfully Posted!", Toast.LENGTH_SHORT).show();

                    SharedPreferences sp = getSharedPreferences(getPackageName(), Activity.MODE_PRIVATE);
                    String userUid = sp.getString("USER_UID", null);

                    DatabaseReference newPet = databaseReference.push();
                    newPet.child("owner").setValue(userUid);
                    newPet.child("name").setValue(name);
                    newPet.child("breed").setValue(breed);
                    newPet.child("color").setValue(color);
                    newPet.child("image").setValue(downloadUrl.toString());

                    progressDialog.dismiss();

                    Intent i = new Intent(AddPetActivity.this, HomeActivity.class);
                    startActivity(i);
                    finish();
                }
            }).addOnFailureListener(new OnFailureListener() {
				@Override
				public void onFailure(@NonNull Exception exception) {
        			Toast.makeText(AddPetActivity.this, "Error: " + exception.getMessage(), Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
			    }
			});
        } else {
        	Toast.makeText(AddPetActivity.this, "Input all fields", Toast.LENGTH_SHORT).show();
            progressDialog.dismiss();
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == GALLERY_REQUEST && resultCode == RESULT_OK){
            mImageUri = data.getData();
            mImgSelect.setImageURI(mImageUri);
        }
    }

}
