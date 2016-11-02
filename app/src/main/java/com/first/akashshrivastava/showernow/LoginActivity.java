package com.first.akashshrivastava.showernow;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
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

/**
 * Created by akashshrivastava on 31/07/16.
 */
public class LoginActivity extends Activity {

    EditText mlogin_email;
    EditText mlogin_password;
    Button mlogin_button;

    private ProgressDialog mProgressDialog;

    private FirebaseAuth mFirebaseAuth;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_fragment);

        //Firebase Auth initialization
        mFirebaseAuth = FirebaseAuth.getInstance();


        //PrograssDialog obj
        mProgressDialog = new ProgressDialog(this);

        mlogin_email = (EditText) findViewById(R.id.login_email);
        mlogin_password = (EditText) findViewById(R.id.login_password);
        mlogin_button = (Button) findViewById(R.id.login_button);

        mlogin_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //validate();

                if (validate()) {
                    String email = mlogin_email.getText().toString().trim();
                    String password = mlogin_password.getText().toString().trim();

                    mFirebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                Intent i = new Intent(LoginActivity.this, MainActivity.class);
                                startActivity(i);
                                finish();
                            } else {
                                Toast.makeText(LoginActivity.this, "Invalid email or password", Toast.LENGTH_LONG).show();
                            }
                        }
                    });
                    //At this point, all validations are OKAY!
              /* mProgressDialog.setMessage("Logging in, Please wait :)");
                mProgressDialog.show();

                mFirebaseAuth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener((Executor) getApplicationContext(), new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    //User is registered with success
                                    Toast.makeText(getApplicationContext(), "User Registered", Toast.LENGTH_LONG).show();

                                    //Call profile activity here..
                                } else {
                                    Toast.makeText(getApplicationContext(), "Failed to register", Toast.LENGTH_LONG).show();


                                }
                            }
                        }); */

                   /* Intent i = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(i);*/

                }//if bracket
            }
        });


        if (savedInstanceState == null) {

            Log.d("First ", "onCreate FIRST TIME");
        } else {
            Log.d("Next", "onCreate SUBSEQUENT TIME");
        }

        TextView mloginAct = (TextView) findViewById(R.id.link_signup);

        mloginAct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("ONCLICK", "ONCLICK!");
                Intent i = new Intent(LoginActivity.this, SignupActivity.class);
                startActivity(i);
                finish();

            }
        });


    }


    @Override
    public void onStart() {
        super.onStart();
        Log.d("On Start", "onStart");

    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d("OnResume", "onResume");

    }

    @Override
    public void onPause() {
        super.onPause();
        Log.d("On Pause", "onPause");

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.d("On save instance state", "onSaveInstanceState");

    }

    @Override
    public void onStop() {
        super.onStop();
        Log.d("On stop", "onStop");

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d("on destroy", "onDestroy");

    }

    public boolean validate() {
        boolean valid = true;

        String email = mlogin_email.getText().toString();
        String password = mlogin_password.getText().toString();


        if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            mlogin_email.setError("Enter a valid email address");
            valid = false;
        } else {
            mlogin_email.setError(null);
        }

        if (password.isEmpty() || password.length() < 4 || password.length() > 10) {
            mlogin_password.setError("Between 4 and 10 alphanumeric characters");
            valid = false;
        } else {
            mlogin_password.setError(null);
        }

        return valid;
    }

}
