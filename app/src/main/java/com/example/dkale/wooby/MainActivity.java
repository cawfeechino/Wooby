package com.example.dkale.wooby;

import android.media.MediaPlayer;
import android.nfc.Tag;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import static com.google.firebase.auth.FirebaseAuth.getInstance;

public class MainActivity extends AppCompatActivity {

    FirebaseApp mApp;
    FirebaseAuth mAuth;
    FirebaseAuth.AuthStateListener mAuthListener;
    final String TAG = "Firebase Status";
    String mDisplayname;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initFirebase();
    }

    private void initFirebase(){
        mApp = FirebaseApp.getInstance();
        mAuth = FirebaseAuth.getInstance(mApp);
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = mAuth.getCurrentUser();
                if(user != null){
                    Log.e(TAG,"User is valid. Email: " + user.getEmail() + " Display Name: " + user.getDisplayName());
                    mDisplayname = user.getDisplayName();
                }
                else{
                    Log.e(TAG,"There is no valid user logged on");
                    mDisplayname = "Null";

                }
            }
        };
        mAuth.addAuthStateListener(mAuthListener);
    }
}
