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

public class SignUpActivity extends AppCompatActivity  {
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
        databaseReference = FirebaseDatabase.getInstance().getReference().child("users");


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
        final String gender = sgender.getSelectedItem().toString();
        final String birthday = inputbday.getText().toString().trim();

        if (!TextUtils.isEmpty(email) && !TextUtils.isEmpty(password) &&!TextUtils.isEmpty(firstname) && !TextUtils.isEmpty(lastname) && !TextUtils.isEmpty(birthday) && !TextUtils.isEmpty(age)) {

            progressDialog = ProgressDialog.show(this, "Registering User", "Please wait...");

            auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(SignUpActivity.this, "Register Successful", Toast.LENGTH_SHORT).show();

                            String User = auth.getCurrentUser().getUid();

                            DatabaseReference currentUserDB = databaseReference.child(User);
                            currentUserDB.child("firstname").setValue(firstname);
                            currentUserDB.child("lastname").setValue(lastname);
                            currentUserDB.child("email").setValue(email);
                            // currentUserDB.child("password").setValue(password);
                            currentUserDB.child("age").setValue(age);
                            currentUserDB.child("birthday").setValue(birthday);
                            currentUserDB.child("gender").setValue(gender);

                            finish();
                        } else {
                            Toast.makeText(SignUpActivity.this, "error registering user", Toast.LENGTH_SHORT).show();
                        }

                        progressDialog.hide();
                    }
                });
        } else {
            Toast.makeText(SignUpActivity.this, "Some Fields are Empty. Please Fill All Fields" , Toast.LENGTH_SHORT).show();
        }
    }
}


