package com.uploading;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;

public class GoogleLogin extends AppCompatActivity implements View.OnClickListener, GoogleApiClient.OnConnectionFailedListener {


    private LinearLayout profile_section;
    private SignInButton sign_in;
    private TextView name, email;
    private GoogleApiClient googleApiClient;
    private static final int REQ_CODE = 9001;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_google_login);

        profile_section = (LinearLayout) findViewById(R.id.profile_section);
        sign_in = (SignInButton) findViewById(R.id.btn_login);
        name = (TextView) findViewById(R.id.name);
        email = (TextView) findViewById(R.id.email);

        sign_in.setOnClickListener(this);

        profile_section.setVisibility(View.GONE);

        GoogleSignInOptions signInOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build();
        googleApiClient = new GoogleApiClient.Builder(this).enableAutoManage(this,this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, signInOptions).build();
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.btn_login:
                signIn();
                break;
        }
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {


    }

    private void signIn() {

        Intent intent = Auth.GoogleSignInApi.getSignInIntent(googleApiClient);
        startActivityForResult(intent, REQ_CODE);

    }

    private void sign_out() {

        Auth.GoogleSignInApi.signOut(googleApiClient).setResultCallback(new ResultCallback<Status>() {
            @Override
            public void onResult(@NonNull Status status) {
                updateUI(false);
            }
        });

    }

    private void handleResult(GoogleSignInResult result) {

        if (result.isSuccess()){
            GoogleSignInAccount account = result.getSignInAccount();
            String s_name = account.getDisplayName();
            String s_email = account.getEmail();
            name.setText(s_name);
            email.setText(s_email);
            updateUI(true);
        }else {
            updateUI(false);
        }

    }

    private void updateUI(boolean isLogin) {

        if (isLogin){
            profile_section.setVisibility(View.VISIBLE);
            sign_in.setVisibility(View.GONE);

        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQ_CODE){
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            handleResult(result);

        }
    }

//    @Override
//    protected void onStart() {
//        super.onStart();
//        OptionalPendingResult<GoogleSignInResult> optionalPendingResult = Auth.GoogleSignInApi.silentSignIn(googleApiClient);
//        if (optionalPendingResult.isDone()){
//            GoogleSignInResult result = optionalPendingResult.get();
//            handleResult(result);
//        }else {
//            progressDialog = new ProgressDialog(this);
//            progressDialog.show();
//            optionalPendingResult.setResultCallback(new ResultCallback<GoogleSignInResult>() {
//                @Override
//                public void onResult(@NonNull GoogleSignInResult result) {
//                    progressDialog.hide();
//                    handleResult(result);
//                }
//            });
//
//        }
//    }




}
