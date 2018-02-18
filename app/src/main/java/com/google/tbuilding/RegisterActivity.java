package com.google.tbuilding;

import android.app.*;
import android.content.Intent;
import android.os.*;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class RegisterActivity extends Activity {
    private EditText mEmailInput;
    private EditText mPasswordInput;
    private EditText mVerifyPasswordInput;
    private EditText mLastNameInput;
    private EditText mFirstNameInput;
    private EditText mAgeInput;
    private EditText mGenderInput;
    private EditText mBirthdayInput;
    private View progressView;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        setReferences();

        mAuth = FirebaseAuth.getInstance();

        Button registerButton = findViewById(R.id.reg_btn);
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = mEmailInput.getText().toString().trim();
                String password = mPasswordInput.getText().toString().trim();
                String verificationPassword = mVerifyPasswordInput.getText().toString().trim();

                if (TextUtils.isEmpty(email)) {
                    mEmailInput.setError("Required");
                    mEmailInput.requestFocus();
                    return;
                }
                if (TextUtils.isEmpty(password)) {
                    mPasswordInput.setError("Required");
                    mPasswordInput.requestFocus();
                    return;
                }
                if (TextUtils.isEmpty(verificationPassword)) {
                    mVerifyPasswordInput.setError("Required");
                    mVerifyPasswordInput.requestFocus();
                    return;
                }
                if (!password.equals(verificationPassword)) {
                    mVerifyPasswordInput.setError("Passwords does not match");
                    mVerifyPasswordInput.requestFocus();
                    return;
                }

                progressView.setVisibility(View.VISIBLE);

                mAuth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(RegisterActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                Toast.makeText(RegisterActivity.this, "createUserWithEmail:onComplete:" + task.isSuccessful(), Toast.LENGTH_SHORT).show();
                                progressView.setVisibility(View.GONE);

                                if (!task.isSuccessful()) {
                                    Toast.makeText(RegisterActivity.this, "Authentication failed." + task.getException(),
                                            Toast.LENGTH_SHORT).show();
                                } else {
                                    startActivity(new Intent(RegisterActivity.this, SignInActivity.class));
                                    finish();
                                }
                            }
                        });

            }
        });
    }

    private void setReferences() {
        mEmailInput = findViewById(R.id.reg_email);
        mPasswordInput = findViewById(R.id.reg_pass);
        mVerifyPasswordInput = findViewById(R.id.reg_pass_verify);
        mLastNameInput = findViewById(R.id.reg_lname);
        mFirstNameInput = findViewById(R.id.reg_fname);
        mAgeInput = findViewById(R.id.reg_age);
        mGenderInput = findViewById(R.id.reg_gender);
        mBirthdayInput = findViewById(R.id.reg_bday);
        progressView = findViewById(R.id.progress_view);
    }

}
