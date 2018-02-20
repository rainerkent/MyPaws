package com.google.tbuilding;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignupActivity extends AppCompatActivity  {
    Spinner sgender;
    private EditText inputEmail, inputPassword, inputbday, inputlname, inputfname, inputage;
    private Button btnSignIn, btnSignUp;
    private FirebaseAuth auth;
    private FirebaseAuth firebaseAuth;
    DatabaseReference databaseReference;
    private ProgressDialog progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        auth = FirebaseAuth.getInstance();
        progressDialog = new ProgressDialog(this);
        btnSignIn = (Button) findViewById(R.id.sign_in_button);
        btnSignUp = (Button) findViewById(R.id.sign_up_button);
        inputEmail = (EditText) findViewById(R.id.email);
        inputPassword = (EditText) findViewById(R.id.password);
        inputbday = (EditText) findViewById(R.id.biday);
        inputlname = (EditText) findViewById(R.id.lastname);
        inputfname = (EditText) findViewById(R.id.firstname);
        sgender = (Spinner) findViewById(R.id.spingender);
        inputage = (EditText) findViewById(R.id.age);
        databaseReference = FirebaseDatabase.getInstance().getReference().child("All Users");


        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                finish();
            }

        });

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startRegister();

            }
        });
    }

    private void startRegister() {
        final String email = inputEmail.getText().toString().trim();
        final String password = inputPassword.getText().toString().trim();
        final String firstname= inputfname.getText().toString().trim();
        final String lastname = inputlname.getText().toString().trim();
        final String age = inputage.getText().toString().trim();
        final  String gender = sgender.getSelectedItem().toString();
        final String birthday = inputbday.getText().toString().trim();

        if (!TextUtils.isEmpty(email) && !TextUtils.isEmpty(password) &&!TextUtils.isEmpty(firstname) && !TextUtils.isEmpty(lastname) && !TextUtils.isEmpty(birthday) && !TextUtils.isEmpty(age)) {


            ProgressDialog.show(this, "Registering User...", "Registering User...");

            auth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(SignupActivity.this, "Register Successful", Toast.LENGTH_SHORT).show();

                              String User = firebaseAuth.getCurrentUser().getUid();

                               DatabaseReference currentUserDB = databaseReference.child(User);
                                currentUserDB.child("Firstname").setValue(firstname);
                                currentUserDB.child("Lastname").setValue(lastname);
                               currentUserDB.child("Email").setValue(email);
                                currentUserDB.child("Password").setValue(password);
                                currentUserDB.child("Age").setValue(age);
                                currentUserDB.child("Birthday").setValue(birthday);
                               currentUserDB.child("Gender").setValue(gender);

                                progressDialog.hide();
                            } else
                                Toast.makeText(SignupActivity.this, "error registering user", Toast.LENGTH_SHORT).show();

                        }
                    });
        } else {
            Toast.makeText(SignupActivity.this, "Some Fields are Empty. Please Fill All Fields" , Toast.LENGTH_SHORT).show();
        }
    }




}


