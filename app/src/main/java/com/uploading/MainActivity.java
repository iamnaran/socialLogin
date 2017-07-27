package com.uploading;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.api.client.googleapis.extensions.android.gms.auth.GoogleAccountCredential;
import com.uploading.util.VideoData;

public class MainActivity extends AppCompatActivity {

    private TextView filePath , loginName;
    private Button pickButton, login_Button;
    GoogleAccountCredential credential;
    private String mChosenAccountName;
    private Uri mFileURI = null;
    private VideoData mVideoData;
    private ProgressDialog progressDialog;
    private GoogleApiClient googleApiClient;
    private static final int REQ_CODE = 9001;

    public static final String ACCOUNT_KEY = "accountName";

    private static final int RESULT_PICK_IMAGE_CROP = 4;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        filePath = (TextView) findViewById(R.id.file_path);
        loginName = (TextView) findViewById(R.id.loginName);
        pickButton = (Button) findViewById(R.id.pick_button);
        login_Button = (Button) findViewById(R.id.login_Button);


        login_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, GoogleLogin.class);
                startActivity(intent);
            }
        });


        pickButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                pickFileFun();
            }
        });

    }


    private void pickFileFun() {

        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("video/*");
        startActivityForResult(intent, RESULT_PICK_IMAGE_CROP);

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQ_CODE){
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            handleResult(result);

        }
    }
    private void handleResult(GoogleSignInResult result) {

        if (result.isSuccess()){
            GoogleSignInAccount account = result.getSignInAccount();
            String s_name = account.getDisplayName();
            String s_email = account.getEmail();

            loginName.setText(s_name+ s_email);

        }else {
            loginName.setText("null");
        }

    }


    public void uploadVideo(View view) {

    }

    private void loadAccount() {
        SharedPreferences sp = PreferenceManager
                .getDefaultSharedPreferences(this);
        mChosenAccountName = sp.getString(MainActivity.ACCOUNT_KEY, null);
        invalidateOptionsMenu();
    }






}
