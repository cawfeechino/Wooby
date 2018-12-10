package com.example.dkale.wooby;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.FirebaseDatabase;

import org.w3c.dom.Text;

public class SignIn extends AppCompatActivity {
    final String TAG = "FirebaseTestSignIn";
    ImageView image;
    FirebaseApp mApp;
    FirebaseAuth mAuth;
    FirebaseAuth.AuthStateListener mAuthStateListener;

    EditText mEmailText;
    EditText mPasswordText;
    EditText mConfirmPasswordText;

    Button mLoginButton;
    TextView mRegisterText;
    TextView invalidUser;
    TextView passwordConfirm;

    boolean mLoginInProgress = false;
    boolean mRegisterInProgress = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        image = (ImageView) findViewById(R.id.aniImage);
        image.setImageResource(R.drawable.watchdog_man);
        initControls();
        initListeners();
        initFirebase();
    }

    private void initControls() {
        mEmailText = (EditText) findViewById(R.id.emailEdit);
        mPasswordText = (EditText) findViewById(R.id.passwordEdit);
        mConfirmPasswordText = (EditText) findViewById(R.id.confirmPasswordEdit);
        invalidUser = (TextView) findViewById(R.id.invalidUserView);
        mLoginButton = (Button) findViewById(R.id.logonButton);
        mRegisterText = (TextView) findViewById(R.id.registerText);
        passwordConfirm = (TextView) findViewById(R.id.passwordConfirmText);

        mEmailText.setVisibility(View.GONE);
        mPasswordText.setVisibility(View.GONE);
        mConfirmPasswordText.setVisibility(View.GONE);
        invalidUser.setVisibility(View.GONE);
        passwordConfirm.setVisibility(View.GONE);
    }

    private void initListeners() {

        mLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mRegisterInProgress = false;

                if (!mLoginInProgress) {

                    // Login button has been hit
                    mEmailText.setVisibility(View.VISIBLE);
                    mPasswordText.setVisibility(View.VISIBLE);
                    mConfirmPasswordText.setVisibility(View.GONE);

                    mLoginInProgress = true;
                } else {

                    ;// Login button has been hit again
                    String email = mEmailText.getText().toString();
                    String password = mPasswordText.getText().toString();
                    if(email.matches("") || password.matches("")){
                        if(email.matches("")){
                            mEmailText.setHint("Input an Email");
                        }
                        if(password.matches("")){
                            mPasswordText.setHint("Input a Password");
                        }
                    }
                    else{
                        loginUser(email, password);
                    }

                }
            }
        });

        mRegisterText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mLoginInProgress = false;

                if (!mRegisterInProgress) {

                    // Register button has been hit
                    mEmailText.setVisibility(View.VISIBLE);
                    mPasswordText.setVisibility(View.VISIBLE);
                    mConfirmPasswordText.setVisibility(View.VISIBLE);
                    mLoginButton.setVisibility(View.GONE);
                    mRegisterInProgress = true;
                } else {

                    // Register button has been hit again
                    String email = mEmailText.getText().toString();
                    String password = mPasswordText.getText().toString();
                    String confirmPassword = mConfirmPasswordText.getText().toString();
                    // Validate password/password confirm here
                    if(email.matches("") || password.matches("") || confirmPassword.matches("")){
                        if(email.matches("")){
                            mEmailText.setHint("Input an Email");
                        }
                        if(password.matches("")){
                            mPasswordText.setHint("Input a Password");
                        }
                        if(confirmPassword.matches("")){
                            mConfirmPasswordText.setHint("Confirm Password");
                        }
                    }
                    else {
                        if (password.equals(confirmPassword)) {
                            passwordConfirm.setVisibility(View.GONE);
                            registerUser(email, password);
                        } else {
                            passwordConfirm.setVisibility(View.VISIBLE);
                        }
                    }
                }
            }
        });
    }

    private void initFirebase() {

        mApp = FirebaseApp.getInstance();
        mAuth = FirebaseAuth.getInstance(mApp);

        mAuthStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {

                FirebaseUser user = firebaseAuth.getCurrentUser();

                if (user != null) {
                    Log.e(TAG, "SignIn : Valid current user : email [" + user.getEmail().toString() + "]");
                    mLoginInProgress = false;
                    mRegisterInProgress = false;

                    finishActivity();
                }
                else
                    Log.e(TAG, "SignIn : No Current user");
            }
        };
        mAuth.addAuthStateListener(mAuthStateListener);
    }

    private void registerUser(String email, String password) {

        OnCompleteListener<AuthResult> complete = new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if (task.isSuccessful())
                    Log.e(TAG, "SignIn : User registered ");
                else
                    Log.e(TAG, "SignIn : User registration response, but failed ");
            }
        };

        OnFailureListener failure = new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.e(TAG,"SignIn : Register user failure");
            }
        };

        Log.e(TAG, "SignIn : Registering : eMail [" + email + "] password [" + password + "]");
        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(complete).addOnFailureListener(failure);
    }

    private void loginUser(String email, String password) {

        OnCompleteListener<AuthResult> complete = new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if (task.isSuccessful()) {
                    Log.e(TAG, "SignIn : User logged on ");
                }
                else {
                    Log.e(TAG, "SignIn : User log on response, but failed ");
                    invalidUser.setVisibility(View.VISIBLE);
                }
            }
        };

        OnFailureListener failure = new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.e(TAG,"SignIn : Log on user failure");
            }
        };

        Log.e(TAG, "SignIn : Logging in : eMail [" + email + "] password [" + password + "]");
        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(complete).addOnFailureListener(failure);
    }

    private void finishActivity() {

        Log.e(TAG,"SignIn Returning to main activity");
        mAuth.removeAuthStateListener(mAuthStateListener);

        Intent returningIntent = new Intent();
        returningIntent.putExtra("Email", mEmailText.toString());
        setResult(RESULT_OK, returningIntent);

        finish();
    }}