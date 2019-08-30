package com.stcodesapp.noteit.tasks.networkingTasks;

import android.app.Activity;
import android.content.Intent;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.googleapis.extensions.android.gms.auth.GoogleAccountCredential;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.DriveScopes;
import com.stcodesapp.noteit.R;
import com.stcodesapp.noteit.common.Logger;
import com.stcodesapp.noteit.constants.RequestCode;

import java.util.Collections;

public class GoogleSignInHandlingTask
{
    private Activity activity;
    private GoogleSignInClient googleSignInClient;
    private GoogleSignInAccount googleSignInAccount;
    private Drive driveService;
    private boolean isAlreadySignedIn = false;

    public GoogleSignInHandlingTask(Activity activity) {
        this.activity = activity;
    }

    public void createGoogleSignInAccount()
    {
        GoogleSignInOptions googleSignInOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .requestIdToken(activity.getResources().getString(R.string.google_sign_in_server_client_id))
                .build();
        googleSignInClient = GoogleSignIn.getClient(activity, googleSignInOptions);
        googleSignInAccount = GoogleSignIn.getLastSignedInAccount(activity);
        if(googleSignInAccount!=null)
        {
            isAlreadySignedIn = true;
            setupGoogleDriveCredentials();
        }
    }

    private void setupGoogleDriveCredentials()
    {
        GoogleAccountCredential credential =
                GoogleAccountCredential.usingOAuth2(
                        activity, Collections.singleton(DriveScopes.DRIVE));
        credential.setSelectedAccount(googleSignInAccount.getAccount());
        driveService =
                new Drive.Builder(
                        AndroidHttp.newCompatibleTransport(),
                        new GsonFactory(),
                        credential)
                        .setApplicationName("JustAName")
                        .build();
    }

    public void startGoogleSignInFlow()
    {
        Intent signInIntent = googleSignInClient.getSignInIntent();
        activity.startActivityForResult(signInIntent, RequestCode.GOOGLE_SIGN_IN_REQUEST);
    }

    public void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try
        {
            googleSignInAccount = completedTask.getResult(ApiException.class);
            setupGoogleDriveCredentials();
            isAlreadySignedIn = true;
        } catch (Exception e)
        {
            Logger.logMessage("Exception","inHandleSignInResult");


        }
    }

    public Drive getDriveService() {
        return driveService;
    }

    public GoogleSignInAccount getGoogleSignInAccount() {
        return googleSignInAccount;
    }

    public GoogleSignInClient getGoogleSignInClient() {
        return googleSignInClient;
    }

    public boolean isAlreadySignedIn() {
        return isAlreadySignedIn;
    }
}
