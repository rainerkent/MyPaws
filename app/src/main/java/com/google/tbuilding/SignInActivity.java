package com.google.tbuilding;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class SignInActivity extends AppCompatActivity {
    Button blog;
    Button breg;
    EditText etuser;
    EditText etpass;
    View progressView;

    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        setReferences();

        auth = FirebaseAuth.getInstance();

        blog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = etuser.getText().toString().trim();
                final String password = etpass.getText().toString().trim();

                if (TextUtils.isEmpty(email)) {
                    etuser.setError("Required");
                    etuser.requestFocus();
                    return;
                }
                if (TextUtils.isEmpty(password)) {
                    etpass.setError("Required");
                    etpass.requestFocus();
                    return;
                }

                progressView.setVisibility(View.VISIBLE);

                // if (etuser.getText().toString().equals("admin") &&
                //        etpass.getText().toString().equals("admin")) {
                //    Intent loginIntent = new Intent(getApplicationContext(), HomeActivity.class);
                //    startActivity(loginIntent);
                //    Toast.makeText(getApplicationContext(),
                //            "Logging in .......", Toast.LENGTH_SHORT).show();

                // } else {
                //    Toast.makeText(SignInActivity.this, "Wrong Credentials", Toast.LENGTH_SHORT).show();
                // }
                auth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(SignInActivity.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            progressView.setVisibility(View.GONE);
                            if (!task.isSuccessful()) {
                                // there was an error
                                if (password.length() < 6) {
                                    etpass.setError("Minimum of 6 characters");
                                } else {
                                    Toast.makeText(SignInActivity.this, "Authentication Failed", Toast.LENGTH_LONG).show();
                                }
                            } else {
                                Intent intent = new Intent(SignInActivity.this, HomeActivity.class);
                                startActivity(intent);
                                finish();
                            }
                        }
                    });
            }
        });

        breg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent registerIntent = new Intent(getApplicationContext(), SignUpActivity.class);
                startActivity(registerIntent);
            }
        });
    }

    public void setReferences() {
        etuser = findViewById(R.id.logintext);
        etpass = findViewById(R.id.passwordtext);
        blog = findViewById(R.id.signin);
        breg = findViewById(R.id.signup);
        progressView = findViewById(R.id.progress_view);
    }
}
