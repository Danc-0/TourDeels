package com.example.designtest;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import static android.text.TextUtils.isEmpty;

public class RegisterActivity extends AppCompatActivity {

    public static final String DOMAIN_NAME = "danc.ca";


    EditText mUserName;
    EditText mEmail;
    EditText mPassword;
    EditText mConfirmPassword;
    Button mRegister;
    TextView TvLogin;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mUserName = findViewById(R.id.et_user_name);
        mEmail = findViewById(R.id.et_email_address);
        mPassword = findViewById(R.id.et_password);
        mConfirmPassword = findViewById(R.id.et_confirm_password);
        mRegister = findViewById(R.id.btn_register);

        mAuth = FirebaseAuth.getInstance();

        TvLogin = findViewById(R.id.tv_login);

        TvLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });

        mRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (!isEmpty(mUserName.getText().toString())
                        && !isEmpty(mEmail.getText().toString())
                        && !isEmpty(mPassword.getText().toString())
                        && !isEmpty(mConfirmPassword.getText().toString())) {

                    if (isValidDomain(mEmail.getText().toString())) {

                    if (doStringMatch(mPassword.getText().toString(), mConfirmPassword.getText().toString())) {
                        registerNewEmail(mEmail.getText().toString(),mPassword.getText().toString());


                        } else {
                            Toast.makeText(RegisterActivity.this, "Passwords do not match", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(RegisterActivity.this, "Please enter a valid email", Toast.LENGTH_SHORT).show();
                    }

                } else {
                    Toast.makeText(RegisterActivity.this, "You must fill all fields", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void sendEmailVerification() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();


        if (user != null) {
            user.sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()) {
                        Toast.makeText(RegisterActivity.this, "Verification sent", Toast.LENGTH_SHORT).show();
                    }else {
                        Toast.makeText(RegisterActivity.this, "Couldn't send verification email", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }

    }
    private void registerNewEmail(String email, String password) {

        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(this,
                new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                Log.d("isSuccessful","isTaskSuccessful " + task.isSuccessful());
                sendEmailVerification();

                if (task.isSuccessful()) {
                    Log.d("Onclick","Auth State " + mAuth.getCurrentUser().getUid());
                }else {
                    Toast.makeText(RegisterActivity.this, "Unable to Register", Toast.LENGTH_SHORT).show();
                    mAuth.signOut();
                }
            }
        });
}

    private boolean doStringMatch(String Pass1, String Pass2) {
        return Pass1.equals(Pass2);
    }

    private boolean isValidDomain(String email) {
        Log.d("isValidDomain","Verifying Email Has correct domain: " + email);
        String domain = email.substring(email.indexOf('@') + 1).toLowerCase();
        Log.d("isValidDomain: ", "User's domain" + domain);
        return domain.equals(DOMAIN_NAME);
    }
}


            
