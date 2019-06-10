package com.stcodesapp.noteit.ui.views.screenViews.activityScreenView;

import android.os.Build;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.stcodesapp.noteit.R;
import com.stcodesapp.noteit.constants.Constants;
import com.stcodesapp.noteit.ui.views.baseScreens.BaseScreenView;

public class PrivacyPolicyScreenView extends BaseScreenView {

    private WebView policyWebView;
    private ProgressBar loadingView;



    public PrivacyPolicyScreenView(LayoutInflater layoutInflater, @Nullable ViewGroup parent)
    {
        setRootView(layoutInflater.inflate(R.layout.privacy_policy_layout,parent,false));
        inflateUIElements();
    }


    @Override
    public void inflateUIElements() {
        policyWebView = findViewById(R.id.policy_webview);
        loadingView = findViewById(R.id.loading_view);
        initWebView();


    }

    private void initWebView()
    {
        policyWebView.setWebViewClient(new WebViewClient(){
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
//                Log.i(TAG, "Processing webview url click...");
                view.loadUrl(url);
                return true;
            }

            public void onPageFinished(WebView view, String url) {
//                Log.i(TAG, "Finished loading URL: " + url);
                loadingView.setVisibility(View.GONE);
            }

            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                /*Log.e(TAG, "Error: " + description);
                Toast.makeText(Main.this, "Oh no! " + description, Toast.LENGTH_SHORT).show();
                alertDialog.setTitle("Error");
                alertDialog.setMessage(description);
                alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        return;
                    }
                });
                alertDialog.show();*/
            }
        });
        policyWebView.loadUrl(Constants.PRIVACY_POLICY_URL);
        policyWebView.getSettings().setJavaScriptEnabled(true);

    }
}
